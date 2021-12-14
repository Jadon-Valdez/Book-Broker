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
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<meta charset="UTF-8">
<title>Book Broker</title>
</head>
<body>
	<div class="container"
		style="background-color: lightgray; margin-top: 100px; padding: 50px;">
		<div class="row">
			<h1>Hello, ${name}</h1>
			<div style="margin-left: 680px;">
				<a href="/logout">Logout</a>
			</div>
			<div class="row">
				<div style="margin-left: 20px;">
					<p>Available books to borrow!</p>
				</div>
				<div style="margin-left: 890px;">
					<a href="/books/new">+ Add a book to my shelf!</a>
				</div>
			</div>
		</div>
		<hr>
		<div class="container">
			<table>
				<thead style="border: 1px solid gray;">
					<tr style="border: 1px solid gray;">
						<th style="border: 1px solid gray;">ID</th>
						<th style="border: 1px solid gray;">Title</th>
						<th style="border: 1px solid gray;">Author Name</th>
						<th style="border: 1px solid gray;">Owner</th>
						<th>Actions</th>
					<tr>
				</thead>
				<tbody style="border: 1px solid gray;">
					<c:forEach items="${books}" var="b">
						
						<tr style="border: 1px solid gray;">
							<c:if test="${b.borrower.id == null}">
								<td style="border: 1px solid gray;">${b.id}</td>
								<td style="border: 1px solid gray;"><a href="/books/${b.id}">${b.getTitle()}</a></td>
								<td style="border: 1px solid gray;">${b.author}</td>
								<td style="border: 1px solid gray;">${b.getUser().getUserName()}</td>
							
							
							<td>
								<c:if test="${b.getUser().getUserName().contains(name) == true}">
									<a href="/books/${b.id}/edit">Edit</a>
									<form action="/books/${b.id}" method="post">
										<input type="hidden" name="_method" value="delete" style="">
										<input type="submit" value="Delete" style="  outline: none;
  											border: 0px; 
  											box-sizing: none; 
  											color: red;
  											background-color: transparent; ">
									</form>
								</c:if>
								<c:if test="${b.getUser().getUserName().contains(name) == false}">
									<a href="/books/${b.id}/borrow">Borrow</a>
								</c:if>
							</td>
						</c:if>
							
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="container"
		style="background-color: lightgray;
		margin-top: 20px;
		padding: 50px;">
		<h1>Books I'm Borrowing...</h1>
		<table style="border: 1px solid gray;">
			<thead>
				<tr style="border: 1px solid gray;">
					<th style="border: 1px solid gray;">ID</th>
					<th style="border: 1px solid gray;">Title</th>
					<th style="border: 1px solid gray;">Author Name</th>
					<th style="border: 1px solid gray;">Owner</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${books}" var="b">
					<c:if test="${b.borrower.id == user}">
						<tr>
							<td style="border: 1px solid gray;">${b.id}</td>
							<td style="border: 1px solid gray;">${b.title}</td>
							<td style="border: 1px solid gray;">${b.author}</td>
							<td style="border: 1px solid gray;">${b.getUser().getUserName()}</td>
							<td style="border: 1px solid gray;">
								<a href="/books/${b.id}/return">return</a>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>