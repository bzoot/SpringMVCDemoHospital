package action;

import java.io.Serializable;
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

import dto.DepartmentDTO;
import service.DepartmentService;

/**   
* @Title: DepartmentAction.java 
* @Package action 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月7日 上午12:07:43 
* @version V1.0   
*/

@Controller
public class DepartmentAction {

	@Autowired
	private DepartmentService departmentService;

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
	
	@RequestMapping(value = "/getDepartments.do")
	@ResponseBody
	public Map<String, Object> getExperts (ModelMap map) {
		Map<String, Object> result = new ModelMap();
		ArrayList<DepartmentDTO> depts = departmentService.getAll();
		result.put("data", depts);
		return result;
	}
	
	@RequestMapping(value = "/toDepartmentList.do")
	public String toDepartmentList(ModelMap map) {
		return "department/departmentList.jsp";
	}
	
	@RequestMapping(value = "/toDepartmentDetail.do")
	public String toDepartmentDetail(DepartmentDTO departmentDTO, ModelMap map) {
		ArrayList<DepartmentDTO> list = departmentService.getById(departmentDTO.getId());
		map.addAttribute("data", list.get(0));
		return "department/viewDepartment.jsp";
	}
	
	@RequestMapping(value = "/toAddDepartment.do")
	public String toAddDepartment(ModelMap map) {
		return "department/addDepartment.jsp";
	}
	
	@RequestMapping(value = "/toUpdateDepartment.do")
	public String toUpdateDepartment(DepartmentDTO departmentDTO, ModelMap map) {
		ArrayList<DepartmentDTO> list = departmentService.getById(departmentDTO.getId());
		map.addAttribute("data", list.get(0));
		return "department/updateDepartment.jsp";
	}
	
	@RequestMapping(value = "/addDepartment.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addDepartment(DepartmentDTO departmentDTO, ModelMap map) {
		Serializable s = departmentService.insert(departmentDTO);
		if (s != null && (Long)s > 0L) {
			return "添加成功！";
		} else {
			return "添加失败！" ;
		}
	}
	
	@RequestMapping(value = "/updateDepartment.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateDoctor(DepartmentDTO departmentDTO, ModelMap map) {
		return departmentService.update(departmentDTO) ? "更新成功！" : "更新失败！" ;
	}
	
	@RequestMapping(value="/deleteDepartment.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String deleteDoctor(DepartmentDTO departmentDTO, ModelMap map) {
		if (departmentService.isReady2Delete(departmentDTO)) {
			return departmentService.delete(departmentDTO) ? "删除成功！" : "删除失败！";
		} else {
			return "该部门下还有医生或还有预约未完成，无法删除！";
		}
	}
	
	@RequestMapping(value = "/queryDepartmentByPage.do")
	@ResponseBody
	public Map<String, Object> queryDepartmentByPage (DepartmentDTO departmentDTO, @RequestParam(value = "pageNum", required = true) int pageNum,
			@RequestParam(value = "pageSize", required = true) int pageSize, ModelMap map) {
		Map<String, Object> result = departmentService.queryByPage(departmentDTO, pageNum, pageSize);
		return result;
	}
	
}
