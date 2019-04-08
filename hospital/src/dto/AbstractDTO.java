package dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Title: AbstractDTO.java
 * @Package dto
 * @Description:
 * @author Pengbin Li
 * @date 2017年9月9日 下午4:57:05
 * @version V1.0
 */

public abstract class AbstractDTO {
	
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

}
