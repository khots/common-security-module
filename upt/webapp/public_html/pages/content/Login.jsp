
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>


<table summary="" cellpadding="0" cellspacing="0" border="0" 
	class="contentPage" width="100%" height="100%">
	<tr>
		<td valign="top"><!-- target of anchor to skip menus --><a
			name="content" /></a>
		<table summary="" cellpadding="0" cellspacing="0" border="0"
			class="contentPage" width="100%" height="100%">

			<!-- banner begins -->
			<tr>
				<td class="bannerHome"><img src="images/bannerHome.gif" height="140"></td>
			</tr>
			<!-- banner begins -->

			<tr>
				<td height="100%"><!-- target of anchor to skip menus --><a
					name="content" /></a>

				<table summary="" cellpadding="0" cellspacing="0" border="0"
					height="100%">
					<tr>
						<td width="70%"><!-- welcome begins -->
						<table summary="" cellpadding="0" cellspacing="0" border="0"
							height="100%">
							<tr>
								<td class="welcomeTitle" height="20">WELCOME TO THE USER
								PROVISIONING TOOL</td>
							</tr>
							<tr>
								<td class="welcomeContent" valign="top">Welcome to the User
								Provisioning Tool. Here you may configure your application's
								authorization data in the common security module database.
								Modify, delete, or create Groups, Privileges, Protection
								Elements, Protection Groups, Roles, or Users. Begin by loggin
								in.</td>
							</tr>
						</table>
						<!-- welcome ends --></td>
						<td valign="top" width="30%"><!-- sidebar begins -->
						<table summary="" cellpadding="0" cellspacing="0" border="0"
							height="100%">
<html:form action="/Login">
							<!-- login begins -->
							<tr>
								<td valign="top">
								<table summary="" cellpadding="2" cellspacing="0" border="0"
									width="100%" class="sidebarSection">
									<tr>
										<td class="sidebarTitle" height="20">LOGIN TO U.P.T.</td>
									</tr>
									<tr>
										<td class="sidebarContent">
										<table cellpadding="2" cellspacing="0" border="0">
											<tr>
												<td class="sidebarLogin" align="right"><label for="loginID">LOGIN ID</label></td>
												<td class="formFieldLogin"><html:text style="formField" size="14" property="loginId" value="" /></td>
											</tr>
											<tr>
												<td class="sidebarLogin" align="right"><label for="password">PASSWORD</label></td>
												<td class="formFieldLogin"><html:password style="formField" size="14" property="password" value="" /></td>
											</tr>
											<tr>
												<td>&nbsp;</td>
												<td><html:submit style="actionButton" value="Login"/></td>
											</tr>
										</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<!-- login ends -->
</html:form>
							<!-- what's new begins -->
							<tr>
								<td valign="top">
								<table summary="" cellpadding="0" cellspacing="0" border="0"
									width="100%" class="sidebarSection">
									<tr>
										<td class="sidebarTitle" height="20">WHAT'S NEW</td>
									</tr>
									<tr>
										<td class="sidebarContent">What's new message. What's new
										message. What's new message. What's new message. What's new
										message.</td>
									</tr>
								</table>
								</td>
							</tr>
							<!-- what's new ends -->

							<!-- did you know? begins -->
							<tr>
								<td valign="top">
								<table summary="" cellpadding="0" cellspacing="0" border="0"
									width="100%" height="100%" class="sidebarSection">
									<tr>
										<td class="sidebarTitle" height="20">DID YOU KNOW?</td>
									</tr>
									<tr>
										<td class="sidebarContent" valign="top">Did you know message.
										Did you know message. Did you know message. Did you know
										message.</td>
									</tr>
								</table>
								</td>
							</tr>
							<!-- did you know? ends -->

							<!-- spacer cell begins (keep for dynamic expanding) -->
							<tr>
								<td valign="top" height="100%">
								<table summary="" cellpadding="0" cellspacing="0" border="0"
									width="100%" height="100%" class="sidebarSection">
									<tr>
										<td class="sidebarContent" valign="top">&nbsp;</td>
									</tr>
								</table>
								</td>
							</tr>
							<!-- spacer cell ends -->

						</table>
						<!-- sidebar ends --></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
