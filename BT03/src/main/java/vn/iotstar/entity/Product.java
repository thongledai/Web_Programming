package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productid")
	private int productId;

	@Column(name = "productname", columnDefinition = "nvarchar(255)", nullable = false)
	private String productName;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "description", columnDefinition = "nvarchar(1000)")
	private String description;

	@Column(name = "image", columnDefinition = "nvarchar(500)")
	private String image;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryid", nullable = false)
	private Category category;

	public Product() {
	}

	public Product(String productName, double price, String description, String image, Category category) {
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.image = image;
		this.category = category;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}