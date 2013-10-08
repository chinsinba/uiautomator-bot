package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.run.model.TestRunCase.TestStatus;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.testrunner.ITestRunListener;
import com.android.ddmlib.testrunner.TestIdentifier;

public class TestCaseExecutionListener implements ITestRunListener {

	private TestRunInstanceModel runCase;
	private DeviceTestRun deviceRun;
	private TestStatus status = TestStatus.PASS;

	public TestCaseExecutionListener(TestRunInstanceModel testRunCase, DeviceTestRun deviceRun){
		this.runCase = testRunCase;
		this.deviceRun = deviceRun;
	}

	@Override
	public void testEnded(TestIdentifier arg0, Map<String, String> arg1) {
	}

	@Override
	public void testFailed(TestFailure arg0, TestIdentifier arg1, String arg2) {
		status=TestStatus.FAIL;
	}

	@Override
	public synchronized void testRunEnded(long timeTaken, Map<String, String> arg1) {
		runCase.setTimeTaken(timeTaken);
		runCase.setStatus(status.getStatus());
		runCase.update();
		refresh();
	}

	@Override
	public void testRunFailed(String arg0) {
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
					e.printStackTrace();
				}		

				deviceRun.refresh();
			}
		});
	}
}