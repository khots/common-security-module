package gov.nih.nci.security.dao;

import gov.nih.nci.security.authorization.domainobjects.*;
import gov.nih.nci.security.util.*;

import java.util.Collection;
import java.util.Iterator;

import gov.nih.nci.security.authorization.jaas.*;
import gov.nih.nci.security.dao.hibernate.HibernateSessionFactory;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;

import java.security.Principal;
import java.util.Set;

import javax.security.auth.Subject;

import org.apache.log4j.Logger;

import net.sf.hibernate.Session;
import net.sf.hibernate.*;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.SessionFactory;
import java.util.List;

import net.sf.hibernate.expression.*;

import gov.nih.nci.security.dao.hibernate.*;
import java.util.*;


/**
 * @version 1.0
 * @created 03-Dec-2004 1:17:47 AM
 */
public class AuthorizationDAOImpl implements AuthorizationDAO {
	
	static final Logger log = Logger.getLogger(AuthorizationDAOImpl.class
			.getName());


	private SessionFactory sf = null;

	private Application application = null;

	public AuthorizationDAOImpl(SessionFactory sf, String applicationContextName) {
		this.sf = sf;
		try {
			System.out.println("The context Name passed:"+applicationContextName);
			this.application = this.getApplicationByName(applicationContextName);
			//this.application= (Application)this.getObjectByPrimaryKey(Application.class,new Long("1"));
			System.out.println("The Application:"+application.getApplicationId()+":"+application.getApplicationDescription());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	public void setHibernateSessionFactory(SessionFactory sf) {
		this.sf = sf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#addUserToGroup(java.lang.String,
	 *      java.lang.String)
	 */
	public void addUserToGroupxx(String groupId, String userId)
			throws CSTransactionException {
		Session s = null;
		Transaction t = null;
		//log.debug("Running create test...");
		try {

			

			s = sf.openSession();

			System.out.println("The original user Id:"+userId);
			//Group grp = new Group();
			//grp.setGroupId(new Long(groupId));
			User user = (User)this.getObjectByPrimaryKey(User.class,new Long(userId));
			System.out.println("The user id ="+user.getUserId());
			//System.out.println("The user id ="+grp.getGroupId());
			/**
			 * First check if there are some privileges for this role If there
			 * are any then delete them.
			 */
			//UserGroup search = new UserGroup();
			//search.setGroup(grp);
			//search.setUser(user);
            Criteria criteria = s.createCriteria(UserGroup.class);
            criteria.add(Expression.eq("user",user));
			List list = criteria.list();
			
			ArrayList oldList = new ArrayList();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				UserGroup ug = (UserGroup) it.next();
				Group group = ug.getGroup();
				oldList.add(group.getGroupId().toString());
				System.out.println("The old group id ="+group.getGroupId());
				log.debug("The old List" + group.getGroupId().toString());
			}
			/**
			t = s.beginTransaction();
			 if(!oldList.contains(groupId)){
			 	System.out.println("Inside");
			 	Group g = this.getGroup(new Long(groupId));
			 	UserGroup ugToBeSaved = new UserGroup();
			 	ugToBeSaved.setGroup(g);
			 	//ugToBeSaved.setUser(user);
			 	s.save(ugToBeSaved);
			 }
			t.commit();
			*/

			//log.debug( "Privilege ID is: " +
			// privilege.getId().doubleValue() );
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

		
		

	}
	
	public void addUserToGroup(String groupId, String userId)
	throws CSTransactionException {
		Session s = null;
		Transaction t = null;
		//log.debug("Running create test...");
		try {
		
			
		
			s = sf.openSession();
		   t = s.beginTransaction();
			
			User user = (User)this.getObjectByPrimaryKey(User.class,new Long(userId));
			Set groups = user.getGroups();
			Group group = (Group)this.getObjectByPrimaryKey(Group.class,new Long(groupId));
			
			if(!groups.contains(group)){
				groups.add(group);
				user.setGroups(groups);
				s.update(user);
			}
			
			t.commit();
			
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}




}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#assignGroupRoleToProtectionGroup(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void assignGroupRoleToProtectionGroup(String protectionGroupId,
			String groupId, String[] rolesId) throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#assignPrivilegesToRole(java.lang.String[],
	 *      java.lang.String)
	 */

	public void assignPrivilegesToRole(String roleId, String[] privilegeIds)
			throws CSTransactionException {

		Session s = null;
		Transaction t = null;
		//log.debug("Running create test...");
		try {
			s = sf.openSession();
			t = s.beginTransaction();
            Set newPrivs = new HashSet();
			ArrayList newList = new ArrayList();
			for (int k = 0; k < privilegeIds.length; k++) {
				log.debug("The new list:" + privilegeIds[k]);
				Privilege pr = (Privilege)this.getObjectByPrimaryKey(Privilege.class,new Long(privilegeIds[k]));
				newPrivs.add(pr);
			}

			Role role = (Role)this.getObjectByPrimaryKey(Role.class,new Long(roleId));
			role.setPrivileges(newPrivs);
			s.update(role);
			t.commit();

			//log.debug( "Privilege ID is: " +
			// privilege.getId().doubleValue() );
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}
	
	public void assignPrivilegesToRoleXX(String roleId, String[] privilegeIds)
	throws CSTransactionException {

		Session s = null;
		Transaction t = null;
		//log.debug("Running create test...");
		try {
		
			ArrayList newList = new ArrayList();
			for (int k = 0; k < privilegeIds.length; k++) {
				log.debug("The new list:" + privilegeIds[k]);
				newList.add(privilegeIds[k]);
			}
		
			s = sf.openSession();
		
			t = s.beginTransaction();
			Role r = this.getRole(new Long(roleId));
			/**
			 * First check if there are some privileges for this role If there
			 * are any then delete them.
			 */
			RolePrivilege search = new RolePrivilege();
			search.setRole(r);
		
			List list = s.createCriteria(RolePrivilege.class).add(
					Example.create(search)).list();
			ArrayList oldList = new ArrayList();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				RolePrivilege rp1 = (RolePrivilege) it.next();
				Privilege priv = rp1.getPrivilege();
				oldList.add(priv.getId().toString());
				System.out.println("The old Priv Id:"+priv.getId().toString());
				log.debug("The old List" + priv.getId().toString());
			}
		
			Collection toBeInserted = ObjectSetUtil.minus(newList, oldList);
			Collection toBeRemoved = ObjectSetUtil.minus(oldList, newList);
		
			Iterator toBeInsertedIt = toBeInserted.iterator();
			Iterator toBeRemovedIt = toBeRemoved.iterator();
		
			while (toBeInsertedIt.hasNext()) {
				String rp_id = (String) toBeInsertedIt.next();
				System.out.println("To Be Inserted: " + rp_id);
				log.debug("To Be Inserted: " + rp_id);
				Privilege p = this.getPrivilege(rp_id);
				RolePrivilege rp = new RolePrivilege();
				rp.setPrivilege(p);
				rp.setRole(r);
				rp.setUpdateDate(new java.util.Date());
				s.save(rp);
			}
			while (toBeRemovedIt.hasNext()) {
		
				String p_id = (String) toBeRemovedIt.next();
				System.out.println("To Be Removed: " + p_id);
				log.debug("To Be Removed: " + p_id);
				Privilege p = new Privilege();
				p.setId(new Long(p_id));
				search.setPrivilege(p);
				List list_del = s.createCriteria(RolePrivilege.class).add(
						Example.create(search)).list();
				RolePrivilege rp_del = (RolePrivilege) list_del.get(0);
				s.delete(rp_del);
			}
		
			t.commit();
		
			//log.debug( "Privilege ID is: " +
			// privilege.getId().doubleValue() );
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		
		}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#assignProtectionElements(java.lang.String,
	 *      java.lang.String[], java.lang.String[])
	 */
	public void assignProtectionElements(String protectionGroupName,
			String protectionElementObjectId,
			String protectionElementAttributeName)
			throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#assignProtectionElements(java.lang.String,
	 *      java.lang.String[])
	 */
	public void assignProtectionElements(String protectionGroupName,
			String protectionElementObjectId)
			throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#assignUserRoleToProtectionGroup(java.lang.String,
	 *      java.lang.String[], java.lang.String)
	 */
	public void assignUserRoleToProtectionGroup(String userId,
			String[] rolesId, String protectionGroupId)
			throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#checkPermission(gov.nih.nci.security.authorization.jaas.AccessPermission,
	 *      java.lang.String)
	 */
	public boolean checkPermission(AccessPermission permission, String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#checkPermission(gov.nih.nci.security.authorization.jaas.AccessPermission,
	 *      javax.security.auth.Subject)
	 */
	public boolean checkPermission(AccessPermission permission, Subject subject) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#checkPermission(gov.nih.nci.security.authorization.jaas.AccessPermission,
	 *      gov.nih.nci.security.authorization.domainobjects.User)
	 */
	public boolean checkPermission(AccessPermission permission, User user) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#checkPermission(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean checkPermission(String userName, String objectId,
			String attributeId, String privilegeName) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#checkPermission(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public boolean checkPermission(String userName, String objectId,
			String privilegeName) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#createGroup(gov.nih.nci.security.authorization.domainobjects.Group)
	 */
	public void createGroup(Group group) throws CSTransactionException {
		// TODO Auto-generated method stub
		Session s = null;
		Transaction t = null;
		try {
			s = sf.openSession();
			t = s.beginTransaction();
			group.setApplication(application);
			group.setUpdateDate(new Date());
			s.save(group);
			t.commit();
			log.debug("Group ID is: " + group.getGroupId());
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#createPrivilege(gov.nih.nci.security.authorization.domainobjects.Privilege)
	 */
	public void createPrivilegeXXX(Privilege privilege)
			throws CSTransactionException {
		// TODO Auto-generated method stub
		Session s = null;
		Transaction t = null;
		log.debug("Running create test...");
		try {
			s = HibernateSessionFactory.currentSession();

			t = s.beginTransaction();
			//Privilege p = new Privilege();

			//p.setDesc("TestDesc");
			//p.setName("TestName");
			s.save(privilege);
			t.commit();
			log.debug("Privilege ID is: "
					+ privilege.getId().doubleValue());
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}

	public void createPrivilege(Privilege privilege)
			throws CSTransactionException {
		// TODO Auto-generated method stub
		Session s = null;
		Transaction t = null;
		try {
			s = sf.openSession();
			t = s.beginTransaction();
			privilege.setUpdateDate(new Date());
			s.save(privilege);
			t.commit();

			log.debug("Privilege ID is: "
					+ privilege.getId().doubleValue());
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#createProtectionElement(gov.nih.nci.security.authorization.domainobjects.ProtectionElement)
	 */
	public void createProtectionElement(ProtectionElement protectionElement)
			throws CSTransactionException {
		Session s = null;
		Transaction t = null;
		try {
			s = sf.openSession();
			t = s.beginTransaction();
			protectionElement.setApplication(application);
			protectionElement.setUpdateDate(new Date());
			s.save(protectionElement);
			t.commit();
			log.debug("Protection element ID is: " + protectionElement.getProtectionElementId());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Protection Element could not be created:", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#createProtectionGroup(gov.nih.nci.security.authorization.domainobjects.ProtectionGroup)
	 */
	public void createProtectionGroup(ProtectionGroup protectionGroup)
			throws CSTransactionException {
		Session s = null;
		Transaction t = null;
		try {
			s = sf.openSession();
			t = s.beginTransaction();
			protectionGroup.setApplication(application);
			protectionGroup.setUpdateDate(new Date());
			s.save(protectionGroup);
			t.commit();
			log.debug("Protection group ID is: " + protectionGroup.getProtectionGroupId());
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Protection group could not be created:", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#createRole(gov.nih.nci.security.authorization.domainobjects.Role)
	 */
	public void createRole(Role role) throws CSTransactionException {
		// TODO Auto-generated method stub

		Session s = null;
		Transaction t = null;
		try {
			s = sf.openSession();
			t = s.beginTransaction();
			role.setApplication(application);
			role.setUpdateDate(new Date());
			s.save(role);
			t.commit();
			log.debug("Role ID is: " + role.getId().doubleValue());
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#deAssignProtectionElements(java.lang.String,
	 *      java.lang.String[], java.lang.String[])
	 */
	public void deAssignProtectionElements(String protectionGroupName,
			String[] protectionElementObjectNames,
			String[] protectionElementAttributeNames)
			throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#deAssignProtectionElements(java.lang.String[],
	 *      java.lang.String)
	 */
	public void deAssignProtectionElements(
			String[] protectionElementObjectNames, String protectionGroupName)
			throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getApplicationContext()
	 */
	public ApplicationContext getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getGroup(java.lang.Long)
	 */
	public Group getGroup(Long groupId) throws CSObjectNotFoundException {
		// TODO Auto-generated method stub
		return (Group)this.getObjectByPrimaryKey(Group.class,groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getGroup(java.lang.String)
	 */
	public Group getGroup(String groupName) throws CSObjectNotFoundException {
		// TODO Auto-generated method stub
		Session s = null;
		Group grp = null;
		try {
			Group search = new Group();
			search.setGroupName(groupName);
			search.setApplication(application);
			//String query = "FROM
			// gov.nih.nci.security.authorization.domianobjects.Application";
			s = sf.openSession();
			List list = s.createCriteria(Group.class).add(
					Example.create(search)).list();
			//p = (Privilege)s.load(Privilege.class,new Long(privilegeId));
			
			if (list.size() == 0) {
				throw new CSObjectNotFoundException("Group not found");
			}
			grp = (Group) list.get(0);

		} catch (Exception ex) {
			log.fatal("Unable to find Group", ex);

		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		return grp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getObjects(gov.nih.nci.security.dao.SearchCriteria)
	 */
	public Set getObjects(SearchCriteria searchCriteria) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getPrincipals(java.lang.String)
	 */
	public Principal[] getPrincipals(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getPrivilege(java.lang.String)
	 */
	public Privilege getPrivilege(String privilegeId)
			throws CSObjectNotFoundException {
		// TODO Auto-generated method stub
		/**
		Session s = null;
		Privilege pr = null;
		try {
			s = sf.openSession();
			Query query = s.createQuery("select p from Privilege as p where p.name =:name");
			query.setString("name",privilegeId);
			for (Iterator it = query.iterate(); it.hasNext();) {
				Privilege pr1 = (Privilege) it.next();
			    System.out.println("Privilege: " + pr1.getName() );
			    pr=pr1;
			}

		} catch (Exception ex) {
			log.fatal("Unable to find Group", ex);

		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		return pr;
		*/

		return (Privilege) this.getObjectByPrimaryKey(Privilege.class,
				new Long(privilegeId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getProtectionElement(java.lang.Long)
	 */
	public ProtectionElement getProtectionElement(Long protectionElementId)
			throws CSObjectNotFoundException {
		// TODO Auto-generated method stub
		return (ProtectionElement)this.getObjectByPrimaryKey(ProtectionElement.class,protectionElementId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getProtectionElement(java.lang.String)
	 */
	public ProtectionElement getProtectionElement(String objectId)
			throws CSObjectNotFoundException {
		Session s = null;
		ProtectionElement pe = null;
		try {
			ProtectionElement search = new ProtectionElement();
			search.setObjectId(objectId);
			search.setApplication(application);
			//String query = "FROM
			// gov.nih.nci.security.authorization.domianobjects.Application";
			s = sf.openSession();
			List list = s.createCriteria(ProtectionElement.class).add(
					Example.create(search)).list();
			
			
			if (list.size() == 0) {
				throw new CSObjectNotFoundException("Protection Element not found");
			}
			pe = (ProtectionElement)list.get(0);

		} catch (Exception ex) {
			log.fatal("Unable to find Protection Element", ex);

		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		return pe;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getProtectionGroup(java.lang.Long)
	 */
	public ProtectionGroup getProtectionGroup(Long protectionGroupId)
			throws CSObjectNotFoundException {
		// TODO Auto-generated method stub
		return (ProtectionGroup)this.getObjectByPrimaryKey(ProtectionGroup.class,protectionGroupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getProtectionGroup(java.lang.String)
	 */
	public ProtectionGroup getProtectionGroup(String protectionGroupName)
			throws CSObjectNotFoundException {
		// TODO Auto-generated method stub
		Session s = null;
		ProtectionGroup pgrp = null;
		try {
			ProtectionGroup search = new ProtectionGroup();
			search.setProtectionGroupName(protectionGroupName);
			search.setApplication(application);
			//String query = "FROM
			// gov.nih.nci.security.authorization.domianobjects.Application";
			s = sf.openSession();
			List list = s.createCriteria(ProtectionGroup.class).add(
					Example.create(search)).list();
			
			
			if (list.size() == 0) {
				throw new CSObjectNotFoundException("Protection Group not found");
			}
			pgrp = (ProtectionGroup) list.get(0);

		} catch (Exception ex) {
			log.fatal("Unable to find Protection group", ex);

		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		return pgrp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getRole(java.lang.Long)
	 */
	public Role getRole(Long roleId) throws CSObjectNotFoundException {
		// TODO Auto-generated method stub
		Role r = (Role) this.getObjectByPrimaryKey(Role.class, roleId);
		//r.setPrivileges(this.getPrivileges(roleId.toString()));
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getRole(java.lang.String)
	 */
	public Role getRole(String roleName) throws CSObjectNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#getUser(java.lang.String)
	 */
	public User getUser(String loginName) {
		Session s = null;
		User user = null;
		try {
			User search = new User();
			search.setLoginName(loginName);
			
			//String query = "FROM
			// gov.nih.nci.security.authorization.domianobjects.Application";
			s = sf.openSession();
			List list = s.createCriteria(User.class).add(
					Example.create(search)).list();
			//p = (Privilege)s.load(Privilege.class,new Long(privilegeId));
			
			if (list.size()!= 0) {
				user = (User) list.get(0);
			}
			
			Collection groups = user.getGroups();
			Iterator it = groups.iterator();
			while(it.hasNext()){
				Group grp = (Group)it.next();
				System.out.println("The group Id:"+grp.getGroupId());
			}
			

		} catch (Exception ex) {
			log.fatal("Unable to find Group", ex);

		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#modifyGroup(gov.nih.nci.security.authorization.domainobjects.Group)
	 */
	public void modifyGroup(Group group) throws CSTransactionException {
		// TODO Auto-generated method stub
		Session s = null;
		Transaction t = null;
		try {
			log.debug("About to be Modified");
			s = sf.openSession();
			t = s.beginTransaction();
			group.setUpdateDate(new Date());
			s.update(group);
			log.debug("Modified");
			t.commit();

			//log.debug( "Privilege ID is: " +
			// privilege.getId().doubleValue() );
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Group Object could not be modified:", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#modifyPrivilege(gov.nih.nci.security.authorization.domainobjects.Privilege)
	 */
	public void modifyPrivilege(Privilege privilege)
			throws CSTransactionException {
		// TODO Auto-generated method stub
		Session s = null;
		Transaction t = null;
		try {
			log.debug("About to be Modified");
			s = sf.openSession();
			t = s.beginTransaction();
			privilege.setUpdateDate(new Date());
			s.update(privilege);
			log.debug("Modified");
			t.commit();

			//log.debug( "Privilege ID is: " +
			// privilege.getId().doubleValue() );
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#modifyProtectionGroup(gov.nih.nci.security.authorization.domainobjects.ProtectionGroup)
	 */
	public void modifyProtectionGroup(ProtectionGroup protectionGroup)
			throws CSTransactionException {
		Session s = null;
		Transaction t = null;
		try {

			s = sf.openSession();
			t = s.beginTransaction();
			protectionGroup.setUpdateDate(new Date());
			s.update(protectionGroup);
			log.debug("Modified");
			t.commit();

		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Protection group could not be modified", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#modifyRole(gov.nih.nci.security.authorization.domainobjects.Role)
	 */
	public void modifyRole(Role role) throws CSTransactionException {
		// TODO Auto-generated method stub
		Session s = null;
		Transaction t = null;
		try {

			s = sf.openSession();
			t = s.beginTransaction();
			s.update(role);
			log.debug("Modified");
			t.commit();

		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeGroup(java.lang.String)
	 */
	public void removeGroup(String groupId) throws CSTransactionException {
		// TODO Auto-generated method stub
		/**
		 * This method should remove all the children
		 * Namely all the reocrds form user_group table where this 
		 * group is refernced
		 * Also
		 * All the records from the user_group_role_protection_group
		 */

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeGroupFromProtectionGroup(java.lang.String,
	 *      java.lang.String)
	 */
	public void removeGroupFromProtectionGroup(String protectionGroupId,
			String groupId) throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeGroupRoleFromProtectionGroup(java.lang.String,
	 *      java.lang.String, java.lang.String[])
	 */
	public void removeGroupRoleFromProtectionGroup(String protectionGroupId,
			String groupId, String[] roleId) throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removePrivilege(java.lang.String)
	 */
	public void removePrivilege(String privilegeId)
			throws CSTransactionException {
		Privilege p = new Privilege();
		p.setId(new Long(privilegeId));
		this.removeObject(p);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeProtectionElement(gov.nih.nci.security.authorization.domainobjects.ProtectionElement)
	 */
	public void removeProtectionElement(ProtectionElement element)
			throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeProtectionGroup(java.lang.String)
	 */
	public void removeProtectionGroup(String protectionGroupName)
			throws CSTransactionException {
		// TODO Auto-generated method stub
		/**
		 * All the children should be removed 
		 * Namely - all the records from protection_elements
		 * all the records from user_group_role_protectiongroup
		 */

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeRole(java.lang.String)
	 */
	public void removeRole(String roleId) throws CSTransactionException {
		// TODO Auto-generated method stub
		Role r = new Role();
		r.setId(new Long(roleId));
		this.removeObject(r);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeUserFromGroup(java.lang.String,
	 *      java.lang.String)
	 */
	public void removeUserFromGroup(String groupId, String userId)
			throws CSTransactionException {
		 
		Session s = null;
		Transaction t = null;
		//log.debug("Running create test...");
		try {
			s = sf.openSession();
			t = s.beginTransaction();
			 User user = (User)this.getObjectByPrimaryKey(User.class,new Long(userId));
			 Group group = (Group)this.getObjectByPrimaryKey(Group.class,new Long(groupId));
			 Set groups = user.getGroups();
			 if(groups.contains(group)){
			 	groups.remove(group);
			 	user.setGroups(groups);
			 	s.update(user);
			 }

			t.commit();

			//log.debug( "Privilege ID is: " +
			// privilege.getId().doubleValue() );
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeUserFromProtectionGroup(java.lang.String,
	 *      java.lang.String)
	 */
	public void removeUserFromProtectionGroup(String protectionGroupId,
			String userId) throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#removeUserRoleFromProtectionGroup(java.lang.String,
	 *      java.lang.String, java.lang.String[])
	 */
	public void removeUserRoleFromProtectionGroup(String protectionGroupName,
			String userName, String[] roles) throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#setOwnerForProtectionElement(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public void setOwnerForProtectionElement(String userName,
			String protectionElementObjectName,
			String protectionElementAttributeName)
			throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nih.nci.security.dao.AuthorizationDAO#setOwnerForProtectionElement(java.lang.String,
	 *      java.lang.String)
	 */
	public void setOwnerForProtectionElement(
			String protectionElementObjectName, String userName)
			throws CSTransactionException {
		// TODO Auto-generated method stub

	}

	public Set getPrivilegesXX(String roleId) throws CSObjectNotFoundException {
		Session s = null;

		//ArrayList result = new ArrayList();
		Set result = new HashSet();
		try {
			s = sf.openSession();
			RolePrivilege search = new RolePrivilege();
			Role r = new Role();
			r.setId(new Long(roleId));
			search.setRole(r);
			List list = s.createCriteria(RolePrivilege.class).add(
					Example.create(search)).list();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				RolePrivilege rp = (RolePrivilege) it.next();
				Privilege p = rp.getPrivilege();
				result.add(p);
			}

		} catch (Exception ex) {
			log.error(ex);
			throw new CSObjectNotFoundException("No Set found", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		return result;
	}
	
	public Set getPrivileges(String roleId) throws CSObjectNotFoundException {
		Session s = null;
		System.out.println("The role: getting there");
		//ArrayList result = new ArrayList();
		Set result = new HashSet();
		try {
			s = sf.openSession();
			Role role = (Role)this.getObjectByPrimaryKey(Role.class,new Long(roleId));
			System.out.println("The role:"+role.getName());
			result = role.getPrivileges();
			System.out.println("The result size:"+result.size());

		} catch (Exception ex) {
			log.error(ex);
			throw new CSObjectNotFoundException("No Set found", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		return result;
	}
	
	public void createUser(User user) throws CSTransactionException{
		Session s = null;
		Transaction t = null;
		try {
			s = sf.openSession();
			t = s.beginTransaction();
			user.setUpdateDate(new Date());
			s.save(user);
			t.commit();
			log.debug("User ID is: " + user.getUserId());
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Could not create the user", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}
	
	public void assignProtectionElements(String protectionGroupId,String[] protectionElementIds) throws CSTransactionException{
		Session s = null;
		Transaction t = null;
		//log.debug("Running create test...");
		try {
			s = sf.openSession();
            t = s.beginTransaction();
			//System.out.println("The original user Id:"+userId);
			
			 ProtectionGroup protectionGroup = (ProtectionGroup)this.getObjectByPrimaryKey(ProtectionGroup.class,new Long(protectionGroupId));
			for (int i=0;i<protectionElementIds.length;i++){
				Criteria criteria = s.createCriteria(ProtectionGroupProtectionElement.class);
	            criteria.add(Expression.eq("protectionGroup",protectionGroup));
	            ProtectionElement protectionElement = (ProtectionElement)this.getObjectByPrimaryKey(ProtectionElement.class,new Long(protectionElementIds[i]));
	            criteria.add(Expression.eq("protectionElement",protectionElement));
				List list = criteria.list();
				if(list.size()==0){
					ProtectionGroupProtectionElement pgpe = new ProtectionGroupProtectionElement();
					pgpe.setProtectionGroup(protectionGroup);
					pgpe.setProtectionElement(protectionElement);
					pgpe.setUpdateDate(new Date());
					s.save(pgpe);
				}
				
			}
            
			t.commit();
			
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}
	
	private void assignProtectionElement(ProtectionGroup pg,String peId){
		
	}


	private Object getObjectByPrimaryKey(Class objectType, Long primaryKey)
			throws CSObjectNotFoundException {
		Object oj = null;

		Session s = null;

		try {

			s = sf.openSession();

			oj = s.load(objectType, primaryKey);

			if (oj == null) {
				throw new CSObjectNotFoundException(objectType.getName()
						+ " not found");
			}

		} catch (Exception ex) {
			log.error(ex);
			throw new CSObjectNotFoundException(objectType.getName()
					+ " not found", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

		return oj;
	}

	private void removeObject(Object oj) throws CSTransactionException {

		Session s = null;
		Transaction t = null;
		try {

			s = sf.openSession();
			t = s.beginTransaction();

			s.delete(oj);

			t.commit();
			
		} catch (Exception ex) {
			log.error(ex);
			try {
				t.rollback();
			} catch (Exception ex3) {
			}
			throw new CSTransactionException("Bad", ex);
		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}

	}

	private Application getApplicationByName(String contextName)
			throws CSObjectNotFoundException {
		Session s = null;
		Application app = null;
		try {
			Application search = new Application();
			search.setApplicationName(contextName);
			//String query = "FROM
			// gov.nih.nci.security.authorization.domianobjects.Application";
			s = sf.openSession();
			List list = s.createCriteria(Application.class).add(
					Example.create(search)).list();
			//p = (Privilege)s.load(Privilege.class,new Long(privilegeId));
			log.debug("Somwthing");
			if (list.size() == 0) {
				System.out.println("Could not find the Application");
				throw new CSObjectNotFoundException("Not found");
			}
			app = (Application) list.get(0);
			System.out.println("Found the Application");

		} catch (Exception ex) {
			log.fatal("Unable to find application context", ex);

		} finally {
			try {
				s.close();
			} catch (Exception ex2) {
			}
		}
		return app;
	}
}