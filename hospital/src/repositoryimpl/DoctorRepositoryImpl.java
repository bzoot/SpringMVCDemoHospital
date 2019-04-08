package repositoryimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import bean.Doctor;
import bean.User;
import repository.DoctorRepository;

/**   
* @Title: DoctorRepositoryImpl.java 
* @Package repositoryimpl 
* @Description:  
* @author Pengbin Li   
* @date 2018年1月14日 下午10:54:26 
* @version V1.0   
*/

@Repository
public class DoctorRepositoryImpl extends AbstractRepositoryImpl<Doctor> implements DoctorRepository{

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Doctor> getExperts() {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from Doctor as doctor where doctor.expert = :expert";
		Query query = session.createQuery(hql);
		query.setParameter("expert", 1);
		ArrayList<Doctor> result = (ArrayList<Doctor>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Doctor> getByDept(Long deptId) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from Doctor as doctor where doctor.department = :department";
		Query query = session.createQuery(hql);
		query.setParameter("department", deptId);
		ArrayList<Doctor> result = (ArrayList<Doctor>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Doctor> getAllBookable() {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from Doctor as doctor where doctor.bookable = :bookable";
		Query query = session.createQuery(hql);
		query.setParameter("bookable", 1);
		ArrayList<Doctor> result = (ArrayList<Doctor>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}

	@Override
	public String addDoctor(Doctor doctor, String userName, String phone) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 插入
		try {
			doctor.setCreateDate(new Date());
			doctor.setUpdateDate(new Date());
			Serializable s = session.save(doctor);
			if (s != null && (Long)s > 0L) {
				// 查询
				String hql = "from User as user where user.userName =:userName";
				Query query=session.createQuery(hql);
				query.setString("userName", userName);
				@SuppressWarnings("unchecked")
				ArrayList<User> users = (ArrayList<User>) query.list();
				if (users.size() > 0) {
					transaction.rollback();
					return "用户名重复，请使用其他用户名！";
				}
				
				User user = new User();
				user.setUserName(userName);
				user.setTrueName(doctor.getDoctorName());
				user.setPhone(phone);
				user.setPassword("doc" + phone);
				user.setCreateDate(new Date());
				user.setUpdateDate(new Date());
				user.setDoctor((Long)s);
				
				Serializable us = session.save(user);
				if (us != null && (Long)us > 0L) {
					doctor.setUser((Long)us);
					session.update(doctor);
					// 提交事务
					transaction.commit();
					return "添加成功！";
				} else {
					transaction.rollback();
					return "添加用户失败！";
				}
			} else {
				transaction.rollback();
				return "添加医生失败！";
			}
		} catch (HibernateException | NullPointerException e) {
			System.out.println(e);
			transaction.rollback();
		} finally {
			session.close();
		}
		
		return "添加失败！";
	}

}
