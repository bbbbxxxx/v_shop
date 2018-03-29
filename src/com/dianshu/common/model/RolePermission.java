package com.dianshu.common.model;


import java.io.Serializable;

import net.sf.json.JSONObject;
/**
 * 
 * 角色{@link Role}和 权限{@link UPermission}中间表
 * 
 */
public class RolePermission implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**{@link Role.id}*/
    private Long rid;
    /**{@link UPermission.id}*/
    private Long pid;

    public RolePermission() {
	}
    public RolePermission(Long rid,Long pid) {
    	this.rid = rid;
    	this.pid = pid;
    }
    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
    public String toString(){
    	return JSONObject.fromObject(this).toString();
    }
}