package tmall.controller.front;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import  org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import tmall.annotation.Nullable;
import tmall.pojo.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("/")
public class UserFrontController extends FrontBaseController {
    @RequestMapping("register")
    public String register() {
        return "register";
    }

    @RequestMapping("registerAdd")
    public String registerAdd(User user) throws Exception {
        user.setRegisterdate(new Date());
        user.setGroup(User.Group.user);
        userService.add(user);
        return "registerSuccess";
    }

    //ajax校验表单访问
    @RequestMapping("checkUsername")
    public void checkUsername(String username, HttpServletResponse response) throws Exception {
        boolean isExist = userService.isExist(username.trim()); //不存在：false  存在：true
        String json = "{\"isExist\":" + isExist + "}";
        response.getWriter().write(json);
    }

    /*   @RequestMapping("loginIn")
       public String loginIn(String name, String password, Model model, HttpSession session, String refer) {
           User userFromDB = userService.get(name.trim());
           if (userFromDB == null) {
               String msg = "用户名错误，请重试！";
               model.addAttribute("msg", msg);
               return "login";
           } else if (!(userFromDB.getPassword().equals(new Md5Hash(password,userFromDB.getSalt(),2).toString()))) {//用!=会判断错误，判断的应该是地址，所以出错
               String msg = "密码错误，请重试！";
               model.addAttribute("msg", msg);
               return "login";
           }
           session.setAttribute("user", userFromDB);
           return "redirect:" + refer;
       }*/
    @RequestMapping("loginIn")
    public String loginIn(Model model, HttpServletRequest request) throws Exception {
        // 如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        // 根据shiro返回的异常类路径判断，抛出指定异常信息
        if (exceptionClassName != null) {
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                // 最终会抛给异常处理器
                model.addAttribute("msg", "会员名不存在！");
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                model.addAttribute("msg", "密码错误！");
            }
        }
        return "login";
    }

    @RequestMapping("forgetPassword")
    public String forgetPassword(int resetType) {
        if (resetType == 2) {
            return "forgetPassword";//转发，地址栏不变
        }
        return "resetOldPassword";
    }

    @RequestMapping("resetPassword")
    public String resetPassword(User user, Model model, String resetType, @Nullable String oldPassword) throws Exception {
        User userSelect = userService.get(user.getUsername());
        if (resetType != null && resetType.equals("forget")) {
            /*忘记密码*/
            if (userSelect != null) {
                if (!userSelect.getPhone().equals(user.getPhone())) {
                    model.addAttribute("fail", "手机号填写错误！");
                    return "forgetPassword";
                } else if (!userSelect.getEmail().equals(user.getEmail())) {
                    model.addAttribute("fail", "邮箱填写错误！");
                    return "forgetPassword";
                }
                user.setId(userSelect.getId());
                user.setGroup(userSelect.getGroup());
                boolean isSuccess = userService.updatePassword(user);
                if (isSuccess) {
                    return "resetSuccess";
                }
            }
        } else {
            /*修改密码*/
            if (userSelect != null && oldPassword != null) {
                String salt = userSelect.getSalt();
                if (!userSelect.getPassword().equals(new Md5Hash(oldPassword, salt, 2).toString())) {
                    model.addAttribute("fail", "旧密码输入错误！");
                    return "resetOldPassword";
                } else if (!userSelect.getPhone().equals(user.getPhone())) {
                    model.addAttribute("fail", "手机号填写错误！");
                    return "resetOldPassword";
                } else if (!userSelect.getEmail().equals(user.getEmail())) {
                    model.addAttribute("fail", "邮箱填写错误！");
                    return "resetOldPassword";
                }
                user.setId(userSelect.getId());
                user.setGroup(userSelect.getGroup());
                boolean isSuccess = userService.updatePassword(user);
                if (isSuccess) {
                    Subject subject= SecurityUtils.getSubject();
                    subject.getSession().removeAttribute("user");
                    return "resetSuccess";
                }
            }
        }
        return "redirect:error";
    }

    /* @RequestMapping("checkLogin")
    @ResponseBody
    public String checkLogin(HttpServletResponse response, HttpSession session) throws Exception {
        String msg = session.getAttribute("user") != null ? "success" : "fail";
        return msg;
    }*/


    /*@Auth(User.Group.unLogin)
    @RequestMapping("noAuth")
    public String noAuth(Model model) {
        String msg = "没有权限访问此页面";
        model.addAttribute("msg", msg);
        return "msg";
    }*/


}
