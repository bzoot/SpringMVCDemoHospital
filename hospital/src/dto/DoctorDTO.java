package dto;

import java.util.ArrayList;

/**   
* @Title: DoctorDTO.java 
* @Package dto 
* @Description:  
* @author Pengbin Li   
* @date 2018年1月7日 下午4:06:24 
* @version V1.0   
*/

public class DoctorDTO extends AbstractDTO {
	
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

	public DoctorDTO() {}
	
	public DoctorDTO(String doctorName, String doctorDesc, int expert, Long department, String departmentName,
			Long user, int bookable, ArrayList<Integer> onDuty) {
		super();
		this.doctorName = doctorName;
		this.doctorDesc = doctorDesc;
		this.expert = expert;
		this.department = department;
		this.departmentName = departmentName;
		this.user = user;
		this.bookable = bookable;
		this.onDuty = onDuty;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getDoctorDesc() {
		return doctorDesc;
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
	
	public void setDoctorDesc(String doctorDesc) {
		this.doctorDesc = doctorDesc;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
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

	public ArrayList<Integer> getOnDuty() {
		return onDuty;
	}

	public void setOnDuty(ArrayList<Integer> onDuty) {
		this.onDuty = onDuty;
	}
	
}
