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
* @date 2018��1��14�� ����10:54:26 
* @version V1.0   
*/

@Repository
public class DoctorRepositoryImpl extends AbstractRepositoryImpl<Doctor> implements DoctorRepository{

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Doctor> getExperts() {
		Session session = factory.openSession();
		// ��ȡ����
		Transaction transaction = session.beginTransaction();
		// ��ѯ
		String hql = "from Doctor as doctor where doctor.expert = :expert";
		Query query = session.createQuery(hql);
		query.setParameter("expert", 1);
		ArrayList<Doctor> result = (ArrayList<Doctor>) query.list();
		// �ύ����
		transaction.commit();
		session.close();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Doctor> getByDept(Long deptId) {
		Session session = factory.openSession();
		// ��ȡ����
		Transaction transaction = session.beginTransaction();
		// ��ѯ
		String hql = "from Doctor as doctor where doctor.department = :department";
		Query query = session.createQuery(hql);
		query.setParameter("department", deptId);
		ArrayList<Doctor> result = (ArrayList<Doctor>) query.list();
		// �ύ����
		transaction.commit();
		session.close();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Doctor> getAllBookable() {
		Session session = factory.openSession();
		// ��ȡ����
		Transaction transaction = session.beginTransaction();
		// ��ѯ
		String hql = "from Doctor as doctor where doctor.bookable = :bookable";
		Query query = session.createQuery(hql);
		query.setParameter("bookable", 1);
		ArrayList<Doctor> result = (ArrayList<Doctor>) query.list();
		// �ύ����
		transaction.commit();
		session.close();
		
		return result;
	}

	@Override
	public String addDoctor(Doctor doctor, String userName, String phone) {
		Session session = factory.openSession();
		// ��ȡ����
		Transaction transaction = session.beginTransaction();
		// ����
		try {
			doctor.setCreateDate(new Date());
			doctor.setUpdateDate(new Date());
			Serializable s = session.save(doctor);
			if (s != null && (Long)s > 0L) {
				// ��ѯ
				String hql = "from User as user where user.userName =:userName";
				Query query=session.createQuery(hql);
				query.setString("userName", userName);
				@SuppressWarnings("unchecked")
				ArrayList<User> users = (ArrayList<User>) query.list();
				if (users.size() > 0) {
					transaction.rollback();
					return "�û����ظ�����ʹ�������û�����";
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
					// �ύ����
					transaction.commit();
					return "��ӳɹ���";
				} else {
					transaction.rollback();
					return "����û�ʧ�ܣ�";
				}
			} else {
				transaction.rollback();
				return "���ҽ��ʧ�ܣ�";
			}
		} catch (HibernateException | NullPointerException e) {
			System.out.println(e);
			transaction.rollback();
		} finally {
			session.close();
		}
		
		return "���ʧ�ܣ�";
	}

}
