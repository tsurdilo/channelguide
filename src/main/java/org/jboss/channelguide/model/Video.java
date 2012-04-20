package org.jboss.channelguide.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Video {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String url;
	private String thumb;
	private String title;
	@ManyToOne(cascade={CascadeType.ALL})
	private Category category;
	
	public Video() {} 
	public Video(String url, String thumb, String title, Category category) {
		this.url = url;
		this.thumb = thumb;
		this.title = title;
		this.category = category;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		
		this.category = category;
        if (!category.getVideos().contains(this)) {
        	category.getVideos().add(this);
        }
		
		this.category = category;
	}
	
	public String toJSONString() {
		StringBuffer buff = new StringBuffer();
		buff.append("{\"videourl\": \"").append(this.url).append("\", \"videothumb\": \"").append(this.thumb).append("\", \"videotitle\": \"").append(this.title).append("\", \"videocategory\": \"").append(this.category.getName()).append("\" }");
		return buff.toString();
	}
}