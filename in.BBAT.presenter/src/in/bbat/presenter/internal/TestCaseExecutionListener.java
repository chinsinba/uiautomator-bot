package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.io.File;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.RawImage;
import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;

public class TestCaseExecutionListener implements ITestRunListener {

	private static final Logger LOG = BBATLogger.getLogger(TestCaseExecutionListener.class.getName());
	private TestRunInstanceModel runCase;
	private DeviceTestRun deviceRun;
	private TestStatus status = TestStatus.PASS;

	public TestCaseExecutionListener(TestRunInstanceModel testRunCase, DeviceTestRun deviceRun){
		this.runCase = testRunCase;
		this.deviceRun = deviceRun;
		refresh();
	}

	@Override
	public void testEnded(TestIdentifier arg0, Map<String, String> arg1) {
	}

	@Override	
	public void testFailed(TestFailure arg0, TestIdentifier arg1, String arg2) {
		if(arg0.ordinal() == ITestRunListener.TestFailure.FAILURE.ordinal())
			status=TestStatus.FAIL;
		if(arg0.ordinal() == ITestRunListener.TestFailure.ERROR.ordinal())
			status=TestStatus.ERROR;

		processScreenshot(deviceRun.getDevice().getiDevice().getScreenshot(),status);
	}

	@Override
	public synchronized void testRunEnded(long timeTaken, Map<String, String> arg1) {
		runCase.setTimeTaken(timeTaken);
		runCase.setStatus(status.getStatus());
		runCase.update();
		processScreenshot(deviceRun.getDevice().getiDevice().getScreenshot(),status);
		refresh();

	}

	@Override
	public void testRunFailed(String arg0) {
		status = TestStatus.ERROR;
		processScreenshot(deviceRun.getDevice().getiDevice().getScreenshot(),status);
	}

	@Override
	public synchronized void testRunStarted(String arg0, int arg1) {
		runCase.setStatus(TestStatus.EXECUTING.getStatus());
		runCase.update();
		refresh();
	}

	@Override
	public void testRunStopped(long arg0) {
	}

	@Override
	public void testStarted(TestIdentifier arg0) {
	}

	public void refresh(){
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				TestRunnerView view  = (TestRunnerView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
				try {
					view.refresh();
				} catch (Exception e) {
					LOG.error(e);
				}		

			}
		});
	}

	public void processScreenshot(RawImage rawImage,TestStatus status) {

		if(rawImage==null){
			return;
		}
		PaletteData palette = new PaletteData(rawImage.getRedMask(),rawImage.getGreenMask(),rawImage.getBlueMask());
		ImageData imageData = new ImageData(rawImage.width, rawImage.height,rawImage.bpp, palette, 1, rawImage.data);
		runCase.saveScreenShot(imageData, status.getStatus());
	}
}
