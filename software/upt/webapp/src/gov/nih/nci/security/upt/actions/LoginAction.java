/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC, SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/common-security-module/LICENSE.txt for details.
 */

package gov.nih.nci.security.upt.actions;

/**
 *
 *<!-- LICENSE_TEXT_START -->
 *
 *The NCICB Common Security Module's User Provisioning Tool (UPT) Software License,
 *Version 3.0 Copyright 2004-2005 Ekagra Software Technologies Limited ('Ekagra')
 *
 *Copyright Notice.  The software subject to this notice and license includes both
 *human readable source code form and machine readable, binary, object code form
 *(the 'UPT Software').  The UPT Software was developed in conjunction with the
 *National Cancer Institute ('NCI') by NCI employees and employees of Ekagra.  To
 *the extent government employees are authors, any rights in such works shall be
 *subject to Title 17 of the United States Code, section 105.
 *
 *This UPT Software License (the 'License') is between NCI and You.  'You (or
 *'Your') shall mean a person or an entity, and all other entities that control,
 *are controlled by, or are under common control with the entity.  'Control' for
 *purposes of this definition means (i) the direct or indirect power to cause the
 *direction or management of such entity, whether by contract or otherwise, or
 *(ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *(iii) beneficial ownership of such entity.
 *
 *This License is granted provided that You agree to the conditions described
 *below.  NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 *no-charge, irrevocable, transferable and royalty-free right and license in its
 *rights in the UPT Software to (i) use, install, access, operate, execute, copy,
 *modify, translate, market, publicly display, publicly perform, and prepare
 *derivative works of the UPT Software; (ii) distribute and have distributed to
 *and by third parties the UPT Software and any modifications and derivative works
 *thereof; and (iii) sublicense the foregoing rights set out in (i) and (ii) to
 *third parties, including the right to license such rights to further third
 *parties.  For sake of clarity, and not by way of limitation, NCI shall have no
 *right of accounting or right of payment from You or Your sublicensees for the
 *rights granted under this License.  This License is granted at no charge to You.
 *
 *1.	Your redistributions of the source code for the Software must retain the
 *above copyright notice, this list of conditions and the disclaimer and
 *limitation of liability of Article 6 below.  Your redistributions in object code
 *form must reproduce the above copyright notice, this list of conditions and the
 *disclaimer of Article 6 in the documentation and/or other materials provided
 *with the distribution, if any.
 *2.	Your end-user documentation included with the redistribution, if any, must
 *include the following acknowledgment: 'This product includes software developed
 *by Ekagra and the National Cancer Institute.'  If You do not include such
 *end-user documentation, You shall include this acknowledgment in the Software
 *itself, wherever such third-party acknowledgments normally appear.
 *
 *3.	You may not use the names 'The National Cancer Institute', 'NCI' 'Ekagra
 *Software Technologies Limited' and 'Ekagra' to endorse or promote products
 *derived from this Software.  This License does not authorize You to use any
 *trademarks, service marks, trade names, logos or product names of either NCI or
 *Ekagra, except as required to comply with the terms of this License.
 *
 *4.	For sake of clarity, and not by way of limitation, You may incorporate this
 *Software into Your proprietary programs and into any third party proprietary
 *programs.  However, if You incorporate the Software into third party proprietary
 *programs, You agree that You are solely responsible for obtaining any permission
 *from such third parties required to incorporate the Software into such third
 *party proprietary programs and for informing Your sublicensees, including
 *without limitation Your end-users, of their obligation to secure any required
 *permissions from such third parties before incorporating the Software into such
 *third party proprietary software programs.  In the event that You fail to obtain
 *such permissions, You agree to indemnify NCI for any claims against NCI by such
 *third parties, except to the extent prohibited by law, resulting from Your
 *failure to obtain such permissions.
 *
 *5.	For sake of clarity, and not by way of limitation, You may add Your own
 *copyright statement to Your modifications and to the derivative works, and You
 *may provide additional or different license terms and conditions in Your
 *sublicenses of modifications of the Software, or any derivative works of the
 *Software as a whole, provided Your use, reproduction, and distribution of the
 *Work otherwise complies with the conditions stated in this License.
 *
 *6.	THIS SOFTWARE IS PROVIDED 'AS IS,' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 *(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 *NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN NO
 *EVENT SHALL THE NATIONAL CANCER INSTITUTE, EKAGRA, OR THEIR AFFILIATES BE LIABLE
 *FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 *TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *<!-- LICENSE_TEXT_END -->
 *
 */


import gov.nih.nci.logging.api.user.UserInfoHelper;
import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Application;
import gov.nih.nci.security.constants.Constants;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSTransactionException;
import gov.nih.nci.security.upt.constants.DisplayConstants;
import gov.nih.nci.security.upt.constants.ForwardConstants;
import gov.nih.nci.security.upt.forms.LoginForm;
import gov.nih.nci.security.upt.util.StringUtils;
import gov.nih.nci.security.upt.util.properties.ObjectFactory;
import gov.nih.nci.security.upt.util.properties.UPTProperties;
import gov.nih.nci.security.upt.util.properties.exceptions.UPTConfigurationException;
import gov.nih.nci.security.util.StringUtilities;
import gov.nih.nci.security.exceptions.CSCredentialException;
import gov.nih.nci.security.exceptions.CSFirstTimeLoginException;
import gov.nih.nci.security.exceptions.CSCredentialExpiredException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 *
 */
public class LoginAction extends ActionSupport implements SessionAware
{
	private static final Logger log = Logger.getLogger(LoginAction.class);


	private String redirectAction;
	private String loginId;
	private String password;
	private String applicationContextName;
	private Map<String, Object> sessionMap;
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApplicationContextName() {
		return applicationContextName;
	}

	public void setApplicationContextName(String applicationContextName) {
		this.applicationContextName = applicationContextName;
	}
	private LoginForm loginForm;
	
	public String getRedirectAction()
	{
		return redirectAction;
	}
	
	public void setLoginForm(LoginForm loginForm)
	{
		this.loginForm = loginForm;
	}

	public LoginForm getLoginForm()
	{
		return loginForm;
	}
	
	public String execute()
	{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		AuthenticationManager authenticationManager = null;
		AuthorizationManager authorizationManager = null;
		UserProvisioningManager userProvisioningManager = null;
		boolean loginSuccessful = false;
		boolean hasPermission = false;
		String uptContextName = DisplayConstants.UPT_CONTEXT_NAME;
		Application application = null;

		String serverInfoPathPort = (request.isSecure()?"https://":"http://")+ request.getServerName()+ ":"+ request.getServerPort();
		ObjectFactory.initialize("upt-beans.xml");
		UPTProperties uptProperties = null;
		String urlContextForLoginApp = "";
		String centralUPTConfiguration = "";
		try {
			uptProperties = (UPTProperties) ObjectFactory
					.getObject("UPTProperties");
			urlContextForLoginApp = uptProperties.getBackwardsCompatibilityInformation().getLoginApplicationContextName();
			if (!StringUtils.isBlank(urlContextForLoginApp)) {
				serverInfoPathPort = serverInfoPathPort + "/"+urlContextForLoginApp+"/";
			} else {
				serverInfoPathPort = serverInfoPathPort + "/"
						+ DisplayConstants.LOGIN_APPLICATION_CONTEXT_NAME + "/";
			}
			uptContextName = DisplayConstants.UPT_AUTHENTICATION_CONTEXT_NAME;
		} catch (UPTConfigurationException e) {
			serverInfoPathPort = serverInfoPathPort + "/"+ DisplayConstants.LOGIN_APPLICATION_CONTEXT_NAME + "/";

		}

		if(loginForm == null)
		{
			loginForm = new LoginForm();
			loginForm.setApplicationContextName(applicationContextName);
			loginForm.setLoginId(loginId);
			loginForm.setPassword(password);
		}
		
		if(StringUtils.isBlank(loginForm.getApplicationContextName()) || StringUtils.isBlank(loginForm.getLoginId())
				|| StringUtils.isBlank(loginForm.getPassword())){

			redirectAction = serverInfoPathPort;
			return "redirect";
		}


		UserInfoHelper.setUserInfo(loginForm.getLoginId(), request.getSession().getId());
		clearActionErrors();

		try
		{
			authorizationManager = SecurityServiceProvider.getAuthorizationManager(uptContextName);
			if (null == authorizationManager)
			{
				addActionError("Unable to initialize Authorization Manager for the given application context using new configuration");
				if (log.isDebugEnabled())
					log.debug("|"+loginForm.getLoginId()+
							"||Login|Failure|Unable to instantiate Authorization Manager for UPT application using new configuration||");
				return ForwardConstants.LOGIN_FAILURE;
			}
		}
		catch (CSException cse)
		{

			authorizationManager = null;
		}

		if (null == authorizationManager)
		{

			try
			{

				if (null == uptContextName || uptContextName.equalsIgnoreCase(""))
				{
					addActionError( "Unable to read the UPT Context Name from Security Config File");
					if (log.isDebugEnabled())
						log.debug("|"+loginForm.getLoginId()+
								"||Login|Failure|Unable to read the UPT Context Name from Security Config File");
					return ForwardConstants.LOGIN_FAILURE;
				}
			}
			catch (Exception ex)
			{
				addActionError(org.apache.commons.lang.StringEscapeUtils.escapeHtml(ex.getMessage()));
				if (log.isDebugEnabled())
					log.debug("|"+loginForm.getLoginId()+
							"||Login|Failure|Unable to read the UPT Context Name from Security Config File||");
				return ForwardConstants.LOGIN_FAILURE;
			}
		}
		try
		{

			authenticationManager = SecurityServiceProvider.getAuthenticationManager(DisplayConstants.UPT_AUTHENTICATION_CONTEXT_NAME);
			if (null == authenticationManager)
			{
				addActionError("Unable to initialize Authentication Manager for the given application context");
				if (log.isDebugEnabled())
					log.debug("|"+loginForm.getLoginId()+
							"||Login|Failure|Unable to instantiate AuthenticationManager for UPT application||");
				return ForwardConstants.LOGIN_FAILURE;
			}
		}
		catch (CSException cse)
		{
			addActionError( org.apache.commons.lang.StringEscapeUtils.escapeHtml(cse.getMessage()));
			if (log.isDebugEnabled())
				log.debug("|"+loginForm.getLoginId()+
						"||Login|Failure|Unable to instantiate AuthenticationManager for UPT application|"+loginForm.toString()+"|"+cse.getMessage());
			return ForwardConstants.LOGIN_FAILURE;
		}
		try
		{
			loginSuccessful = authenticationManager.login(loginForm.getLoginId(),loginForm.getPassword());
		}
		catch (CSCredentialExpiredException cse)
		{
			addActionError(org.apache.commons.lang.StringEscapeUtils.escapeHtml(cse.getMessage()));
			if (log.isDebugEnabled())
				log.debug("|"+loginForm.getLoginId()+
						"||Login|Failure|Password Expired for user name "+loginForm.getLoginId()+" and"+loginForm.getApplicationContextName()+" application|"+loginForm.toString()+"|"+cse.getMessage());
			return ForwardConstants.EXPIRED_PASSWORD;
		}
		catch (CSFirstTimeLoginException cse)
		{
			addActionError( org.apache.commons.lang.StringEscapeUtils.escapeHtml(cse.getMessage()));
			if (log.isDebugEnabled())
				log.debug("|"+loginForm.getLoginId()+
						"||Login|Failure|Password Expired for user name "+loginForm.getLoginId()+" and"+loginForm.getApplicationContextName()+" application|"+loginForm.toString()+"|"+cse.getMessage());
			return ForwardConstants.EXPIRED_PASSWORD;
		}
		catch (CSException cse)
		{
			addActionError(org.apache.commons.lang.StringEscapeUtils.escapeHtml(cse.getMessage()));
			if (log.isDebugEnabled())
				log.debug("|"+loginForm.getLoginId()+
						"||Login|Failure|Login Failed for user name "+loginForm.getLoginId()+" and"+loginForm.getApplicationContextName()+" application|"+loginForm.toString()+"|"+cse.getMessage());
			return ForwardConstants.LOGIN_FAILURE;
		}

		try
		{
			authorizationManager = SecurityServiceProvider.getAuthorizationManager(uptContextName);
			if (null == authorizationManager)
			{
				addActionError("Unable to initialize Authorization Manager for the given application context");
				if (log.isDebugEnabled())
					log.debug("|"+loginForm.getLoginId()+
							"||Login|Failure|Unable to instantiate Authorization Manager for UPT application||");
				return ForwardConstants.LOGIN_FAILURE;
			}
		}
		catch (CSException cse)
		{
			addActionError(org.apache.commons.lang.StringEscapeUtils.escapeHtml(cse.getMessage()));
			if (log.isDebugEnabled())
				log.debug("|"+loginForm.getLoginId()+
						"||Login|Failure|Unable to instantiate AuthorizationManager for UPT application|"+loginForm.toString()+"|"+cse.getMessage());
			return ForwardConstants.LOGIN_FAILURE;
		}
		try
		{
			hasPermission = authorizationManager.checkPermission(loginForm.getLoginId(),loginForm.getApplicationContextName(),null);
			if (!hasPermission)
			{
				try
				{
					userProvisioningManager = getUserProvisioningManager(authorizationManager,loginForm.getApplicationContextName());
					if (null == userProvisioningManager)
					{
						addActionError("Unable to initialize Authorization Manager for the given application context");
						if (log.isDebugEnabled())
							log.debug("|"+loginForm.getLoginId()+
									"||Login|Failure|Unable to instantiate User Provisioning Manager for "+loginForm.getApplicationContextName()+" application||");
						return ForwardConstants.LOGIN_FAILURE;
					}
				}
				catch (CSException cse)
				{
					addActionError(org.apache.commons.lang.StringEscapeUtils.escapeHtml(cse.getMessage()));
					if (log.isDebugEnabled())
						log.debug("|"+loginForm.getLoginId()+
								"||Login|Failure|Unable to instantiate User Provisioning Manager for |"+loginForm.toString()+"|"+cse.getMessage());
					return ForwardConstants.LOGIN_FAILURE;
				}
				sessionMap.put(DisplayConstants.USER_PROVISIONING_MANAGER, userProvisioningManager);
				sessionMap.put(DisplayConstants.LOGIN_OBJECT,loginForm);
				sessionMap.put(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);

				sessionMap.put(Constants.UPT_USER_OPERATION+"_"+Constants.CSM_ACCESS_PRIVILEGE, "false");
				sessionMap.put(Constants.UPT_PROTECTION_ELEMENT_OPERATION+"_"+Constants.CSM_ACCESS_PRIVILEGE, "false");
				sessionMap.put(Constants.UPT_PRIVILEGE_OPERATION+"_"+Constants.CSM_ACCESS_PRIVILEGE, "false");
				sessionMap.put(Constants.UPT_GROUP_OPERATION+"_"+Constants.CSM_ACCESS_PRIVILEGE, "false");
				sessionMap.put(Constants.UPT_PROTECTION_GROUP_OPERATION+"_"+Constants.CSM_ACCESS_PRIVILEGE, "false");
				sessionMap.put(Constants.UPT_ROLE_OPERATION+"_"+Constants.CSM_ACCESS_PRIVILEGE, "false");
				sessionMap.put(Constants.UPT_INSTANCE_LEVEL_OPERATION+"_"+Constants.CSM_ACCESS_PRIVILEGE, "false");

				return ForwardConstants.LOGIN_SUCCESS;
			}
		}
		catch (CSException cse)
		{
			addActionError(org.apache.commons.lang.StringEscapeUtils.escapeHtml(cse.getMessage()));
			if (log.isDebugEnabled())
				log.debug("|"+loginForm.getLoginId()+
						"||Login|Failure|Error in checking permission|"+loginForm.toString()+"|"+cse.getMessage());
			return ForwardConstants.LOGIN_FAILURE;
		}
		
		try
		{
			//UserProvisioningManager upm = (UserProvisioningManager)authorizationManager;
			application = authorizationManager.getApplication(loginForm.getApplicationContextName());
			userProvisioningManager = getUserProvisioningManager(authorizationManager,loginForm.getApplicationContextName());
			if (null == userProvisioningManager)
			{
				addActionError("Unable to initialize Authorization Manager for the given application context");
				if (log.isDebugEnabled())
					log.debug("|"+loginForm.getLoginId()+
							"||Login|Failure|Unable to instantiate User Provisioning Manager for "+loginForm.getApplicationContextName()+" application||");
				return ForwardConstants.LOGIN_FAILURE;
			}
		}
		catch (CSException cse)
		{
			addActionError(org.apache.commons.lang.StringEscapeUtils.escapeHtml(cse.getMessage()));
			if (log.isDebugEnabled())
				log.debug("|"+loginForm.getLoginId()+
						"||Login|Failure|Unable to instantiate User Provisioning Manager for |"+loginForm.toString()+"|"+cse.getMessage());
			return ForwardConstants.LOGIN_FAILURE;
		}

		sessionMap.put(DisplayConstants.USER_PROVISIONING_MANAGER, userProvisioningManager);
		sessionMap.put(DisplayConstants.LOGIN_OBJECT,loginForm);
		sessionMap.put(DisplayConstants.CURRENT_TABLE_ID,DisplayConstants.HOME_ID);

		authenticationManager = null;
		authorizationManager = null;

		try {
			processUptOperation(userProvisioningManager ,loginForm.getLoginId(),application.getApplicationName());
		} catch (CSTransactionException e) {
			addActionError(org.apache.commons.lang.StringEscapeUtils.escapeHtml(e.getMessage()));
			if (log.isDebugEnabled())
				log.debug("|"+loginForm.getLoginId()+
						"||Login|Failure|Unable to check permissions for the user operations |"+loginForm.toString()+"|"+e.getMessage());
			return ForwardConstants.LOGIN_FAILURE;
		}
		
		if (loginForm.getApplicationContextName().equalsIgnoreCase(uptContextName))
		{
			sessionMap.put(DisplayConstants.ADMIN_USER,DisplayConstants.ADMIN_USER);
			if (log.isDebugEnabled())
				log.debug(request.getSession().getId()+"|"+((LoginForm)sessionMap.get(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
				"||Login|Success|Login Successful for user "+loginForm.getLoginId()+" and "+loginForm.getApplicationContextName()+" application, Forwarding to the Super Admin Home Page||");
			return ForwardConstants.ADMIN_LOGIN_SUCCESS;
		}
		else
		{
			if (log.isDebugEnabled())
				log.debug(request.getSession().getId()+"|"+((LoginForm)sessionMap.get(DisplayConstants.LOGIN_OBJECT)).getLoginId()+
				"||Login|Success|Login Successful for user "+loginForm.getLoginId()+" and "+loginForm.getApplicationContextName()+" application, Forwarding to the Home Page||");
			return ForwardConstants.LOGIN_SUCCESS;
		}
	}

	private UserProvisioningManager getUserProvisioningManager(AuthorizationManager authorizationManager,String applictionContextName) throws CSException
	{
		UserProvisioningManager userProvisioningManager = null;
		
		Application application = authorizationManager.getApplication(applictionContextName);
		if (!StringUtilities.isBlank(application.getDatabaseURL()))
		{
			HashMap hashMap = new HashMap();
			hashMap.put("hibernate.connection.url", application.getDatabaseURL());
			hashMap.put("hibernate.connection.username", application.getDatabaseUserName());
			hashMap.put("hibernate.connection.password", application.getDatabasePassword());
			hashMap.put("hibernate.dialect", application.getDatabaseDialect());
			hashMap.put("hibernate.connection.driver_class", application.getDatabaseDriver());
			userProvisioningManager = SecurityServiceProvider.getUserProvisioningManager(applictionContextName,hashMap);
		}
		else
		{
			userProvisioningManager = SecurityServiceProvider.getUserProvisioningManager(applictionContextName);
		}
		
		return userProvisioningManager;
	}

	
	private void processUptOperation(UserProvisioningManager uptManager, String userId, String applicationName) throws CSTransactionException
	{
		checkPermissionForUptOperation(uptManager,Constants.UPT_USER_OPERATION, userId, applicationName);
		checkPermissionForUptOperation(uptManager,Constants.UPT_PROTECTION_ELEMENT_OPERATION, userId, applicationName);
		checkPermissionForUptOperation(uptManager,Constants.UPT_PRIVILEGE_OPERATION, userId, applicationName);
		checkPermissionForUptOperation(uptManager,Constants.UPT_GROUP_OPERATION, userId, applicationName);
		checkPermissionForUptOperation(uptManager,Constants.UPT_PROTECTION_GROUP_OPERATION, userId, applicationName);
		checkPermissionForUptOperation(uptManager,Constants.UPT_ROLE_OPERATION, userId, applicationName);
		checkPermissionForUptOperation(uptManager,Constants.UPT_INSTANCE_LEVEL_OPERATION, userId, applicationName);
	}

	private void checkPermissionForUptOperation(UserProvisioningManager uptManager, String uptOperation, String userId, String applicationName) throws CSTransactionException
	{
		checkUptPrivilegeForOperation(uptManager, uptOperation, Constants.CSM_ACCESS_PRIVILEGE, userId, applicationName);
		checkUptPrivilegeForOperation(uptManager, uptOperation, Constants.CSM_CREATE_PRIVILEGE, userId, applicationName);
		checkUptPrivilegeForOperation(uptManager, uptOperation, Constants.CSM_UPDATE_PRIVILEGE, userId, applicationName);
		checkUptPrivilegeForOperation(uptManager, uptOperation, Constants.CSM_DELETE_PRIVILEGE, userId, applicationName);
	}
	private void checkUptPrivilegeForOperation(UserProvisioningManager uptManager, String uptOperation, String privilege, String userId, String applicationName) throws CSTransactionException
	{
		String uptPersionKey=privilege+"_"+uptOperation;
		boolean uptPermission=uptManager.checkPermissionForProvisioningOperation(uptOperation, privilege, userId, applicationName);
		if (uptPermission)
			sessionMap.put(uptPersionKey, "true");
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		sessionMap = arg0;
	}
}
