package tmall.controller.admin;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tmall.pojo.Product;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin/solr/")
@SuppressWarnings("unchecked")
public class SolrController extends AdminBaseController {
    @Resource
    private HttpSolrClient httpSolrClient;

    /**
     * @Author ZDQ
     * @MethodName
     * @Param No such property: code for class: Script1
     * @Return
     * @Exception
     * @Description 导入solr索引库，使用ajax调用，返回提示消息
     * @Date 2018/6/29 17:50
     */
    @RequestMapping(value = "importSolrIndex", method = RequestMethod.POST)
    @ResponseBody
    public String importSolrIndex() throws Exception {
        // HttpSolrClient httpSolrClient = new HttpSolrClient.Builder("http://127.0.0.1:8082/solr/mycore").build();
        try {
            List<Product> list = productService.list("stock_gt", 0);
            for (Product product : list) {
                //创建文档对
                SolrInputDocument solrInputDocument = new SolrInputDocument();
                //添加域
                solrInputDocument.addField("id", product.getId().toString());
                solrInputDocument.addField("p_name", product.getName());
                solrInputDocument.addField("p_stock", product.getStock());
                //卖出数量
                solrInputDocument.addField("p_sale", product.getSaleCount());
                solrInputDocument.addField("p_comment", product.getCommentCount());
                //商品添加时间  UTC时区比中国晚八小时，需要加上八小时
                //查询结果 id=2的商品  "p_createDate":["2018-05-20T13:32:36Z"],
                Date date = product.getCreateDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                date = calendar.getTime();
                solrInputDocument.addField("p_createDate", date);
                // NowPrice为BigDecimal
                solrInputDocument.addField("p_price", product.getNowPrice().toString());
                solrInputDocument.addField("p_image", product.getImgid());
                solrInputDocument.addField("p_subtitle", product.getSubTitle());
                //写入索引库
                httpSolrClient.add(solrInputDocument);
            }
            //提交
            httpSolrClient.commit();
            return "true";
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    @RequestMapping("solrIndex")
    public String solrIndex() throws Exception {
        return "admin/editSolr";
    }
}
