package mongoDBHelper;

import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;

public interface IMongoDBHelper {
	// 增加一系列数据documents并将其插入collectionName容器中
	void InsertDocuments(String collectionName, List<Document> documents);

	// 更新操作
	void updateDocuments(String collectionName, BasicDBObject query, BasicDBObject result);

	// 查询collectionName容器中的所有条目并返回一个document的list
	List<Document> FindDocuments(String collectionName);

	// 条件查询collectionName容器中的所有条目并返回一个document的list
	List<Document> FindDocumentsBy(String collectionName, BasicDBObject condition);
}
