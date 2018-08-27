package tmall.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class PasswordUtil {
    /**
     * 密码不可逆加密，取 md5截取 [8,24)再算md5
     *注意：该方法不使用，使用shiro的加密方法
     * @param password 未加密的原始密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) {
        return md5Encrypt(md5Encrypt(password).substring(8, 24));
    }
/**
* @Author       ZDQ
* @MethodName   md5Encrypt
* @Description  使用java自带加密方法
* @Param        
* @Return       加密后的值
* @Exception    
* @Date         2018/6/5 15:40
*/
    private static String md5Encrypt(String str) {
        String result = "";
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            result = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception ignored) {
        }
        return result;
    }
/**
* @Author       ZDQ
* @MethodName   Salt
* @Description  生成密码加密需要的盐，6位随机字母
* @Param        无参
* @Return       6位随机大小写混合字母
* @Exception    
* @Date         2018/6/5 15:39
*/
    public static String Salt() {

        String result = "";
        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                //小写字母
                int intVal = (int) (Math.random() * 26 + 97);
                result = result + (char) intVal;
            } else {
                //大写字母
                int intVal = (int) (Math.random() * 26 + 65);
                result = result + (char) intVal;
            }
        }
        return result;
    }

}