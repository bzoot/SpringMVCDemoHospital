package serviceimpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import repository.AbstractRepository;
import service.AbstractService;
import util.DtoBeanUtil;
import dto.AbstractDTO;;

/**   
* @Title: AbstractServiceImpl.java 
* @Package serviceimpl 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月8日 下午4:43:59 
* @version V1.0   
 * @param <E>
*/

public class AbstractServiceImpl<E, T> implements AbstractService<E> {
	
	private Class<T> t;
	
	@SuppressWarnings("unchecked")
	private Class<T> getTClz() {
		if (null == t) {
			t = (Class<T>)(((ParameterizedType)
					this.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
		}
		
		return t;
	}
	
	private Class<E> e;
	
	@SuppressWarnings("unchecked")
	private Class<E> getEClz() {
		if (null == e) {
			e = (Class<E>)(((ParameterizedType)
					this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		}
		
		return e;
	}
	
	@Autowired
	private AbstractRepository<T> abstractRepository;

	@Override
	public ArrayList<E> getById(Long id) {
		ArrayList<T> raw = abstractRepository.getById(id);
		ArrayList<E> result = new ArrayList<E>();
		for (T t : raw) {
			try {
				E e = getEClz().newInstance();
				DtoBeanUtil.transalte(t, e);
				result.add(e);
			} catch (InstantiationException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Serializable insert(E beanDTO) {
		try {
			((AbstractDTO) beanDTO).setCreateDate(new Date());
			((AbstractDTO) beanDTO).setUpdateDate(new Date());
			T bean = getTClz().newInstance();
			DtoBeanUtil.transalte(beanDTO, bean);
			return abstractRepository.insert(bean);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean update(E beanDTO) {
		try {
			((AbstractDTO) beanDTO).setUpdateDate(new Date());
			T bean = getTClz().newInstance();
			DtoBeanUtil.transalte(beanDTO, bean);
			return abstractRepository.update(bean);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(E beanDTO) {
		try {
			T bean = getTClz().newInstance();
			DtoBeanUtil.transalte(beanDTO, bean);
			return abstractRepository.delete(bean);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<E> query(E beanDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<E> getAll() {
		ArrayList<E> result = new ArrayList<E>();
		ArrayList<T> list = abstractRepository.getAll();
		for (T t : list) {
			try {
				E e = getEClz().newInstance();
				DtoBeanUtil.transalte(t, e);
				result.add(e);
			} catch (InstantiationException | IllegalAccessException exception) {
				exception.printStackTrace();
			}
		}
		
		return result;
	}

}