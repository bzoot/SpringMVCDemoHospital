package repository;

import java.util.ArrayList;

import bean.Doctor;

/**   
* @Title: DoctorRepository.java 
* @Package repository 
* @Description:  
* @author Pengbin Li   
* @date 2018年1月14日 下午10:53:50 
* @version V1.0   
*/

public interface DoctorRepository extends AbstractRepository<Doctor> {
	
	public ArrayList<Doctor> getExperts();
	
	public ArrayList<Doctor> getAllBookable();
	
	public ArrayList<Doctor> getByDept(Long deptId);
	
	public String addDoctor(Doctor doctor, String userName, String phone);
	
}
