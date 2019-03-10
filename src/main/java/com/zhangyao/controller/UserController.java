package com.zhangyao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhangyao.service.UserService;

/**
 * @author zhangyao
 *@date Mar 7, 2019
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
    /**
     * 查询用户列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/findAll")
    public String findAllUser(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "page/user";
    }

}
