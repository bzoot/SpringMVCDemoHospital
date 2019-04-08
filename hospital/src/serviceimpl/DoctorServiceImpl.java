package serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.Doctor;
import dto.DoctorDTO;
import repository.DoctorRepository;
import service.BookRecordService;
import service.DoctorService;
import util.DtoBeanUtil;
import util.ParamForQuery;

/**   
* @Title: DoctorServiceImpl.java 
* @Package serviceimpl 
* @Description:  
* @author Pengbin Li   
* @date 2018年1月14日 下午10:52:27 
* @version V1.0   
*/

@Service
public class DoctorServiceImpl extends AbstractServiceImpl<DoctorDTO, Doctor> implements DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private BookRecordService bookRecordService;
	
	@Override
	public ArrayList<DoctorDTO> getExperts() {
		ArrayList<DoctorDTO> result = new ArrayList<DoctorDTO>();
		ArrayList<Doctor> raw = doctorRepository.getExperts();

		for (Doctor d : raw) {
			DoctorDTO ddto = new DoctorDTO();
			DtoBeanUtil.transalte(d, ddto);
			result.add(ddto);
		}
		
		return result;
	}
	
	@Override
	public ArrayList<DoctorDTO> getByDept(Long deptId) {
		ArrayList<DoctorDTO> result = new ArrayList<DoctorDTO>();
		ArrayList<Doctor> raw = doctorRepository.getByDept(deptId);

		for (Doctor d : raw) {
			DoctorDTO ddto = new DoctorDTO();
			DtoBeanUtil.transalte(d, ddto);
			result.add(ddto);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryByPage(DoctorDTO doctorDTO, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		ArrayList<DoctorDTO> list = new ArrayList<DoctorDTO>();
		ArrayList<ParamForQuery> params = new ArrayList<ParamForQuery>();
		String hql = "from Doctor as doctor";
		hql = addParam2HQL(hql, params, doctorDTO);
		Map<String, Object> raw = doctorRepository.queryByPage(hql, params, pageNum, pageSize);
		ArrayList<Doctor> rawList = (ArrayList<Doctor>) raw.get("list");
		for (Doctor u : rawList) {
			DoctorDTO dto = new DoctorDTO();
			DtoBeanUtil.transalte(u, dto);
			list.add(dto);
		}
		result.put("data", list);
		result.put("total", raw.get("total"));
		
		return result;
	}

	@Override
	public ArrayList<DoctorDTO> getAllBookable() {
		ArrayList<DoctorDTO> result = new ArrayList<DoctorDTO>();
		ArrayList<Doctor> raw = doctorRepository.getAllBookable();
		for (Doctor d : raw) {
			DoctorDTO dto = new DoctorDTO();
			DtoBeanUtil.transalte(d, dto);
			result.add(dto);
		}
		
		return result;
	}

	@Override
	public boolean isReady2Delete(DoctorDTO doctorDTO) {
		if (bookRecordService.getByDocId(doctorDTO.getId()).size() > 0) {
			return false;
		}
		
		return true;
	}
	
	private String addParam2HQL(String hql, ArrayList<ParamForQuery> params, DoctorDTO doctorDTO) {
		if (null == doctorDTO) { return hql; }
		StringBuilder sb = new StringBuilder();
		if (doctorDTO.getDepartment() != null && doctorDTO.getDepartment() > 0) {
			sb.append("doctor.department = ?");
			params.add(new ParamForQuery(doctorDTO.getDepartment(), false));
		}
		if (sb.length() > 0) {
			hql = hql + " where " + sb.toString();
		}
		
		return hql;
	}

	@Override
	public String addDoctor(DoctorDTO doctorDTO, String userName, String phone) {
		Doctor d = new Doctor();
		DtoBeanUtil.transalte(doctorDTO, d);
		return doctorRepository.addDoctor(d, userName, phone);
	}

}
