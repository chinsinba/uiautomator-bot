package in.BBAT.TestRunner.device;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.NullOutputReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;

import in.BBAT.TestRunner.Listener.ICpuUsageListener;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.logger.BBATLogger;

public class CpuUsageThread implements Runnable {


	private TestDevice device;
	private ICpuUsageListener listener;
	
	private static final Logger LOG = BBATLogger.getLogger(CpuUsageThread.class.getName());

	public CpuUsageThread(TestDevice device, ICpuUsageListener listener) {
		this.device = device;
		this.listener = listener;
	}

	@Override
	public void run() {

		final String cmd ="dumsys cpuinfo";
		try {
			device.getMonkeyDevice().executeShellCommand(cmd, new MultiLineReceiver() {
				@Override
				public boolean isCancelled() {
					return false;
				}

				@Override
				public void processNewLines(String[] arg0) {

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
	}

	public void stop(){

	}

}
