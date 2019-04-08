package serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.BookRecord;
import dto.BookRecordDTO;
import dto.DepartmentDTO;
import dto.DoctorDTO;
import repository.BookRecordRepository;
import service.BookRecordService;
import service.DepartmentService;
import service.DoctorService;
import util.DtoBeanUtil;
import util.GlobalVariable;
import util.ParamForQuery;
import util.StringUtil;

/**   
* @Title: BookRecordServiceImpl.java 
* @Package serviceimpl 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月6日 下午11:33:50 
* @version V1.0   
*/

@Service
public class BookRecordServiceImpl extends AbstractServiceImpl<BookRecordDTO, BookRecord> implements BookRecordService {

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private BookRecordRepository bookRecordRepository;
	
	@Override
	public boolean deleteRootDataByDate(String date) {
		return bookRecordRepository.deleteRootDataByDate(date);
	}
	
	@Override
	public void deleteAllExpired() {
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		bookRecordRepository.deleteAllExpired(y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d));
	}
	
	@Override
	public void generateEveryDayRootData() {
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		d += 13;
		if (d > c.getActualMaximum(Calendar.DATE)) {
			d -= c.getActualMaximum(Calendar.DATE);
			m += 1;
			if (m > 12) {
				y += 1;
				c.set(c.get(Calendar.YEAR), y);
				m = 1;
			}
			c.set(Calendar.MONTH, m - 1);
		}
		c.set(Calendar.DATE, d);
		String date = y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
		deleteRootDataByDate(date);
		generateSpecDayData(c, date);
	}
	
	@Override
	public void generateTodyRootData() {
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		String date = y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
		deleteRootDataByDate(date);
		generateSpecDayData(c, date);
	}
	
	@Override
	public void generateTwoWeekData() {
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		for (int i = 0; i < 14; i++) {
			String date = y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);

			deleteRootDataByDate(date);
			generateSpecDayData(c, date);
			
			d += 1;
			if (d > c.getActualMaximum(Calendar.DATE)) {
				m += 1;
				if (m > 12) {
					y += 1;
					c.set(c.get(Calendar.YEAR), y);
					m = 1;
				}
				c.set(Calendar.MONTH, m - 1);
				d = 1;
			}
			c.set(Calendar.DATE, d);
		}
	}

	@Override
	public ArrayList<BookRecordDTO> getByDocId(Long docId) {
		ArrayList<BookRecordDTO> result = new ArrayList<BookRecordDTO>();
		ArrayList<BookRecord> raw = bookRecordRepository.getByDocId(docId);
		for (BookRecord b : raw) {
			BookRecordDTO dto = new BookRecordDTO();
			DtoBeanUtil.transalte(b, dto);
			result.add(dto);
		}
		
		return result;
	}

	@Override
	public ArrayList<BookRecordDTO> getByDeptId(Long deptId) {
		ArrayList<BookRecordDTO> result = new ArrayList<BookRecordDTO>();
		ArrayList<BookRecord> raw = bookRecordRepository.getByDeptId(deptId);
		for (BookRecord b : raw) {
			BookRecordDTO dto = new BookRecordDTO();
			DtoBeanUtil.transalte(b, dto);
			result.add(dto);
		}
		
		return result;
	}
	
	private void generateSpecDayData(Calendar c, String date) {
		Date createAndUpdateDate = new Date();
		int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : c.get(Calendar.DAY_OF_WEEK) - 1;
		for (DepartmentDTO departmentDTO : departmentService.getAllBookable()) {
			Long deptId = departmentDTO.getId();
			int unExpertDoctorNum = 0;
			int expertDoctorNum = 0;
			for (DoctorDTO doctorDTO : doctorService.getByDept(deptId)) {
				if (doctorDTO.getBookable() == 1 && doctorDTO.getOnDuty() != null
						&& doctorDTO.getOnDuty().contains(dayOfWeek)) {
					if (doctorDTO.getExpert() == 1) {
						expertDoctorNum++;
					} else {
						unExpertDoctorNum++;
					}
					BookRecordDTO b1 = new BookRecordDTO(departmentDTO.getId(), doctorDTO.getId(),
							doctorDTO.getExpert(), date, -1L, GlobalVariable.NUMBER_FOR_APPOINT_PER_DOC_PER_TIME, 1, -1L,
							doctorDTO.getDepartmentName(), doctorDTO.getDoctorName());
					BookRecordDTO b2 = new BookRecordDTO(departmentDTO.getId(), doctorDTO.getId(),
							doctorDTO.getExpert(), date, -1L, GlobalVariable.NUMBER_FOR_APPOINT_PER_DOC_PER_TIME, 2, -1L,
							doctorDTO.getDepartmentName(), doctorDTO.getDoctorName());
					BookRecord bb1 = new BookRecord();
					BookRecord bb2 = new BookRecord();
					DtoBeanUtil.transalte(b1, bb1);
					DtoBeanUtil.transalte(b2, bb2);
					bb1.setCreateDate(createAndUpdateDate);
					bb1.setUpdateDate(createAndUpdateDate);
					bb2.setCreateDate(createAndUpdateDate);
					bb2.setUpdateDate(createAndUpdateDate);
					bookRecordRepository.insert(bb1);
					bookRecordRepository.insert(bb2);
				}
			}
			if (expertDoctorNum > 0) {
				BookRecordDTO bt1 = new BookRecordDTO(departmentDTO.getId(), -1L,
						1, date, -1L, expertDoctorNum * GlobalVariable.NUMBER_FOR_APPOINT_PER_DOC_PER_TIME, 1, -1L,
						departmentDTO.getDepartmentName(), "");
				BookRecordDTO bt2 = new BookRecordDTO(departmentDTO.getId(), -1L,
						1, date, -1L, expertDoctorNum * GlobalVariable.NUMBER_FOR_APPOINT_PER_DOC_PER_TIME, 2, -1L,
						departmentDTO.getDepartmentName(), "");
				BookRecord btb1 = new BookRecord();
				BookRecord btb2 = new BookRecord();
				DtoBeanUtil.transalte(bt1, btb1);
				DtoBeanUtil.transalte(bt2, btb2);
				btb1.setCreateDate(createAndUpdateDate);
				btb1.setUpdateDate(createAndUpdateDate);
				btb2.setCreateDate(createAndUpdateDate);
				btb2.setUpdateDate(createAndUpdateDate);
				bookRecordRepository.insert(btb1);
				bookRecordRepository.insert(btb2);
			}
			if (unExpertDoctorNum > 0) {
				BookRecordDTO bf1 = new BookRecordDTO(departmentDTO.getId(), -1L,
						0, date, -1L, unExpertDoctorNum * GlobalVariable.NUMBER_FOR_APPOINT_PER_DOC_PER_TIME, 1, -1L,
						departmentDTO.getDepartmentName(), "");
				BookRecordDTO bf2 = new BookRecordDTO(departmentDTO.getId(), -1L,
						0, date, -1L, unExpertDoctorNum * GlobalVariable.NUMBER_FOR_APPOINT_PER_DOC_PER_TIME, 2, -1L,
						departmentDTO.getDepartmentName(), "");
				BookRecord bfb1 = new BookRecord();
				BookRecord bfb2 = new BookRecord();
				DtoBeanUtil.transalte(bf1, bfb1);
				DtoBeanUtil.transalte(bf2, bfb2);
				bfb1.setCreateDate(createAndUpdateDate);
				bfb1.setUpdateDate(createAndUpdateDate);
				bfb2.setCreateDate(createAndUpdateDate);
				bfb2.setUpdateDate(createAndUpdateDate);
				bookRecordRepository.insert(bfb1);
				bookRecordRepository.insert(bfb2);
			}
		}
	}

	@Override
	public ArrayList<BookRecordDTO> getReady4Book(BookRecordDTO dto) {
		BookRecord b = new BookRecord();
		DtoBeanUtil.transalte(dto, b);
		ArrayList<BookRecord> raw = bookRecordRepository.getReady4Book(b);
		ArrayList<BookRecordDTO> result = new ArrayList<BookRecordDTO>();
		for (BookRecord br : raw) {
			BookRecordDTO bd = new BookRecordDTO();
			DtoBeanUtil.transalte(br, bd);
			result.add(bd);
		}
		
		return result;
	}

	@Override
	public boolean book(BookRecordDTO dto, Long currentUser) {
		return bookRecordRepository.book(bookRecordRepository.getById(dto.getId()).get(0), currentUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> querySelfBookRecord(Long currentUser, int page, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from BookRecord as b where b.userId = ?";
		ArrayList<ParamForQuery> params = new ArrayList<ParamForQuery>();
		params.add(new ParamForQuery(currentUser, false));
		Map<String, Object> raw = bookRecordRepository.queryByPage(hql, params, page, pageSize);
		ArrayList<BookRecord> rawList = (ArrayList<BookRecord>) raw.get("list");
		ArrayList<BookRecordDTO> list = new ArrayList<BookRecordDTO>();
		for (BookRecord b : rawList) {
			BookRecordDTO bd = new BookRecordDTO();
			DtoBeanUtil.transalte(b, bd);
			list.add(bd);
		}
		result.put("data", list);
		result.put("total", raw.get("total"));
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> querySelfBookedRecord(Long doctor, int page, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from BookRecord as b where b.doctor = ? and b.root > 0";
		ArrayList<ParamForQuery> params = new ArrayList<ParamForQuery>();
		params.add(new ParamForQuery(doctor, false));
		Map<String, Object> raw = bookRecordRepository.queryByPage(hql, params, page, pageSize);
		ArrayList<BookRecord> rawList = (ArrayList<BookRecord>) raw.get("list");
		ArrayList<BookRecordDTO> list = new ArrayList<BookRecordDTO>();
		for (BookRecord b : rawList) {
			BookRecordDTO bd = new BookRecordDTO();
			DtoBeanUtil.transalte(b, bd);
			list.add(bd);
		}
		result.put("data", list);
		result.put("total", raw.get("total"));
		
		return result;
	}

	@Override
	public boolean deleteBookedRecord(BookRecordDTO dto) {
		BookRecord b = new BookRecord();
		DtoBeanUtil.transalte(dto, b);
		return bookRecordRepository.deleteBookedRecord(b);
	}

	@Override
	public boolean isOverBooked(BookRecordDTO dto, Long currentUser) {
		BookRecord b = new BookRecord();
		DtoBeanUtil.transalte(dto, b);
		return bookRecordRepository.isOverBooked(b, currentUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryBookData4Trans(BookRecordDTO dto, int page, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from BookRecord as b where b.doctor != ? and b.root = -1 and b.doctor != -1L and"
				+ " b.department = ? and b.bookDate = ? and b.time = ? and b.total > 0";
		ArrayList<ParamForQuery> params = new ArrayList<ParamForQuery>();
		params.add(new ParamForQuery(dto.getDoctor(), false));
		params.add(new ParamForQuery(dto.getDepartment(), false));
		params.add(new ParamForQuery(dto.getBookDate(), false));
		params.add(new ParamForQuery(dto.getTime(), false));
		Map<String, Object> raw = bookRecordRepository.queryByPage(hql, params, page, pageSize);
		ArrayList<BookRecord> rawList = (ArrayList<BookRecord>) raw.get("list");
		ArrayList<BookRecordDTO> list = new ArrayList<BookRecordDTO>();
		for (BookRecord b : rawList) {
			BookRecordDTO bd = new BookRecordDTO();
			DtoBeanUtil.transalte(b, bd);
			list.add(bd);
		}
		result.put("data", list);
		result.put("total", raw.get("total"));
		
		return result;
	}

	@Override
	public String transBookRecord(Long source, Long target) {
		return bookRecordRepository.transBookRecord(source, target);
	}

	@Override
	public Map<String, Object> statisticBookRecord(BookRecordDTO dto, int page, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		String hql = "from BookRecord as b where b.root > 0";
		ArrayList<ParamForQuery> params = new ArrayList<ParamForQuery>();
		if (dto.getDepartment() > 0) {
			params.add(new ParamForQuery(dto.getDepartment(), false));
			hql = hql + " and b.department = ?";
		}
		if (dto.getDoctor() > 0) {
			params.add(new ParamForQuery(dto.getDoctor(), false));
			hql = hql + " and b.doctor = ?";
		}
		if (dto.getExpert() != -1) {
			params.add(new ParamForQuery(dto.getExpert(), false));
			hql = hql + " and b.expert = ?";
		}
		if (!StringUtil.isEmptyOrNull(dto.getBookDate())) {
			params.add(new ParamForQuery(dto.getBookDate(), true));
			hql = hql + " and b.bookDate like ?";
		}
		Map<String, Object> raw = bookRecordRepository.queryByPage(hql, params, page, pageSize);
		@SuppressWarnings("unchecked")
		ArrayList<BookRecord> rawList = (ArrayList<BookRecord>) raw.get("list");
		ArrayList<BookRecordDTO> list = new ArrayList<BookRecordDTO>();
		for (BookRecord b : rawList) {
			BookRecordDTO bd = new BookRecordDTO();
			DtoBeanUtil.transalte(b, bd);
			list.add(bd);
		}
		result.put("data", list);
		result.put("total", raw.get("total"));
		
		return result;
	}

}
