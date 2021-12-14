<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) -->
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/jquery/jquery.min.js"></script>
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>

<meta charset="UTF-8">
<title>${thisBook.title}</title>
</head>
<body>
	<div class="container" style="
	background-color: lightgray;
	 margin-top: 100px;
	 padding: 50px;">
		<div class="row">
			<h1>${thisBook.title}</h1>
			<div style="margin-left: 1099px;">
				<a href="/home">Back to the shelves</a>
			</div>
		</div>
		<p style="font-style: bold;">
			<span style="color: red;">${user.getUserName()}</span>
				read
			<span style="color: purple;">${thisBook.title}</span>
				by
			<span style="color: green;">${thisBook.author}</span>	
			
		</p>
		<hr>
			<p>Here are ${user.getUserName()}'s thoughts: </p>
			<p>"${thisBook.thoughts}"</p>
			
		<c:if test="${userCon.contains(userEdit) == true}">
			<a href="/books/${thisBook.id}/edit">Edit Book!</a>
		</c:if>
	</div>
</body>
</html>