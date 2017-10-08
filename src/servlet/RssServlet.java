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
		doPost(req,resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String user_id = req.getParameterValues("user_id")[0];
		reviewAnalyse test = new reviewAnalyse();
		Document testtest = test.run(user_id);
		System.out.println(testtest.toJson());
	}
}
