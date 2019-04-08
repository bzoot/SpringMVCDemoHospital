package service;

import java.util.ArrayList;
import java.util.Map;

import dto.DoctorDTO;

/**   
* @Title: DoctorService.java 
* @Package service 
* @Description:  
* @author Pengbin Li   
* @date 2018年1月14日 下午10:49:35 
* @version V1.0   
*/

public interface DoctorService extends AbstractService<DoctorDTO> {
	
	public ArrayList<DoctorDTO> getExperts();
	
	public ArrayList<DoctorDTO> getByDept(Long deptId);
	
	public ArrayList<DoctorDTO> getAllBookable();
		
	public Map<String, Object> queryByPage(DoctorDTO doctorDTO, int pageNum, int pageSize);
	
	public boolean isReady2Delete(DoctorDTO doctorDTO);
	
	public String addDoctor(DoctorDTO doctorDTO, String userName, String phone);
	
}
