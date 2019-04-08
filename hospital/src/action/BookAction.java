package action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.BookRecordDTO;
import service.BookRecordService;

/**   
* @Title: BookAction.java 
* @Package action 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月6日 下午11:31:51 
* @version V1.0   
*/

@Controller
public class BookAction {
	
	@Autowired
	private BookRecordService bookRecordService;

	@InitBinder
	public void initBinder(WebDataBinder b) {  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        b.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));  
          
        /*SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");  
        String[] fileds = {"字段名", "字段名", "字段名"};  
        for(String filed : fileds){  
            b.registerCustomEditor(Date.class, filed, new CustomDateEditor(sdf2, true));  
        } */ 
    }
	
	@RequestMapping(value = "/toBook.do")
	public String toBook(ModelMap map) {
		return "book/book.jsp";
	}
	
	@RequestMapping(value = "/queryBookData.do")
	@ResponseBody
	public Map<String, Object> queryBookData(ModelMap map, BookRecordDTO dto) {
		Map<String, Object> result = new HashMap<String, Object>();
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		
		String[] dates = new String[14];
		int[] days = new int[14];
		for (int i = 0; i < dates.length; i++) {
			String date = y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d);
			dates[i] = date;
			int day = c.get(Calendar.DAY_OF_WEEK);
			days[i] = day - 1;
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
		
		result.put("data", bookRecordService.getReady4Book(dto));
		result.put("dates", dates);
		result.put("days", days);
		
		return result;
	}
	
	@RequestMapping(value = "/queryBookData4Trans.do")
	@ResponseBody
	public Map<String, Object> queryBookData4Trans(ModelMap map, BookRecordDTO dto,
			@RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "pageSize", required = true) int pageSize) {
		Map<String, Object> result = bookRecordService.queryBookData4Trans(dto, pageNum, pageSize);
		
		return result;
	}
	
	@RequestMapping(value = "/transBookRecord.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String transBookRecord(ModelMap map, @RequestParam(value = "transSourceID", required = true) Long source,
			@RequestParam(value = "transTargetID", required = true) Long target) {
		return bookRecordService.transBookRecord(source, target);
	}
	
	@RequestMapping(value="/book.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String book(ModelMap map, BookRecordDTO dto, @RequestParam(value = "currentUser", required = true) Long currentUser) {
		if (bookRecordService.isOverBooked(dto, currentUser)) {
			return "同一天在同一部门下有预约，请不要重复预约！";
		}
		return bookRecordService.book(dto, currentUser) ? "预约成功！" : "预约失败！";
	}
	
	@RequestMapping(value="/toBookRecordList.do")
	public String toBookRecordList(ModelMap map) {
		return "book/bookList.jsp";
	}
	
	@RequestMapping(value="/querySelfBookRecord.do")
	@ResponseBody
	public Map<String, Object> querySelfBookRecord(ModelMap map, @RequestParam(value = "currentUser", required = true) Long userId,
			@RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "pageSize", required = true) int pageSize) {
		Map<String, Object> result = bookRecordService.querySelfBookRecord(userId, pageNum, pageSize);
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		result.put("date", y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d));
		
		return result;
	}
	
	@RequestMapping(value="/querySelfBookedRecord.do")
	@ResponseBody
	public Map<String, Object> querySelfBookedRecord(ModelMap map, @RequestParam(value = "doctor", required = true) Long doctor,
			@RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "pageSize", required = true) int pageSize) {
		Map<String, Object> result = bookRecordService.querySelfBookedRecord(doctor, pageNum, pageSize);
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DATE);
		result.put("date", y + "-" + (m < 10 ? ("0" + m) : m) + "-" + (d < 10 ? ("0" + d) : d));
		
		return result;
	}
	
	@RequestMapping(value="/statisticBookRecord.do")
	@ResponseBody
	public Map<String, Object> statisticBookRecord(ModelMap map, BookRecordDTO dto,
			@RequestParam(value = "pageNum", required = true) int pageNum, @RequestParam(value = "pageSize", required = true) int pageSize) {
		return bookRecordService.statisticBookRecord(dto, pageNum, pageSize);
	}
	
	@RequestMapping(value="/deleteBookRecord.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteBookRecord(ModelMap map, BookRecordDTO dto) {
		return bookRecordService.deleteBookedRecord(dto) ? "删除成功" : "删除失败";
	}
	
	@RequestMapping(value = "/generateTwoWeekData.do")
	@ResponseBody
	public void generateTwoWeekData(ModelMap map) {
		bookRecordService.generateTwoWeekData();
	}
	
	@RequestMapping(value = "/generateOneDayRootData.do")
	@ResponseBody
	public void generateOneDayRootData(ModelMap map) {
		TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	bookRecordService.deleteAllExpired();
            	bookRecordService.generateEveryDayRootData();
            }
        };
        
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 24 * 60 *60 * 1000);
	}
}
