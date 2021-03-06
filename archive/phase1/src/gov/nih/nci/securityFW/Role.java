/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC, SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/common-security-module/LICENSE.txt for details.
 */

/**
 *	The caBIG Software License, Version 1.0
 *
 *	Copyright 2004 SAIC. This software was developed in conjunction with the National Cancer
 *	Institute, and so to the extent government employees are co-authors, any rights in such works
 *	shall be subject to Title 17 of the United States Code, section 105.
 *
 *	Redistribution and use in source and binary forms, with or without modification, are permitted
 *	provided that the following conditions are met:
 *
 *	1. Redistributions of source code must retain the above copyright notice, this list of conditions
 *	and the disclaimer of Article 3, below.  Redistributions in binary form must reproduce the above
 *	copyright notice, this list of conditions and the following disclaimer in the documentation and/or
 *	other materials provided with the distribution.
 *
 *	2.  The end-user documentation included with the redistribution, if any, must include the
 *	following acknowledgment:
 *
 *	"This product includes software developed by the SAIC and the National Cancer
 *	Institute."
 *
 *	If no such end-user documentation is to be included, this acknowledgment shall appear in the
 *	software itself, wherever such third-party acknowledgments normally appear.
 *
 *	3. The names "The National Cancer Institute", "NCI" and "SAIC" must not be used to endorse or
 *	promote products derived from this software.
 *
 *	4. This license does not authorize the incorporation of this software into any third party proprietary
 *	programs.  This license does not authorize the recipient to use any trademarks owned by either
 *	NCI or SAIC-Frederick.
 *
 *
 *	5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED
 *	WARRANTIES, (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *	MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE) ARE
 *	DISCLAIMED.  IN NO EVENT SHALL THE NATIONAL CANCER INSTITUTE, SAIC, OR
 *	THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *	EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *	PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *	PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 *	OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package gov.nih.nci.securityFW;

import org.apache.log4j.*;

/**
 * This class encapsulates the properties of a security role object
 * @author       Q. Pan
 * @version      1.0
*/



public class Role
{
  private long roleId;
  private String roleName;
  private String roleDesc;
  private long applicationId;

   /**
    * Constructor for a new <code></code>Role</code> object.
    */
  public Role()
  {}

  public Role(String roleName)
  {
    this.roleName = roleName;
  }
   /**
    * Sets the roleName for this <code>Role</code> object
    * @param roleName the roleName
    */
  public void setRoleName(String roleName)
  {
    this.roleName = roleName;
  }

   /**
    * Return the roleName
    * @return the roleName
    */
  public String getRoleName()
  {
    return roleName;
  }

   /**
    * Sets the roleDesc for this <code>Role</code> object
    * @param roleDesc the roleDesc
    */
  public void setRoleDesc(String roleDesc)
  {
    this.roleDesc = roleDesc;
  }

   /**
    * Return the roleDesc
    * @return the roleDesc
    */
  public String getRoleDesc()
  {
    return roleDesc;
  }

   /**
    * Sets the roleId for this <code>Role</code> object
    * @param roleId the roleId
    */
  public void setRoleId(long roleId)
  {
    this.roleId = roleId;
  }

   /**
    * Return the roleId
    * @return the roleId
    */
  public long getRoleId()
  {
    return roleId;
  }

   /**
    * Sets the applicationId for this <code>Role</code> object
    * @param applicationId the applicationId
    */
  public void setApplicationId(long applicationId)
  {
    this.applicationId = applicationId;
  }

   /**
    * Return the applicationId
    * @return the applicationId
    */
  public long getApplicationId()
  {
    return applicationId;
  }
}
