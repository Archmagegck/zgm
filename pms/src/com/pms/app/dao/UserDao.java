package com.pms.app.dao;

import java.util.List;

import com.pms.app.entity.User;
import com.pms.base.dao.BaseDao;

public interface UserDao extends BaseDao<User, String> {
	
	public List<User> findByLoginNameAndLoginPwd(String loginName, String loginPwd);

}
