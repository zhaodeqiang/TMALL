package tmall.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
* @Author:         ZDQ
* @ClassName:      GlobalExceptionResolver
* @Description:    全局异常处理类 需要在Springmvcx.xml文件中配置
* @CreateDate:     2018/6/20 14:32
* @UpdateUser:     ZDQ
* @UpdateDate:     2018/6/20 14:32
* @UpdateRemark:   TODO
* @Version:        1.0
*/
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ex.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("500");
        return modelAndView;
    }
}
