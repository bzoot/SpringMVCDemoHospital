package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**   
* @Title: HibernateSessionFactory.java 
* @Package util.hibernate 
* @Description:  
* @author Pengbin Li   
* @date 2017年8月20日 上午1:25:34 
* @version V1.0   
*/

public class HibernateSessionFactory {
	
	private SessionFactory factory; 
	
	private HibernateSessionFactory() {
		setFactory(new AnnotationConfiguration().configure().buildSessionFactory());
	}
	
	private static HibernateSessionFactory instance = new HibernateSessionFactory();
	
	public static HibernateSessionFactory getInstance() {
		return instance;
	}

	public SessionFactory getFactory() {
		return factory;
	}

	private void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
}
