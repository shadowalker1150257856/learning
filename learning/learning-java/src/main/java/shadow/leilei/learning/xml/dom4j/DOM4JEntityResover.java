package shadow.leilei.learning.xml.dom4j;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * xml外部实体引用, 用于载入外部定义的实体对象
 * 
 * @author Grandata
 *
 */
public class DOM4JEntityResover implements EntityResolver {

	@Override
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
