package com.demo.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/** 
* @author nilzxq
* @version 2018年8月8日 下午2:08:46 
* 类说明 
*/
public class CustomRealm extends AuthorizingRealm{

	Map<String,String> userMap=new HashMap<String, String>();
	{
		//md5加密之后
		userMap.put("Mark", "283538989cef48f3d7d8a1c1bdf2008f");
		super.setName("customRealm");
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName=(String) principals.getPrimaryPrincipal();
		//从数据库或者缓存中获取角色数据
		Set<String> roles=getRolesByUserName(userName);
		Set<String> perminssions=getPermissionByUserName(userName);
		SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(perminssions);
		simpleAuthorizationInfo.setRoles(roles);
		return simpleAuthorizationInfo;
	}

	private Set<String> getPermissionByUserName(String userName) {
		Set<String> sets=new HashSet<String>();
		sets.add("user:delete");
		sets.add("user:add");
		return sets;
	}

	/**
	 * 模拟数据库查询凭证
	 * @param userName
	 * @return
	 */
	private Set<String> getRolesByUserName(String userName) {
		Set<String> sets=new HashSet<String>();
		sets.add("admin");
		sets.add("user");
		return sets;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.从主体传过来的认证信息中，获得用户名
		String userName=(String)token.getPrincipal();
		
		//2.通过用户名到数据库中获取凭证
		String password=getPasswordByUserName(userName);
		if(password==null) {
			return null;
		}
		SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo("Mark",password,"customRealm");
		//加盐
		authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Mark"));
		return authenticationInfo;
	}

	/**
	 * 模拟数据库查询凭证
	 * @param userName
	 * @return
	 */
	private String getPasswordByUserName(String userName) {
		
		return userMap.get(userName);
	}

	public static void main(String[] args) {
		//加盐 一般用随机数 这里写死了用Mark
		Md5Hash md5Hash=new Md5Hash("123456","Mark");
		System.out.println(md5Hash.toString());
	}
}
