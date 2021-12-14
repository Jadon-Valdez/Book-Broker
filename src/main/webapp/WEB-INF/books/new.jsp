<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Formatting (dates) --> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<meta charset="UTF-8">
<title>New Book</title>
</head>
<body>
	<div class="container" style="
	background-color: lightgray;
	 margin-top: 100px;
	 padding: 50px;">
		<h1>Add a book to your shelf!</h1>
		<form:form action="/books" method="post" modelAttribute="book">
			<form:errors path="*"/>
			<p>
				<form:label path="title">Title: </form:label>
				<form:errors path="title" />
				<form:input path="title" />
			</p>
			<p>
				<form:label path="author">Author: </form:label>
				<form:errors path="author" />
				<form:input path="author" />
			</p>
			<p>
				<form:label path="thoughts">My Thoughts: </form:label>
				<form:errors path="thoughts" />
				<form:input path="thoughts" />
			</p>
			<input type="submit" value="Submit" />
		</form:form>
		<a href="/home">Home</a>
	</div>
</body>
</html>