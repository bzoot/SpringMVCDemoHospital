package serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.Department;
import dto.DepartmentDTO;
import repository.DepartmentRepository;
import service.BookRecordService;
import service.DepartmentService;
import service.DoctorService;
import util.DtoBeanUtil;
import util.ParamForQuery;

/**   
* @Title: DepartmentServiceImpl.java 
* @Package serviceimpl 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月6日 下午11:34:51 
* @version V1.0   
*/

@Service
public class DepartmentServiceImpl extends AbstractServiceImpl<DepartmentDTO, Department> implements DepartmentService {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private BookRecordService bookRecordService;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryByPage(DepartmentDTO departmentDTO, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		ArrayList<DepartmentDTO> list = new ArrayList<DepartmentDTO>();
		ArrayList<ParamForQuery> params = new ArrayList<ParamForQuery>();
		String hql = "from Department as department";
		Map<String, Object> raw = departmentRepository.queryByPage(hql, params, pageNum, pageSize);
		ArrayList<Department> rawList = (ArrayList<Department>) raw.get("list");
		for (Department u : rawList) {
			DepartmentDTO dto = new DepartmentDTO();
			DtoBeanUtil.transalte(u, dto);
			list.add(dto);
		}
		result.put("data", list);
		result.put("total", raw.get("total"));
		
		return result;
	}

	@Override
	public boolean isReady2Delete(DepartmentDTO departmentDTO) {
		if (doctorService.getByDept(departmentDTO.getId()).size() > 0
				|| bookRecordService.getByDeptId(departmentDTO.getId()).size() > 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public ArrayList<DepartmentDTO> getAllBookable() {
		ArrayList<DepartmentDTO> result = new ArrayList<DepartmentDTO>();
		ArrayList<Department> raw = departmentRepository.getAllBookable();
		for (Department d : raw) {
			DepartmentDTO dto = new DepartmentDTO();
			DtoBeanUtil.transalte(d, dto);
			result.add(dto);
		}
		
		return result;
	}

}
