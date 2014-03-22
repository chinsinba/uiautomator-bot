package in.bbat.presenter.internal;

import in.BBAT.TestRunner.Listener.ICpuUsageListener;
import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.BBAT.testRunner.runner.TestRunner;
import in.BBAT.testRunner.runner.UiAutoTestCaseJar;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.android.uiautomator.MonkeyRecorderFrame;

public class DeviceTestRun {

	private static final Logger LOG = BBATLogger.getLogger(DeviceTestRun.class.getName());
	private AndroidDevice device;
	private List<TestRunCaseModel> testCases = new ArrayList<TestRunCaseModel>();
	private ArrayList<TestRunInstanceModel> testRunInstances = new ArrayList<TestRunInstanceModel>();
	private TestStatus status = TestStatus.NOTEXECUTED;
	private boolean stopped = false;
	private List<IDeviceRunExecutionlistener> listener = new ArrayList<IDeviceRunExecutionlistener>();
	private TestDeviceRunModel testDeviceRun;

	public DeviceTestRun(final AndroidDevice device,List<TestRunCaseModel> testCases) {
		this.setDevice(device);
		this.testCases.addAll(testCases);
		/*Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				MonkeyRecorderFrame.start(device.getiDevice().getChimpDevice());				
			}
		});*/

	}

	public AndroidDevice getDevice() {
		return device;
	}

	public void setDevice(AndroidDevice device) {
		this.device = device;
	}

	public List<TestRunInstanceModel> getRunInstances() {
		return testRunInstances;
	}

	private void createRunInstances() {
		for(TestRunCaseModel caseObj:getTestRunCases()){
			TestRunInstanceModel runInstModel = new TestRunInstanceModel(testDeviceRun,caseObj.getTestcase(),TestStatus.NOTEXECUTED.getStatus());
			runInstModel.save();
			testRunInstances.add(runInstModel);
		}
	}

	public List<TestRunCaseModel> getTestRunCases() {
		return testCases;
	}

	public List<String> getTestScriptPaths() {
		List<String> testScriptPaths = new ArrayList<String>();
		for (TestRunCaseModel testRunCase : testCases) {
			if(!testScriptPaths.contains(testRunCase.getTestcase().getTestScriptPath()))
				testScriptPaths.add(testRunCase.getTestcase().getTestScriptPath());
		}
		return testScriptPaths;
	}

	public void execute(final UiAutoTestCaseJar jar,final TestRunModel testRun) {

		Job testRunJob = new Job("Execution on device") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				preExecute(testRun);
				monitor.beginTask("Executing "+testDeviceRun.getDeviceName(),getRunInstances().size()+2);
				monitor.worked(1);
				TestRunner runner = new TestRunner(jar,getDevice().getiDevice());
				for (TestRunInstanceModel testRunCase : getRunInstances()) {
					if(isStopped()){
						break;
					}
					monitor.worked(1);
					testRunCase.setStartTime(System.currentTimeMillis());
					testRunCase.setStatus(TestStatus.EXECUTING.getStatus());
					testRunCase.update();
					DeviceLogListener deviceLogListener = new DeviceLogListener(testRunCase);
					MemoryCpuUsageListener memCpuListener = new MemoryCpuUsageListener(testRunCase, getDevice());
					runner.execute(testRunCase.getCompleteScriptName(), new TestCaseExecutionListener(testRunCase, DeviceTestRun.this), deviceLogListener,new UIAutomatorOutputListener(testRunCase),deviceLogListener,memCpuListener,memCpuListener);
					getDevice().pullScreenShotsFromDevice(testRunCase.getScreenShotDir(),testRunCase.getTestCaseModel().getName(),true);
					testRunCase.setEndTime(System.currentTimeMillis());
					testRunCase.update();

				}
				testDeviceRun.setEndTime(System.currentTimeMillis());
				testDeviceRun.update();
				removeTestJar();
				testRun.setEndTime(new Timestamp(System.currentTimeMillis()));
				testRun.update();
				if(stopped)
					updateStatus(TestStatus.ERROR);
				else
					updateStatus(TestStatus.EXECUTED);
				monitor.done(); 
				return Status.OK_STATUS;
			}
		};
		testRunJob.schedule();
		testRunJob.addJobChangeListener(new IJobChangeListener() {
			public void sleeping(IJobChangeEvent event) {}
			public void scheduled(IJobChangeEvent event) {}
			public void running(IJobChangeEvent event) {}
			@Override
			public void done(IJobChangeEvent event) {
				for(IDeviceRunExecutionlistener l :listener){
					l.deviceRunExecutionCompleted(DeviceTestRun.this);
				}
			}
			public void awake(IJobChangeEvent event) {}
			public void aboutToRun(IJobChangeEvent event) {}
		});
	}

	private void preExecute(final TestRunModel testRun) {
		reset();
		testDeviceRun = new TestDeviceRunModel(testRun, getDevice());
		testDeviceRun.save();
		createRunInstances();
		for(IDeviceRunExecutionlistener l :listener){
			l.deviceRunExecutionStarted(DeviceTestRun.this);
		}
		setStopped(false);
		updateStatus(TestStatus.EXECUTING);
		testDeviceRun.setStartTime(System.currentTimeMillis());
		testDeviceRun.update();
	}

	private void reset() {
		testRunInstances.clear();
	}


	private void updateStatus(TestStatus status) {
		setStatus(status);
		testDeviceRun.setStatus(getStatus());
		testDeviceRun.update();
		refrestTestRunnerView();		
	}

	private void refrestTestRunnerView() {
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				IViewPart autoLogView =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
				if(autoLogView!= null){
					try {
						((TestRunnerView)autoLogView).refresh();
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		});
	}


	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DeviceTestRun){
			((DeviceTestRun) obj).getDevice().equals(getDevice());
			return true;
		}
		return false ;
	}

	@Override
	public int hashCode() {
		return getDevice().hashCode();
	}


	public TestStatus getStatus() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public void stop(){
		setStopped(true);
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public void addListener(IDeviceRunExecutionlistener listener) {
		this.listener.add(listener);		
	}

	public void addTestCases(List<TestRunCaseModel> testCases){
		this.testCases.addAll(testCases);
	}

	public List<TestRunCaseModel> getCases(){
		return testCases;
	}

	public boolean hasChildren(){
		return !testCases.isEmpty();
	}

	public void removeCase(TestRunCaseModel runCase) {
		testCases.remove(runCase);		
	}

	public int noOfPassedCases(){
		return getCount(TestStatus.PASS.getStatus());

	}

	private int getCount(String status) {
		int count =0;
		for(TestRunInstanceModel model : getRunInstances())
		{
			if(model.getStatus().equalsIgnoreCase(status))
				count++;
		}
		return count;
	}

	public int noOfFailedCases(){
		return getCount(TestStatus.FAIL.getStatus());

	}

	public int noOfErrorCases(){
		return getCount(TestStatus.ERROR.getStatus());
	}

	public int noOfExecutedCases(){
		return noOfErrorCases()+noOfFailedCases()+noOfPassedCases();
	}

	public void removeTestJar(){
		getDevice().removeTestJar();
	}

	public  List<String> getDistinctLibraryClassPaths() throws Exception{
		Set<TestProjectModel> testProjs = new HashSet<TestProjectModel>();
		for(TestRunCaseModel cas: getCases()){
			testProjs.add(cas.getProject());	
		}

		List<String> testScriptPaths = new ArrayList<String>();
		for(TestProjectModel proj: testProjs){
			testScriptPaths.addAll(proj.getLibraryScriptpaths());
		}

		return testScriptPaths;
	}

	public List<String> getDistinctScriptPaths(){
		Set<String> testScriptPaths = new HashSet<String>();

		for(TestRunCaseModel cas: getCases()){
			testScriptPaths.add(cas.getTestcase().getTestScriptPath());
		}
		return new ArrayList<String>(testScriptPaths);
	}

	public Image getImage() {
		if(getStatus().equals(TestStatus.EXECUTING))
			return BBATImageManager.getInstance().getImage(BBATImageManager.EXECUTING);

		if(getStatus().equals(TestStatus.EXECUTED))
			return BBATImageManager.getInstance().getImage(BBATImageManager.PASS);

		if(getStatus().equals(TestStatus.ERROR))
			return BBATImageManager.getInstance().getImage(BBATImageManager.ERROR);
		return null;
	}


	public int getMaxApiLevel()
	{
		int apiLevel =16;
		for(TestRunCaseModel cas: getCases())
		{
			int tempLevel = ((TestProjectModel)cas.getTestcase().getParent().getParent()).getApiLevel();
			if( apiLevel < tempLevel){
				apiLevel =tempLevel;
			}
		}
		return apiLevel;
	}
}
