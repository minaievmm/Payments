<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Payments List" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr class="content-table">
			<td class="content-table"><c:if test="${paymentList.size() > 0}">
					<table>
						<thead>
							<tr class="content-table">
								<th><fmt:message key="payment.table.admin.payment.number"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.card.number"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.amount"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.state"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.date"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.recipient.card.number"></fmt:message></th>
							</tr>
						</thead>

						<c:forEach var="item" items="${paymentList}">


							<tr>
								<td>${item.paymentId}</td>
								<td>${item.userCard}</td>
								<td>${item.amount}</td>
								<c:if test="${item.stateId == 0}">
									<td><fmt:message key="payment.state.prepared"></fmt:message></td>
								</c:if>
								<c:if test="${item.stateId == 1}">
									<td><fmt:message key="payment.state.sent"></fmt:message></td>
								</c:if>
								<td>${item.paymentDate}</td>
								<td>${item.userRecipient}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if> <c:if test="${paymentList.size() == 0 }">
					<fmt:message key="payment.table.admin.no.payments"></fmt:message>
					</c:if></td>
		</tr>
	</table>
	<div id="footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>