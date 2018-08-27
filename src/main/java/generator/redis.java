package generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tmall.redis.RedisCacheUtil;

import java.util.Scanner;

//让项目运行于Spring环境
//@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
//@ContextConfiguration("classpath:applicationContext.xml")
public class redis {
   // @Autowired
    private RedisCacheUtil redisCache;
    private static String key;
    private static String field;
    private static String value;

    static {
        key = "tb_student";
        field = "stu_name";
        value = "一系列的关于student的信息！";
    }

    @Test
    public void testHset() {
        // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        // context.start();  不写也可以运行
        // redisCache = (RedisCacheUtil) context.getBean("redisCacheUtil");
        // redisCache.hset(key, field, value);
        //System.out.println("数据保存成功！");
        //String str = redisCache.hGet(key, field);
      //  System.out.println(str);

    }

}
