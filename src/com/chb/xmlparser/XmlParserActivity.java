package com.chb.xmlparser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;


public class XmlParserActivity extends Activity {
	/**
	 * Called when the activity is first created.
	 */

	private static final String TAG = "XML";

//	private BookParser parser;
//	private List<Book> books;

	private ContactParser parser;
	private List<Contact> contacts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button readBtn = (Button) findViewById(R.id.readBtn);
		Button writeBtn = (Button) findViewById(R.id.writeBtn);

		readBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
//					InputStream is = getAssets().open("books.xml");
//					parser = new SaxBookParser();  //创建SaxBookParser实例
//					books = parser.parse(is);  //解析输入流
//					for (Book book : books) {
//						Log.i(TAG, book.toString());
//					}

					InputStream is = getAssets().open("birthday_chb.xml");
					parser = new SaxContactParser();  //创建SaxContactParser实例
					contacts = parser.parse(is);  //解析输入流
					for (Contact contact : contacts) {
						Log.i(TAG, contact.toString());
					}
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
				}
			}
		});
		writeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String xml = parser.serialize(contacts);  //序列化
					FileOutputStream fos = openFileOutput("books.xml", Context.MODE_PRIVATE);
					fos.write(xml.getBytes("UTF-8"));
				} catch (Exception e) {
					Log.e(TAG, e.getMessage());
				}
			}
		});
	}
}
