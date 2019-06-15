package shadow.leilei.learning.xml.dom;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser {

	/**
	 * 节点类型枚举
	 * 
	 * @author Grandata
	 *
	 */
	private static enum NodeTypeEnum {

		ELEMENT_NODE(1, "ELEMENT_NODE"), //
		ATTRIBUTE_NODE(2, "ATTRIBUTE_NODE"), //
		TEXT_NODE(3, "TEXT_NODE"), //
		CDATA_SECTION_NODE(4, "CDATA_SECTION_NODE"), //
		ENTITY_REFERENCE_NODE(5, "ENTITY_REFERENCE_NODE"), //
		ENTITY_NODE(6, "ENTITY_NODE"), //
		PROCESSING_INSTRUCTION_NODE(7, "PROCESSING_INSTRUCTION_NODE"), //
		COMMENT_NODE(8, "COMMENT_NODE"), //
		DOCUMENT_NODE(9, "DOCUMENT_NODE"), //
		DOCUMENT_TYPE_NODE(10, "DOCUMENT_TYPE_NODE"), //
		DOCUMENT_FRAGMENT_NODE(11, "DOCUMENT_FRAGMENT_NODE"), //
		NOTATION_NODE(12, "NOTATION_NODE");

		private short nodeType;
		private String nodeTypeName;

		NodeTypeEnum(int nodeType, String nodeTypeName) {
			this.nodeType = (short) nodeType;
			this.nodeTypeName = nodeTypeName;
		}

		public static String getNodeTypeName(short nodeType) {

			for (NodeTypeEnum value : values()) {
				if (value.nodeType == nodeType) {
					return value.nodeTypeName;
				}
			}

			return "";
		}
	}

	/**
	 * 打印节点信息
	 * 
	 * @param element
	 */
	private static void printElementInfo(Element element) {
		// 节点名称, 属性， 及值
		if (element == null) {
			return;
		}

		String nodeTypeName = NodeTypeEnum.getNodeTypeName(element.getNodeType());
		System.out.println("Element name: " + element.getNodeName() + " type: " + nodeTypeName);

	}

	/**
	 * 打印节点信息, 如果是text节点则打印内容 <br/>
	 * Node.getTextContent()方法可以返回节点的所有文本标签信息, 相应的注释和子标签等将被丢弃。<br/>
	 * 
	 * @param node
	 */
	private static void printNodeInfo(Node node) {
		// 解析该节点的名称, 属性
		System.out.print("Node name: " + node.getNodeName());
		String nodeTypeName = NodeTypeEnum.getNodeTypeName(node.getNodeType());
		System.out.println(", Node type:  " + nodeTypeName);

		// 打印节点属性
		printNodeAttributes(node.getAttributes());

		// 解析该节点的子节点
		NodeList childrenNodeList = node.getChildNodes();
		if (childrenNodeList != null && childrenNodeList.getLength() > 0) {

			for (int index = 0, length = childrenNodeList.getLength(); index < length; index++) {
				Node childNode = childrenNodeList.item(index);
				printNodeInfo(childNode);
			}

		} else if (NodeTypeEnum.TEXT_NODE.nodeType == node.getNodeType()) {
			System.out.println("Node value: " + node.getNodeValue());
		} else if (NodeTypeEnum.COMMENT_NODE.nodeType == node.getNodeType()) {
			System.out.println("Node value: " + node.getNodeValue());
		} else if (NodeTypeEnum.CDATA_SECTION_NODE.nodeType == node.getNodeType()) {
			System.out.println("Node value: " + node.getNodeValue());
		}
	}

	/**
	 * 打印节点属性信息
	 * 
	 * @param node
	 */
	private static void printNodeAttributes(NamedNodeMap namedNodeMap) {
		if (namedNodeMap != null) {
			System.out.println("Node Attributes:");
			for (int index = 0, length = namedNodeMap.getLength(); index < length; index++) {
				Node attributeNode = namedNodeMap.item(index);
				System.out.println("Name: " + attributeNode.getNodeName() + ", Value: " + attributeNode.getNodeValue());
			}
		}
	}

	/**
	 * 练习使用DOM方式解析xml文件。</br>
	 * 解析文档，输出每个标签名称， 以及每个标签的属性， </br>
	 * 如果该标签下没有子标签， 则直接输出该标签的值 </br>
	 * DOM解析方式, 语言自带类库, 基于w3c的标准解析
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String xmlFilePath = "E:\\Github\\shadowalker\\learning\\learning\\learning-java\\src\\main\\java\\shadow\\leilei\\learning\\xml\\XMLTest.xml";
		File xmlFile = new File(xmlFilePath);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			Document document = db.parse(xmlFile);

			// 获取文档相关属性信息
			System.out.println("BaseURI: " + document.getBaseURI());
			DocumentType docType = document.getDoctype();
			System.out.println("DocType-Name: " + docType.getName() + ", DocType-PublicId: " + docType.getPublicId());
			System.out.println("DocumentURI: " + document.getDocumentURI());
			DOMConfiguration domConfig = document.getDomConfig();
			DOMStringList domStringList = domConfig.getParameterNames();
			for (int index = 0, length = domStringList.getLength(); index < length; index++) {
				String domString = domStringList.item(index);
				System.out.println("DOMStringList DOMString: " + domString);
			}
			DOMImplementation domImplementation = document.getImplementation();
			String inputEncoding = document.getInputEncoding();
			System.out.println("Encoding: " + inputEncoding);
			System.out.println("LocalName: " + document.getLocalName());
			System.out.println("NamespaceURI: " + document.getNamespaceURI());
			System.out.println("XmlEncoding: " + document.getXmlEncoding());
			System.out.println("XmlStandalone: " + document.getXmlStandalone());
			Document ownerDocument = document.getOwnerDocument();
			System.out.println("XmlVersion: " + document.getXmlVersion());

			// 解析xml文档
			System.out.println("document 属性:");
			NamedNodeMap namedNodeMap = document.getAttributes();
			if (namedNodeMap != null) {
				for (int index = 0, length = namedNodeMap.getLength(); index < length; index++) {
					Node node = namedNodeMap.item(index);
					System.out.println(
							"Node: " + node.getNodeType() + ", " + node.getNodeName() + ", " + node.getNodeValue());
				}
			}

			System.out.println("文档第一个节点:");
			Node firstNode = document.getFirstChild();
			printNodeInfo(firstNode);

			System.out.println("文档最后一个节点:");
			printNodeInfo(document.getLastChild());

			System.out.println("解析文档结果:");
			NodeList nodeList = document.getChildNodes();
			if (nodeList != null) {
				for (int index = 0, length = nodeList.getLength(); index < length; index++) {
					Node node = nodeList.item(index);
					printNodeInfo(node);
				}
			}

			System.out.println("读取文档标签");
			Element element = document.getElementById("selectByPrimaryKey");
			if (element != null) {
				printElementInfo(element);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
