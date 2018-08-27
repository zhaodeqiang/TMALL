package tmall.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tmall.pojo.Category;
import tmall.pojo.CategoryExample;
import tmall.pojo.base.BaseExample;
import tmall.pojo.base.BasePOJO;
import tmall.redis.RedisCacheUtil;
import tmall.service.BaseService;
import tmall.util.Pagination;

import javax.annotation.Resource;
import java.rmi.NoSuchObjectException;
import java.util.Date;
import java.util.List;


/**
 * 该类继承 Service4DAOImpl，会自动识别继承子类的前缀，自动预装载表（使用createCriteria()函数）
 * 该类是 BaseService 的实现，接口要求见BaseService
 *
 * @see Service4DAOImpl
 * @see BaseService
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, readOnly = false)
@SuppressWarnings("ALL")
public class BaseServiceImpl<M, E> extends Service4DAOImpl<M, E> implements BaseService {
    @Resource(name = "redisCacheUtil")
    private RedisCacheUtil redisCache;

    @Override
    public List list(Object... paramAndObjects) throws Exception {
        //先查询redis缓存
        BaseExample example = getExample();//具体的Example就看BaseServiceImpl<M, E>中传入的泛型E
        if (example instanceof CategoryExample) {
            String json = redisCache.hGet("category", "cgy");
            if (json != null) {
                return JSON.parseArray(json,Category.class);
            }
        }
        Object criteria = example.createCriteria();
        if (paramAndObjects.length % 2 != 0) {
            return null;
        }
        Pagination pagination = null;
        //默认按id降序排序
        example.setOrderByClause("id desc");
        //默认对查询进行两次遍历查询
        int depth = 2;
        /*传入可变参数(paramAndObjects)："depth",1,"pagination",pagination,"recommend_gt",0, "order", "recommend desc, id desc"
        参数表示查询推荐度大于0的商品
         */

        for (int i = 0; i < paramAndObjects.length; i += 2) {
            if (paramAndObjects[i].equals("order") && paramAndObjects[i + 1] instanceof String) {
                example.setOrderByClause(paramAndObjects[i + 1].toString());
                continue;//跳出本次循环，执行下一次循环
            }
            if (paramAndObjects[i].equals("depth") && paramAndObjects[i + 1] instanceof Integer) {
                depth = (Integer) paramAndObjects[i + 1];
                continue;
            }
            if (paramAndObjects[i].toString().contains("_like") && paramAndObjects[i + 1] instanceof String) {
                String column = StringUtils.replace(paramAndObjects[i].toString(), "_like", "");
                criteria.getClass()
                        .getMethod("and" + StringUtils.capitalize(column) + "Like", String.class)
                        .invoke(criteria, "%" + paramAndObjects[i + 1].toString() + "%");
                continue;
            }
            if (paramAndObjects[i].toString().contains("_gt") && paramAndObjects[i + 1] instanceof Integer) {
                String column = StringUtils.replace(paramAndObjects[i].toString(), "_gt", "");//将_gt替换为空格
                criteria.getClass()
                        .getMethod("and" + StringUtils.capitalize(column) + "GreaterThan", Integer.class)//StringUtils.capitalize:首字母大写
                        .invoke(criteria, paramAndObjects[i + 1]);
                continue;
            }
            if (paramAndObjects[i].equals("pagination") && paramAndObjects[i + 1] instanceof Pagination) {
                pagination = (Pagination) paramAndObjects[i + 1];
                continue;
            }

            if (paramAndObjects[i + 1] != null) {
                criteria.getClass()//调用对应的Example类中的方法
                        .getMethod("and" + StringUtils.capitalize(paramAndObjects[i].toString()) + "EqualTo"
                                , paramAndObjects[i + 1].getClass())
                        .invoke(criteria, paramAndObjects[i + 1]);
            }
        }
        List list;
        if (pagination != null) {
            PageHelper.offsetPage(pagination.getStart(), pagination.getCount());
            list = mapper.selectByExample(example, depth);
            pagination.setTotal((int) new PageInfo<>(list).getTotal());
        } else {
            list = mapper.selectByExample(example, depth);
        }
        //将结果放入redis缓存
        if (example instanceof CategoryExample) {
            redisCache.hSet("category", "cgy", JSON.toJSONString(list));
        }
        return list;
    }

    @Override
    public BasePOJO getOne(Object... paramAndObjects) {
        BasePOJO object = null;
        try {
            object = (BasePOJO) list(paramAndObjects).get(0);//取List集合中的第一个，(查出来的值在List中也只有一个)
        } catch (Exception ignored) {
        }
        return object;
    }

    @Override
    public Integer add(BasePOJO object) throws Exception {
        return mapper.insert(object);
    }

    @Override
    public void update(BasePOJO object) throws Exception {
        mapper.updateByPrimaryKey(object);
    }

    @Override
    public int updateUser(BasePOJO object) throws Exception {
        return mapper.updateByPrimaryKeySelective(object);
    }

    @Override
    public void update(Integer[] ids, String[] values, String changeFiledName) throws Exception {
        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];
            String newValue = values[i];
            //从数据库获取旧对象
            Object objectFromDB = get(id);
            //把新值插入旧对象
            objectFromDB.getClass()
                    .getMethod("set" + StringUtils.capitalize(changeFiledName), newValue.getClass())
                    .invoke(objectFromDB, newValue);
            //更新旧对象
            update((BasePOJO) objectFromDB);
        }
    }

    @Override
    public BasePOJO get(int id) throws Exception {
        BasePOJO object = (BasePOJO) mapper.selectByPrimaryKey(id);
        if (object == null) {
            throw new NoSuchObjectException("访问的商品不存在或已经被删除！");
        }
        return object;
    }

    @Override
    public BasePOJO get(int id, int depth) throws Exception {
        BasePOJO object = (BasePOJO) mapper.selectByPrimaryKey(id, depth);
        if (object == null) {
            throw new NoSuchObjectException("访问的id不存在或已经被删除");
        }
        return object;
    }

    @Override
    public BasePOJO get(Class mapperInterface, int id) throws Exception {
        mapper = getMapper(mapperInterface);
        BasePOJO object = (BasePOJO) mapper.selectByPrimaryKey(id);
        return object;
    }

    /**
     * @see BaseService
     */
    @Override
    public void delete(BasePOJO object) throws Exception {
        object.setDeleteAt(new Date());
        mapper.updateByPrimaryKey(object);
    }
}