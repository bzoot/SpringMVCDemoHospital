package repositoryimpl;

import java.io.Serializable;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import bean.User;
import repository.UserRepository;

/**
 * @Title: UserRepositoryImpl.java
 * @Package user.repositoryimpl
 * @Description:
 * @author Pengbin Li
 * @date 2017年8月19日 下午3:14:54
 * @version V1.0
 */

@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> isPassedLogin(String userName, String password) {
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from User as user where user.userName =:userName and user.password =:password";
		Query query=session.createQuery(hql);
		query.setString("userName", userName);
		query.setString("password", password);
		ArrayList<User> users = (ArrayList<User>) query.list();
		// 提交事务
		transaction.commit();
		session.close();

		return users;
	}

	@Override
	public Serializable register(User user) {
		//判断重名
		Session session = factory.openSession();
		// 获取事务
		Transaction transaction = session.beginTransaction();
		// 查询
		String hql = "from User as user where user.userName =:userName";
		Query query=session.createQuery(hql);
		query.setString("userName", user.getUserName());
		@SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>) query.list();
		// 提交事务
		transaction.commit();
		session.close();
		if (users.size() > 0) {
			return -1L;
		} else {
			return insert(user);
		}
	}

}
