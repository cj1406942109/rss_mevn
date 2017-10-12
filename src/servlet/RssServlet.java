package servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
		resp.setCharacterEncoding("utf-8");
		//resp.setHeader("Access-Control-Allow-Headers", "");
		
		// 设置：Access-Control-Allow-Origin头，处理Session问题
//        resp.setHeader("Access-Control-Allow-Origin", resp.getHeader("Origin"));
//        resp.setHeader("Access-Control-Allow-Credentials", "true");
//        resp.setHeader("P3P", "CP=CAO PSA OUR");
//        if (req.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(req.getMethod())) {
//        	resp.addHeader("Access-Control-Allow-Methods", "POST,GET,TRACE,OPTIONS");
//        	resp.addHeader("Access-Control-Allow-Headers", "Content-Type,Origin,Accept");
//        	resp.addHeader("Access-Control-Max-Age", "120");
//        }
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
		resp.setHeader("Access-Control-Allow-Credentials","true");
		String user_id = req.getParameterValues("user_id")[0];
		reviewAnalyse reviewResult = new reviewAnalyse();
		Document articleResult = reviewResult.run(user_id);
		int status = 0;
		String message = "";
		Document toFront = new Document();
		if (articleResult == null) {
			status = 0;
			message = "There aren't any articles anymore, :)";
			System.out.println("There aren't any articles anymore, :)");
			toFront.append("status", status).append("message", message).append("data", "");
		} else {
			status = 1;
			message = "successful";
			System.out.println(articleResult.toJson());
			toFront.append("status", status).append("message", message).append("data", articleResult);
		}
		try {
			PrintWriter out = resp.getWriter();
			out.print(toFront.toJson());
			System.out.println(toFront.toJson());
			out.flush();
			out.close();
		}catch(Exception e) {
			
		}
	}
}
