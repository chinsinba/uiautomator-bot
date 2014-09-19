package in.BBAT.presenter.history.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.history.HistoryAutoLogView;
import in.BBAT.presenter.views.tester.AutomatorLogView;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;

public class OpenUIAutomatorLogsHandler  extends AbstractTestRunInfoHandler{

	private static final Logger LOG = BBATLogger.getLogger(OpenUIAutomatorLogsHandler.class.getName());

	@Override
	public Object run(ExecutionEvent event, final List<?> selectedObjects) {

		LOG.info("Open uiautomator logs");
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				try{
					BBATViewPart.hideView(HistoryAutoLogView.ID);
					IViewPart autoLogView =  BBATViewPart.openView(HistoryAutoLogView.ID);
					if(autoLogView!= null){
						((AutomatorLogView)autoLogView).setInput((TestRunInstanceModel)selectedObjects.get(0));
					}
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
