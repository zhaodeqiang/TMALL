package tmall.controller.front;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tmall.annotation.Nullable;
import tmall.pojo.*;
import tmall.util.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
@SuppressWarnings("ALL")
public class ShowController extends FrontBaseController {
    @RequestMapping("")
    public String home(Model model) throws Exception {
        Pagination pagination = new Pagination();
        pagination.setCount(15);
        List<Category> categories = categoryService.
                list("depth", 1, "pagination", pagination, "recommend_gt", 0, "order", "recommend desc, id desc");
        for (Category category : categories) {
            category.setProducts(productService
                    .list("cid", category.getId(), "stock_gt", 0));
        }
        model.addAttribute("categories", categories);
        return "home";
    }

    @RequestMapping("product")
    public String product(Integer id, Model model) throws Exception {
        Product product = (Product) productService.get(id);
        List<ProductImage> productTopImages = productImageService.list("pid", product.getId(), "type", ProductImage.Type.top.toString(), "order", "id asc");
        List<ProductImage> productDetailImages = productImageService.list("pid", product.getId(), "type", ProductImage.Type.detail.toString(), "order", "id asc");
        List<Comment> comments = commentService.list("pid", product.getId());
        List<PropertyValue> propertyValues = propertyValueService.list("pid", product.getId());
        model.addAttribute("productTopImages", productTopImages);
        model.addAttribute("productDetailImages", productDetailImages);
        model.addAttribute("comments", comments);
        model.addAttribute("propertyValues", propertyValues);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping("category")
    public String category(Integer id, Pagination pagination, @Nullable String sort, Model model) throws Exception {
        pagination.setCount(15);//前台可以直接获取到参数列表中的pagination,不需要addAttribute
        Category category = (Category) categoryService.get(id);
        List<Product> products = productService
                .list("cid", category.getId(), "pagination", pagination, "order", handleSort(sort), "stock_gt", 0);
        pagination.setParam("&id=" + id);//下一次请求把上一次的id传进来
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        return "category";
    }

    //使用solr搜索，此方法不再使用
    @RequestMapping("search")
    public String search(String keyword, Pagination pagination, @Nullable String sort, Model model) throws Exception {
        pagination.setCount(15);
        List<Product> products = productService
                .list("name_like", keyword.trim(), "pagination", pagination, "order", handleSort(sort), "stock_gt", 0);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "search";
    }

    /**
     * @Author       ZDQ
     * @MethodName   solrSearch
     * @Param        [keyword, pagination, sort, model]
     * @Return       java.lang.String
     * @Exception
     * @Description
     * @Date         2018/6/30 18:14
     */
    @RequestMapping("solrSearch")
    public String solrSearch(String keyword, Pagination pagination, @Nullable String sort, Model model) throws Exception {
        List<SolrSearchResult> solrSearchResult = solrSearchService.search(keyword, pagination, HandSort(sort));
        model.addAttribute("keyword", keyword);
        model.addAttribute("solrSearchResult", solrSearchResult);
        return "solrSearch";
    }

    //ajax访问查询商品名称
    @RequestMapping(value = "searchProductName", method = RequestMethod.POST)
    public void searchProductName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String keyword = request.getParameter("keyword");
        List<Product> products = productService.list("name_like", keyword.trim());
        List list = new ArrayList();
        for (Product p : products) {
            list.add(p.getName());
        }
        Set set = new HashSet(list);//去重
        String json = JSON.toJSONString(set);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private String handleSort(String sort) {
        sort = (sort == null) ? "" : sort;
        String column = "";
        String order = "desc";
        switch (sort) {
            case "date":
                column = "createDate";
                order="asc";
                break;
            case "dataInverse":
                column = "createDate";
                break;
            case "comment":
                column = "commentCount";
                order="asc";
                break;
            case "commentInverse":
                column = "commentCount";
                break;
            case "saleCount":
                column = "saleCount";
                order="asc";
                break;
            case "saleCountInverse":
                column = "saleCount";
                break;
            case "price":
                column = "nowPrice";
                order = "asc";
                break;
            case "priceInverse":
                column = "nowPrice";
                break;
            default:
                column = "commentCount";
                break;
        }
       return  String.format("%s %s , id desc", column, order);//%s字符串格式化，将column,order转换为字符串
    }

    /*solr搜索时处理页面排序*/
    private List<String> HandSort(String sort) {
        String order = "desc";
        List<String> list = new ArrayList<>();
        sort = (sort == null) ? "" : sort;
            switch (sort) {
                case "date":
                    list.add("p_createDate");
                    list.add("asc");
                    break;
                case "dataInverse":
                    list.add("p_createDate");
                    list.add(order);
                case "comment":
                    list.add("p_comment");
                    list.add(order);
                    break;
                case "commentInverse":
                    list.add("p_comment");
                    list.add("asc");
                    break;
                case "saleCount":
                    list.add("p_sale");
                    list.add(order);
                    break;
                case "saleCountInverse":
                    list.add("p_sale");
                    list.add("asc");
                    break;
                case "price":
                    list.add("p_price");
                    list.add("asc");
                    break;
                case "priceInverse":
                    list.add("p_price");
                    list.add(order);
                    break;
                default:
                    list.add("p_comment");
                    list.add(order);
                    break;
            }
        return list;
    }
}
