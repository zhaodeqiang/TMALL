package tmall.service;

import org.springframework.stereotype.Service;
import tmall.annotation.Nullable;
import tmall.pojo.SolrSearchResult;
import tmall.util.Pagination;

import java.util.List;


public interface SolrSearchService {

    public List<SolrSearchResult> search(String keyword, Pagination pagination,  List<String> sort) throws  Exception;

}
