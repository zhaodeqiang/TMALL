package tmall.pojo.extension;

import tmall.pojo.Product;
import tmall.pojo.base.BasePOJO;
import tmall.annotation.ORMAnnotation.JoinColumn;
import tmall.annotation.ORMAnnotation.ManyToOne;

public class ProductImageExtension extends BasePOJO {
    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;

    public enum Type{
        top,detail,cover;
    }
    /**
    * @Author       ZDQ
    * @MethodName   
    * @Param        No such property: code for class: Script1
    * @Return       
    * @Exception    
    * @Description  图片路径
    * @Date         2018/6/29 17:49
    */
    public String getPath() {
        return getId() + ".jpg";
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

