<%@ page pageEncoding="UTF-8"%>
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
				<form id="refillCard" action="controller" method="post"
					class="form-container">
					<div class="form-title">
						<fmt:message key="payment.table.admin.amount"></fmt:message>
					</div>
					<input name="amount" class="form-field" pattern="\d{1,8}" />
					<div class="form-title">
						<fmt:message key="refilling.terminal.choose.card"></fmt:message>
					</div>
					<select name="card" class="form-field">
						<c:forEach var="card" items="${cardList}">
							<c:if test="${card.isBlocked() eq false }">
								<option value="${card.cardNumber}">${card.cardNumber}</option>
							</c:if>
						</c:forEach>
					</select> <input type="hidden" name="command" value="refillCard" formmethod="post"/> <input
						type="submit" class="submit-button"
						value="<fmt:message key="refill.button"></fmt:message>" />
				</form>
			</td>
		</tr>
	</table>
	<div id="footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>