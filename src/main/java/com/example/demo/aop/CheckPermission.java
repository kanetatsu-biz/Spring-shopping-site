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
			+ "execution(* com.example.demo.controller.OrderController.histories(..)) ||"
			+ "execution(* com.example.demo.controller.OrderController.showHistory(..))")
	public void adminPermissionPointcut() {
	}

	// 管理ユーザーではない場合はアクセス権限エラー画面にリダイレクト
	@Around("adminPermissionPointcut()")
	public Object checkAdminPermission(
			ProceedingJoinPoint jp) throws Throwable {
		// ログインしていない　または　ログインユーザーが管理権限を持っていない場合
		if (!loginUser.isLogin() ||
				!(loginUser.getRole().equals("admin") || loginUser.getRole().equals("system"))) {
			// アクセス権限エラー画面にリダイレクト
			return "redirect:/error403";
		}

		// 元のメソッドを実行する
		return jp.proceed();
	}

	// 一般ログインユーザーでしか実行できないメソッド
	@Pointcut("execution(* com.example.demo.controller.AddressController.*(..))")
	public void generalLoginUserPermissionPointcut() {
	}

	// 一般ログインユーザーではない場合はアクセス権限エラー画面にリダイレクト
	@Around("generalLoginUserPermissionPointcut()")
	public Object checkGeneralLoginUserPermission(
			ProceedingJoinPoint jp) throws Throwable {
		// ログインしていない または　ログインユーザーが管理権限を持っている場合
		if (!loginUser.isLogin() ||
				loginUser.getRole().equals("admin") || loginUser.getRole().equals("system")) {
			// アクセス権限エラー画面にリダイレクト
			return "redirect:/error403";
		}

		// 元のメソッドを実行する
		return jp.proceed();
	}
}
