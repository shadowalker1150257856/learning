package shadow.leilei.learning.xml.dom4j;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class DOM4JParser {

	/**
	 * 打印文档属性信息
	 * 
	 * @param document
	 */
	private static void printDocumentInfo(Document document) {

		if (document == null)
			return;

		// 解析doctype
		DocumentType documentType = document.getDocType();
		System.out.println(String.format("文档DOCTYPE name:%s, nodeTypeName:%s, nodeType:%s", documentType.getName(),
				documentType.getNodeTypeName(), document.getNodeType()));
		System.out.println(
				String.format("文档PublicId:%s, SystemId:%s", documentType.getPublicID(), documentType.getSystemID()));
		System.out.println(String.format("文档DOCTYPE标签:%s", documentType.asXML()));
		System.out.println(String.format("文档DOCTYPE文本:%s", documentType.getText()));

		// 解析文档自身其它属性配置
		System.out.println(String.format("文档Text:%s", document.getText()));
		System.out.println(String.format("文档标签:%s", document.asXML()));

	}

	/**
	 * 打印节点信息
	 * 
	 * @param element
	 */
	private static void printElementInfo(Element element) {

		if (element == null)
			return;

		System.out.println(String.format("开始解析节点:%s", element.getName()));
		System.out.println(String.format("标签StringValue:%s", element.getStringValue()));
		System.out.println(String.format("标签text:%s", element.getText()));

		// 解析属性
		Iterator<Attribute> attrIterator = element.attributeIterator();
		while (attrIterator.hasNext()) {
			Attribute attribute = attrIterator.next();

			System.out.println(String.format("属性, name:%s, stringvalue:%s, value:%s", attribute.getName(),
					attribute.getStringValue(), attribute.getValue()));
			System.out.println(String.format("属性的xml:%s", attribute.asXML()));

		}

		if (element.getNodeType() == Element.COMMENT_NODE) {
			System.out.println("注释节点");
		}

	}

	/**
	 * 打印node节点的信息 </br>
	 * node节点无法获取属性值
	 * 
	 * @param node
	 */
	private static void printNodeInfo(Node node) {

		if (node == null)
			return;

		System.out.println(String.format("通过node方式解析标签:%s", node.getName()));
		System.out.println(String.format("节点StringValue:%s", node.getStringValue()));
		System.out.println(String.format("节点Text:%s", node.getText()));
		System.out.println(String.format("节点XML:%s", node.asXML()));

		// 解析属性
		// node无法获取属性
		// 属性貌似也别解析为一个node

		System.out.println(String.format("节点类型名称:%s", node.getNodeTypeName()));

	}

	/**
	 * DOM4J解析xml文件示例 </br>
	 * DOM4J是大多开源项目的标配解析工具 </br>
	 * DOM4J支持xpath路径解析 </br>
	 * asXML接口返回的是整个完整的xml标签文本。</br>
	 * stringValue接口返回所有子文本标签的值，element元素的位置用换行取代。</br>
	 * text接口仅对text类型节点起作用，返回节点的值。</br>
	 * 直接获取List<Element>会忽略文本中的注释节点。 </br>
	 * xpath的使用需要引入jaxen包
	 */
	public static void main(String[] args) {

		SAXReader saxReader = new SAXReader();

		String filePath = "E:\\Github\\shadowalker\\learning\\learning\\learning-java\\src\\main\\java\\shadow\\leilei\\learning\\xml\\XMLTest.xml";
		File file = new File(filePath);

		try {
			Document document = saxReader.read(file);

			printDocumentInfo(document);

			Element rootElement = document.getRootElement();
			printElementInfo(rootElement);

			List<Element> elements = rootElement.elements();
			if (CollectionUtils.isNotEmpty(elements)) {
				for (Element element : elements) {
					printElementInfo(element);
				}
			}

			// iterator方式遍历
			System.out.println("iterator遍历");
			Iterator<Element> elementIterator = rootElement.elementIterator();
			while (elementIterator.hasNext()) {
				Element element = elementIterator.next();
				printElementInfo(element);
			}

			// 遍历node
			System.out.println("通过node遍历");
			List<Node> nodes = document.content();
			if (CollectionUtils.isNotEmpty(nodes)) {
				for (Node node : nodes) {
					printNodeInfo(node);
				}
			}

			// xpath 使用
			System.out.println("测试xpath路径选取节点:");
			// XPath xpath = document.createXPath("//select");
			List<Node> selectNodes = document.selectNodes("//select");
			if (CollectionUtils.isNotEmpty(selectNodes)) {
				for (Node node : nodes) {
					printNodeInfo(node);
				}
			}

			System.out.println("测试属性选择器选取节点:");
			List<Node> parameterNodes = document.selectNodes("/@parameterType");
			if (CollectionUtils.isNotEmpty(parameterNodes)) {
				for (Node node : parameterNodes) {
					printNodeInfo(node);
				}
			}

			System.out.println("测试谓语选择节点:");
			List<Node> firstParameterNodes = document.selectNodes("//@id[1]");
			if (CollectionUtils.isNotEmpty(firstParameterNodes)) {
				for (Node node : firstParameterNodes) {
					printNodeInfo(node);
				}
			}

			// EntityResolver
			//

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

}
