package gov.nih.nci.security.authentication;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class LockoutManager
{

	private static HashMap lockoutCache = null;
	private static LockoutManager lockoutManager = null;
	static Timer cleanupTimer = new Timer();

	private long lockoutTime;
	private long allowedLoginTime;
	private int allowedAttempts;
	private boolean disableLockoutManager;
	private long delayTime;
	
	private class CleanupTask extends TimerTask
	{
		public void run()
		{
			Collection<String> userIds = lockoutCache.keySet();
			for (String userId : userIds)
			{
				LockoutInfo lockoutInfo = (LockoutInfo)lockoutCache.get(userId);
				if (delayTime < (System.currentTimeMillis() - lockoutInfo.getFirstLoginTime()))
				{
					lockoutCache.remove(userId);
				}
			}
		}
	}

	protected class LockoutInfo
	{
		private int noOfAttempts;
		private long firstLoginTime;
		private boolean lockedout;

		public long getFirstLoginTime()
		{
			return firstLoginTime;
		}

		public void setFirstLoginTime(long firstLoginTime)
		{
			this.firstLoginTime = firstLoginTime;
		}

		public int getNoOfAttempts()
		{
			return noOfAttempts;
		}

		public void setNoOfAttempts(int noOfAttempts)
		{
			this.noOfAttempts = noOfAttempts;
		}

		public boolean isLockedout()
		{
			return lockedout;
		}

		public void setLockedout(boolean lockedout)
		{
			this.lockedout = lockedout;
		}
		
	}

	private LockoutManager(String lockoutTime, String allowedLoginTime, String allowedAttempts)
	{
		lockoutManager = new LockoutManager();
		lockoutCache = new HashMap();
		if (lockoutTime.equals("0") || allowedLoginTime.equals("0") || allowedAttempts.equals("0"))
			disableLockoutManager = true;
		else
		{
			this.lockoutTime = new Long(lockoutTime);
			this.allowedLoginTime = new Long(allowedLoginTime);
			this.allowedAttempts = Integer.parseInt(allowedAttempts);
			this.disableLockoutManager = false;
			this.delayTime = this.lockoutTime + this.allowedLoginTime;
			cleanupTimer.schedule(new CleanupTask(), delayTime, delayTime);
		}

	}
	
	private LockoutManager()
	{
	}
	

	public static void initialize(String lockoutTime, String allowedLoginTime, String allowedAttempts)
	{
		if (null == lockoutManager)
		{
			lockoutManager = new LockoutManager(lockoutTime, allowedLoginTime, allowedAttempts);
		}
	}
	
	public static LockoutManager getInstance()
	{
		if (null == lockoutManager)
		{
			return null;
		}
		return lockoutManager;
	}	

	public boolean isUserLockedOut(String userId)
	{
		if (!disableLockoutManager)
		{
			LockoutInfo lockoutInfo = (LockoutInfo)lockoutCache.get(userId);
			if (null != lockoutInfo)
				return lockoutInfo.isLockedout();
			else
				return false;
		}
		else
			return false;
	}
	
	public void setFailedAttempt(String userId)
	{
		if (!disableLockoutManager)
		{
			LockoutInfo lockoutInfo = (LockoutInfo)lockoutCache.get(userId);
			if (null != lockoutInfo)
			{
				if (!lockoutInfo.isLockedout())
				{
					if ((System.currentTimeMillis() - lockoutInfo.getFirstLoginTime()) < allowedLoginTime)
					{
						lockoutInfo.setNoOfAttempts(lockoutInfo.getNoOfAttempts()+ 1);
						System.out.println("lockoutInfo.getNoOfAttempts()" + lockoutInfo.getNoOfAttempts());
						if (lockoutInfo.getNoOfAttempts() > allowedAttempts)
							lockoutInfo.setLockedout(true);
					}
					else
					{
						lockoutInfo.setFirstLoginTime(System.currentTimeMillis());
						lockoutInfo.setNoOfAttempts(1);
					}	
				}
			}
			else
			{
				lockoutInfo = lockoutManager.new LockoutInfo();
				lockoutInfo.setFirstLoginTime(System.currentTimeMillis());
				lockoutInfo.setNoOfAttempts(1);			
			}
			lockoutCache.put(userId,lockoutInfo);
		}
	}
}