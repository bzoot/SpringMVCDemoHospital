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
 * @date 2017��8��19�� ����3:14:54
 * @version V1.0
 */

@Repository
public class UserRepositoryImpl extends AbstractRepositoryImpl<User> implements UserRepository {

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<User> isPassedLogin(String userName, String password) {
		Session session = factory.openSession();
		// ��ȡ����
		Transaction transaction = session.beginTransaction();
		// ��ѯ
		String hql = "from User as user where user.userName =:userName and user.password =:password";
		Query query=session.createQuery(hql);
		query.setString("userName", userName);
		query.setString("password", password);
		ArrayList<User> users = (ArrayList<User>) query.list();
		// �ύ����
		transaction.commit();
		session.close();

		return users;
	}

	@Override
	public Serializable register(User user) {
		//�ж�����
		Session session = factory.openSession();
		// ��ȡ����
		Transaction transaction = session.beginTransaction();
		// ��ѯ
		String hql = "from User as user where user.userName =:userName";
		Query query=session.createQuery(hql);
		query.setString("userName", user.getUserName());
		@SuppressWarnings("unchecked")
		ArrayList<User> users = (ArrayList<User>) query.list();
		// �ύ����
		transaction.commit();
		session.close();
		if (users.size() > 0) {
			return -1L;
		} else {
			return insert(user);
		}
	}

}
