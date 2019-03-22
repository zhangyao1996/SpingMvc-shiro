package com.zhangyao.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author zhangyao
 * @version Mar 19, 2019 3:03:46 PM
 */
public class DocumentUtils {

	// 读写XML文档主要依赖于org.dom4j.io包，有DOMReader和SAXReader两种方式
	public static Document load(String filename) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(filename)); // 读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	public static Document load(URL url) {
		Document document = null;
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(url); // 读取XML文件,获得document对象
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}
	
	
	//写入XML文件
	public static boolean doc2XmlFile(Document document, String filename) {  
	    boolean flag = true;  
	    try {  
	        XMLWriter writer = new XMLWriter(new OutputStreamWriter(  
	                new FileOutputStream(filename), "UTF-8"));  
	        writer.write(document);  
	        writer.close();  
	    } catch (Exception ex) {  
	        flag = false;  
	        ex.printStackTrace();  
	    }  
	    System.out.println(flag);  
	    return flag;  
	}
	


}
