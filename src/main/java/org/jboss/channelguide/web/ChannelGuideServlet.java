package org.jboss.channelguide.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.jboss.channelguide.model.Category;
import org.jboss.channelguide.model.Video;

public class ChannelGuideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String videoUrl = req.getParameter("vurl");
		String videoThumb = req.getParameter("vth");
		String videoTitle = req.getParameter("vti");
		String videoCategory = req.getParameter("vc");

		String retStr = "";
		try {
			Session session = sessionFactory.getCurrentSession();
			Transaction tx = session.beginTransaction();
			Video newVideo = new Video(videoUrl, videoThumb, videoTitle,
					new Category(videoCategory));

			session.saveOrUpdate(newVideo);
			tx.commit();
			
			retStr = "video stored";
		} catch (Exception e) {
			retStr = "error storing video: " + e.getMessage();
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().write(retStr);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String format = req.getParameter("format");
		if(format == null || format.equals("json")) {
			String json = "{ \"videos\": [\n";
			try {
				Session session = sessionFactory.getCurrentSession();
				Transaction tx = session.beginTransaction();
				@SuppressWarnings("unchecked")
				List<Category> categories = session.createQuery("from Category").list();
				if(categories != null && categories.size() > 0) {
					String videosStr = "";
					for(Category c : categories) {
						videosStr += c.toJSONString();
					}
					if(videosStr.endsWith(",")) {
						videosStr = videosStr.substring(0, videosStr.length() - 1);
					}
					json += videosStr;
				}
				tx.commit();
			} catch (Exception e) {
				System.out.println("ERROR GETTING VIDEO INFO: " + e.getMessage());
			}
			json += "\n]}";
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("application/json");
	    	resp.getWriter().print(json);
		}
	}
}
