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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dto.UserDTO;
import service.UserService;
import util.StringUtil;

/**
 * @Title: UserAction.java
 * @Package user.action
 * @Description:
 * @author Pengbin Li
 * @date 2017年8月19日 下午3:07:20
 * @version V1.0
 */

@Controller
public class UserAction {

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
	
	@RequestMapping(value = "/toLogin.do")
	public String login(ModelMap map) {
		return "login.jsp";
	}

	@RequestMapping(value = "/userLogin.do", method = RequestMethod.POST)
	public String loginResult(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "loginPassword", required = true) String password, ModelMap map) {
		ArrayList<UserDTO> result = userService.isPassedLogin(userName, password);
		
		if (result.size() > 0) {
			return "loginResult.jsp";
		} else {
			return "login.jsp";
		}
	}
	
	@RequestMapping(value="/loginRecord.do")
	@ResponseBody
	public Map<String, Object> loginRecord(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "loginPassword", required = true) String password, ModelMap map) {
		Map<String, Object> result = new ModelMap();
		ArrayList<UserDTO> userDTO = userService.isPassedLogin(userName, password);
		if (userDTO.size() > 0) {
			result.put("id", userDTO.get(0).getId());
			result.put("name", userDTO.get(0).getUserName());
			result.put("isAdmin", userDTO.get(0).isAdmin());
			result.put("doctor", userDTO.get(0).getDoctor());
			return result;
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/toRegister.do")
	public String toRegister(ModelMap map) {
		return "user/register.jsp";
	}
	
	@RequestMapping(value = "/register.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String register(UserDTO userDTO, ModelMap map) {
		Serializable s = userService.register(userDTO);
		if (s != null && (Long)s > 0L) {
			return "注册成功！";
		} else if ((Long)s == -1L) {
			return "用户名重复，请使用其他用户名！";
		}else {
			return "注册失败！" ;
		}
	}
	
	@RequestMapping(value="/toUpdateUser.do")
	public String toUpdateUser(UserDTO userDTO, ModelMap map) {
		map.put("data", userService.getById(userDTO.getId()).get(0));
		return "user/update.jsp";
	}
	
	@RequestMapping(value="/updateUser.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String updateUser(UserDTO userDTO, @RequestParam(value = "exLoginPassword", required = true) String exLoginPassword,
			ModelMap map) {
		UserDTO u = userService.getById(userDTO.getId()).get(0);
		if (!StringUtil.isEmptyOrNull(exLoginPassword) && u.getPassword() != exLoginPassword) {
			return "旧密码不正确！";
		} else {
			if (StringUtil.isEmptyOrNull(userDTO.getPassword())) {
				userDTO.setPassword(u.getPassword());
			}
			return userService.update(userDTO) ? "更新成功！" : "更新失败！";
		}
	}
	
	@RequestMapping(value="/toUserDetail.do")
	public String toUserDetail(UserDTO userDTO, ModelMap map) {
		ArrayList<UserDTO> list = userService.getById(userDTO.getId());
		map.addAttribute("data", list.get(0));
		return "user/viewUser.jsp";
	}
	
	@RequestMapping(value="/toUserList.do")
	public String toUserList(ModelMap map) {
		return "user/userList.jsp";
	}
	
	@RequestMapping(value="/queryUserForPage.do")
	@ResponseBody
	public Map<String, Object> queryUserForPage(UserDTO userDto, @RequestParam(value = "pageNum", required = true) int pageNum,
			@RequestParam(value = "pageSize", required = true) int pageSize, ModelMap map) {
		Map<String, Object> result = userService.queryByPage(userDto, pageNum, pageSize);
		return result;
	}
	
	@RequestMapping(value="/queryUser.do")
	@ResponseBody
	public Map<String, Object> queryUser(UserDTO userDto, ModelMap map) {
		Map<String, Object> result = new ModelMap();
		ArrayList<UserDTO> users = userService.query(userDto);
		result.put("data", users);
		return result;
	}
}