package util;

/**   
* @Title: ParamForQuery.java 
* @Package util 
* @Description:  
* @author Pengbin Li   
* @date 2018��3��12�� ����12:21:43 
* @version V1.0   
*/

public class ParamForQuery {

	private Object param;
	
	private boolean fuzz;

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public boolean isFuzz() {
		return fuzz;
	}

	public void setFuzz(boolean fuzz) {
		this.fuzz = fuzz;
	}

	public ParamForQuery() {}
	
	public ParamForQuery(Object param, boolean fuzz) {
		super();
		this.param = param;
		this.fuzz = fuzz;
	}
	
}
