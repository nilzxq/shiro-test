package com.demo.shiro.test;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

/** 
* @author nilzxq
* @version 2018年8月7日 下午7:44:14 
* 类说明 
*/
public class JdbcRealmTest {
	
	DruidDataSource dataSource=new DruidDataSource();
	
	{
		dataSource.setUrl("jdbc:mysql://localhost:3306/shiro_test?useUnicode=true&characterEncoding=utf-8&useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
	}
	@Test
	public void testAuthentication() {
		
		JdbcRealm jdbcRealm=new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);
		
		//使用自定义的sql语句
		String sql="select password from test_user where user_name=?";
		jdbcRealm.setAuthenticationQuery(sql);
		String roleSql="select role_name from test_user_role where user_name=?";
		jdbcRealm.setUserRolesQuery(roleSql);
		
		//1.构建securityManager环境
		DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
		defaultSecurityManager.setRealm(jdbcRealm);
		//2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject=SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("xiaoming","654321");
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
//		subject.checkRole("admin");
//		subject.checkRoles("admin","user");
//		subject.checkPermission("user:select");
		subject.checkRole("user");
	}
}
