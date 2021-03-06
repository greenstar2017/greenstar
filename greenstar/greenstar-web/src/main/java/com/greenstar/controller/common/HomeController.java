package com.greenstar.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.greenstar.entity.User;
import com.greenstar.utils.IpUtil;
import com.greenstar.utils.MD5Util;

/**
 * Created by yangqj on 2017/4/21.
 */
@Controller
public class HomeController {
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, User user, Model model) {
		if (StringUtils.isEmpty(user.getUsername())
				|| StringUtils.isEmpty(user.getPassword())) {
			request.setAttribute("msg", "用户名或密码不能为空！");
			return "login";
		}
		String host = IpUtil.getIpAddr(request);
		boolean rememberMe = true;
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				user.getUsername(), MD5Util.getMD5code(user.getPassword()), rememberMe, host);
		try {
			subject.login(token);
			return "redirect:usersPage";
		} catch (LockedAccountException lae) {
			token.clear();
			request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
			return "login";
		} catch (AuthenticationException e) {
			token.clear();
			request.setAttribute("msg", "用户或密码不正确！");
			return "login";
		}
	}

	@RequestMapping(value = { "/usersPage", "" })
	public String usersPage() {
		return "user/users";
	}

	@RequestMapping("/rolesPage")
	public String rolesPage() {
		return "role/roles";
	}

	@RequestMapping("/resourcesPage")
	public String resourcesPage() {
		return "resources/resources";
	}

	@RequestMapping("/403")
	public String forbidden() {
		return "403";
	}
}
