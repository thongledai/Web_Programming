package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int userid;

	@Column(name = "username", columnDefinition = "nvarchar(50) not null")
	@NotEmpty(message = "Không được phép rỗng")
	private String username;

	@Column(name = "password", columnDefinition = "nvarchar(50) not null")
	@NotEmpty(message = "Không được phép rỗng")
	private String password;

	@Column(name = "fullname", columnDefinition = "nvarchar(50)")
	private String fullname;

	@Column(name = "email", columnDefinition = "nvarchar(50)")
	@NotEmpty(message = "Không được phép rỗng")
	private String email;

	@Column(name = "phone")
	private String phone;

	@ManyToOne
	@JoinColumn(name = "roleid")
	private Role role;

	@Column(name = "avatar", columnDefinition = "nvarchar(50)")
	private String avatar;

	@Column(name = "code", columnDefinition = "nvarchar(50)")
	private String code;

	@Column(name = "createdate")
	private Date createdate;

	@Column(name = "sell_id")
	private Integer sellId;

	@Column(name = "status")
	private int status;

	@Transient
	private String OTP;

	public User() {
		super();
	}

	public User(
			@NotEmpty(message = "Không được phép rỗng") String username,
			@NotEmpty(message = "Không được phép rỗng") String password,
			@NotEmpty(message = "Không được phép rỗng") String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Integer getSellId() {
		return sellId;
	}

	public void setSellId(Integer sellId) {
		this.sellId = sellId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String OTP) {
		this.OTP = OTP;
	}

	public Integer getRoleid() {
		return role != null ? role.getRoleid() : null;
	}
}