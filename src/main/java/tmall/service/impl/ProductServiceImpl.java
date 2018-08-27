package tmall.service.impl;

import org.springframework.stereotype.Service;
import tmall.mapper.ProductMapper;
import tmall.pojo.ProductExample;
import tmall.service.ProductService;

@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper,ProductExample> implements ProductService {

}