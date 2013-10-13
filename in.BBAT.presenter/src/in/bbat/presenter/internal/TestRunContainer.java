package in.bbat.presenter.internal;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.device.model.IDeviceModelChangeListener;
import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.abstrakt.presenter.run.model.TestRunCase;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.tester.TestRunnerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class TestRunContainer {

	private List<TestRunCase> testRunCases = new ArrayList<TestRunCase>();
	private Set<DeviceTestRun> deviceTestRuns = new HashSet<DeviceTestRun>();

	public TestRunContainer() {
		TestDeviceManager.getInstance().addDeviceModelChangeListener(new DeviceModelListener());
	}

	public List<TestRunCase> getTestRunCases(){
		return testRunCases;
	}

	public void addTestRunCase(TestRunCase runCase){
		testRunCases.add(runCase);	
	}

	public void removeTestRunCase(TestRunCase runCase){
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
			run.clear();
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
						e.printStackTrace();
					}					
				}
			});

		}
	}
}
