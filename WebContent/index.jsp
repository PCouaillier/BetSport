<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Parrions sport</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link type="text/css" rel="stylesheet" href="css/main.css" />
        <link type="text/css" rel="stylesheet" href="css/bootstrap-theme.css" />
        <link type="text/css" rel="stylesheet" href="css/bootstrap-theme.min.css" />
        <link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
        <link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
    </head>
    <body>
        <div class="header">
            <div  class="open">
		<span class="cls"></span>
		<span>
			<ul class="sub-menu">
				<li class="login">
					<a href="#" id="connec" title="Connexion">Connexion</a>
				</li>
				<li class="register">
					<a href="#" id="inscrip" title="S'inscrire">S'inscrire</a>
				</li>
			</ul>
		</span>
		<span class="cls"></span>
	</div>
            
        </div>
        <div class="body">
            
            <div class="register_form">
		<form action="" method="post" class="">
			<section class="input-list style-1 clearfix">
		  
			   
			    <input type="text" placeholder="Login" name="_username" id="username">
			    
			    <input type="password" placeholder="mot de passe" name="_password" id="password">
			    
                            <input type="submit" name="login" />
			</section>
                </form>
            </div>
            <div class="login_form">
		
		<form action="" method="post" class="">
			<section class="input-list style-1 clearfix">
		  
			   
			    <input type="text" placeholder="Login" name="_username" id="username">
			    
			    <input type="password" placeholder="mot de passe" name="_password" id="password">
			    
                            <input type="submit" name="login" />
			</section>
                </form>
            </div>	
            
            <c:forEach items="${matches}" var="match">
	            <c:if test="${not empty match}">
		            <div class="match">
		                <img src='image/baniere.jpg' class='banniere'/>
		                <div class='equipe1' data-equipe="${match.getTeamOne().getId().getValue()}">
		                    ${match.getTeamOne().get().getName()}
		                </div>
		                <div class="equipe2" data-equipe="">
		                    ${match.getTeamTwo().get().getName()}
		                </div>
		            </div>
		         </c:if>
            </c:forEach>
        </div>
        
        <div class="footer">
            
        </div>
    </body>
    <script src="js/jquery-2.2.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
</html>
