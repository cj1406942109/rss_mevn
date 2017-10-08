package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.BasicDBObject;

import mongoDBHelper.IMongoDBHelper;
import mongoDBHelper.MongoDBHelper;

public class adminController {
//	public static void main(String [] args) {
////		System.out.println("---------------管理员登录界面---------------");
////		System.out.println("请输入密码：");
////		Scanner sa = new Scanner(System.in);
////		int passwd = sa.nextInt();
////		if(!(passwd == 123456))
////			return;
////		articleInsert test = new articleInsert();
////		test.insert("/Users/othert/Desktop/自媒体库.txt","article");
//		IMongoDBHelper dbHelper = new MongoDBHelper();
//		List<Document> documents = new ArrayList<Document>();
////		BasicDBObject result = new BasicDBObject("user_id","001").append("article", "{}").append("author", "无")
////				.append("classification", "游戏").append("sources", "百科").append("is_like", true).append("is_new", true);
////		public void updateDocuments(String collectionName, BasicDBObject query, BasicDBObject result) {
////		BasicDBObject query = new BasicDBObject("article_title", "DOTA2");
////		BasicDBObject result = new BasicDBObject("期刊",0.5)
////				.append("百科", 0.5)
////				.append("自媒体", 0.5);
////		dbHelper.updateDocuments("article", query, new BasicDBObject("pulled_user",Arrays.asList()));
//		//dbHelper.InsertDocuments("review", documents);
//		
//		Document documentUser = new Document("title","DOTATEST").append("author", "无").append("sources", "百科").append("classification", "游戏");
//		Document insertUser = new Document("user_id","001").append("article", documentUser).append("is_like", true).append("is_new", true);
//		//BasicDBObject query = new BasicDBObject("title", "DOTA2");
//		List<Document> test = new ArrayList<Document>();
//		test.add(insertUser);
//		dbHelper.InsertDocuments("review", test);
//	}
}
