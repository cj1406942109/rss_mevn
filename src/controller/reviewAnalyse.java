package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.BasicDBObject;

import mongoDBHelper.IMongoDBHelper;
import mongoDBHelper.MongoDBHelper;

public class reviewAnalyse {
	private String user_id;
	IMongoDBHelper dbHelper;
	Document analyseResult;

	public reviewAnalyse() {
		this.user_id = "";
		this.dbHelper = new MongoDBHelper();
		this.analyseResult = null;
	}
	
	public Document run(String user_id) {
		this.user_id = user_id;
		analyse();
		return this.analyseResult;
	}

	public void analyse() {
		// 先查找label表，判断当前用户在label表中是否存在，如果不存在就新建一个用户
		BasicDBObject query = new BasicDBObject("user_id", this.user_id);
		if (dbHelper.FindDocumentsBy("user_label_classification", query).size() == 0) {
			// 分别在两个label表中插入字段，初始值为0.5
			List<Document> insert = new ArrayList<Document>();
			insert.add(new Document("user_id", user_id).append("体育", 0.5).append("文化", 0.5).append("游戏", 0.5)
					.append("科技", 0.5).append("财经", 0.5));
			dbHelper.InsertDocuments("user_label_classification", insert);
			insert.clear();
			insert.add(new Document("user_id", user_id).append("期刊", 0.5).append("百科", 0.5).append("自媒体", 0.5));
			dbHelper.InsertDocuments("user_label_resource", insert);
		}
		// 查询review表判断是否有新读后感,创建查询条件： user_id = user_id && is_new = true
		query = new BasicDBObject("user_id", this.user_id).append("is_new", true);
		// 根据查询条件查询判断是否存在新的读后感
		List<Document> documents = dbHelper.FindDocumentsBy("review", query);
		List<Document> insertArticleDocuments = new ArrayList<Document>();
		List<Document> insertUserLabelDocuments = new ArrayList<Document>();
		// 获取当前article表中已经有多少个元素了，用于插入新的文章时增加id用
		int indexBeginAt = dbHelper.FindDocuments("article").size();
		// 如果存在新的读后感
		if (documents.size() > 0) {
			// 查找article库，如果该文章为新文章则入库
			for (int i = 0; i < documents.size(); i++) {
				// 创建查找条件为文章标题
				Document article = (Document)documents.get(i).get("article");
				String articleTitle = (String)((Document)documents.get(i).get("article")).get("title");
				query = new BasicDBObject("article_title", articleTitle);
				// 查找article库，判断是否已经存在
				if (dbHelper.FindDocumentsBy("article", query).size() > 0) {
					Document findArticle = dbHelper.FindDocumentsBy("article", query).get(0);
					List<String> pulleduserList = ((ArrayList<String>)(findArticle.get("pulled_user")));
					pulleduserList.add(user_id);
					dbHelper.updateDocuments("article", 
							new BasicDBObject("article_title", articleTitle), 
							new BasicDBObject("pulled_user", pulleduserList));
				}
				// 如果不存在就加进去，并且将当前用户标示为已读
				else {
					// 创建新文章
					Document document = new Document();
					document.append("article_id", String.valueOf(++indexBeginAt)).append("article_title", articleTitle)
							.append("author", (String)article.get("author"))
							.append("classification", (String)article.get("classification"))
							.append("sources", (String)article.get("sources"))
							// 要改
							.append("link", "")
							.append("content", "")
							.append("pulled_user", Arrays.asList(user_id));
					insertArticleDocuments.add(document);
				}

				// 根据提取的读后感更新用户label表
				updateLabel(user_id, (String) article.get("classification"),
						(String) article.get("sources"), (boolean) documents.get(i).get("is_like"));
			}
			query = new BasicDBObject("user_id", this.user_id).append("is_new", true);
			BasicDBObject result = new BasicDBObject("is_new", false);
			dbHelper.updateDocuments("review", query, result);
			dbHelper.InsertDocuments("article", insertArticleDocuments);
		}
		
		cal();
		// 否则进行标签匹配推送
	}

	public void updateLabel(String user_id, String classification, String sources, boolean isLike) {
		// 设置搜索依据——user_id
		BasicDBObject query = new BasicDBObject("user_id", user_id);

		// 获取isLike信息
		// double类型，0代表不喜欢，1代表非常喜欢
		// 默认值0.5
		// a = a+0.1;
		double classi = (double) dbHelper.FindDocumentsBy("user_label_classification", query).get(0)
				.get(classification);
		double sour = (double) dbHelper.FindDocumentsBy("user_label_resource", query).get(0).get(sources);
		if (isLike) {
			if (classi != 1)
				classi = classi + 0.1;
			if (sour != 1)
				sour = sour + 0.1;
		} else {
			if (classi != 0)
				classi = classi - 0.1;
			if (sour != 0)
				sour = sour - 0.1;
		}

		// 创建搜索结果
		BasicDBObject resultClassi = new BasicDBObject(classification, classi);
		BasicDBObject resultSour = new BasicDBObject(sources, sour);
		// 更新表
		dbHelper.updateDocuments("user_label_classification", query, resultClassi);
		dbHelper.updateDocuments("user_label_resource", query, resultSour);
	}

	public void cal() {
		BasicDBObject query = new BasicDBObject("user_id", user_id);
		Set<String> key_classi = dbHelper.FindDocumentsBy("user_label_classification", query).get(0).keySet();
		Set<String> key_sour = dbHelper.FindDocumentsBy("user_label_resource", query).get(0).keySet();
		double[] classiMatrix = new double[key_classi.size() - 1];
		double[] sourMatrix = new double[key_sour.size() - 1];
		double[][] resultMatrix = new double[classiMatrix.length][sourMatrix.length];
		
		for (int i = 2; i < key_classi.toArray().length; i++) {
			classiMatrix[i - 2] = (double)(dbHelper.FindDocumentsBy("user_label_classification", query).get(0)
					.get((String) key_classi.toArray()[i]));
		}
		for (int i = 2; i < key_sour.toArray().length; i++) {
			sourMatrix[i - 2] = (double)(dbHelper.FindDocumentsBy("user_label_resource", query).get(0)
					.get((String) key_sour.toArray()[i]));
		}

		int i_index = 0;
		int j_index = 0;
		double temp = 0;

		for (int i = 0; i < classiMatrix.length; i++) {
			for (int j = 0; j < sourMatrix.length; j++) {
				resultMatrix[i][j] = classiMatrix[i] * sourMatrix[j];
				if (resultMatrix[i][j] > temp) {
					temp = resultMatrix[i][j];
					i_index = i;
					j_index = j;
				}
			}
		}
		
		System.out.println(i_index+"  "+j_index+" "+temp);

		query = new BasicDBObject("classification", (String) key_classi.toArray()[i_index + 2])
				.append("sources", (String) key_sour.toArray()[j_index + 2]);
		List<Document> resultArticle = dbHelper.FindDocumentsBy("article", query);
		boolean isFind = false;
		for (int i = 0; i < resultArticle.size(); i++) {
			List<String> pulled_user = (List<String>) resultArticle.get(i).get("pulled_user");
			for (int j = 0; j < pulled_user.size(); j++) {
				if (user_id.equals(pulled_user.get(j))) {
					isFind = true;
					break;
				}
			}
			if (!isFind)
				this.analyseResult = resultArticle.get(i);
		}
		String articleTitle = (String) this.analyseResult.get("article_title");
		List<String> pulleduserList = ((ArrayList<String>)(this.analyseResult.get("pulled_user")));
		pulleduserList.add(user_id);
		query = new BasicDBObject("article_title",articleTitle);
		BasicDBObject insert = new BasicDBObject("pulled_user",pulleduserList);
		dbHelper.updateDocuments("article", query, insert);
	}
}
