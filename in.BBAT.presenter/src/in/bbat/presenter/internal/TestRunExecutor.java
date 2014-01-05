package in.bbat.presenter.internal;


import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.PlatformUI;


public class TestRunExecutor {

	private static final Logger LOG = BBATLogger.getLogger(TestRunExecutor.class.getName());
	private Set<DeviceTestRun> deviceTestRuns;
	private TestRunModel testRun;

	public TestRunExecutor(Set<DeviceTestRun> deviceTestRuns) {
		this.deviceTestRuns = deviceTestRuns;
		testRun = new TestRunModel();
		testRun.setStartTime(new Timestamp(System.currentTimeMillis()));
		testRun.save();
	}

	public void run() {
		Job job = new Job("Test Run Execution") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Create Uiautomator jar", deviceTestRuns.size()+2);
				monitor.worked(1);
				UiAutoTestCaseJar jar = new UiAutoTestCaseJar(getTestScriptPaths());
				monitor.worked(1);
				
				DeviceRunListener listener = new DeviceRunListener(deviceTestRuns.size());
				for (DeviceTestRun deviceRun : deviceTestRuns) {
					monitor.worked(1);
					deviceRun.addListener(listener);
					deviceRun.execute(jar,testRun);
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		
	}

	public List<String> getTestScriptPaths() {
		List<String> testScriptPaths = new ArrayList<String>();
		for (DeviceTestRun testRunCase : deviceTestRuns) {
			testScriptPaths.addAll(testRunCase.getDistinctScriptPaths());
		}
		return testScriptPaths;
	}

	class DeviceRunListener implements IDeviceRunExecutionlistener
	{
		int executingDevRuns = 0;
		public DeviceRunListener(int execCount) {
			executingDevRuns =execCount;
		}

		@Override
		public void deviceRunExecutionStarted(DeviceTestRun deviceRun) {
		}

		@Override
		public void deviceRunExecutionCompleted(DeviceTestRun deviceRun) {
			executingDevRuns-=1;
			if(executingDevRuns ==0)
			{
				testRun.setEndTime(new Timestamp(System.currentTimeMillis()));
				testRun.update();
				TestRunExecutionManager.getInstance().setExecuting(false);
			}
			TestRunnerView.refreshView();
		}

	}
}
