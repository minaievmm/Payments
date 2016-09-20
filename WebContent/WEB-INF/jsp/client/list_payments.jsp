<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Payments List" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>

<body>
	<br />
	<form class="form-container" method="post" action="controller">
		<select name="order" class="form-field">
			<option value="number">
				<fmt:message key="payment.order.by.number" />
			</option>
			<option value="dateAsc">
				<fmt:message key="payment.order.by.date.asc" />
			</option>
			<option value="dateDesc">
				<fmt:message key="payment.order.by.date.desc" />
			</option>
		</select> <input type="hidden" name="command" value="listPaymentsSorted" /> <input
			type="submit" class="submit-button"
			value="<fmt:message key="sort.button"/>" />
	</form>
	<br />
	<table id="main-container">


		<tr class="content-table">
			<td class="content-table"><c:if test="${paymentList.size() > 0}">
					<table class="content-table">
						<thead>
							<tr class="content-table">
								<th><fmt:message key="payment.table.admin.payment.number"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.card.number"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.amount"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.state"></fmt:message></th>
								<th><fmt:message key="payment.table.admin.date"></fmt:message></th>
								<th><fmt:message
										key="payment.table.admin.recipient.card.number"></fmt:message></th>
								<th><fmt:message key="account.table.admin.action"></fmt:message></th>
							</tr>
						</thead>

						<c:forEach var="item" items="${paymentList}">


							<tr class="content-table">
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
								<c:if test="${item.stateId eq 0}">
									<td>
										<form id="makePayment" action="controller" method="post">
											<input type="hidden" name="paymentId"
												value="${item.paymentId}" /> <input type="hidden"
												name="userCard" value="${item.userCard}" /> <input
												type="hidden" name="command" value="makePayment" /> <input
												type="submit"
												value="<fmt:message key="confirm.payment.button"></fmt:message>"
												class="submit-button" />
										</form>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</table>
				</c:if> <c:if test="${paymentList.size() == 0 }">
					<fmt:message key="payment.table.if.no.payments"></fmt:message>
				</c:if></td>
		</tr>
	</table>
	<table id="separateButton">
		<tr id="separateButton">
			<td id="separateButton">
				<form action="controller">
					<input id="preparePayment" type="hidden" name="command"
						value="makePaymentView" /> <input
						value="<fmt:message key="prepare.payment.in.payments.button" />"
						type="submit" class="submit-button" />
				</form>
			</td>
		</tr>
	</table>
	<div id="footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>