<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Refilling Terminal" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr>
			<td class="content">
				<form id="preparePayment" action="controller" class="form-container" method="post">
				<div class="form-title"><fmt:message key="payment.table.admin.amount"></fmt:message></div>
					<input name="amount" class="form-field" pattern="\d{1,8}"/> 
					<div class="form-title"><fmt:message key="refilling.terminal.choose.card"></fmt:message></div>
					<select name="id"
						class="form-field">
						<c:forEach var="card" items="${cardList}">
							<c:if test="${card.isBlocked() eq false }">
								<option value="${card.cardNumber}">${card.cardNumber}</option>
							</c:if>
						</c:forEach>
					</select> 
					<div class="form-title"><fmt:message key="payment.maker.recipient.card"></fmt:message></div>
					<input name="recipientCard" class="form-field" pattern="\d{1,8}" max="${cardList.size()}"/> <input
						type="hidden" name="command" value="preparePayment" formmethod="post"/> <input
						value="<fmt:message key="prepare.payment.in.terminal.button"></fmt:message>" type="submit" class="submit-button" />
				</form>
			</td>
		</tr>
	</table>
	<div id="footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>