package com.sjtu.icare.common.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sjtu.icare.common.config.ErrorConstants;
import com.sjtu.icare.common.config.OrderByConstant;
import com.sjtu.icare.common.persistence.Page;
import com.sjtu.icare.modules.sys.entity.Gero;
import com.sjtu.icare.modules.sys.entity.Privilege;
import com.sjtu.icare.modules.sys.entity.Role;
import com.sjtu.icare.modules.sys.entity.User;
import com.sjtu.icare.modules.sys.service.SystemService;
import com.sjtu.icare.modules.sys.utils.UserUtils;
import com.sjtu.icare.modules.sys.webservice.UserController;

@RestController
public class SysBaseController {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private SystemService systemService;
	
	/**
	 * 通过id获取user
	 * @param uid
	 * @return
	 */
	protected User getUserFromId (int uid){
		User user;
		try {
			user = UserUtils.get(uid);
			if (user == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.USER_FOR_ID_NOT_FOUND,
					"[uid=" + uid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return user;
	}
	
	protected Gero getGeroFromId(int gid){
		
		Gero gero = new Gero();
		try {
			gero = systemService.getGeroById(new Gero(gid));
			if (gero == null){
				throw new Exception();
			}
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.GERO_FOR_ID_NOT_FOUND,
					"[gid=" + gid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return gero;
	}
	
	protected Role getRoleFromId(int rid){
		
		Role role = new Role();
		try {
			role = systemService.getRoleById(new Role(rid));
			if (role == null){
				throw new Exception();
			}
		} catch (Exception e) {
			String message = ErrorConstants.format(ErrorConstants.ROLE_FOR_ID_NOT_FOUND,
					"[rid=" + rid + "]");
			logger.error(message);
			throw new RestException(HttpStatus.NOT_FOUND, message);
		}
		return role;
	}
	
	protected <T> Page<T> setOrderBy (Page<T> page, String orderByTag){
		String orderBy = "id";
		try {
			orderBy = OrderByConstant.valueOf(orderByTag).getTag();
		} catch (Exception e1) {
			String message = ErrorConstants.format(ErrorConstants.ORDER_BY_PARAM_INVALID,"");
			logger.error(message);
			throw new RestException(HttpStatus.BAD_REQUEST, message);
		}
		page.setOrderBy(orderBy);
		return page;
	}
	
	/**
	 * user返回格式
	 * @param user
	 * @return
	 */
	protected Map<String, Object> getUserMapFromUser(User user){
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("id", user.getId());
		userMap.put("name", user.getName());
		userMap.put("username", user.getLoginName());
		userMap.put("user_type", user.getUserType());
		userMap.put("user_id", user.getUserId());
		ArrayList<Object> roleList = new ArrayList<Object>();
		for (Role role : user.getRoleList()){
			Map<String, Object> roleMap = new HashMap<String, Object>();
			roleMap.put("id", role.getId());
			roleMap.put("name", role.getName());
			roleList.add(roleMap);
		}
		userMap.put("role_list", roleList);
		ArrayList<Object> privilegeList = new ArrayList<Object>();
		for (Privilege privilege : UserUtils.getPrivilegeList(user)){
			privilegeList.add(getPrivilegeMapFromPrivilege(privilege));
		}
		userMap.put("privilege_list", privilegeList);
		userMap.put("register_date", user.getRegisterDate());
		userMap.put("cancel_date", user.getCancelDate());
		userMap.put("photo_url", user.getPhotoUrl());
		return userMap;
	}
	
	/**
	 * Privilege返回格式
	 * @param privilege
	 * @return
	 */
	protected Map<String, Object> getPrivilegeMapFromPrivilege(Privilege privilege){
		Map<String, Object> privilegeMap = new HashMap<String, Object>();
		privilegeMap.put("id", privilege.getId());
		privilegeMap.put("name", privilege.getName());
		privilegeMap.put("parent_id", privilege.getParentId());
		privilegeMap.put("parent_ids", privilege.getParentIds());
		privilegeMap.put("permission", privilege.getPermission());
		privilegeMap.put("href", privilege.getHref());
		privilegeMap.put("icon", privilege.getIcon());
		return privilegeMap;
	}
	
	protected Map<String, Object> getRoleInfoMapFromRole(Role role) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("id", role.getId());
		roleMap.put("name", role.getName());
		roleMap.put("note", role.getNotes());
		return roleMap;
	}
	
	protected Map<String, Object> getRoleMapFromRole(Role role) {
		Map<String, Object> roleMap = new HashMap<String, Object>();
		roleMap.put("id", role.getId());
		roleMap.put("name", role.getName());
		roleMap.put("note", role.getNotes());
		ArrayList<Object> privilegeList = new ArrayList<Object>();
		for (Privilege privilege : role.getPrivilegeList()){
			privilegeList.add(getPrivilegeMapFromPrivilege(privilege));
		}
		roleMap.put("privilege_list", privilegeList);
		return roleMap;
	}
	
}