package bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: AbstractBean.java
 * @Package bean
 * @Description:
 * @author Pengbin Li
 * @date 2017年9月9日 下午3:09:04
 * @version V1.0
 */

@MappedSuperclass
public abstract class AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -495566175417925469L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)//代表主键的生成策略
	private Long id;
	
	private String name;
	
	private Date createDate;

	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "AbstractBean [id=" + id + ", name=" + name + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ "]";
	}

}
