package tmall.realm;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import tmall.pojo.User;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginSuccess extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
        issueSuccessRedirect(request, response);
        Session session = subject.getSession();
        User user = (User) subject.getPrincipal();
        /*存到session中*/
        session.setAttribute("user", user);
        return false;
    }

    /**
    * @Author       ZDQ
    * @MethodName   issueSuccessRedirect  登录成功后的重定向方法
    * @Description  由购物车跳转登录重定向首页
    * @Param        
    * @Return       
    * @Exception    
    * @Date         2018/6/11 16:22
    */
    @Override
    protected void issueSuccessRedirect(ServletRequest request,
                                        ServletResponse response) throws Exception {
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        String superURL = null;
        if (savedRequest != null) {
            superURL = savedRequest.getRequestUrl();
            if (superURL == null || "".equals(superURL)) {
                superURL = getSuccessUrl();
            } else if (superURL.contains("/TMALL")) {
                superURL = superURL.replace("/TMALL", "");
            }
        } else {
            superURL = getSuccessUrl();// 默认为 "/"
        }
       // System.out.println(superURL);
        WebUtils.issueRedirect(request, response, superURL, null, true);
    }
}
