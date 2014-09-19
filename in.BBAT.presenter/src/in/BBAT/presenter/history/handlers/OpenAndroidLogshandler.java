package in.BBAT.presenter.history.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.history.HistoryDeviceLogView;
import in.BBAT.presenter.views.tester.TestLogView;
import in.bbat.logger.BBATLogger;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.widgets.Display;

import com.android.ddmlib.logcat.LogCatMessage;

public class OpenAndroidLogshandler extends AbstractTestRunInfoHandler {

	private static final Logger LOG = BBATLogger.getLogger(OpenAndroidLogshandler.class.getName());
	@Override
	public Object run(ExecutionEvent event,final List<?> selectedObjects) {
		LOG.info("OPen android logs");
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					BBATViewPart.hideView(HistoryDeviceLogView.ID);
					TestLogView view  = (TestLogView) BBATViewPart.openView(HistoryDeviceLogView.ID);
					view.setInput((TestRunInstanceModel)selectedObjects.get(0));
//					view.bufferChanged(((TestRunInstanceModel)selectedObjects.get(0)).getDeviceLogsFromDB(), new ArrayList<LogCatMessage>());
				} catch (Exception e) {
					LOG.error(e);
				}	
			}
		});
		return null;
	}

	@Override
	public boolean isEnabled(List<?> object) {
		if(!object.isEmpty())
		{
			if(object.size()==1)
			{
				if(object.get(0) instanceof TestRunInstanceModel)
				{
					return true;
				}
			}
		}
		return false;
	}

}
