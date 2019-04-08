package repositoryimpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import bean.AbstractBean;
import repository.AbstractRepository;
import util.DtoBeanUtil;
import util.HibernateSessionFactory;
import util.ParamForQuery;

/**   
* @Title: AbstractRepositoryImpl.java 
* @Package repositoryimpl 
* @Description:  
* @author Pengbin Li   
* @date 2017年11月12日 上午2:43:13 
* @version V1.0   
*/

public abstract class AbstractRepositoryImpl<T> implements AbstractRepository<T> {
	protected SessionFactory factory = HibernateSessionFactory.getInstance().getFactory();
	
	private Class<T> t;
	
	@SuppressWarnings("unchecked")
	private Class<T> getClz() {
		if (null == t) {
			t = (Class<T>)(((ParameterizedType)
					this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		}
		
		return t;
	}
	
	private String tClassName = getClz().getName().substring(getClz().getName().lastIndexOf(".") + 1);
	
	private String tTableName = tClassName.toLowerCase(Locale.US);
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> getById(Long id) {
		if (null == id) {
			return null;
		}
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from " + tClassName + " as " + tTableName + " where " + tTableName + ".id = :id";
		Query query=session.createQuery(hql);
		query.setLong("id", id);
		ArrayList<T> result = (ArrayList<T>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	};
	
	@Override
	public boolean update(T bean) {
		boolean result = false;
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 更新
		try {
			DtoBeanUtil.autoFillNull(getById(((AbstractBean) bean).getId()).get(0), bean);
			session.update(bean);
			
			// 提交事务
			transaction.commit();
			result = true;
		} catch (HibernateException | NullPointerException e) {
			System.out.println(e);
			transaction.rollback();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	@Override
	public Serializable insert(T bean) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 插入
		try {
			Serializable res = session.save(bean); //? false : true;
			// 提交事务
			transaction.commit();
			return res;
		} catch (HibernateException | NullPointerException e) {
			System.out.println(e);
			transaction.rollback();
		} finally {
			session.close();
		}
		
		return null;
	}
	
	@Override
	public boolean delete(T bean) {
		boolean result = false;
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 删除
		try {
			session.delete(bean);
			
			// 提交事务
			transaction.commit();
			result = true;
		} catch (HibernateException e) {
			System.out.println(e);
			transaction.rollback();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> query(T bean) {
		ArrayList<T> result = new ArrayList<T>();
		
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(getClz());
		c.add(Example.create(bean));
		result = (ArrayList<T>) c.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> getAll() {
		ArrayList<T> result = new ArrayList<T>();
		
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from " + tClassName + " as " + tTableName;
		Query query=session.createQuery(hql);
		result = (ArrayList<T>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryByPage(String hql, ArrayList<ParamForQuery> params, int page, int pageSize) {
		Map<String, Object> result = new HashMap<String, Object>();

		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		Query query=session.createQuery(hql);
		for (int i = 0; i < params.size(); i++) {
			ParamForQuery p = params.get(i);
			if (p.isFuzz()) {
				query.setParameter(i, "%" + String.valueOf(p.getParam()) + "%");
			} else {
				query.setParameter(i, p.getParam());
			}
		}
		//得到滚动结果集
        ScrollableResults scroll = query.scroll();
        //滚动到最后一行
        scroll.last();
        int i = scroll.getRowNumber() + 1;
        result.put("total", i);
		query.setFirstResult(pageSize * (page - 1));
        query.setMaxResults(pageSize);
		result.put("list", (ArrayList<T>) query.list());
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}
}