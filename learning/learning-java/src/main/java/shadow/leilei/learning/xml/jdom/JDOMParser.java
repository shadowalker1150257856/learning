package shadow.leilei.learning.xml.jdom;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class JDOMParser {
	
	/**
	 * 解析节点信息
	 * @param element
	 */
	private static void printElementInfo(Element element){
		if(element == null)
			return ;
		
		String elementName = element.getName();
		// 会获取该节点所有文本节点信息值
		String elementValue = element.getValue();
		String textContent = element.getText();
		System.out.println(String.format("打印节点信息, 名称:%s, 值:%s", elementName, elementValue));
		System.out.println(String.format("节点文本信息: %s", textContent));
		
		List<Attribute> attributes = element.getAttributes();
		if(CollectionUtils.isNotEmpty(attributes)){
			for(Attribute attribute : attributes){
				System.out.println(String.format("节点属性, name:%s, value:%s", 
						attribute.getName(), attribute.getValue()));
			}
		}
		
		// content类似于子节点，会将子节点根绝类型构造成content对象
		List<Content> contents = element.getContent();
		if(CollectionUtils.isNotEmpty(contents)){
			for(Content content :contents){
				CType ctype = content.getCType();
				System.out.println(String.format("节点ctype: %s, 节点content: %s", ctype.name(), content.getValue()));
			}
		}
	}

	/**
	 * JDOM解析xml示例 <br/>
	 * 解析节点标签的名称, 属性, 值 <br/>
	 * */
	public static void main(String[] args) {

		String filePath = "E:\\Github\\shadowalker\\learning\\learning\\learning-java\\src\\main\\java\\shadow\\leilei\\learning\\xml\\XMLTest.xml";
		
		SAXBuilder saxBuilder = new SAXBuilder();
		File file = new File(filePath);
		try {
			Document document = saxBuilder.build(file);
			Element rootElement = document.getRootElement();
			System.out.println("开始解析文档:");
			printElementInfo(rootElement);
			
			List<Element> elements = rootElement.getChildren();
			if(CollectionUtils.isNotEmpty(elements)){
				for(Element element : elements){
					printElementInfo(element);
				}
			}
			
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		
	}

}
