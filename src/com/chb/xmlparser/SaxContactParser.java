package com.chb.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hempel on 14-2-28.
 */
public class SaxContactParser implements ContactParser{
	@Override
	public List<Contact> parse(InputStream is) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();	//取得SAXParserFactory实例
		SAXParser parser = factory.newSAXParser();					//从factory获取SAXParser实例
		MyHandler handler = new MyHandler();						//实例化自定义Handler
		parser.parse(is, handler);									//根据自定义Handler规则解析输入流
		return handler.getContacts();
	}

	@Override
	public String serialize(List<Contact> contacts) throws Exception {
		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();//取得SAXTransformerFactory实例
		TransformerHandler handler = factory.newTransformerHandler();			//从factory获取TransformerHandler实例
		Transformer transformer = handler.getTransformer();						//从handler获取Transformer实例
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");			// 设置输出采用的编码方式
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");				// 是否自动添加额外的空白
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");	// 是否忽略XML声明

		StringWriter writer = new StringWriter();
		Result result = new StreamResult(writer);
		handler.setResult(result);

		String uri = "";	//代表命名空间的URI 当URI无值时 须置为空字符串
		String localName = "";	//命名空间的本地名称(不包含前缀) 当没有进行命名空间处理时 须置为空字符串

		handler.startDocument();
		handler.startElement(uri, localName, "books", null);

		AttributesImpl attrs = new AttributesImpl();	//负责存放元素的属性信息
		char[] ch = null;
		for (Contact contact : contacts) {
//			attrs.clear();	//清空属性列表
//			attrs.addAttribute(uri, localName, "name", "string", contact.getName());//添加一个名为id的属性(type影响不大,这里设为string)
//			handler.startElement(uri, localName, "contact", attrs);	//开始一个book元素 关联上面设定的id属性
//
//			handler.startElement(uri, localName, "name", null);	//开始一个name元素 没有属性
//			ch = String.valueOf(contact.getName()).toCharArray();
//			handler.characters(ch, 0, ch.length);	//设置name元素的文本节点
//			handler.endElement(uri, localName, "name");
//
//			handler.startElement(uri, localName, "price", null);//开始一个price元素 没有属性
//			ch = String.valueOf(contact.getPrice()).toCharArray();
//			handler.characters(ch, 0, ch.length);	//设置price元素的文本节点
//			handler.endElement(uri, localName, "price");
//
//			handler.endElement(uri, localName, "book");
		}
//		handler.endElement(uri, localName, "books");
//		handler.endDocument();

		return writer.toString();
	}

	//需要重写DefaultHandler的方法
	private class MyHandler extends DefaultHandler {

		private List<Contact> contacts;
		private Contact contact;
		private StringBuilder builder;

		//返回解析后得到的Book对象集合
		public List<Contact> getContacts() {
			return contacts;
		}

		@Override
		public void startDocument() throws SAXException {
			super.startDocument();
			contacts = new ArrayList<Contact>();
			builder = new StringBuilder();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			super.startElement(uri, localName, qName, attributes);
			if (localName.equals("contact")) {
				contact = new Contact();
			}
			builder.setLength(0);	//将字符长度设置为0 以便重新开始读取元素内的字符节点
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			super.characters(ch, start, length);
			builder.append(ch, start, length);	//将读取的字符数组追加到builder中
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			super.endElement(uri, localName, qName);
			if (localName.equals("name")) {
				contact.setName(builder.toString());
			} else if (localName.equals("birthday")) {
				contact.setBirthday(builder.toString());
			} else if (localName.equals("birthdayMoon")) {
				contact.setBirthdayMoon(builder.toString());
			} else if (localName.equals("contact")) {
				contacts.add(contact);
			}
		}
	}
}
