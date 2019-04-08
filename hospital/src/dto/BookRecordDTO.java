package dto;

/**   
* @Title: BookRecordDTO.java 
* @Package dto 
* @Description:  
* @author Pengbin Li   
* @date 2018年2月14日 下午4:02:39 
* @version V1.0   
*/

public class BookRecordDTO extends AbstractDTO {

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
	
	public BookRecordDTO() {}

	public BookRecordDTO(Long department, Long doctor, int expert, String bookDate, Long root,
			int total, int time, Long userId, String departmentName, String doctorName) {
		super();
		this.department = department;
		this.doctor = doctor;
		this.expert = expert;
		this.bookDate = bookDate;
		this.root = root;
		this.total = total;
		this.time = time;
		this.userId = userId;
		this.departmentName = departmentName;
		this.doctorName = doctorName;
	}

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
	
}
