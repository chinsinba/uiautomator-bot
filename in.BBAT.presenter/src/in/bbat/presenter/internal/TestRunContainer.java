package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.device.model.IDeviceModelChangeListener;
import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class TestRunContainer {

	private static final Logger LOG = BBATLogger.getLogger(TestRunContainer.class.getName());
	private List<TestRunCaseModel> testRunCases = new ArrayList<TestRunCaseModel>();
	private Set<DeviceTestRun> deviceTestRuns = new HashSet<DeviceTestRun>();

	public TestRunContainer() {
		TestDeviceManager.getInstance().addDeviceModelChangeListener(new DeviceModelListener());
	}

	public List<TestRunCaseModel> getTestRunCases(){
		return testRunCases;
	}

	public void addTestRunCase(TestRunCaseModel runCase){
		testRunCases.add(runCase);	
	}

	public void removeTestRunCase(TestRunCaseModel runCase){
		testRunCases.remove(runCase);
	}

	public void clearTestRunCases(){
		testRunCases.clear();
	}

	public Set<DeviceTestRun> getdeviceTestRuns() {
		return deviceTestRuns;
	}

	public void addTestDevice(DeviceTestRun deviceTestRun) {
		deviceTestRuns.add(deviceTestRun);
	}

	public void clearDeviceRuns(){
		for(DeviceTestRun run : deviceTestRuns){
//			run.clear();
		}
		deviceTestRuns.clear();
	}

	public void removeDeviceRun(DeviceTestRun selectedTestDevice){
		deviceTestRuns.remove(selectedTestDevice);
	}

	public DeviceTestRun getDeviceRun(AndroidDevice device){
		for(DeviceTestRun devRun : deviceTestRuns){
			if(devRun.getDevice().equals(device)){
				return devRun;		
			}
		}
		return null;
	}

	public void stopRuns(){
		for(DeviceTestRun devRun : deviceTestRuns){
			devRun.stop();
		}
	}

	public void removeDeviceRun(AndroidDevice device) {
		DeviceTestRun run = getDeviceRun(device);
		if(run!=null){
			removeDeviceRun(run);
		}
	}

	private class DeviceModelListener implements IDeviceModelChangeListener
	{
		@Override
		public void deviceAdded(AndroidDevice device) {

		}

		@Override
		public void deviceRemoved(AndroidDevice device) {
			removeDeviceRun(device);
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					BBATViewPart testRunView = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
					try {
						testRunView.refresh();
					} catch (Exception e) {
						LOG.error(e);
					}					
				}
			});

		}
	}
}
