<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Cards List" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf"%>

		<tr class="content-table">
			<td class="content-table">
				<table class="content-table">
					<thead>
						<tr class="content-table">
							<th><fmt:message key="card.table.card.number"></fmt:message></th>
							<th><fmt:message key="card.table.balance"></fmt:message></th>
							<th><fmt:message key="card.table.currency"></fmt:message></th>
							<th><fmt:message key="card.table.unblock"></fmt:message></th>
						</tr>
					</thead>


					<c:forEach var="item" items="${cardList}">

						<tr class="content-table">
							<td>${item.cardNumber}</td>
							<td>${item.cardBalance}</td>
							<td><fmt:message key="card.table.uah"></fmt:message></td>
							<c:if
								test="${item.isBlocked() eq true and item.isRequested() eq true }">
								<td>
									<form id="unblockCard" action="controller" method="post">
										<input type="hidden" name="command" value="unblockCard" /> <input
											type="hidden" name="cardNumber" value="${item.cardNumber}" />
										<input type="submit"
											value="<fmt:message key="unblock.card.admin.button"></fmt:message>"
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
	<div id="footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>