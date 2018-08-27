package tmall.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import tmall.pojo.User;
import tmall.service.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: ZDQ
 * @ClassName: loginRealm
 * @Description: 自定义realm用于登录认证和授权
 * @CreateDate: 2018/6/5 13:23
 * @UpdateUser: ZDQ
 * @UpdateDate: 2018/6/5 13:23
 * @UpdateRemark: TODO
 * @Version: 1.0
 */
public class LoginRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    public void setName(String name) {
        super.setName("loginRealm");
    }

    /*授权方法*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //User user = (User)getAvailablePrincipal(principals);  //第一种方法
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        if (user != null) {
            set.add(user.getGroup().name());//枚举类型
        }
        info.setRoles(set);
        return info;
    }

    /*认证方法*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        User user = userService.get(username);
        if (user == null) {
            return null;
        }
        String password = user.getPassword();
        String salt = user.getSalt();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user, password, ByteSource.Util.bytes(salt), this.getName());
        return simpleAuthenticationInfo;
    }
}
