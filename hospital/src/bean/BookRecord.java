package bean;

import javax.persistence.Entity;
import javax.persistence.Table;

/**   
* @Title: BookRecord.java 
* @Package bean 
* @Description:  
* @author Pengbin Li   
* @date 2018年2月14日 下午3:56:29 
* @version V1.0   
*/

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true, dynamicInsert = true)
@Table(name="bookRecord", schema = "hospital")
public class BookRecord extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1895013228102013325L;
	
	private Long department;
	
	private String departmentName;
	
	private Long doctor;
	
	private String doctorName;
	
	private int expert; //1是0否 -1全
	
	private String bookDate;
	
	private Long root;
	
	private int total;
	
	private int time; //1上午 2下午
	
	private Long userId;

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public Long getDoctor() {
		return doctor;
	}

	public void setDoctor(Long doctor) {
		this.doctor = doctor;
	}

	public int getExpert() {
		return expert;
	}

	public void setExpert(int expert) {
		this.expert = expert;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public Long getRoot() {
		return root;
	}

	public void setRoot(Long root) {
		this.root = root;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Override
	public String toString() {
		return "BookRecord [department=" + department + ", doctor=" + doctor + ", expert=" + expert + ", bookDate="
				+ bookDate + ", root=" + root + ", total=" + total + ", time=" + time + ", userId" + userId
				+ ", departmentName=" + departmentName + ", doctorName=" + doctorName + "]";
	}
	
}
