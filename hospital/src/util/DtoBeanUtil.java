package util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Locale;

/**
 * @Title: Dto2Bean.java
 * @Package util
 * @Description:
 * @author Pengbin Li
 * @date 2017��9��9�� ����4:51:42
 * @version V1.0
 */

public class DtoBeanUtil {

	/**
	 * ͨ�����淴����ʽ DTO����\ʵ�����ת����
	 * 
	 * @param source
	 *            Դת���Ķ���
	 * @param goal
	 *            Ŀ��ת���Ķ���
	 */
	@SuppressWarnings("rawtypes")
	public static <T, E> void transalte(T source, E goal) {
		Field[] sf = source.getClass().getDeclaredFields();
		int sfLength = sf.length;
		
		Class superClass = source.getClass().getSuperclass();
		while (null != superClass) {
			Field[] ssf = superClass.getDeclaredFields();
			sf = Arrays.copyOf(sf, sf.length + ssf.length);
			System.arraycopy(ssf, 0, sf, sfLength, ssf.length);
			sfLength = sf.length;
			superClass = superClass.getSuperclass();
		}
		
		for (Field f : sf) {
			String sfName = f.getName();
			String sfmName = sfName.substring(0, 1).toUpperCase(Locale.US) + sfName.substring(1);
			try {
				Method m = f.getType().toString().equals("boolean") ? 
						source.getClass().getMethod("is" + sfmName) : 
							source.getClass().getMethod("get" + sfmName);
				Object value = m.invoke(source);
				if (null != value) {
					Method ms = goal.getClass().getMethod("set" + sfmName, f.getType());
					ms.invoke(goal, value);
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * �Զ����null����
	 * 
	 * @param source
	 *            Դת���Ķ���
	 * @param goal
	 *            Ŀ��ת���Ķ���
	 */
	@SuppressWarnings("rawtypes")
	public static <T, E> void autoFillNull(T source, E goal) {
		Field[] sf = source.getClass().getDeclaredFields();
		int sfLength = sf.length;
		
		Class superClass = source.getClass().getSuperclass();
		while (null != superClass) {
			Field[] ssf = superClass.getDeclaredFields();
			sf = Arrays.copyOf(sf, sf.length + ssf.length);
			System.arraycopy(ssf, 0, sf, sfLength, ssf.length);
			sfLength = sf.length;
			superClass = superClass.getSuperclass();
		}
		
		for (Field f : sf) {
			String sfName = f.getName();
			String sfmName = sfName.substring(0, 1).toUpperCase(Locale.US) + sfName.substring(1);
			try {
				Method m = f.getType().toString().equals("boolean") ? 
						source.getClass().getMethod("is" + sfmName) : 
							source.getClass().getMethod("get" + sfmName);
				Object value = m.invoke(source);
				if (null != value && null == m.invoke(goal)) {
					Method ms = goal.getClass().getMethod("set" + sfmName, f.getType());
					ms.invoke(goal, value);
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				System.out.println(e);
			}
		}
	}
	
}
