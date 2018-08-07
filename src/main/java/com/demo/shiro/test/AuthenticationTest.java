package com.demo.shiro.test;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/** 
* @author nilzxq
* @version 2018年8月6日 下午8:13:45 
* 类说明 
*/
public class AuthenticationTest {

	SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();
	@Before
	public void addUser() {
		simpleAccountRealm.addAccount("Mark","123456");
	}
	@Test
	public void testAuthentication() {
		
		//1.构建securityManager环境
		DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
		defaultSecurityManager.setRealm(simpleAccountRealm);
		
		//2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject=SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("Mark","123456");
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
	}
}
