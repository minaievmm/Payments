<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Register" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<%@ include file="/WEB-INF/jspf/header.jspf"%>

<body>
	<table>
		<tr>
			<td>
				<form class="form-container" action="controller" method="post">
					<input type="hidden" name="command" value="register" />
					<div class="form-title">
						<fmt:message key="login.form"></fmt:message>
					</div>
					<input class="form-field" type="text" name="login" /><br />
					<div class="form-title">
						<fmt:message key="password.form"></fmt:message>
					</div>
					<input class="form-field" type="password" name="password" /><br />
					<div class="form-title">
						<fmt:message key="account.table.admin.first.name"></fmt:message>
					</div>
					<input class="form-field" name="first_name" /><br />
					<div class="form-title">
						<fmt:message key="account.table.admin.last.name"></fmt:message>
					</div>
					<input class="form-field" name="last_name" /><br />
					<div class="submit-container">
					<input type="hidden" name="command" value="register" />
						<input class="submit-button" type="submit"
							value="<fmt:message key="register.button"></fmt:message>" />
					</div>
				</form>

			</td>
		</tr>

	</table>
	<div id="footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>