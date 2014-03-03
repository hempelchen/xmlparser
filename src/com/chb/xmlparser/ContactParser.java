package com.chb.xmlparser;

import java.io.InputStream;
import java.util.List;

/**
 * Created by hempel on 14-2-28.
 */
public interface ContactParser {
	/**
	 * 解析输入流 得到Book对象集合
	 *
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public List<Contact> parse(InputStream is) throws Exception;

	/**
	 * 序列化Book对象集合 得到XML形式的字符串
	 *
	 * @param books
	 * @return
	 * @throws Exception
	 */
	public String serialize(List<Contact> contacts) throws Exception;
}
