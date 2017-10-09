package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import controller.reviewAnalyse;
import mongoDBHelper.IMongoDBHelper;
import mongoDBHelper.MongoDBHelper;

public class RssServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String user_id = req.getParameterValues("user_id")[0];
		reviewAnalyse reviewResult = new reviewAnalyse();
		Document articleResult = reviewResult.run(user_id);
		int status = 0;
		String message = "";
		Document toFront = new Document();
		if (articleResult == null) {
			status = 0;
			message = "文章没有啦！请耐心等待管理员导入新文章";
			System.out.println("文章没有啦！请耐心等待管理员导入新文章");
		} else {
			status = 1;
			message = "successful";
			System.out.println(articleResult.toJson());
		}
		toFront.append("status", status).append("message", message).append("data", reviewResult);
	}
}
