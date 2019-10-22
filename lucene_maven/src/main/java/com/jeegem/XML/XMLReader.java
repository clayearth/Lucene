package com.jeegem.XML;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.File;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;



public class XMLReader {

	/**
	 * @param path	String 文件路径
	 * @param tagName	标签名
	 * @return String	标签内容
	 * @usage	查找单一节点的内容
	 */
	public static String getContent(String path,String tagName){
		File source =  new File(path);
		if (!source.canRead()) {
			System.out.println("【   File Can't Read   】");
			return "No result";
		}
		try {
			//System.out.println("开始解析");
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(source);// 得到doc
			Node node = doc.selectSingleNode("//" + tagName);
			System.out.println("【   " + node+"   】");
			return node.getText();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == DocumentException.class) {
				System.out.println("文档读取出错");
			}
			return "No result";
		}
	}
	/**
	 * @param path	XML文件路径
	 * @param nodeName	节点名称
	 * @param attrName	属性名称
	 * @return List<String>	所有对应的属性值
	 * @usage	用于得到XML中相应节点相应属性集的集合，例如一篇文章中所有的QUESTION 下面的QUESTION_TYPE值
	 */
	public static List<String> getAttributes(String path,String nodeName,String attrName){
//		String xPath = "//"+nodeName+"[@"+atrName+"]";
		String xPath = "//"+nodeName;
		System.out.println("【   XPath" + xPath+"   】");
		File source =  new File(path);
		ArrayList<String> result  = new ArrayList<>();
		if (!source.canRead()) {
			System.out.println("【   File Can't Read   】");
			return null;
		}
		try {
			// System.out.println("开始解析");
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(source);// 得到doc
			//得到相应的节点列表
			List<Node> nodesList = doc.selectNodes(xPath);
			Element currentEle =null;
			
			//得到迭代器
			for (Iterator it = nodesList.iterator(); it.hasNext();) {
				
				currentEle  = (Element) it.next();//对于每一个找到的节点
				
				System.out.println("【   attr*" +currentEle.attributeValue(attrName) +"   】");
				//添加该节点对应的属性值
				result.add(currentEle.attributeValue(attrName));
			}			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == DocumentException.class) {
				System.out.println("文档读取出错");
			}
			return null;
		}
	}
	
	/**
	 * @param path	XML文件路径
	 * @param nodeName	节点名
	 * @param attrName	属性名
	 * @param attrValue	属性需要等于什么
	 * @return List<String>	节点值
	 * @usage	按照相应的属性条件，返回相应的节点值
	 */
	public static List<String> getContentsWithAttrEqualing(String path,String nodeName,String attrName,String attrValue){
		/*
		 * http://www.w3school.com.cn/xpath/xpath_syntax.asp
		 * //title[@*]	选取所有带有属性的 title 元素。
		 * //title[@lang='eng']
		 */
		
		String xPath = "//"+nodeName+"[@"+attrName+"=\'"+attrValue+"\']";//XPATH路径
		//判断相等 例如 title[@lang='eng']
		System.out.println(" 【   XPATH -------" +xPath +"   】");
		
		File source =  new File(path);
		ArrayList<String> results = new ArrayList<>();
		if (!source.canRead()) {
			System.out.println("【   File Can't Read   】");
			return null;
		}
		try {
			// System.out.println("开始解析");
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(source);// 得到doc
			List<Node> nodes = doc.selectNodes(xPath);
			
			//遍历所有节点
			for(Node node:nodes){
				results.add(node.getStringValue());
			}
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == DocumentException.class) {
				System.out.println("文档读取出错");
			}
			return null;
		}

	}
	
	/**
	 * @param path	文件路径
	 * @param nodeName	节点名称
	 * @return List<String>
	 * @usage	返回指定XML中某节点的所有属性以及内容，每个元素代表一个节点
	 */
	public static List<String> getContAndAttrsInOrder(String path,String nodeName){
		String xPath = "//"+nodeName;
		System.out.println("【   XPATH -------" +xPath +"   】");
		File source =  new File(path);
		ArrayList<String> results = new ArrayList<>();
		if (!source.canRead()) {
			System.out.println("【   File Can't Read   】");
			return null;
		}
		try {
			// System.out.println("开始解析");
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(source);// 得到doc
			//得到所有相关节点
			List<Node> nodes = doc.selectNodes(xPath);
			String oneRow = "";//一行内容，代表属性以及内容的有序排列
			
			for(Node node:nodes){
				
				oneRow = "";//新的节点初始化新的一行
				
				//遍历所有属性
				List<Attribute> attrs= ((Element)node).attributes();
				
				for(Attribute attr :attrs){
					System.out.println("Attribute attr :attrs ");
					System.out.println("【 " +attr.asXML() +"   】");
					//依次添加所有属性值
					oneRow += attr.getValue()+"\t";
				}
				
				//添加节点的内容值
				oneRow += node.getStringValue();
				results.add(oneRow);
			}
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getClass() == DocumentException.class) {
				System.out.println("文档读取出错");
			}
			return null;
		}

	}
	
	/**
	 * @param path	文件路径
	 * @param nodeName	节点名称
	 * @return List<String>
	 * @usage	返回指定XML中某节点的所有属性以及内容，每个元素代表一个节点
	 */
	public static List<String> getContAndAttrsInOrder(File path,String nodeName){
		return XMLReader.getContAndAttrsInOrder(path.getPath(),nodeName);
	}
	public static void main(String[] args) {
		String result  = XMLReader.getContent("D:/149790.0.xml", "HEADLINE");
		
		System.out.println("【   " + result+"   】");
		System.out.println("【   " + XMLReader.getAttributes("C:/Users/筱钰/Documents/【【【【【【结果】】】】】】"
				+ "/Part018/150612_018.xml", "QUESTION", "QUESTION_TYPE")+"   】");
		
		System.out.println("【   " + XMLReader.getContentsWithAttrEqualing("C:/Users"
				+ "/筱钰/Documents/【【【【【【结果】】】】】】"
				+ "/Part018/150612_018.xml","QUESTION","QUESTION_TYPE","1" )+" 】");
		
		System.out.println("【   " + XMLReader.getAttributes("D:/150832_018.xml", "CHARSEQ", "START")+" 】");
		
		System.out.println("【   " + XMLReader.getContAndAttrsInOrder("D:/150832_018.xml", "CHARSEQ")+"   】");

	}

}
