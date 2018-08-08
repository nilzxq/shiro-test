package com.demo.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.demo.shiro.realm.CustomRealm;

/** 
* @author nilzxq
* @version 2018年8月8日 下午2:18:23 
* 类说明 
*/
public class CustomRealmTest {

	@Test
	public void testAuthentication() {
		
		CustomRealm customRealm=new CustomRealm();
		//1.构建securityManager环境
		DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
		defaultSecurityManager.setRealm(customRealm);
		//2.主体提交认证请求
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		Subject subject=SecurityUtils.getSubject();
		
		UsernamePasswordToken token=new UsernamePasswordToken("Mark","123456");
		subject.login(token);
		
		System.out.println("isAuthenticated:"+subject.isAuthenticated());
		subject.checkRole("admin");
		subject.checkPermissions("user:add","user:delete");
	}
}
