package org.jboss.channelguide.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Category {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	@OneToMany(cascade= CascadeType.ALL, mappedBy="category") @OrderBy("title")
	private List<Video> videos = new ArrayList<Video>();
	
	public Category(){}
	public Category(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	public void addVideo(Video video) {
		this.videos.add(video);
        if (video.getCategory() != this) {
            video.setCategory(this);
        }
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toJSONString() {
		StringBuffer buff = new StringBuffer();
		for(Video v : this.getVideos()) {
			buff.append(v.toJSONString()).append(",");
		}
		return buff.toString();
	}
	
}
