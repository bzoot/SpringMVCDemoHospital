package repositoryimpl;

import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import bean.Department;
import repository.DepartmentRepository;

/**   
* @Title: DepartmentRepositoryImpl.java 
* @Package repositoryimpl 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月6日 下午11:38:17 
* @version V1.0   
*/

@Repository
public class DepartmentRepositoryImpl extends AbstractRepositoryImpl<Department> implements DepartmentRepository {

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Department> getAllBookable() {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from Department as department where department.bookable = :bookable";
		Query query = session.createQuery(hql);
		query.setParameter("bookable", 1);
		ArrayList<Department> result = (ArrayList<Department>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		
		return result;
	}
	
}
