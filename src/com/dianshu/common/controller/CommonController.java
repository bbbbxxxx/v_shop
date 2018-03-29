package com.dianshu.common.controller;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import com.dianshu.permission.service.RoleService;


@Controller
@Scope(value="prototype")
@RequestMapping("open")
public class CommonController extends BaseController {
	@Resource
	RoleService roleService;
	
	/**
	 * 404错误
	 * @param request
	 * @return
	 */
	@RequestMapping("404")
	public ModelAndView _404(HttpServletRequest request){
		ModelAndView view = new ModelAndView("common/404");
		return view;
	}
	/**
	 * 500错误
	 * @param request
	 * @return
	 */
	@RequestMapping("500")
	public ModelAndView _500(HttpServletRequest request){
		ModelAndView view = new ModelAndView("common/500");
		
		Throwable t = (Throwable)request.getAttribute("javax.servlet.error.exception");
		String defaultMessage = "未知" ;
		if(null == t){
			view.addObject("line", defaultMessage);
			view.addObject("clazz", defaultMessage);
			view.addObject("methodName",defaultMessage);
			return view;
		}
		String message = t.getMessage() ;//错误信息
		StackTraceElement[] stack = t.getStackTrace();
		view.addObject("message", message);
		if(null != stack && stack.length != 0 ){
			StackTraceElement element = stack[0];
			int line = element.getLineNumber();//错误行号
			String clazz = element.getClassName();//错误java类
			String fileName = element.getFileName();
			
			String methodName = element.getMethodName() ;//错误方法
			view.addObject("line", line);
			view.addObject("clazz", clazz);
			view.addObject("methodName",methodName);
//			LoggerUtils.fmtError(getClass(), "line:%s,clazz:%s,fileName:%s,methodName:%s()",
//					line,clazz,fileName,methodName);
		}
		return view;
	}
	
	
	/**
	 * 跳转到其他网站
	 * @param url
	 * @return
	 */
	@RequestMapping(value="www/open/goto",method=RequestMethod.GET)
	public ModelAndView _goto(String url){
		
		return new ModelAndView("www/go_to","url",url);
	}
	
	/**
	 * 没有权限提示页面
	 * @return
	 */
	@RequestMapping(value="unauthorized",method=RequestMethod.GET)
	public ModelAndView unauthorized(){
		return new ModelAndView("open/unauthorized");
	}
	@RequestMapping(value="shiro",method=RequestMethod.GET)
	public ModelAndView shiro(){
		return new ModelAndView("shiro");
	}
}
