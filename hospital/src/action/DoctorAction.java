package action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.DoctorDTO;
import service.DoctorService;
import service.UserService;

/**   
* @Title: DoctorAction.java 
* @Package action 
* @Description:  
* @author Pengbin Li   
* @date 2018年1月14日 下午10:48:00 
* @version V1.0   
*/

@Controller
public class DoctorAction {

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private UserService userService;
	
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
	
	@RequestMapping(value = "/getExperts.do")
	@ResponseBody
	public Map<String, Object> getExperts (ModelMap map) {
		Map<String, Object> result = new ModelMap();
		ArrayList<DoctorDTO> experts = doctorService.getExperts();
		result.put("data", experts.subList(0, experts.size() > 4 ? 4 : experts.size()));
		//System.out.println(experts);
		return result;
	}
	
	@RequestMapping(value = "/toAddDoctor.do")
	public String toAddDoctor(ModelMap map) {
		return "doctor/addDoc.jsp";
	}
	
	@RequestMapping(value = "/toUpdateDoctor.do")
	public String toUpdateDoctor(DoctorDTO doctorDTO, ModelMap map) {
		ArrayList<DoctorDTO> list = doctorService.getById(doctorDTO.getId());
		map.addAttribute("data", list.get(0));
		return "doctor/updateDoc.jsp";
	}
	
	@RequestMapping(value = "/addDoctor.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addDoctor(DoctorDTO doctorDTO, @RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "phone", required = true) String phone, ModelMap map) {
		return doctorService.addDoctor(doctorDTO, userName, phone);
	}
	
	@RequestMapping(value = "/updateDoctor.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateDoctor(DoctorDTO doctorDTO, ModelMap map) {
		return doctorService.update(doctorDTO) ? "更新成功！" : "更新失败！" ;
	}
	
	@RequestMapping(value = "/toDoctorList.do")
	public String toDoctorList(ModelMap map) {
		return "doctor/doctorList.jsp";
	}
	
	@RequestMapping(value = "/toDoctorDetail.do")
	public String toDoctorDetail(DoctorDTO doctorDTO, ModelMap map) {
		ArrayList<DoctorDTO> list = doctorService.getById(doctorDTO.getId());
		map.addAttribute("data", list.get(0));
		return "doctor/viewDoc.jsp";
	}
	
	@RequestMapping(value="/queryAllDoctor.do")
	@ResponseBody
	public Map<String, Object> queryAllDoctor(ModelMap map) {
		Map<String, Object> result = new ModelMap();
		ArrayList<DoctorDTO> doctors = doctorService.getAll();
		result.put("data", doctors);
		
		return result;
	}
	
	@RequestMapping(value="/deleteDoctor.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteDoctor(DoctorDTO doctorDTO, ModelMap map) {
		if (doctorService.isReady2Delete(doctorDTO)) {
			return doctorService.delete(doctorDTO) ? userService.delete(userService.getById(doctorDTO.getUser()).get(0)) ? "删除成功！" : "删除失败！" : "删除失败！";
		} else {
			return "该医生还有预约未完成，无法删除！";
		}
	}
	
	@RequestMapping(value="/queryDoctorForPage.do")
	@ResponseBody
	public Map<String, Object> queryDoctorForPage(DoctorDTO doctorDTO, @RequestParam(value = "pageNum", required = true) int pageNum,
			@RequestParam(value = "pageSize", required = true) int pageSize, ModelMap map) {
		Map<String, Object> result = doctorService.queryByPage(doctorDTO, pageNum, pageSize);
		return result;
	}
	
}
