/*
 * Created on Dec 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package gov.nih.nci.security.upt.actions;

import gov.nih.nci.security.upt.constants.DisplayConstants;
import gov.nih.nci.security.upt.constants.ForwardConstants;
import gov.nih.nci.security.upt.forms.MenuForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Kunal Modi (Ekagra Software Technologies Ltd.)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class MenuSelectionAction extends Action 
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		/* perform login task*/
		HttpSession session = request.getSession(true);
		MenuForm menuSelectionForm = (MenuForm)form; 
		
		if (session.isNew() || (session.getAttribute(DisplayConstants.USER_OBJECT) == null)) {
			return mapping.findForward(ForwardConstants.LOGIN_PAGE);
		}

		session.removeAttribute(DisplayConstants.CURRENT_ACTION);
		session.removeAttribute(DisplayConstants.CURRENT_FORM);
		session.removeAttribute(DisplayConstants.SEARCH_RESULT);

		session.setAttribute(DisplayConstants.CURRENT_TABLE_ID,menuSelectionForm.getTableId());		
		
		if (menuSelectionForm.getTableId().equalsIgnoreCase(DisplayConstants.HOME_ID))
			return (mapping.findForward(ForwardConstants.HOME_PAGE));
		return (mapping.findForward(ForwardConstants.TABLE_HOME_PAGE));
	}

}
