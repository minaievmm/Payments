<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf"%>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	<table>
		<tr>
			<td>
				<form class="form-container" action="controller">
					<input type="hidden" name="command" value="login" />
					<div class="form-title">
						<fmt:message key="login.form"></fmt:message>
					</div>
					<input class="form-field" type="text" name="login" /><br />
					<div class="form-title">
						<fmt:message key="password.form"></fmt:message>
					</div>
					<input class="form-field" type="password" name="password" /><br />
					<div class="submit-container">
						<a href="register.jsp" class="submit-button"><fmt:message
								key="register.button" /></a> <input class="submit-button"
							type="submit"
							value="<fmt:message key="login.action"></fmt:message>" />
					</div>
				</form> <br />
				<form id="language" class="form-container">
					<select class="form-field" id="language" name="language"
						onchange="submit()">
						<option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
						<option value="ru" ${language == 'ru' ? 'selected' : ''}>RU</option>
					</select>
				</form>
			</td>
		</tr>
	</table>
	<div id="footer">
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</div>
</body>
</html>