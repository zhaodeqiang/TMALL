package tmall.service.impl;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import tmall.mapper.UserMapper;
import tmall.pojo.User;
import tmall.pojo.UserExample;
import tmall.service.UserService;
import tmall.util.PasswordUtil;


@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserExample> implements UserService {
    @Override
    public boolean isExist(String username) throws Exception {
        return !list("username", username).isEmpty();
    }

    @Override
    public Integer add(User user) throws Exception {
        String rawPassword = user.getPassword();
        String salt = PasswordUtil.Salt();
        Md5Hash hash = new Md5Hash(rawPassword, salt, 2);//加盐并且加密两次
        String password = hash.toString();
        user.setPassword(password);
        user.setSalt(salt);
        return super.add(user);
    }

    /*  @Override
      public User get(String name, String password) {
          return (User) getOne("name", name, "password", PasswordUtil.encryptPassword(password), "order","id asc");
      }*/
    @Override
    public User get(String name) {
        return (User) getOne("username", name, "order", "id asc");
    }

    @Override
    public boolean updatePassword(User user) throws Exception {
        String rawPassword = user.getPassword();
        String salt = PasswordUtil.Salt();
        Md5Hash hash = new Md5Hash(rawPassword, salt, 2);//加盐并且加密两次
        String password = hash.toString();
        user.setPassword(password);
        user.setSalt(salt);
        return updateUser(user) > 0 ? true : false;
    }
}