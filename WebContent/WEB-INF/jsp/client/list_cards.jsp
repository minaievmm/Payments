<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Cards List" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<div id="header">
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
</div>
<body>
	<br />
	<form class="form-container" method="post" action="controller">
		<select name="order" class="form-field">
			<option value="number">
				<fmt:message key="card.order.by.number" />
			</option>
			<option value="balance">
				<fmt:message key="card.order.by.balance" />
			</option>
		</select> <input type="hidden" name="command" value="listCardsSorted" /> <input
			type="submit" class="submit-button"
			value="<fmt:message key="sort.button"/>" />
	</form>
	<br />
	<table id="main-container">

		<tr class="content-table">
			<td class="content-table">
				<table>
					<thead>
						<tr class="content-table">
							<th><fmt:message key="card.table.card.number"></fmt:message></th>
							<th><fmt:message key="card.table.balance"></fmt:message></th>
							<th><fmt:message key="card.table.currency"></fmt:message></th>
							<th><fmt:message key="card.table.block.unblock"></fmt:message></th>
						</tr>
					</thead>

					<c:forEach var="item" items="${cardList}">

						<tr class="content-table">
							<td>${item.cardNumber}</td>
							<td>${item.cardBalance}</td>
							<td><fmt:message key="card.table.uah"></fmt:message></td>
							<c:if test="${item.isBlocked() eq false}">
								<td>
									<form id="blockCard" action="controller" method="post">
										<input type="hidden" name="command" value="blockCard" /> <input
											type="hidden" name="cardNumber" value="${item.cardNumber}" />
										<input type="submit"
											value="<fmt:message key="block.card.button"></fmt:message>"
											class="submit-button" />
									</form>
								</td>
							</c:if>
							<c:if
								test="${item.isBlocked() eq true and item.isRequested() eq false }">
								<td>
									<form id="requestUnblock" action="controller" method="post">
										<input type="hidden" name="command" value="requestUnblock" />
										<input type="hidden" name="cardNumber"
											value="${item.cardNumber}" /> <input type="submit"
											value="<fmt:message key="request.unblock.card.button"></fmt:message>"
											class="submit-button" />
									</form>
								</td>
							</c:if>
							<c:if
								test="${item.isRequested() eq true and item.isBlocked() eq true }">
								<td>
									<form id="unblockRequested">
										<input type="button"
											value="<fmt:message key="unblock.requested.on.card.button.show"></fmt:message>"
											class="submit-button" />
									</form>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
	<table id="separateButton">
		<tr id="separateButton">
			<td id="separateButton">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="createCard" /> <input
						value="<fmt:message key="create.card.button"></fmt:message>"
						type="submit" class="submit-button" />
				</form>
			</td>
		</tr>
	</table>
</body>
<div id="footer">
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</html>