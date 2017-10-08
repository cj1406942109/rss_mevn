package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import mongoDBHelper.IMongoDBHelper;
import mongoDBHelper.MongoDBHelper;

/**
 * 
 * @author othert
 * 
 * 该类用于一键导入txt格式文件类型的文章
 *
 */

public class articleInsert {
	private List<Document> documents;
	private int indexBenginAt;
	
	public articleInsert() {
		this.documents = new ArrayList<Document>();
		this.indexBenginAt = 0;
	}

	public void insert(String articlePath,String collectionName) {
		// 建立数据库链接
		IMongoDBHelper dbHelper = new MongoDBHelper();
		// 获取当前表中有多少个元素，从接下来一个元素开始设置文章id
		this.indexBenginAt = dbHelper.FindDocuments(collectionName).size();
		// 读txt
		readFileByLines(articlePath);
		// 插入数据
		dbHelper.InsertDocuments("article", documents);
		
	}

	public void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				String[] aLine = tempString.split("\t");
				Document document = new Document();
				document.append("article_id",String.valueOf(++this.indexBenginAt)).
				append("article_title", aLine[0]).
				append("author", aLine[4]).
				append("classification", aLine[2]).
				append("sources", aLine[3]).
				append("link", aLine[1]).
				append("content", "").
				append("pulled_user", Arrays.asList());
				this.documents.add(document);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
}
