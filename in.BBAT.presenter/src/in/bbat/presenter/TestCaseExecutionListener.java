package in.bbat.presenter;

import in.BBAT.abstrakt.presenter.run.model.TestRunCase.TestStatus;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.bbat.presenter.internal.DeviceTestRun;
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
		System.out.println("Test Ended "+arg1);
	}

	@Override
	public void testFailed(TestFailure arg0, TestIdentifier arg1, String arg2) {
		System.out.println("Test Failled "+arg2);
		status=TestStatus.FAIL;
	}

	@Override
	public void testRunEnded(long arg0, Map<String, String> arg1) {
		System.out.println("Run Ended "+arg1);
		runCase.setStatus(status.getStatus());
		refresh();
	}

	@Override
	public void testRunFailed(String arg0) {
		System.out.println("RUn Failled "+arg0);
	}

	@Override
	public void testRunStarted(String arg0, int arg1) {
		System.out.println("Run Started "+ arg1);
		runCase.setStatus(TestStatus.EXECUTING.getStatus());
		refresh();
	}

	@Override
	public void testRunStopped(long arg0) {
		System.out.println("Run stopped "+ arg0);
	}

	@Override
	public void testStarted(TestIdentifier arg0) {
		System.out.println("Test started "+ arg0);
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
