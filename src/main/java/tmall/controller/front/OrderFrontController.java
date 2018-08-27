package tmall.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import tmall.exception.AuthException;
import tmall.exception.ParameterException;
import tmall.pojo.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

//@Auth(User.Group.user)
@Controller
@RequestMapping("/")
@SuppressWarnings("unchecked")
public class OrderFrontController extends FrontBaseController {
    /**
     * @Description 购物车方法
     * @Author ZDQ
     * @Param 商品编号  数量
     * @Return 返回添加成功与否的message
     * @Exception
     * @Date 2018/6/3 23:25
     */
    @RequestMapping("addCart")
    public void addCart(Integer pid, Integer num, HttpServletResponse response, HttpSession session) throws Exception {
        Product product = (Product) productService.get(pid);
        User user = (User) session.getAttribute("user");
        String msg;
        if (user != null) {
            //获取原来就在购物车的数据
            CartItem cartItem = (CartItem) cartItemService
                    .getOne("uid", user.getId(), "pid", product.getId());
            Boolean isInDB = (cartItem != null);
            //判断是否超出库存
            if (isInDB) {
                num += cartItem.getNumber();
            } else {
                //购物车中没有
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setUser(user);
            }
            if (num > product.getStock()) {
                msg = "OutOfStock";
                response.getWriter().write(msg);
                return;
            } else {
                cartItem.setNumber(num);
                cartItem.setSum(product.getNowPrice().subtract(new BigDecimal(num)));
            }
            if (isInDB) {
                cartItemService.update(cartItem);
            } else {
                cartItemService.add(cartItem);
            }
            msg = "success";
            response.getWriter().write(msg);
        }
    }

    @RequestMapping("cart")
    public String cart(Model model, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        List<CartItem> cartItems = cartItemService.list("uid", user.getId());
        model.addAttribute("cartItems", cartItems);
        return "cart";
    }

    @RequestMapping("changeCartNum")
    public void changeCartNum(Integer id, Integer num, Double sum, HttpServletResponse response, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        CartItem cartItemFromDB = (CartItem) cartItemService.get(id);
        String msg = "fail";
        checkUser(user, cartItemFromDB.getUser());
        if (cartItemFromDB.getProduct().getStock() >= num) {
            cartItemFromDB.setNumber(num);
            cartItemFromDB.setSum(new BigDecimal(sum));
            cartItemService.update(cartItemFromDB);
            msg = "success";
        }
        response.getWriter().write(msg);
    }

    @RequestMapping("deleteCartItem")
    public void deleteCartItem(Integer id, HttpServletResponse response, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        CartItem cartItemFromDB = (CartItem) cartItemService.get(id);
        checkUser(user, cartItemFromDB.getUser());
        cartItemService.delete(cartItemFromDB);
        response.getWriter().write("success");

    }

    /**
     * @Author ZDQ
     * @MethodName
     * @Description ajax查询购物车商品数量
     * @Param
     * @Return
     * @Exception
     * @Date 2018/6/11 13:37
     */
    @RequestMapping("cartNumber")
    public void cartNumber(HttpServletResponse response, HttpSession session) throws Exception {
        User user = (User) session.getAttribute("user");
        String number = String.valueOf(cartItemService.list("uid", user.getId()).size());
        response.getWriter().write(number);
    }

    @RequestMapping("buyOne")
    public String buyOne(Integer pid, Integer num, Model model, HttpSession session) throws Exception {
        Product product = (Product) productService.get(pid);
        User user = (User) session.getAttribute("user");
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setNumber(num);
        cartItem.setSum(product.getNowPrice().multiply(new BigDecimal(num)));
        cartItem.setId(-1);
        session.setAttribute("tempCartItem", cartItem);
        return "redirect:buy?ciid=-1";
    }

    /**
     * @Author ZDQ
     * @MethodName
     * @Description 购买商品
     * @Param
     * @Return
     * @Exception
     * @Date 2018/6/12 10:47
     */
    @RequestMapping("buy")
    public String buy(Integer[] ciid, Model model, HttpSession session) throws Exception {
        List<CartItem> cartItems = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        int totalNum = 0;
        BigDecimal sum = new BigDecimal(0);
        for (Integer id : ciid) {
            CartItem cartItem = null;
            if (id == -1) {
                //由buyOne跳转而来
                cartItem = (CartItem) session.getAttribute("tempCartItem");
            } else if (id > 0) {
                //由购物车跳转而来
                cartItem = (CartItem) cartItemService.get(id);
            }
            // 检查
            checkUser(user, cartItem.getUser());

            totalNum += cartItem.getNumber();
            sum = sum.add(cartItem.getSum());
            cartItems.add(cartItem);

        }
        session.setAttribute("cartItems", cartItems);
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("sum", sum);
        return "buy";
    }

    @RequestMapping("createOrder")
    public String createOrder(String address, String post, String receiver,
                              String mobile,
                              String userMessage,
                              HttpSession session) throws Exception {
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
        User user = (User) session.getAttribute("user");
        //简单校验下手机
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$");
        if (!pattern.matcher(mobile).matches()) {
            throw new ParameterException("手机号填写错误");
        }
        Order order = new Order();
        order.setAddress(address);
        order.setPost(post);
        order.setReceiver(receiver);
        order.setMobile(mobile);
        order.setUserMessage(userMessage);
        order.setUser(user);
        orderService.createOrder(order, cartItems);
        return "redirect:pay?oid=" + order.getId();
    }

    @RequestMapping("pay")
    public String pay(Integer oid, HttpSession session, Model model) throws Exception {
        User user = (User) session.getAttribute("user");
        Order order = (Order) orderService.get(oid);
        checkUser(user, order.getUser());
        model.addAttribute("order", order);
        return "pay";
    }

    @RequestMapping("payed")
    public String payed(Integer oid, HttpSession session, Model model) throws Exception {
        User user = (User) session.getAttribute("user");
        Order order = (Order) orderService.get(oid);
        checkUser(user, order.getUser());
        order.setStatus(Order.Status.waitDeliver);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("order", order);
        return "payed";
    }

    @RequestMapping("myOrder")
    public String myOrder(HttpSession session, Model model) throws Exception {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.list("uid", user.getId(), "depth", 3);
        model.addAttribute("orders", orders);
        return "myOrder";
    }

    @RequestMapping("deliver")
    public String deliver(Integer oid, HttpSession session, Model model) throws Exception {
        Order order = (Order) orderService.get(oid);
        User user = (User) session.getAttribute("user");
        checkUser(user, order.getUser());
        order.setStatus(Order.Status.waitConfirm);
        order.setDeliverDate(new Date());
        orderService.update(order);
        return "redirect:myOrder";
    }

    @RequestMapping("confirmPay")
    public String confirmPay(Integer oid, HttpSession session, Model model) throws Exception {
        Order order = (Order) orderService.get(oid, 3);
        User user = (User) session.getAttribute("user");
        checkUser(user, order.getUser());
        model.addAttribute("order", order);
        return "confirmPay";
    }

    @RequestMapping("confirmed")
    public String confirmed(Integer oid, HttpSession session, Model model) throws Exception {
        Order order = (Order) orderService.get(oid);
        User user = (User) session.getAttribute("user");
        checkUser(user, order.getUser());
        order.setStatus(Order.Status.waitComment);
        order.setConfirmDate(new Date());
        orderService.update(order);
        model.addAttribute("order", order);
        return "confirmed";
    }

    @RequestMapping("deleteOrder")
    public void deleteOrder(Integer oid, HttpSession session, HttpServletResponse response) throws Exception {
        Order order = (Order) orderService.get(oid);
        User user = (User) session.getAttribute("user");
        String msg = "fail";
        checkUser(user, order.getUser());
        response.getWriter().write(msg);
    }

    @RequestMapping("comment")
    public String comment(Integer oiid,Integer oid, HttpSession session, Model model) throws Exception {
        OrderItem orderItem = (OrderItem) orderItemService.get(oiid);
        Order order=(Order)orderService.get(oid);
        User user = (User) session.getAttribute("user");
        checkUser(user, orderItem.getOrder().getUser());
        model.addAttribute("order", order);
        model.addAttribute("orderItem", orderItem);
        return "comment";
    }

    @RequestMapping("addComment")
    public String addComment(Integer oiid, Comment comment, HttpSession session, Model model) throws Exception {
        OrderItem orderItem = (OrderItem) orderItemService.get(oiid);
        User user = (User) session.getAttribute("user");
        checkUser(user, orderItem.getOrder().getUser());
        comment.setProduct(orderItem.getProduct());
        comment.setUser(user);
        comment.setCreateDate(new Date());
        commentService.add(comment);
        orderItem.setComment(comment);
        orderItemService.update(orderItem);
        Product product = orderItem.getProduct();
        product.setCommentCount(product.getCommentCount() + 1);
        productService.update(product);
        Order orderNew = (Order) orderService.get(orderItem.getOrder().getId());
        if (commentService.checkFinishComment(orderNew)) {
            orderNew.setStatus(Order.Status.finish);
            orderService.update(orderNew);
        }
        return "redirect:myOrder";
    }

    /**
     * @Author ZDQ
     * @MethodName
     * @Description TODO
     * @Param
     * @Return
     * @Exception
     * @Date 2018/6/10 17:48
     */
    private void checkUser(User user1, User user2) throws Exception {
        if (!user1.getId().equals(user2.getId())) {
            throw new AuthException("非法访问，没有权限");
        }
    }
}
