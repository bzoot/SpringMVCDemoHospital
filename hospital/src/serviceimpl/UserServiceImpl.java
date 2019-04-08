package serviceimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bean.User;
import dto.UserDTO;
import repository.UserRepository;
import service.UserService;
import util.DtoBeanUtil;
import util.ParamForQuery;
import util.StringUtil;

/**   
* @Title: UserServiceImpl.java 
* @Package user.serviceimpl 
* @Description:  
* @author Pengbin Li   
* @date 2017年8月19日 下午3:36:39 
* @version V1.0   
*/

@Service
public class UserServiceImpl extends AbstractServiceImpl<UserDTO, User> implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ArrayList<UserDTO> isPassedLogin(String userName, String password) {
		ArrayList<UserDTO> result = new ArrayList<UserDTO>();
		ArrayList<User> list = userRepository.isPassedLogin(userName, password);
		for (User u : list) {
			UserDTO dto = new UserDTO();
			DtoBeanUtil.transalte(u, dto);
			result.add(dto);
		}
		return result;
	}

	@Override
	public Serializable register(UserDTO userDto) {
		userDto.setCreateDate(new Date());
		userDto.setUpdateDate(new Date());
		User user = new User();
		DtoBeanUtil.transalte(userDto, user);
		
		return userRepository.register(user);
	}

	@Override
	public ArrayList<UserDTO> query(UserDTO userdto) {
		ArrayList<UserDTO> result = new ArrayList<UserDTO>();
		User user = new User();
		DtoBeanUtil.transalte(userdto, user);
		ArrayList<User> list = userRepository.query(user);
		for (User u : list) {
			UserDTO dto = new UserDTO();
			DtoBeanUtil.transalte(u, dto);
			result.add(dto);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryByPage(UserDTO userdto, int pageNum, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		ArrayList<ParamForQuery> params = new ArrayList<ParamForQuery>();
		String hql = "from User as user where user.admin = 0 and user.doctor = null";
		hql = addParam2HQL(hql, params, userdto);
		Map<String, Object> raw = userRepository.queryByPage(hql, params, pageNum, pageSize);
		@SuppressWarnings("unchecked")
		ArrayList<User> rawList = (ArrayList<User>) raw.get("list");
		for (User u : rawList) {
			UserDTO dto = new UserDTO();
			DtoBeanUtil.transalte(u, dto);
			list.add(dto);
		}
		result.put("data", list);
		result.put("total", raw.get("total"));
		
		return result;
	}
	
	private String addParam2HQL(String hql, ArrayList<ParamForQuery> params, UserDTO userdto) {
		if (null == userdto) { return hql; }
		StringBuilder sb = new StringBuilder();
		if (!StringUtil.isEmptyOrNull(userdto.getTrueName())) {
			sb.append(" and user.trueName = ?");
			params.add(new ParamForQuery(userdto.getTrueName(), true));
		}
		if (sb.length() > 0) {
			hql = hql + sb.toString();
		}
		
		return hql;
	}
}
