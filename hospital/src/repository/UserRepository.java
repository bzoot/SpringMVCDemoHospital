package repository;

import java.io.Serializable;
import java.util.ArrayList;

import bean.User;

/**   
* @Title: UserRepository.java 
* @Package user.repository 
* @Description:  
* @author Pengbin Li   
* @date 2017年8月19日 下午3:07:20 
* @version V1.0   
*/

public interface UserRepository extends AbstractRepository<User> {
	
	public ArrayList<User> isPassedLogin(String userName, String password);
	
	public Serializable register(User user);
				
}
