package tmall.dateconverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 转换日期类型的数据
 * S : 页面传递过来的类型
 * T ： 转换后的类型
 * @author lx
 *
 */
public class DateConveter implements Converter<String, Date>{

	public Date convert(String source) {
		//测试通过，能够进入转换器
		try {
			if(null != source){
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(source);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
