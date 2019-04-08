package repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import util.ParamForQuery;

/**   
* @Title: AbstractRepository.java 
* @Package repository 
* @Description:  
* @author Pengbin Li   
* @date 2018年2月21日 下午8:35:04 
* @version V1.0   
*/

public abstract interface AbstractRepository<T> {
	
	public ArrayList<T> getById(Long id);
	
	public Serializable insert(T bean);
	
	public boolean update(T bean);
	
	public boolean delete(T bean);
	
	public ArrayList<T> query(T bean);
	
	public ArrayList<T> getAll();
	
	public Map<String, Object> queryByPage(String hql, ArrayList<ParamForQuery> params, int page, int pageSize);
	
}
