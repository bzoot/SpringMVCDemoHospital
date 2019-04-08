package service;

import java.io.Serializable;
import java.util.ArrayList;

/**   
* @Title: AbstractService.java 
* @Package service 
* @Description:  
* @author Pengbin Li   
* @date 2018��3��8�� ����4:40:34 
* @version V1.0   
*/

public abstract interface AbstractService<E> {

	public ArrayList<E> getById(Long id);
	
	public Serializable insert(E beanDTO);
	
	public boolean update(E beanDTO);
	
	public boolean delete(E beanDTO);
	
	public ArrayList<E> query(E beanDTO);
	
	public ArrayList<E> getAll();
		
}
