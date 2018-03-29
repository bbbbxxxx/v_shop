package com.dianshu.common.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
			/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
			private Long id;
			/**昵称*/
		    private String nickname;
		    /**邮箱 | 登录帐号*/
		    private String name;
		    /**密码*/
		    private transient String pswd;
		    /**创建时间*/
		    private Date createTime;
		    /**最后登录时间*/
		    private Date lastLoginTime;
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public String getNickname() {
				return nickname;
			}
			public void setNickname(String nickname) {
				this.nickname = nickname;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getPswd() {
				return pswd;
			}
			public void setPswd(String pswd) {
				this.pswd = pswd;
			}
			public Date getCreateTime() {
				return createTime;
			}
			public void setCreateTime(Date createTime) {
				this.createTime = createTime;
			}
			public Date getLastLoginTime() {
				return lastLoginTime;
			}
			public void setLastLoginTime(Date lastLoginTime) {
				this.lastLoginTime = lastLoginTime;
			}
			public User() {
				super();
				// TODO Auto-generated constructor stub
			}
			public User(Long id, String nickname, String name, String pswd, Date createTime, Date lastLoginTime) {
				super();
				this.id = id;
				this.nickname = nickname;
				this.name = name;
				this.pswd = pswd;
				this.createTime = createTime;
				this.lastLoginTime = lastLoginTime;
			}
		    
		    
		    
		    
}
