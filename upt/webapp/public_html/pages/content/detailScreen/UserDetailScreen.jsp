<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%@ page import="gov.nih.nci.security.upt.viewobjects.*"%>
<%@ page import="gov.nih.nci.security.upt.constants.*"%>
<%@ page import="gov.nih.nci.security.upt.forms.*"%>
<script>
<!--
   	function setAndSubmit(target)
   	{
   		if (target == "delete")
   		{
   			if (confirm("Are you sure you want to delete the record?"))
   			{
   				document.UserForm.operation.value=target;
				document.UserForm.submit();
			}
		}
		else
		{
	  		document.UserForm.operation.value=target;
	  	}
 	}
// -->
</script>
<bean:define id="submitValue" value="error" />
<logic:equal name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
	<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.ADD%>">
		<bean:define id="submitValue" value="create" />
	</logic:equal>
	<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.SEARCH%>">
		<bean:define id="submitValue" value="search" />
	</logic:equal>
</logic:equal>
<logic:notEqual name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
	<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.ADD%>">
		<bean:define id="submitValue" value="loadAdd" />
	</logic:equal>
	<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.SEARCH%>">
		<bean:define id="submitValue" value="loadSearchResult" />
	</logic:equal>
</logic:notEqual>

	<table summary="" cellpadding="0" cellspacing="0" border="0" class="contentPage" width="100%" height="100%">
	<html:form styleId="UserForm" action="/UserDBOperation">
	<html:hidden property="operation" value="<%=submitValue%>"/>
			<tr>
			<td valign="top">
			<table cellpadding="0" cellspacing="0" border="0" width="100%" class="contentBegins">
				<tr>
					<td>
					<table summary="" cellpadding="3" cellspacing="0" border="0" width="100%" align="center">
						<tr>
							<td class="infoMessage" colspan="3">
			  				<html:messages id="message" message="true">
			  				<bean:write name="message"/>
			  				</html:messages>	
			  				</td>
						</tr>
						<tr>
							<td colspan="3">
							<html:errors />
							</td>
						</tr>
						<logic:present name="<%=DisplayConstants.CURRENT_FORM%>">
							<logic:equal name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
								<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.ADD%>">
								<tr>
									<td class="formMessage" colspan="3">Enter the details to add a new User. 
									The <b>User Login Name</b> uniquely identifies the User and is a required field. 
									The <b>User First Name</b> and <b>User Last Name</b> identifies the User. The <b>User Organization, User Department</b>
									 and <b>User Title</b> provides his work details. The <b>User Phone Number</b> and <b>User Email Id</b> provides the contact details for the User.
									The <b>User Password</b> can be entered if the same schema is also going to be used for Authentication.
									The <b>User Start Date</b> and <b>User End Date</b> determine the period for which the User is a valid User.</td>
								</tr>
								<tr>
									<td class="formMessage" colspan="3">* indicates a required field</td>
								</tr>
								</logic:equal>
								<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.SEARCH%>">
								<tr>
									<td class="formMessage" colspan="3">Search for an existing User by entering the 
									<b>User Login Name, User First Name, User Last Name, User Organization, User Department</b> or <b>User Email Id</b>.</td>
								</tr>
								<tr>
									<td class="formMessage" colspan="3">Use * to perform wildcard searches</td>
								</tr>
								</logic:equal>
							</logic:equal>
							<logic:notEqual name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
								<tr>
									<td class="formMessage" colspan="3">Update the details of the displayed User. 
									The <b>User Login Name</b> uniquely identifies the User and is a required field. 
									The <b>User First Name</b> and <b>User Last Name</b> identifies the User. The <b>User Organization, User Department</b>
									 and <b>User Title</b> provides his work details. The <b>User Phone Number</b> and <b>User Email Id</b> provides the contact details for the User.
									The <b>User Password</b> can be entered if the same schema is also going to be used for Authentication.
									The <b>User Start Date</b> and <b>User End Date</b> determine the period for which the User is a valid User.
									The <b>Update Date</b> indicates the date when this User's Details were last updated.</td>
								</tr>							
							</logic:notEqual>
						<tr>
							<logic:equal name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
								<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.ADD%>">
									<td class="formTitle" height="20" colspan="3">ENTER THE NEW USER DETAILS</td>								</logic:equal>
								<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.SEARCH%>">
									<td class="formTitle" height="20" colspan="3">ENTER THE USER SEARCH CRITERIA</td>
								</logic:equal>
							</logic:equal>
							<logic:notEqual name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
									<td class="formTitle" height="20" colspan="3">USER DETAILS</td>
							</logic:notEqual>
						</tr>
							<logic:equal name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
								<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.SEARCH%>">
									<bean:define name="<%=DisplayConstants.CURRENT_FORM%>" property="searchFormElements" id="formElements" />
								</logic:equal>
								<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.ADD%>">
									<bean:define name="<%=DisplayConstants.CURRENT_FORM%>" property="addFormElements" id="formElements" />
								</logic:equal>
							</logic:equal>
							<logic:notEqual name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
								<bean:define name="<%=DisplayConstants.CURRENT_FORM%>" property="displayFormElements" id="formElements" />
							</logic:notEqual>
							<logic:iterate name="formElements" id="formElement" type="FormElement">
								<tr>
									<logic:equal name="formElement" property="propertyRequired" value="<%=DisplayConstants.REQUIRED%>">
										<td class="formRequiredNotice" width="5">*</td>
										<td class="formRequiredLabel2"><label><bean:write name="formElement" property="propertyLabel" /></label></td>
									</logic:equal>
									<logic:notEqual name="formElement" property="propertyRequired" value="<%=DisplayConstants.REQUIRED%>">
										<td class="formRequiredNotice" width="5">&nbsp;</td>
										<td class="formLabel"><label><bean:write name="formElement" property="propertyLabel" /></label></td>
									</logic:notEqual>
									<logic:equal name="formElement" property="propertyType" value="<%=DisplayConstants.INPUT_BOX%>">
										<td class="formField"><html:text style="formFieldSized" size="30" maxlength="100" property="<%=formElement.getPropertyName()%>" value="<%=formElement.getPropertyValue()%>" disabled="<%=formElement.getPropertyDisabled()%>"/></td>
									</logic:equal>
									<logic:equal name="formElement" property="propertyType" value="<%=DisplayConstants.PASSWORD%>">
										<td class="formField"><html:password style="formFieldSized" size="30" maxlength="100" property="<%=formElement.getPropertyName()%>" value="<%=formElement.getPropertyValue()%>" disabled="<%=formElement.getPropertyDisabled()%>"/></td>
									</logic:equal>
									<logic:equal name="formElement" property="propertyType" value="<%=DisplayConstants.INPUT_DATE%>">
										<td class="formField"><html:text style="formFieldSized" size="10" maxlength="10" property="<%=formElement.getPropertyName()%>" value="<%=formElement.getPropertyValue()%>" disabled="<%=formElement.getPropertyDisabled()%>" /></td>
									</logic:equal>
									<logic:equal name="formElement" property="propertyType" value="<%=DisplayConstants.INPUT_TEXTAREA%>">
										<td class="formField"><html:textarea style="formFieldSized" cols="32" rows="2" property="<%=formElement.getPropertyName()%>" value="<%=formElement.getPropertyValue()%>" disabled="<%=formElement.getPropertyDisabled()%>" /></td>
									</logic:equal>
									<logic:equal name="formElement" property="propertyType" value="<%=DisplayConstants.INPUT_RADIO%>">
										<td class="formField"><html:radio style="formFieldSized" property="<%=formElement.getPropertyName()%>" value="<%=DisplayConstants.YES%>" />&nbsp;Yes&nbsp;&nbsp;<html:radio style="formFieldSized" property="<%=formElement.getPropertyName()%>" value="<%=DisplayConstants.NO%>" />&nbsp;No</td>
									</logic:equal>
								</tr>
							</logic:iterate>
						<tr>
							<td align="right" colspan="3"><!-- action buttons begins -->
							<table cellpadding="4" cellspacing="0" border="0">
								<tr>
									<logic:equal name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">

										<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.ADD%>">
											<td><html:submit style="actionButton" onclick="setAndSubmit('create');">Add</html:submit></td>
										</logic:equal>
										<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.SEARCH%>">
											<td><html:submit style="actionButton" onclick="setAndSubmit('search');">Search</html:submit></td>
										</logic:equal>
										<td><html:reset style="actionButton">Reset</html:reset></td>
										<td><html:submit style="actionButton" onclick="setAndSubmit('loadHome');">Back</html:submit></td>										
									</logic:equal>
									<logic:notEqual name="<%=DisplayConstants.CURRENT_FORM%>" property="primaryId" value="<%=DisplayConstants.BLANK%>">
										
										<td><html:submit style="actionButton" onclick="setAndSubmit('update');">Update</html:submit></td>
										<td><button class="actionButton" onclick="setAndSubmit('delete');">Delete</button></td>
										<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.ADD%>">
											<td><html:submit style="actionButton" onclick="setAndSubmit('loadAdd');">Back</html:submit></td>
										</logic:equal>
										<logic:equal name="<%=DisplayConstants.CURRENT_ACTION%>" value="<%=DisplayConstants.SEARCH%>">
											<td><html:submit style="actionButton" onclick="setAndSubmit('loadSearchResult');">Back</html:submit></td>
										</logic:equal>
										<logic:notPresent name="<%=DisplayConstants.ADMIN_USER%>">
										</tr>
									</table>
									<table cellpadding="4" cellspacing="0" border="0">
										<tr>
											<td><html:submit style="actionButton" onclick="setAndSubmit('loadAssociation');">Associated Groups</html:submit></td>
											<td><html:submit style="actionButton" onclick="setAndSubmit('loadProtectionElementPrivilegesAssociation');">Associated PE & Privileges</html:submit></td>
											<td><html:submit style="actionButton" onclick="setAndSubmit('loadProtectionGroupAssociation');">Associated PG & Roles</html:submit></td>
											<td><html:submit style="actionButton" onclick="setAndSubmit('loadDoubleAssociation');">Assign PG & Roles</html:submit></td>
										</logic:notPresent>
									</logic:notEqual>
								</tr>
							</table>
							</td><!-- action buttons end -->
						</logic:present>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		</html:form>
	</table>

