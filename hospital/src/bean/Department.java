package bean;

import javax.persistence.Entity;
import javax.persistence.Table;

/**   
* @Title: Department.java 
* @Package bean 
* @Description:  
* @author Pengbin Li   
* @date 2018年2月14日 下午3:44:29 
* @version V1.0   
*/

@Entity
@org.hibernate.annotations.Entity(dynamicUpdate=true, dynamicInsert = true)
@Table(name="department", schema = "hospital")
public class Department extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4669236925686674408L;
	
	private String departmentName;
	
	private String departmentDesc;
	
	private int bookable; //1是0否，-1所有
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public int getBookable() {
		return bookable;
	}

	public void setBookable(int bookable) {
		this.bookable = bookable;
	}

	@Override
	public String toString() {
		return "Department [departmentName=" + departmentName + ", departmentDesc=" + departmentDesc
				+ ", bookable=" + bookable + "]";
	}

}
