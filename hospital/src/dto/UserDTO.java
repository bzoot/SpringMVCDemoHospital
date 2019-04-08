package dto;

/**   
* @Title: UserDTO.java 
* @Package user.dto 
* @Description:  
* @author Pengbin Li   
* @date 2017年8月19日 下午3:07:20 
* @version V1.0   
*/

public class UserDTO extends AbstractDTO {

	private String userName;

	private String trueName;
	
	private String password;
	
	private boolean admin;
	
	private String phone;
	
	private String identify;
	
	private Long doctor;

	public UserDTO() {}

	public UserDTO(String userName, String trueName, String password, boolean admin,
			String phone, String identify, Long doctor) {
		super();
		this.userName = userName;
		this.trueName = trueName;
		this.password = password;
		this.admin = admin;
		this.phone = phone;
		this.identify = identify;
		this.doctor = doctor;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public Long getDoctor() {
		return doctor;
	}

	public void setDoctor(Long doctor) {
		this.doctor = doctor;
	}
	
}