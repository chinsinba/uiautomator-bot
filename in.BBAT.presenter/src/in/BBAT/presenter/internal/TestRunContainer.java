package in.BBAT.presenter.internal;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.device.model.IDeviceModelChangeListener;
import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.tester.TestRunnerView;
import in.bbat.logger.BBATLogger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class TestRunContainer {

	private static final Logger LOG = BBATLogger.getLogger(TestRunContainer.class.getName());
	private List<TestRunCaseModel> testRunCases = new ArrayList<TestRunCaseModel>();
	private Set<DeviceTestRun> deviceTestRuns = new HashSet<DeviceTestRun>();

	public TestRunContainer() {
//		TestDeviceManager.getInstance().addDeviceModelChangeListener(new DeviceModelListener());
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

	public void deviceRemoved(AndroidDevice device) {
		DeviceTestRun run = getDeviceRun(device);
		if(run!=null){
			run.stop();
			removeDeviceRun(run);
		}
	}

	/*private class DeviceModelListener implements IDeviceModelChangeListener
	{
		@Override
		public void deviceAdded(AndroidDevice device) {

		}

		@Override
		public void deviceRemoved(AndroidDevice device) {
			removeDeviceRun(device);
			TestRunnerView.refreshView();
			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Device Removed", "Device disconnected.");					
				}
			});


		}

		@Override
		public void refresh(AndroidDevice device) {

		}
	}*/
}
