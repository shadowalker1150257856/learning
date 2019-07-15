package shadow.leilei.learning.xml.dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * 使用DOM方式写入XML文件
 * 
 * @author yuelei
 *
 */
public class DOMWriter {

	/**
	 * 测试创建XML </br>
	 * 写入DOCTYPE, 根节点, 根节点下有三种类型子节点, 第二个子节点下有一个子节点, </br>
	 * 每个子节点都有文本节点。第三个节点有一个注释节点 </br>
	 * 最后保存在DOMXMLTest.xml文件目录
	 */
	public static void main(String[] args) {

		String outFilePath = "E:\\Github\\shadowalker\\learning\\learning\\learning-java\\src\\main\\java\\shadow\\leilei\\learning\\xml\\DOMXMLTest.xml";

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder db = dbFactory.newDocumentBuilder();

			// document对象
			Document document = db.newDocument();

			// xml版本
			document.setXmlVersion("1.0");

			// 创建根节点
			Element rootElement = document.createElement("root");
			rootElement.setAttribute("name", "rootElement");

			// 换行文本节点
			Text newLineTextEL = document.createTextNode("\r\n");
			// 空格文本节点
			Text blankTextEL = document.createTextNode("	");

			// 根节点下有三种类型节点
			Element firstEl = document.createElement("first");
			firstEl.setAttribute("id", "firstEl");
			Text firstTextEl = document.createTextNode("select * from table1");
			firstEl.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(firstTextEl);
			rootElement.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(firstEl);
			// 第二个节点有子节点
			Element secondEl = document.createElement("second");
			secondEl.setAttribute("id", "secondEl");
			Element subEl = document.createElement("subEl");
			Text subTextEl = document.createTextNode("insert into values");
			subEl.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(subTextEl);
			secondEl.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(subEl);
			rootElement.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(secondEl);

			// 第三个节点有注释节点
			Element thirdEl = document.createElement("third");
			thirdEl.setAttribute("id", "thirdEl");
			Text thirdTextEl = document.createTextNode("update table1 set 1 = 1 where 1 = 1");
			thirdEl.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(thirdTextEl);

			Comment comment = document.createComment("第三节点的注释");

			rootElement.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(comment);
			rootElement.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(thirdEl);

			document.appendChild(newLineTextEL).appendChild(blankTextEL).appendChild(rootElement);

			// 写出xml
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			// 输出参数配置
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//mybatis.org//DTD Mapper 3.0//EN");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
			transformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

			// 工厂方法参数，配置输出用参数
			transformerFactory.setAttribute("indent-number", new Integer(2));

			File file = new File(outFilePath);
			if (file.exists())
				file.delete();
			transformer.transform(new DOMSource(document), new StreamResult(file));

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}
