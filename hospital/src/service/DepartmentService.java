package service;

import java.util.ArrayList;
import java.util.Map;

import dto.DepartmentDTO;

/**   
* @Title: DepartmentService.java 
* @Package service 
* @Description:  
* @author Pengbin Li   
* @date 2018��3��6�� ����11:33:07 
* @version V1.0   
*/

public interface DepartmentService extends AbstractService<DepartmentDTO> {
		
	public Map<String, Object> queryByPage(DepartmentDTO departmentDTO, int pageNum, int pageSize);
	
	public boolean isReady2Delete(DepartmentDTO departmentDTO);
	
	public ArrayList<DepartmentDTO> getAllBookable();
	
}
