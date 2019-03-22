<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- Shiro提供了JSTL标签用于在JSP/GSP页面进行权限控制；首先需要导入标签库： -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/lib/bootstrap.min.css"/>
</head>
<body>
<!-- 用户没有身份验证时显示相应的信息，即游客访问信息 -->
<shiro:guest>
    <label style="margin-left: 20px;">欢迎游客访问,<a href="${pageContext.request.contextPath}/login.jsp">点击登录</a><br/></label>
</shiro:guest>

<!-- 用户已经身份验证、记住我登录后显示相应的信息，未登录用户将会拦截 -->
<shiro:user>
    <br/>
    <label style="margin-left: 20px;">欢迎：[<shiro:principal/>]登录，<a href="${pageContext.request.contextPath}/logout">点击退出</a></label>
    <br/>
    <hr/>
    <br/>
    <div style="margin-left: 5%;">
        <div>
            <ul class="nav nav-pills nav-stacked" style="width: 10%;border: 1px solid #d9edf7;">
                <li class="active">
                    <a href="${pageContext.request.contextPath}/index.do">首页</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/user/findAll.do">用户列表</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/roles/findAll.do">角色列表</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/permissions/findAll.do">权限列表</a>
                </li>
            </ul>
        </div>
        <div style="margin-left: 21%;margin-right: 16%;margin-top: -6%;" class="text-center">
            <h3>
                SpringMVC + Mybatis + Shiro + Ztree整合案例
            </h3>
            <div style="margin-top: 90px;float: right;font-size: 18px;margin-right: 43px;">
                -CopyFrom TyCoding &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <br/>
                -Blog: <a href="http://www.tycoding.cn" target="_blank">www.tycoding.cn</a>
            </div>
        </div>
    </div>
</shiro:user>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/lib/bootstrap.min.js"></script>
</html>
