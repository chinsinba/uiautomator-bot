package in.BBAT.TestRunner.device;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

import in.BBAT.TestRunner.Listener.IMemoryUsageListener;
import in.bbat.logger.BBATLogger;

public class MemoryUsageThread implements Runnable {

	private TestDevice device;
	private IMemoryUsageListener listener;
	private boolean stop = false;
	private static final Logger LOG = BBATLogger.getLogger(MemoryUsageThread.class.getName());

	public MemoryUsageThread(TestDevice device, IMemoryUsageListener listener) {
		this.device = device;
		this.listener = listener;
	}

	@Override
	public void run() {
		while(!stop){
			final String pack = listener!=null ?listener.getPackageName():"";
			final String cmd ="dumpsys meminfo ";
			try {
				device.getMonkeyDevice().executeShellCommand(cmd, new MultiLineReceiver() {
					@Override
					public boolean isCancelled() {
						return false;
					}

					@Override
					public void processNewLines(String[] arg0) {
						for (String line : arg0) {
							if(line.contains(pack))
							{
								String val = line.substring(0,line.indexOf("kB"));
								listener.memoryUsage(Integer.parseInt(val.trim()), System.currentTimeMillis());
							}	
						}

					}
				},0);
			} catch (TimeoutException e) {
				LOG.error(e);
			} catch (AdbCommandRejectedException e) {
				LOG.error(e);
			} catch (ShellCommandUnresponsiveException e) {
				LOG.error(e);
			} catch (IOException e) {
				LOG.error(e);
			}
			try {
				Thread.sleep(1200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop(){
		stop =true;
	}
}
