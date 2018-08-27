package tmall.util;

import java.util.Random;
/**
 * @ClassName:   ProductIDUtil
 * @Description  商品类的ID生成工具类
 * @Auther:      ZDQ
 * @Date:        2018/6/28 11:52
 */
public class ProductIDUtil {
   /**
   * @Author       ZDQ
   * @MethodName   
   * @Param        No such property: code for class: Script1
   * @Return       
   * @Exception    
   * @Description  生成图片ID
   * @Date         2018/6/28 11:51
   */
    public static long genImageName() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d", end3);
        long id=new Long(str);
        return id;
    }

    /**
    * @Author       ZDQ
    * @MethodName   
    * @Param        No such property: code for class: Script1
    * @Return       
    * @Exception    
    * @Description  商品id生成
    * @Date         2018/6/28 11:51
    */
    public static long genProductId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }
}
