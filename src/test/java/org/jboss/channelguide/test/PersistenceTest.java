package org.jboss.channelguide.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.jboss.channelguide.model.Category;
import org.jboss.channelguide.model.Video;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PersistenceTest extends TestCase {
	public PersistenceTest(String testName) {
		super(testName);
	}

	public static Test suite() {
		return new TestSuite(PersistenceTest.class);
	}

	public void testSaveCategory() throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration()
				.configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Category cat = new Category();
		cat.setName("jbpm videos");

		Video v1 = new Video();
		v1.setCategory(cat);
		v1.setThumb("thumb1");
		v1.setTitle("vid1");
		v1.setUrl("url1");

		Video v2 = new Video();
		v2.setCategory(cat);
		v2.setThumb("thumb2");
		v2.setTitle("vid2");
		v2.setUrl("url2");

		List<Video> videos = new ArrayList<Video>();
		videos.add(v1);
		videos.add(v2);
		cat.setVideos(videos);

		session.saveOrUpdate(cat);

		@SuppressWarnings("unchecked")
		List<Category> categories = session.createQuery("from Category").list();
		assertTrue(categories.size() == 1);
		assertTrue(categories.get(0).getVideos().size() == 2);

		@SuppressWarnings("unchecked")
		List<Video> searchedList = session.createQuery("from Video").list();
		assertTrue(searchedList.size() == 2);
		
		tx.rollback();
		sessionFactory.close();
	}

	public void testSaveVideo() throws Exception {
		SessionFactory sessionFactory = new AnnotationConfiguration()
				.configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Category cat = new Category();
		cat.setName("jbpm videos");

		Video v1 = new Video();
		v1.setCategory(cat);
		v1.setThumb("thumb1");
		v1.setTitle("vid1");
		v1.setUrl("url1");

		Video v2 = new Video();
		v2.setCategory(cat);
		v2.setThumb("thumb2");
		v2.setTitle("vid2");
		v2.setUrl("url2");

		
		session.saveOrUpdate(v1);
		session.saveOrUpdate(v2);

		@SuppressWarnings("unchecked")
		List<Category> categories = session.createQuery("from Category").list();
		assertTrue(categories.size() == 1);
		assertTrue(categories.get(0).getVideos().size() == 2);

		@SuppressWarnings("unchecked")
		List<Video> searchedList = session.createQuery("from Video").list();
		assertTrue(searchedList.size() == 2);
		
		tx.rollback();
		sessionFactory.close();
	}
}
