package com.ebb.model;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Role extends Model<Role>{
	private Integer id;
	private String name;
	private String operator;
	private Date create_time;
	private Date update_time;
	private List<String> menu_list;
	
	public Role() {
		super();
	}
	public Role(Integer id, String name, String operator, Date create_time,
			Date update_time, List<String> menu_list) {
		super();
		this.id = id;
		this.name = name;
		this.operator = operator;
		this.create_time = create_time;
		this.update_time = update_time;
		this.menu_list = menu_list;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public List<String> getMenu_list() {
		return menu_list;
	}
	public void setMenu_list(List<String> menu_list) {
		this.menu_list = menu_list;
	}
	
	
}
