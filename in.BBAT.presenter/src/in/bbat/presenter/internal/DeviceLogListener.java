package in.bbat.presenter.internal;

import in.BBAT.TestRunner.device.ILogListener;
import in.BBAT.abstrakt.presenter.run.model.DeviceLog;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.dataMine.manager.MineManager;
import in.bbat.presenter.views.tester.TestLogView;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.logcat.LogCatMessage;

public class DeviceLogListener implements ILogListener {

	private TestRunInstanceModel testRunCase;
	private List<LogCatMessage> tempLogCatMessageList = new ArrayList<LogCatMessage>();
	
	
	public DeviceLogListener(TestRunInstanceModel runCaseObj) {
		this.testRunCase = runCaseObj;
	}

	@Override
	public void processLogLine(final List<LogCatMessage> logMessages) {
		MineManager.getInstance().beginTransaction();
		for(LogCatMessage message : logMessages){
			tempLogCatMessageList.add(message);
			DeviceLog log  = new DeviceLog(testRunCase,message);
			log.save();
		}
		MineManager.getInstance().commitTransaction();

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				IViewPart view =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestLogView.ID);
				if(view!= null)
					((TestLogView)view).getPanel().bufferChanged(logMessages, new ArrayList<LogCatMessage>());		
			}
		});

	}

	@Override
	public void startLogging(IDevice iDevice) {
	}

	
}
