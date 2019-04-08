package repositoryimpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import bean.BookRecord;
import repository.BookRecordRepository;

/**   
* @Title: BookRecordRepositoryImpl.java 
* @Package repositoryimpl 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月6日 下午11:48:17 
* @version V1.0   
*/

@Repository
public class BookRecordRepositoryImpl extends AbstractRepositoryImpl<BookRecord> implements BookRecordRepository {

	@Override
	public boolean deleteRootDataByDate(String date) {
		boolean result = false;
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 删除
		String hql = "delete BookRecord b where b.root = ? and b.bookDate = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, -1L);
		query.setParameter(1, date);
		try {
			query.executeUpdate();
			result = true;
			// 提交事务
			transaction.commit();
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
	public ArrayList<BookRecord> getByDocId(Long docId) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from BookRecord as b where b.doctor = :doctor";
		Query query = session.createQuery(hql);
		query.setParameter("doctor", docId);
		ArrayList<BookRecord> result = (ArrayList<BookRecord>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BookRecord> getByDeptId(Long deptId) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from BookRecord as b where b.department = :department";
		Query query = session.createQuery(hql);
		query.setParameter("department", deptId);
		ArrayList<BookRecord> result = (ArrayList<BookRecord>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}

	@Override
	public void deleteAllExpired(String date) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		try {
			String hql = "delete BookRecord as b where b.root = ? and b.bookDate < ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, -1L);
			query.setParameter(1, date);
			query.executeUpdate();
			// 提交事务
			transaction.commit();
		} catch (HibernateException | NullPointerException e) {
			System.out.println(e);
			transaction.rollback();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BookRecord> getReady4Book(BookRecord bookRecord) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from BookRecord as b where b.root = -1L and b.expert = :expert "
				+ "and b.department = :department and b.doctor = :doctor";
		Query query = session.createQuery(hql);
		query.setParameter("expert", bookRecord.getExpert());
		query.setParameter("department", bookRecord.getDepartment());
		if (bookRecord.getDoctor() > 0) {
			query.setParameter("doctor", bookRecord.getDoctor());
		} else {
			query.setParameter("doctor", -1L);
		}
		ArrayList<BookRecord> result = (ArrayList<BookRecord>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}

	@Override
	public boolean book(BookRecord b, Long currentUser) {
		boolean result = false;
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		int updateTotal = b.getTotal() - 1;
		try {
			//更新
			String hql1 = "update BookRecord b set b.total = ?, b.updateDate = ? where b.id = ?";
			Query query1 = session.createQuery(hql1);
			query1.setParameter(0, updateTotal);
			query1.setParameter(1, new Date());
			query1.setParameter(2, b.getId());
			query1.executeUpdate();
			//插入
			BookRecord target = new BookRecord();
			target.setCreateDate(new Date());
			target.setUpdateDate(new Date());
			target.setDepartment(b.getDepartment());
			target.setDoctor(b.getDoctor());
			target.setExpert(b.getExpert());
			target.setBookDate(b.getBookDate());
			target.setRoot(b.getId());
			target.setTotal(-1);
			target.setTime(b.getTime());
			target.setUserId(currentUser);
			target.setDepartmentName(b.getDepartmentName());
			target.setDoctorName(b.getDoctorName());
			Serializable res = session.save(target);
			transaction.commit();
			result = null == res ? false : true;
		} catch (HibernateException | NullPointerException e) {
			System.out.println(e);
			transaction.rollback();
		} finally {
			session.close();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<BookRecord> querySelfBookRecord(Long currentUser) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		String hql = "from BookRecord as b where b.userId = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, currentUser);
		ArrayList<BookRecord> result = (ArrayList<BookRecord>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
				
		return result;
	}

	@Override
	public boolean deleteBookedRecord(BookRecord bookRecord) {
		boolean result = false;
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		try {
			session.delete(bookRecord);
			Long id = bookRecord.getRoot();
			String hql1 = "select b.total from BookRecord b where b.id = ?";
			Query query1 = session.createQuery(hql1);
			query1.setParameter(0, id);
			int total = (int) query1.list().get(0) + 1;
			String hql2 = "update BookRecord b set b.total = ? where b.id = ?";
			Query query2 = session.createQuery(hql2);
			query2.setParameter(0, total);
			query2.setParameter(1, id);
			query2.executeUpdate();
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

	@Override
	public boolean isOverBooked(BookRecord b, Long currentUser) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from BookRecord as b where b.bookDate = ? and b.userId = ?";
		Query query=session.createQuery(hql);
		query.setParameter(0, b.getBookDate());
		query.setParameter(1, currentUser);
		@SuppressWarnings("unchecked")
		ArrayList<BookRecord> result = (ArrayList<BookRecord>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result.size() > 0 ? true : false;
	}

	@Override
	public String transBookRecord(Long bs, Long bt) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		try {
			String hql = "from BookRecord as b where b.id = ?";
			Query query = session.createQuery(hql);
			query.setParameter(0, bt);
			BookRecord t = (BookRecord) query.list().get(0);
			if (t.getTotal() <= 0) {
				transaction.rollback();
				return "转让失败，请选取其他医生！";
			}
			String findSourceRoot = "from BookRecord as b where b.id = ?";
			Query queryFindSourceRoot = session.createQuery(findSourceRoot);
			queryFindSourceRoot.setParameter(0, bs);
			BookRecord m = (BookRecord) queryFindSourceRoot.list().get(0);
			queryFindSourceRoot.setParameter(0, m.getRoot());
			BookRecord s = (BookRecord) queryFindSourceRoot.list().get(0);
			s.setTotal(s.getTotal() + 1);
			t.setTotal(t.getTotal() - 1);
			m.setRoot(t.getId());
			m.setDoctor(t.getDoctor());
			m.setDoctorName(t.getDoctorName());
			session.update(s);
			session.update(t);
			session.update(m);
			transaction.commit();
			return "转让成功!";
		} catch(HibernateException e) {
			System.out.println(e);
			transaction.rollback();
		} finally {
			session.close();
		}
		
		return "转让失败!";
	}
	
}
