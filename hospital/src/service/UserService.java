package service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import dto.UserDTO;

/**   
* @Title: UserService.java 
* @Package user.service 
* @Description:  
* @author Pengbin Li   
* @date 2017年8月19日 下午3:36:09 
* @version V1.0   
*/

public interface UserService extends AbstractService<UserDTO> {

	public ArrayList<UserDTO> isPassedLogin(String userName, String password);
	
	public Serializable register(UserDTO userDto);
			
	public ArrayList<UserDTO> query(UserDTO userdto);
	
	public Map<String, Object> queryByPage(UserDTO userdto, int pageNum, int pageSize);
	
}
