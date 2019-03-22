package com.zhangyao.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhangyao.entity.User;
import com.zhangyao.entity.UserOnline;

/**
* @author zhangyao
* @version Mar 19, 2019 9:18:26 AM
*/
@Controller
@RequestMapping("/session")
public class SessionController {
	
	@Autowired
	private SessionDAO  sessionDAO;
	/*
	 * 通过这些Session对象，我们可以实现一些比较有趣的功能，比如查看当前系统的在线人数，查看这些在线用户的一些基本信息，强制让某个用户下线等。
	 */
	@ResponseBody
	@RequestMapping("/sessList")
	 public List<UserOnline> list() {
	        List<UserOnline> list = new ArrayList<>();
	        Collection<Session> sessions = sessionDAO.getActiveSessions();
	        //只能获取登陆的会话
	        for (Session session : sessions) {
	            UserOnline userOnline = new UserOnline();
	            User user = new User();
	            SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
	            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
	                continue;
	            } else {
	                principalCollection = (SimplePrincipalCollection) session
	                	.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
	                String userName=  (String) principalCollection.getPrimaryPrincipal();
	                userOnline.setUsername(userName);
	              //  userOnline.setUserId(user.getId().toString());
	            }
	            userOnline.setId((String) session.getId());
	            userOnline.setHost(session.getHost());
	            userOnline.setStartTimestamp(session.getStartTimestamp());
	            userOnline.setLastAccessTime(session.getLastAccessTime());
	            Long timeout = session.getTimeout();
	            if (timeout == 0l) {
	                userOnline.setStatus("离线");
	            } else {
	                userOnline.setStatus("在线");
	            }
	            userOnline.setTimeout(timeout);
	            list.add(userOnline);
	        }
	        return list;
	    }
}

