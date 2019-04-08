package repository;

import java.util.ArrayList;

import bean.BookRecord;

/**   
* @Title: BookRecordRepository.java 
* @Package repository 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月6日 下午11:35:52 
* @version V1.0   
*/

public interface BookRecordRepository extends AbstractRepository<BookRecord> {
	
	public boolean deleteRootDataByDate(String date);
	
	public boolean deleteBookedRecord(BookRecord dto);
	
	public ArrayList<BookRecord> getByDocId(Long docId);
	
	public ArrayList<BookRecord> getByDeptId(Long deptId);
	
	public ArrayList<BookRecord> getReady4Book(BookRecord bookRecord);
	
	public void deleteAllExpired(String date);
	
	public boolean book(BookRecord b, Long currentUser);
	
	public boolean isOverBooked(BookRecord b, Long currentUser);
	
	public ArrayList<BookRecord> querySelfBookRecord(Long currentUser);
	
	public String transBookRecord(Long bs, Long bt);
	
}
