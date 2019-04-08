package repository;

import java.util.ArrayList;

import bean.Department;

/**   
* @Title: DepartmentRepository.java 
* @Package repository 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月6日 下午11:36:06 
* @version V1.0   
*/

public interface DepartmentRepository extends AbstractRepository<Department> {
	
	public ArrayList<Department> getAllBookable();
	
}
