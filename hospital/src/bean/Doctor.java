package bean;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;

/**   
* @Title: Doctor.java 
* @Package bean 
* @Description:  
* @author Pengbin Li   
* @date 2018年1月7日 下午3:58:56 
* @version V1.0   
*/

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true, dynamicInsert = true)
@Table(name="doctor", schema = "hospital")
public class Doctor extends AbstractBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8382528793723623281L;
	
	private String doctorName;
	
	private String doctorDesc;
	
	private int expert; //1是0否，-1所有
	
	private Long department;
	
	private String departmentName;
	
	private Long user;
	
	private int bookable; //1是0否，-1所有
	
	private ArrayList<Integer> onDuty;

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorDesc() {
		return doctorDesc;
	}

	public void setDoctorDesc(String doctorDesc) {
		this.doctorDesc = doctorDesc;
	}

	public int getExpert() {
		return expert;
	}

	public void setExpert(int expert) {
		this.expert = expert;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public ArrayList<Integer> getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(ArrayList<Integer> onDuty) {
		this.onDuty = onDuty;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public int getBookable() {
		return bookable;
	}

	public void setBookable(int bookable) {
		this.bookable = bookable;
	}

	@Override
	public String toString() {
		return "Doctor [doctorName=" + doctorName + ", doctorDesc=" + doctorDesc + ", expert=" + expert
				+ ", department=" + department + ", departmentName=" + departmentName + ", user=" + user
				+ ", bookable=" + bookable + "]";
	}
}
