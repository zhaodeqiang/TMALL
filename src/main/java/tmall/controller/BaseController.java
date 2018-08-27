package tmall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tmall.service.*;
import tmall.util.FileUtil;

/**
 *  BaseController 负责 Autowired  全局异常处理
 *  注入对应的实现类
 */

public class BaseController {
    @Autowired
    public PropertyService propertyService;
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public ProductService productService;
    @Autowired
    public ProductImageService productImageService;
    @Autowired
    public PropertyValueService propertyValueService;
    @Autowired
    public ConfigService configService;
    @Autowired
    public OrderService orderService;
    @Autowired
    public UserService userService;
    @Autowired
    public OrderItemService orderItemService;
    @Autowired
    public CommentService commentService;
    @Autowired
    public CartItemService cartItemService;
    @Autowired
    public FileUtil fileUtil;
    @Autowired
    public SolrSearchService solrSearchService;
    /**
     * @Author       ZDQ
     * @MethodName
     * @Description  全局异常处理方法 @ControllerAdvice（写在类上）和@ExceptionHandler（写在方法上）一起使用，可以摆脱@ExceptionHandler
     *              只能在当前Controller类中使用的限制，由于本项目所有的controller都继承了basecontroller，所以可以不需要@ControllerAdvice。
     * @Param
     * @Return
     * @Exception
     * @Date         2018/6/20 14:30
     */
    @ExceptionHandler
    public String handleException( Exception exception) {
        exception.printStackTrace();
        return "500";
    }
}
