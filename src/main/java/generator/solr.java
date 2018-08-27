package generator;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import tmall.util.Pagination;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class solr {
    @Test
    public void addDocument() throws Exception {
        HttpSolrClient httpSolrClient = new HttpSolrClient.Builder("http://127.0.0.1:8082/solr/mycore").build();
        //SolrInputDocument solrInputDocument = new SolrInputDocument();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "苹果");
        //默认为0
        //solrQuery.setStart();
        //默认为10
        solrQuery.setRows(15);
        //排序 asc 从小到大排序
        solrQuery.setSort("p_createDate",SolrQuery.ORDER.asc);
        solrQuery.set("df", "p_subtitle");
        QueryResponse queryResponse = httpSolrClient.query(solrQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        Long num = solrDocumentList.getNumFound();
       // System.out.println(num);
        System.out.println("==========");
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("p_image"));
            //id=2 [Sun May 20 21:32:36 CST 2018]  需要减去8小时
            System.out.println(solrDocument.get("p_createDate"));
            System.out.println(solrDocument.get("p_subtitle"));
            System.out.println("============");
            String datestr="Sun May 20 21:32:36 CST 2018";
            //Date date=new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK).parse(datestr);
           // System.out.println(date);
        }
    }
}
