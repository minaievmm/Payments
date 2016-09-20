<%@ page pageEncoding="UTF-8"%>
<%@ page import="ua.nure.minaev.SummaryTask4.db.entity.Role"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Users' Cards" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr class="content-table">
			<td class="content-table">

				<table class="content-table">
					<thead class="content-table">
						<tr class="content-table">
							<th>№</th>
							<th><fmt:message key="account.table.admin.first.name"></fmt:message></th>
							<th><fmt:message key="account.table.admin.last.name"></fmt:message></th>
							<th><fmt:message key="account.table.admin.role"></fmt:message></th>
							<th><fmt:message key="account.table.admin.action"></fmt:message></th>
							<th><fmt:message key="card.quantity" /></th>
							<th><fmt:message key="payment.quantity" /></th>
						</tr>
					</thead>


					<c:forEach var="user" items="${userList}">

						<tr class="content-table">
							<td>${user.id}</td>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<c:if test="${user.roleId == 0 }">
								<td><fmt:message key="account.table.admin.client"></fmt:message></td>
							</c:if>
							<c:if test="${user.roleId == 1 }">
								<td><fmt:message key="account.table.admin.admin"></fmt:message></td>
							</c:if>
							<td>
								<form action="controller">
									<input type="hidden" name="id" value="${user.id}" /> <input
										type="hidden" name="command" value="viewUserCards" /> <input
										value="<fmt:message key="show.cards.admin.button"></fmt:message>" type="submit" class="submit-button" />
								</form>
								<form action="controller">
									<input type="hidden" name="id" value="${user.id}" /> <input
										type="hidden" name="command" value="viewUserPayments" /> <input
										value="<fmt:message key="show.payments.admin.button"></fmt:message>" type="submit" class="submit-button" />
								</form>
							</td>
							<td>${user.cardQuantity}</td>
							<td>${user.paymentQuantity}</td>
						</tr>

					</c:forEach>
				</table>

			</td>
		</tr>
	</table>
	<div id="footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>