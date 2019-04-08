package service;

import java.util.ArrayList;
import java.util.Map;

import dto.BookRecordDTO;

/**   
* @Title: BookService.java 
* @Package service 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月6日 下午11:32:06 
* @version V1.0   
*/

public interface BookRecordService extends AbstractService<BookRecordDTO> {
	
	public boolean deleteRootDataByDate(String date);
	
	public boolean deleteBookedRecord(BookRecordDTO dto);
	
	public void deleteAllExpired();
	
	public ArrayList<BookRecordDTO> getByDocId(Long docId);
	
	public ArrayList<BookRecordDTO> getByDeptId(Long deptId);
	
	public ArrayList<BookRecordDTO> getReady4Book(BookRecordDTO dto);
	
	public Map<String, Object> querySelfBookRecord(Long currentUser, int page, int pageSize);
	
	public Map<String, Object> querySelfBookedRecord(Long doctor, int page, int pageSize);
	
	public Map<String, Object> statisticBookRecord(BookRecordDTO dto, int page, int pageSize);

	public Map<String, Object> queryBookData4Trans(BookRecordDTO dto, int page, int pageSize);
	
	public String transBookRecord(Long source, Long target);
	
	public boolean book(BookRecordDTO dto, Long currentUser);
	
	public boolean isOverBooked(BookRecordDTO dto, Long currentUser);
	
	public void generateEveryDayRootData ();
	
	public void generateTodyRootData();
	
	public void generateTwoWeekData();
	
}
