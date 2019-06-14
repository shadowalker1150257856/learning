package shadow.leilei.learning.xml.sax;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXParser {

	/**
	 * sax解析类
	 * */
	public static void main(String[] args) {

		String filePath = "E:\\Github\\shadowalker\\learning\\learning\\learning-java\\src\\main\\java\\shadow\\leilei\\learning\\xml\\XMLTest.xml";
		File file = new File(filePath);
		
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			javax.xml.parsers.SAXParser saxParser = saxParserFactory.newSAXParser();
			saxParser.parse(file, new DefaultSAXParser());
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
