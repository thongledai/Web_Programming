package entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "categories")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "CategoryId")

	private int categoryid;

	@Column(name = "CategoryName", columnDefinition = "nvarchar(50) not null")

	@NotEmpty(message = "Không được phép rỗng")

	private String categoryname;

	@Column(name = "Images", columnDefinition = "nvarchar(500) null")

	private String images;

	private int status;

	public Category(int categoryid, @NotEmpty(message = "Không được phép rỗng") String categoryname, String images,
			int status, List<Video> videos) {
		super();
		this.categoryid = categoryid;
		this.categoryname = categoryname;
		this.images = images;
		this.status = status;
		this.videos = videos;
	}

	public Category() {
		super();
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Category [categoryid=" + categoryid + ", categoryname=" + categoryname + ", images=" + images
				+ ", status=" + status + "]";
	}

	 @OneToMany(mappedBy = "category")


	 private List<Video> videos;



	 public List<Video> getVideos() {
		return videos;
	}

	 public void setVideos(List<Video> videos) {
		 this.videos = videos;
	 }

	 public Video addVideo(Video video) {


	 getVideos().add(video);


	 video.setCategory(this);


	 return video;


	 }



	 public Video removeVideo(Video video) {


	 getVideos().remove(video);


	 video.setCategory(null);


	 return video;


	 }


	}

