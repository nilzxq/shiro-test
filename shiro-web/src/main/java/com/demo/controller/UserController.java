package com.demo.controller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;

import com.demo.vo.User;

/** 
* @author nilzxq
* @version 2018年8月8日 下午8:41:05 
* 类说明 
*/
@Controller
public class UserController {

	@RequestMapping(value="/subLogin",method=RequestMethod.POST,
			produces="application/json;charset=utf-8")
	@ResponseBody
	public String subLogin(User user) {
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(user.getUsername(),
				user.getPassword());
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			return e.getMessage();
		}
		
		return "登录成功";
	}
}
