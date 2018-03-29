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
		<script  src="<%=basePath%>/js/shiro.demo.js"></script>
	    <script >
	    so.init(function(){
			//初始化全选。
			so.checkBoxInit('#checkAll','[check=box]');
			<shiro:hasPermission name="/permission/deletePermissionById.shtml">
			//全选
			so.id('deleteAll').on('click',function(){
				var checkeds = $('[check=box]:checked');
				if(!checkeds.length){
					return layer.msg('请选择要删除的选项。',so.default),!0;
				}
				var array = [];
				checkeds.each(function(){
					array.push(this.value);
				});
				return deleteById(array);
			});
			</shiro:hasPermission>
		});
	    <shiro:hasPermission name="/permission/deletePermissionById.shtml">
	    <!--根据ID数组删除角色-->
		function deleteById(ids){
			var index = layer.confirm("确定这"+ ids.length +"个权限？",function(){
				var load = layer.load();
				$.post('<%=basePath%>/permission/deletePermissionById',{ids:ids.join(',')},function(result){
					layer.close(load);
					if(result && result.status != 200){
						return layer.msg(result.message,so.default),!0;
					}else{
						layer.msg(result.resultMsg);
						setTimeout(function(){
							$('#formId').submit();
						},1000);
					}
				},'json');
				layer.close(index);
			});
		}
		</shiro:hasPermission>-->
		<shiro:hasPermission name="/permission/addPermission.shtml">
		<!--添加权限-->
		function addPermission(){
			var name = $('#name').val(),
				url  = $('#url').val();
			if($.trim(name) == ''){
				return layer.msg('权限名称不能为空。',so.default),!1;
			}
			if($.trim(url) == ''){
				return layer.msg('权限Url不能为空。',so.default),!1;
			}
			<!--loding-->
			var load = layer.load();
			$.post('<%=basePath%>/permission/addPermission',{name:name,url:url},function(result){
				layer.close(load);
				if(result && result.status != 200){
					return layer.msg(result.message,so.default),!1;
				}
				layer.msg('添加成功。');
				setTimeout(function(){
					$('#formId').submit();
				},1000);
			},'json');
		}
		</shiro:hasPermission>
	</script>
			
	</head>
	<body data-target="#one" data-spy="scroll">
		<!-- 头部开始 -->
		
		<script baseUrl="<%=basePath%>" src="<%=basePath%>/js/user.login.js"></script>
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
	          
	          <!-- 个人中心 -->
				<li class="dropdown ">
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
				<!-- /个人中心 -->
				
				<!--拥有 角色888888（管理员） ||  100002（用户中心）-->
				<!-- 用户中心 -->
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
							<li><a href="<%=basePath%>/member/addMember">用户添加</a></li>
						 </shiro:hasPermission> 
					</ul>
				</li>	
				 </shiro:hasAnyRoles> 
				<!-- /用户中心 -->
				       
				<!-- 权限管理 -->
				<!--拥有 角色888888（管理员） ||  100003（权限频道）-->
				 <shiro:hasAnyRoles name='888888,100003'> 
					<li class="dropdown active">
						<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" class="dropdown-toggle" href="<%=basePath%>/permission/index.shtml">
							权限管理<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							 <shiro:hasPermission name="/role/index"> 
								<li><a href="<%=basePath%>/role/index">角色列表</a></li>
							 </shiro:hasPermission>
							<shiro:hasPermission name="/role/allocation"> 
								<li><a href="<%=basePath%>/role/allocation">角色分配（这是个JSP页面）</a></li>
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
			<!-- /权限管理 -->
	          </ul>
	          
	           <ul class="nav navbar-nav  pull-right" >
				<li class="dropdown " style="color:#fff;">
				<c:if test="${token!=null}">
				<a aria-expanded="false" aria-haspopup="true"  role="button" data-toggle="dropdown" onclick="location.href='<%=basePath%>/user/index.shtml'" href="<%=basePath%>/user/index.shtml" class="dropdown-toggle qqlogin" >
							${token.nickname}<span class="caret"></span></a>
							<ul class="dropdown-menu" userid="${token.id}">
								<li><a href="<%=basePath%>/user/index">个人资料</a></li>
								<li><a href="<%=basePath%>/role/mypermission">我的权限</a></li>
								<li><a href="<%=basePath%>/u/logout" >退出登录</a></li>
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
<!-- 头部结束 -->


	<div class="container" style="padding-bottom: 15px;min-height: 300px; margin-top: 40px;">
			<div class="row">
				<!-- 左侧开始 -->
				
			 <shiro:hasAnyRoles name='888888,100003'>   
		<div id="one" class="col-md-2">
			<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
			 
			  <shiro:hasPermission name="/role/index.shtml"> 
			  <li class="dropdown">
			      <a href="<%=basePath%>/role/index">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>角色列表
			      </a>
			  </li>
			  </shiro:hasPermission>
			 <shiro:hasPermission name="/role/allocation.shtml"> 
			  <li class="dropdown">
			      <a href="<%=basePath%>/role/allocation">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>角色分配
			      </a>
			  </li>
			   </shiro:hasPermission>
			  <shiro:hasPermission name="/permission/index.shtml"> 
			  <li class="active">
			      <a href="<%=basePath%>/permission/index">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>权限列表
			      </a>
			  </li>
			   </shiro:hasPermission>
			  <shiro:hasPermission name="/permission/allocation.shtml"> 
			  <li class=" dropdown">
			      <a href="<%=basePath%>/permission/allocation">
			    	 <i class="glyphicon glyphicon-chevron-right"></i>权限分配
			      </a>
			  </li>
			   </shiro:hasPermission> 
			</ul>
		</div>
	 </shiro:hasAnyRoles>   
				
				<!-- 左侧结束 -->
				<div class="col-md-10">
					<h2>权限列表</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline formId">
						<div clss="well">
					      <div class="form-group">
					        <input type="text" class="form-control" style="width: 300px;" value="${findContent}" 
					        			name="findContent" id="findContent" placeholder="输入权限名称">
					      </div>
					     <span class=""> <!--pull-right -->
				         	<button type="submit" class="btn btn-primary">查询</button>
				         	 <shiro:hasPermission name="/permission/addPermission.shtml"> --%>
				         		<a class="btn btn-success" onclick="$('#addPermission').modal();">增加权限</a>
				         	 </shiro:hasPermission> --%>
				         	 <shiro:hasPermission name="/permission/deletePermissionById.shtml"> 
				         		<button type="button" id="deleteAll" class="btn  btn-danger">Delete</button>
				         	 </shiro:hasPermission> 
				         </span>    
				        </div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th><input type="checkbox" id="checkAll"/></th>
							<th>权限名称</th>
							<th>角色类型</th>
							<th>操作</th>
						</tr>
						<c:choose>
							<c:when test="${page != null && fn:length(page.list) gt 0}">
						     <c:forEach items="${page.list}" var="it">
								<tr>
									<td><input value="${it.id}" check='box' type="checkbox" /></td>
									<c:if test="${it.name==null}"><td>—</td></c:if>
									<c:if test="${it.name!=null}"><td>${it.name}</td></c:if>
									<c:if test="${it.url==null}"><td>—</td></c:if>
									<c:if test="${it.url!=null}"><td>${it.url}</td></c:if>
		
									<td>
										<shiro:hasPermission name="/permission/deletePermissionById.shtml"> 
										<i class="glyphicon glyphicon-remove"></i><a href="javascript:deleteById([${it.id}]);">删除</a>
										 </shiro:hasPermission> 
									</td>
								</tr>
							</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="6">没有找到用户</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
						<c:if test="${page != null && fn:length(page.list) gt 0}">
						<div class="pagination pull-right">
							${page.pageHtml}
						</div>
					</c:if>
					</form>
				</div>
			</div><!--/row-->
			 <shiro:hasPermission name="/permission/addPermission.shtml"> 
				<!--添加弹框-->
				<div class="modal fade" id="addPermission" tabindex="-1" role="dialog" aria-labelledby="addPermissionLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="addPermissionLabel">添加权限</h4>
			      </div>
			      <div class="modal-body">
			        <form id="boxRoleForm">
			          <div class="form-group">
			            <label for="recipient-name" class="control-label">权限名称:</label>
			            <input type="text" class="form-control" name="name" id="name" placeholder="请输入权限名称"/>
			          </div>
			          <div class="form-group">
			            <label for="recipient-name" class="control-label">权限URL地址:</label>
			            <input type="text" class="form-control" id="url" name="url"  placeholder="请输入权限URL地址">
			          </div>
			        </form>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			        <button type="button" onclick="addPermission();" class="btn btn-primary">Save</button>
			      </div>
			    </div>
			  </div>
			</div>
				<!--/添加弹框-->
			 </shiro:hasPermission>
			
			
		</div>
		
		


</body>
</html>