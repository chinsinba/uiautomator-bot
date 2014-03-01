package in.bbat.presenter.internal;


import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.testRunner.runner.BuildJarException;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;


public class TestRunExecutor{

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
				UiAutoTestCaseJar jar = null;
				try {
					jar = new UiAutoTestCaseJar(getTestScriptPaths(),getTargetId());
				} catch (BuildJarException e) {
					endOfTestRun();
					return Status.CANCEL_STATUS;
				}
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

	private String getTargetId(){
		Set<Integer> apiLevels = new HashSet<Integer>();
		for (DeviceTestRun testRunCase : deviceTestRuns) {
			apiLevels.add(testRunCase.getMaxApiLevel());
		}
		Integer max = Collections.max(apiLevels);
		return "android-"+max;
	}

	public List<String> getTestScriptPaths() {
		Set<String> testScriptPaths = new HashSet<String>();
		for (DeviceTestRun testRunCase : deviceTestRuns) {
			testScriptPaths.addAll(testRunCase.getDistinctScriptPaths());
		}
		return new ArrayList<String>(testScriptPaths);
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
				endOfTestRun();
				return;
			}
			TestRunnerView.refreshView();
		}
	}

	private void endOfTestRun() {
		testRun.setEndTime(new Timestamp(System.currentTimeMillis()));
		testRun.update();
		TestRunExecutionManager.getInstance().setExecuting(false);
		TestRunnerView.refreshView();
	}

}
