<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
<%--shiro 标签 --%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;  
%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
	    <base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title>${token.nickname} —个人中心</title>
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
		<link   rel="icon" href="${basePath}/favicon.ico" type="image/x-icon" />
		<link   rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
		<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet"/>
		<link href="<%=basePath%>/css/common/base.css" rel="stylesheet"/>
		<script  src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
		<script  src="<%=basePath%>/js/common/layer/layer.js"></script>
		<script  src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	</head>
	<body data-target="#one" data-spy="scroll">
		
		
<div class="navbar navbar-inverse navbar-fixed-top animated fadeInDown" style="z-index: 101;height: 41px;">
      <div class="container" style="padding-left: 0px; padding-right: 0px;">
        <div class="navbar-header">
          <button data-target=".navbar-collapse" data-toggle="collapse" type="button" class="navbar-toggle collapsed">
            <span class="sr-only">导航</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
	     </div>
	     <div role="navigation" class="navbar-collapse collapse">
	     		<a id="_logo"  href="<%=basePath%>" style="color:#fff; font-size: 24px;" class="navbar-brand hidden-sm">后台管理</a>
	          <ul class="nav navbar-nav" id="topMenu">
				<li class="dropdown active">
					<a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" class="dropdown-toggle" href="<%=basePath%>/user/index.shtml">
						个人中心<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="<%=basePath%>/user/index">个人资料</a></li>
						<li><a href="<%=basePath%>/user/updateSelf" >资料修改</a></li>
						<li><a href="<%=basePath%>/user/updatePswd" >密码修改</a></li>
						<li><a href="<%=basePath%>/role/mypermission">我的权限</a></li>
					</ul>
				</li>	  
				<!--拥有 角色888888（管理员） ||  100002（用户中心）-->
				
			 	<shiro:hasAnyRoles name='888888,100002'>          
				<li class="dropdown ">
					<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" class="dropdown-toggle" href="<%=basePath%>/member/list.shtml">
						用户中心<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						 <shiro:hasPermission name="/member/list"> 
							<li><a href="<%=basePath%>/member/list">用户列表</a></li>
						 </shiro:hasPermission>
						<shiro:hasPermission name="/member/online"> 
							<li><a href="<%=basePath%>/member/addMember">添加用户</a></li>
						 </shiro:hasPermission> 
					</ul>
				</li>	
				 </shiro:hasAnyRoles>        
				<!--拥有 角色888888（管理员） ||  100003（权限频道）-->
				 <shiro:hasAnyRoles name='888888,100003'> 
					<li class="dropdown ">
						<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" class="dropdown-toggle" href="<%=basePath%>/permission/index.shtml">
							权限管理<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							 <shiro:hasPermission name="/role/index"> 
								<li><a href="<%=basePath%>/role/index">角色列表</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/role/allocation"> 
								<li><a href="<%=basePath%>/role/allocation">角色分配</a></li>
							 </shiro:hasPermission>
							<shiro:hasPermission name="/permission/index"> 
								<li><a href="<%=basePath%>/permission/index">权限列表</a></li>
							 </shiro:hasPermission> 
						<shiro:hasPermission name="/permission/allocation"> 
								<li><a href="<%=basePath%>/permission/allocation">权限分配</a></li>
							 </shiro:hasPermission> 
						</ul>
					</li>	
				</shiro:hasAnyRoles>    
				
	          </ul>
	           <ul class="nav navbar-nav  pull-right" >
				<li class="dropdown " style="color:#fff;">
				<c:if test="${token!=null}">
				<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" onclick="location.href='<%=basePath%>/user/index.shtml'" href="<%=basePath%>/user/index.shtml" class="dropdown-toggle qqlogin" >
							${token.nickname}<span class="caret"></span></a>
							<ul class="dropdown-menu" userid="${token.id}">
								<li><a href="<%=basePath%>/user/index">个人资料</a></li>
								<li><a href="<%=basePath%>/role/mypermission">我的权限</a></li>
								<li><a href="<%=basePath%>/u/logout">退出登录</a></li>
							</ul>
				
				</c:if>
				<c:if test="${token==null}">
				  <a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" href="javascript:void(0);" onclick="location.href='<%=basePath%>/u/login.shtml'" class="dropdown-toggle qqlogin" >
				登录</a>
						 					
				</c:if>
					  
				   
				</li>	
	          </ul>
	          <style>#topMenu>li>a{padding:10px 13px}</style>
	    </div>
  	</div>
</div>
		
		
		
		
		
		<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
			
				<!-- 左侧 -->
				<div id="one" class="col-md-2">
	<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
	  <li class="dropdown">
	      <a href="<%=basePath%>/user/index">
	    	 <i class="glyphicon glyphicon-chevron-right"></i>个人资料
	      </a>
	       <ul class="dropdown-menu" aria-labelledby="dLabel" style="margin-left: 160px; margin-top: -40px;">
              <li><a href="<%=basePath%>/user/updateSelf">资料修改</a></li>
              <li><a href="<%=basePath%>/user/updatePswd">密码修改</a></li>
          </ul>
	  </li>
	  <li class="active">
	      <a href="<%=basePath%>/role/mypermission">
	    	 <i class="glyphicon glyphicon-chevron-right"></i>我的权限
	      </a>
	  </li>
	</ul>
</div>
				<!-- 左侧结束 -->
				
				<div class="col-md-10">
					<h2>我的权限</h2>
					<hr>
					<div id="getPermissionTree" >loding... ...</div>
				</div>
			</div>
			<!--/row-->
		</div>
		<script  src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script  src="<%=basePath%>/js/common/bootstrap/bootstrap-treeview.js"></script>
		<script >
			$(function(){
				//加载 permission tree data
				var load = layer.load();
				$.post("<%=basePath%>/role/getPermissionTree",{},function(data){
					layer.close(load);
					if(data && !data.length){
						return $("#getPermissionTree").html('<code>您没有任何权限。只有默认的个人中心。</code>'),!1;
					}
					$('#getPermissionTree').treeview({
			          levels: 1,//层级
			          color: "#428bca",
			          nodeIcon: "glyphicon glyphicon-user",
			          showTags: true,//显示数量
			          data: data//数据
			        });
				},'json');
			});
		</script>
			
	</body>
</html>