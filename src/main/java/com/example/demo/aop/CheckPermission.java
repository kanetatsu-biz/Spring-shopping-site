package com.example.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.LoginUser;

@Aspect
@Component
public class CheckPermission {

	@Autowired
	LoginUser loginUser;

	// 管理ユーザーでしか実行できないメソッド
	@Pointcut("execution(* com.example.demo.controller.AdminController.*(..)) ||"
			+ "execution(* com.example.demo.controller.OrderController.histories()) ||"
			+ "execution(* com.example.demo.controller.OrderController.showHistory())")
	public void adminPermissionPointcut() {
	}

	// 管理ユーザーではない場合はログインページにリダイレクト
	@Around("adminPermissionPointcut()")
	public Object checkAdminPermission(
			ProceedingJoinPoint jp) throws Throwable {
		// ログインしていない　または　ログインユーザーが管理権限を持っていない場合
		if (loginUser.getId() == null ||
				!(loginUser.getRole().equals("admin") || loginUser.getRole().equals("system"))) {
			// ログイン画面にリダイレクト
			return "redirect:/error403";
		}

		// 元のメソッドを実行する
		return jp.proceed();
	}
}
