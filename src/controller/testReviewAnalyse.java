package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.BasicDBObject;

import mongoDBHelper.IMongoDBHelper;
import mongoDBHelper.MongoDBHelper;

public class testReviewAnalyse {
//	public static void main(String [] args) {
//		reviewAnalyse test = new reviewAnalyse();
//		Document testtest = test.run("001");
//		System.out.println(testtest.toJson());
//	}
	public static void main(String [] args) {
//		IMongoDBHelper dbHelper = new MongoDBHelper();
//		String user_id = "1264846302@qq.com";
//		Document analyseResult = null;
//		
//		BasicDBObject query = new BasicDBObject("user_id", user_id);
//		Set<String> key_classi = dbHelper.FindDocumentsBy("user_label_classification", query).get(0).keySet();
//		Set<String> key_sour = dbHelper.FindDocumentsBy("user_label_resource", query).get(0).keySet();
//		double[] classiMatrix = new double[key_classi.size() - 2];
//		double[] sourMatrix = new double[key_sour.size() - 2];
//		double[][] resultMatrix = new double[classiMatrix.length][sourMatrix.length];
//		
//		for (int i = 2; i < key_classi.toArray().length; i++) {
//			classiMatrix[i - 2] = (double)(dbHelper.FindDocumentsBy("user_label_classification", query).get(0)
//					.get((String) key_classi.toArray()[i]));
//		}
//		for (int i = 2; i < key_sour.toArray().length; i++) {
//			sourMatrix[i - 2] = (double)(dbHelper.FindDocumentsBy("user_label_resource", query).get(0)
//					.get((String) key_sour.toArray()[i]));
//		}
//
//		int i_index = 0;
//		int j_index = 0;
//		double temp = 0;
//
//		for (int i = 0; i < classiMatrix.length; i++) {
//			for (int j = 0; j < sourMatrix.length; j++) {
//				resultMatrix[i][j] = classiMatrix[i] * sourMatrix[j];
//				if (resultMatrix[i][j] > temp) {
//					temp = resultMatrix[i][j];
//					i_index = i;
//					j_index = j;
//				}
//			}
//		}
//		
//		System.out.println(i_index+"  "+j_index+" "+temp);
//
//		query = new BasicDBObject("classification", (String) key_classi.toArray()[i_index + 2])
//				.append("sources", (String) key_sour.toArray()[j_index + 2]);
//		List<Document> resultArticle = dbHelper.FindDocumentsBy("article", query);
//		//错误改动
//		//boolean isFind = false;
//		for (int i = 0; i < resultArticle.size(); i++) {
//			boolean isFind = false;
//			List<String> pulled_user = (List<String>) resultArticle.get(i).get("pulled_user");
//			for (int j = 0; j < pulled_user.size(); j++) {
//				if (user_id.equals(pulled_user.get(j))) {
//					isFind = true;
//					break;
//				}
//			}
//			if (!isFind)
//			analyseResult = resultArticle.get(i);
//		}
//		if(analyseResult == null)
//			return;
//		String articleTitle = (String) analyseResult.get("article_title");
//		System.out.println(articleTitle);
//		List<String> pulleduserList = ((ArrayList<String>)(analyseResult.get("pulled_user")));
//		pulleduserList.add(user_id);
//		query = new BasicDBObject("article_title",articleTitle);
//		BasicDBObject insert = new BasicDBObject("pulled_user",pulleduserList);
//		dbHelper.updateDocuments("article", query, insert);
	}
}
