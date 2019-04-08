package dto;

/**   
* @Title: DepartmentDTO.java 
* @Package dto 
* @Description:  
* @author Pengbin Li   
* @date 2018年2月14日 下午3:52:43 
* @version V1.0   
*/

public class DepartmentDTO extends AbstractDTO {

	private String departmentName;
	
	private String departmentDesc;
	
	private int bookable; //1是0否，-1所有

	public DepartmentDTO() {}

	public DepartmentDTO(String departmentName, String departmentDesc, int bookable) {
		super();
		this.departmentName = departmentName;
		this.departmentDesc = departmentDesc;
		this.bookable = bookable;
	}

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
	
}
