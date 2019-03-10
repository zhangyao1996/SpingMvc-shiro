package com.zhangyao.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhangyao
 *@date Mar 7, 2019
 */
@Controller
public class LoginController {
	
	/**
	 * 用户登录的入口
	 * 1.用户输入这两个用户名和密码后提交表单，通过绑定了SecurityManager的SecurityUtils得到Subject实例，然后获取身份验证的UsernamePasswordToken传入用户名和密码。
	 * 2.调用subject.login(token)进行登录，SecurityManager会委托Authenticator把相应的token传给Realm，从Realm中获取身份认证信息。
	 * 3.Realm可以是自己实现的Realm，Realm会根据传入的用户名和密码去数据库进行校验（提供Service层登录接口）。
	 * 4.Shiro从Realm中获取安全数据（如用户、身份、权限等），如果校验失败，就会抛出异常，登录失败；否则就登录成功。
	 * @param username
	 * @param password
	 * @param remember
	 * @param model
	 * @return
	 */
    @RequestMapping("/login")
    public String login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "remember", required = false) String remember,
            Model model) {

        System.out.println("登陆用户输入的用户名：" + username + "，密码：" + password);
        String error = null;
        if (username != null && password != null) {
            //初始化
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            System.out.println("host:"+token.getHost());
            //RememberMe和使用Subject.login(token)登录是有所不同的,RememberMe是使用缓存Cookie的技术实现的登录
            if (remember != null){
                if (remember.equals("on")) {
                    //说明选择了记住我
                    token.setRememberMe(true);
                } else {
                    token.setRememberMe(false);
                }
            }else{
                token.setRememberMe(false);
            }

            try {
                //登录，即身份校验，由通过Spring注入的UserRealm会自动校验输入的用户名和密码在数据库中是否有对应的值
                subject.login(token);
                System.out.println("用户是否登录：" + subject.isAuthenticated());
                return "redirect:index.do";
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                error = "用户账户不存在，错误信息：" + e.getMessage();
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                error = "用户名或密码错误，错误信息：" + e.getMessage();
            } catch (LockedAccountException e) {
                e.printStackTrace();
                error = "该账号已锁定，错误信息：" + e.getMessage();
            } catch (DisabledAccountException e) {
                e.printStackTrace();
                error = "该账号已禁用，错误信息：" + e.getMessage();
            } catch (ExcessiveAttemptsException e) {
                e.printStackTrace();
                error = "该账号登录失败次数过多，错误信息：" + e.getMessage();
            } catch (Exception e){
                e.printStackTrace();
                error = "未知错误，错误信息：" + e.getMessage();
            }
        } else {
            error = "请输入用户名和密码";
        }
        //登录失败，跳转到login页面
        model.addAttribute("error", error);
        return "login";
    }
    
    
    
    /**
     * 登录成功，跳转到首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
    	System.out.println("登陆成功");
        return "index";
    }

}
