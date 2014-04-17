package in.BBAT.presenter.history.handlers;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.history.ScreenShotHistoryView;
import in.BBAT.presenter.views.tester.ScreenShotView;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;

public class OpenScreenShotsHandler extends AbstractTestRunInfoHandler {

	private static final Logger LOG = BBATLogger.getLogger(OpenScreenShotsHandler.class.getName());

	@Override
	public Object run(ExecutionEvent event, final List<?> selectedObjects) {
		ScreenShotView.selectedTestRuncase((TestRunInstanceModel) selectedObjects.get(0));
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				try{
					BBATViewPart.hideView(ScreenShotHistoryView.ID);
					IViewPart scrShtView = BBATViewPart.openView(ScreenShotHistoryView.ID);
					if(scrShtView!= null){
						((ScreenShotHistoryView)scrShtView).setInput((TestRunInstanceModel)selectedObjects.get(0));
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
