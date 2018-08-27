package tmall.service.impl;


import org.springframework.stereotype.Service;
import tmall.mapper.CategoryMapper;
import tmall.pojo.CategoryExample;
import tmall.service.CategoryService;

/**
 * @see CategoryService
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, CategoryExample>
        implements CategoryService {

}