package bean;

import javax.persistence.Entity;
import javax.persistence.Table;

/**   
* @Title: User.java 
* @Package user.bean 
* @Description:  
* @author Pengbin Li   
* @date 2017年8月19日 下午3:07:20 
* @version V1.0   
*/

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true, dynamicInsert = true)
@Table(name="user", schema = "hospital")
public class User extends AbstractBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4614611149769519802L;

	private String userName;
	
	private String trueName;
	
	private String password;
	
	private boolean admin;
	
	private String phone;
	
	private String identify;
	
	private Long doctor;

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

	@Override
	public String toString() {
		return "User [userName=" + userName + ", trueName=" + trueName + ", password=" + password
				+ ", isAdmin=" + admin + ", phone=" + phone + ", identify=" + identify + ", doctor="
				+ doctor + "]";
	}
}
