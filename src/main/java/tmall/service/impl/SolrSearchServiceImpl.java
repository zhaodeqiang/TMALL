package tmall.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;
import tmall.pojo.SolrSearchResult;
import tmall.service.SolrSearchService;
import tmall.util.Pagination;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SolrSearchServiceImpl implements SolrSearchService {
    @Resource
    private HttpSolrClient httpSolrClient;

    @Override
    public List<SolrSearchResult> search(String keyword, Pagination pagination, List<String> sort) throws Exception {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(keyword);
        solrQuery.setStart(pagination.getStart());
        solrQuery.setRows(pagination.getCount());
        solrQuery.set("df", "p_subtitle");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("p_subtitle");
        //排序处理 desc：从大到小 ； asc:从小到大
        if(sort.get(1).equals("desc")){
            solrQuery.setSort(sort.get(0),SolrQuery.ORDER.desc);
        }else{
            solrQuery.setSort(sort.get(0),SolrQuery.ORDER.asc);
        }
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        QueryResponse queryResponse = httpSolrClient.query(solrQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        Long number = solrDocumentList.getNumFound();
        pagination.setTotal(number.intValue());
        //商品列表对象
        List<SolrSearchResult> solrSearchResultList = new ArrayList<>();
        //取高亮后的结果
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument solrDocument : solrDocumentList) {
            SolrSearchResult solrSearchResult = new SolrSearchResult();
            solrSearchResult.setId(solrDocument.get("id").toString());
            solrSearchResult.setName(solrDocument.get("p_name").toString());
            solrSearchResult.setCommentCount(Integer.parseInt(solrDocument.get("p_comment").toString()));
            solrSearchResult.setSaleCount(Integer.parseInt(solrDocument.get("p_sale").toString()));
            solrSearchResult.setImgId(Integer.parseInt(solrDocument.get("p_image").toString()));
            solrSearchResult.setStock(Integer.parseInt(solrDocument.get("p_stock").toString()));
            solrSearchResult.setNowPrice(Float.valueOf(solrDocument.get("p_price").toString()));
            //创建商品的时间 [Sun May 20 21:32:36 CST 2018] 需要分割掉[]再格式化
            String strDate = solrDocument.get("p_createDate").toString().replaceAll("\\[", "").replaceAll("\\]", "");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date date = simpleDateFormat.parse(strDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 8);
            date = calendar.getTime();
            solrSearchResult.setCreateDate(date);
            //取高亮结果
            List<String> list = highlighting.get(solrDocument.get("id")).get("p_subtitle");
            String subtitle = "";
            if (list != null && list.size() > 0) {
                subtitle = list.get(0);
            } else {
                subtitle = (String) solrDocument.get("p_subtitle");
            }
            solrSearchResult.setSubTitle(subtitle);
            solrSearchResultList.add(solrSearchResult);
        }
        return solrSearchResultList;
    }
}
