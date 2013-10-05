package in.bbat.presenter.internal;

import in.BBAT.TestRunner.device.ILogListener;
import in.BBAT.abstrakt.presenter.run.model.DeviceLog;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.dataMine.manager.MineManager;
import in.bbat.presenter.views.tester.TestLogView;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.android.ddmlib.logcat.LogCatMessage;

public class DeviceLogListener implements ILogListener {

	private TestRunInstanceModel testRunCase;

	public DeviceLogListener(TestRunInstanceModel runCaseObj) {
		this.testRunCase = runCaseObj;
	}

	@Override
	public void processLogLine(final List<LogCatMessage> logMessages) {
		MineManager.getInstance().beginTransaction();
		for(LogCatMessage message : logMessages){
			DeviceLog log  = new DeviceLog(testRunCase,message);
			log.save();
		}
		MineManager.getInstance().commitTransaction();

		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				try {
					TestLogView view = (TestLogView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TestLogView.ID);
					view.getPanel().bufferChanged(logMessages, new ArrayList<LogCatMessage>());		
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});




	}

}
