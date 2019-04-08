package util;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.propertyeditors.PropertiesEditor;

import bean.Department;

/**   
* @Title: MyPropertyEditor.java 
* @Package util 
* @Description:  
* @author Pengbin Li   
* @date 2018年3月8日 上午1:59:10 
* @version V1.0   
*/

public class DepartmentEditor extends PropertiesEditor {
	@Override
    public void setAsText(String source) throws IllegalArgumentException {
        Department departmentDTO = new Department();
        // 类型转化
        try {
            JSONObject jsonObject = new JSONObject(source);
            departmentDTO.setId(jsonObject.isNull("id") ? null : Long.valueOf(String.valueOf(jsonObject.get("id"))));
            departmentDTO.setName(jsonObject.isNull("name") ? null : (String) jsonObject.get("name"));
            departmentDTO.setCreateDate(jsonObject.isNull("createDate") ? null : (Date) jsonObject.get("createDate"));
            departmentDTO.setUpdateDate(jsonObject.isNull("updateDate") ? null : (Date) jsonObject.get("updateDate"));
            departmentDTO.setDepartmentName(jsonObject.isNull("departmentName") ? null : (String) jsonObject.get("departmentName"));
            departmentDTO.setDepartmentDesc(jsonObject.isNull("departmentDesc") ? null : (String) jsonObject.get("departmentDesc"));
            setValue(departmentDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
