package in.BBAT.presenter;

import in.bbat.license.LicenseInfo;
import in.bbat.license.LicenseManager;
import in.bbat.logger.BBATLogger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class LicenseCheckerHourly
{
  protected static final long LICENSE_CHECK_INTERVAL = 3600000L;
  protected static final int NTHREDS = 3;
  private static Thread thread = null;
  private static boolean shouldExit = false;
  private static Queue<Runnable> jobsQueue = new ConcurrentLinkedQueue();
  static ExecutorService executor = Executors.newFixedThreadPool(3);
  private static final Logger LOG = BBATLogger.getLogger(LicenseCheckerHourly.class.getName());

  private static void executeQueuedJobs()
  {
    if (jobsQueue.isEmpty())
      return;
    while (!jobsQueue.isEmpty())
    {
      Runnable localRunnable = (Runnable)jobsQueue.poll();
      if (localRunnable != null)
        try
        {
          executor.submit(localRunnable);
        }
        catch (RejectedExecutionException localRejectedExecutionException)
        {
         LOG.error("Could not submit license hourly checker job!", localRejectedExecutionException);
          break;
        }
    }
  }

  public static void startLicenseCheckerThread()
  {
    if ((thread != null) && (thread.isAlive()))
      return;
    Runnable local1 = new Runnable()
    {
      public void run()
      {
        while (!LicenseCheckerHourly.shouldExit)
        {
          try
          {
            Thread.sleep(3600000L);
            LicenseCheckerHourly.addJob(new Runnable()
            {
              public void run()
              {
                LicenseInfo curLicense = LicenseManager.getCurrentLicense();
                LicenseInfo localLicenseInfo2 = LicenseManager.checkoutLicense(LicenseManager.getCurrentLicense());
                if ((curLicense != null) && (localLicenseInfo2 != null) && (curLicense.isTrial()) && (localLicenseInfo2.isCommunity()))
                  Display.getDefault().syncExec(new Runnable()
                  {
                    public void run()
                    {
                      MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Welcome to BBAT Community Edition!", "BBAT has switched your license to the \"Community Edition\", since your Trial license has now expired. You can continue to use BBAT as before, but some features are not available in the Community Edition.\nFor more information, please visit http://www.bbat.co/communityedition/");
//                      PreferenceUtil.setValue("com.littleeyelabs.eclipseide.littleeye.collectStatistics.2", true);
                    }
                  });
              }
            });
//            LicenseCheckerHourly.access$2();
          }
          catch (InterruptedException localInterruptedException)
          {
          }
          Display.getDefault().asyncExec(new Runnable()
          {
            public void run()
            {
//              this.val$view.updateLicenseText();
            }
          });
        }
      }
    };
    thread = new Thread(local1, "LicenseChecker");
    thread.start();
  }

  public static void stopLicenseCheckerThread()
  {
    if (executor.isShutdown())
      return;
    executeQueuedJobs();
    executor.shutdown();
    LicenseManager.returnLicense(LicenseManager.getCurrentLicense());
    LicenseManager.cleanupLockFiles();
    try
    {
      executor.awaitTermination(15L, TimeUnit.SECONDS);
    }
    catch (InterruptedException localInterruptedException)
    {
    	LOG.error( "Could not shutdown the executor threadpool in the license hourly checker cleanly.", localInterruptedException);
    }
    if ((thread != null) && (thread.isAlive()))
    {
      shouldExit = true;
      thread.interrupt();
    }
  }

  private static void addJob(Runnable paramRunnable)
  {
    jobsQueue.add(paramRunnable);
  }

  public static void queueStartProfileLog()
  {
    String str = new SimpleDateFormat(LicenseManager.getLogDateFormat(), Locale.ROOT).format(new Date());
    addJob(new Runnable()
    {
      public void run()
      {
//        LicenseManager.logStartProfile(LicenseCheckerHourly.this);
      }
    });
  }
}

