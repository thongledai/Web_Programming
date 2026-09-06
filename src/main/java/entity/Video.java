package entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity

@Table(name = "Videos")

@NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v")

public class Video implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id

	@Column(name = "VideoId")

	private String videoId;

	@Column(name = "Active")

	private int active;

	@Column(name = "Description", columnDefinition = "nvarchar(500) null")

	private String description;

	@Column(name = "Poster", columnDefinition = "nvarchar(500) null")

	private String poster;

	@Column(name = "Title", columnDefinition = "nvarchar(500) null")

	private String title;

	@Column(name = "Views")

	private int views;

	public Video(String videoId, int active, String description, String poster, String title, int views,
			Category category) {
		super();
		this.videoId = videoId;
		this.active = active;
		this.description = description;
		this.poster = poster;
		this.title = title;
		this.views = views;
		this.category = category;
	 }
	public Video() {
		super();
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	@Override
	public String toString() {
		return "Video [videoId=" + videoId + ", active=" + active + ", description=" + description + ", poster="
				+ poster + ", title=" + title + ", views=" + views + "]";
	}
	@ManyToOne

	 @JoinColumn(name="CategoryId")


	 private Category category;

	 public Category getCategory() {
			return category;
		}
	 public void setCategory(Category category) {
			this.category = category;
	}

	 
}