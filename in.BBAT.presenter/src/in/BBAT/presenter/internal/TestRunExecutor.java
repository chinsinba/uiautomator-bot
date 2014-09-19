package in.BBAT.presenter.internal;


import in.BBAT.TestRunner.runner.BuildJarException;
import in.BBAT.TestRunner.runner.UiAutoTestCaseJar;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.presenter.views.tester.TestRunnerView;
import in.bbat.logger.BBATLogger;

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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;


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
					LOG.error(e);
					showError();
					return Status.CANCEL_STATUS;
				} catch (Exception e) {
					endOfTestRun();
					LOG.error(e);
					showError();
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

			private void showError() {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Build Error", "Failed to build Uiautomator test cases.\n" +
								"For more information do following steps:\n 1. open terminal/command prompt.\n 2. cd "+UiAutoTestCaseJar.TEMP_FOLDER_PATH+
								"\n 3. ant build\n" +
								" 4. The build errors will be listed on the terminal.");						
					}
				});
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

	public List<String> getTestScriptPaths() throws Exception {
		Set<String> testScriptPaths = new HashSet<String>();
		for (DeviceTestRun testRunCase : deviceTestRuns) {
			testScriptPaths.addAll(testRunCase.getDistinctScriptPaths());
			testScriptPaths.addAll(testRunCase.getDistinctLibraryClassPaths());
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
