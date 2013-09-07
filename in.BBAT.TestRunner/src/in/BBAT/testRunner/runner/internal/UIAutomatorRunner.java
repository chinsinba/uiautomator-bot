package in.BBAT.testRunner.runner.internal;

import in.BBAT.testRunner.runner.RunnerResultParser;

import java.io.IOException;
import java.util.Collection;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.IShellEnabledDevice;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.RemoteAndroidTestRunner;

public class UIAutomatorRunner extends RemoteAndroidTestRunner {

	IShellEnabledDevice testDevice ;
	private RunnerResultParser deviceResultReciever;
	public UIAutomatorRunner(String packageName, IShellEnabledDevice remoteDevice) {
		super(packageName, remoteDevice);
		this.testDevice=remoteDevice;
	}
	
	@Override
	public void run(Collection<ITestRunListener> listeners) throws TimeoutException,
			AdbCommandRejectedException, ShellCommandUnresponsiveException,
			IOException {

        final String runCaseCommandStr = String.format("uiautomator runtest");
        String runName = getRunnerName() == null ? getPackageName() : getRunnerName();
        deviceResultReciever = new RunnerResultParser(runName, listeners);

        try {
            testDevice.executeShellCommand(runCaseCommandStr, deviceResultReciever, 0);
        } catch (IOException e) {
            // rely on parser to communicate results to listeners
            deviceResultReciever.handleTestRunFailed(e.toString());
            throw e;
        } catch (ShellCommandUnresponsiveException e) {
            deviceResultReciever.handleTestRunFailed(String.format(
                    "Failed to receive adb shell test output within %1$d ms. " +
                    "Test may have timed out, or adb connection to device became unresponsive",0));
            throw e;
        } catch (TimeoutException e) {
            deviceResultReciever.handleTestRunFailed(e.toString());
            throw e;
        } catch (AdbCommandRejectedException e) {
            deviceResultReciever.handleTestRunFailed(e.toString());
            throw e;
        }
	}
}
