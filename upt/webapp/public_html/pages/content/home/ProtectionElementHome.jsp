<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>

<%@ page import="gov.nih.nci.security.upt.constants.*"%>
<script>
    <!--
    	function setAndSubmit(target)
    	{
    		document.tableHomeForm.operation.value=target;
    		document.tableHomeForm.submit();
    	}
    // -->
    </script>

<html:form styleId="tableHomeForm"
	action="/ProtectionElementDBOperation">
	<table summary="" cellpadding="0" cellspacing="0" border="0"
		class="contentPage" width="100%" height="100%">
		<html:hidden property="operation" value="error" />
		<tr>
			<td valign="top">
			<table cellpadding="0" cellspacing="0" border="0"
				class="contentBegins">
				<tr>
					<td colspan="3">

					<h2>Protection Element</h2>

					<h3>Protection Element Home</h3>

					<p>This is the Protection Element section of the User Provisioning
					Tool. A Protection Element is a data set that has a controlled
					access as defined by Roles and Protection Elements. By identifying
					individual Protection Elements, it becomes easier to control access
					to important application data.<br>
					In this section you may create new Protection Elements or modify
					existing Protection Element details. Please begin by selecting
					either Create a New Protection Element or Select an Existing
					Protection Element.</p>
					</td>
				</tr>
				<tr>
					<td valign="top" width="40%"><!-- sidebar begins -->
					<table summary="" cellpadding="0" cellspacing="0" border="0"
						height="100%">
						<tr>
			  				<td class="infoMessage"><html:messages id="message" message="true">
								<bean:write name="message" />
							</html:messages></td>
						</tr>
						<tr><td><br></br></td></tr>
						<tr>
							<td valign="top">
							<table summary="" cellpadding="0" cellspacing="0" border="0"
								width="100%" class="sidebarSection">
								<tr>

									<td class="sidebarTitle" height="20">PROTECTION ELEMENT LINKS</td>
								</tr>
								<tr>
									<td class="sidebarContent"><a
										href="javascript: setAndSubmit('loadAdd')">Create a New
									Protection Element</a><br>
									Click to add a new protection element.</td>
								</tr>
								<tr>
									<td class="sidebarContent"><a
										href="javascript: setAndSubmit('loadSearch')">Select an
									Existing Protection Element</a><br>
									Enter search criteria to find the protection element you wish
									to operate on.</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
					<td width="60%"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</html:form>
