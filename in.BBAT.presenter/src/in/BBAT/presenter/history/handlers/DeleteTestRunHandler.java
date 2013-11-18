package in.BBAT.presenter.history.handlers;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.presenter.developer.handlers.BBATHandler;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;
import in.bbat.presenter.views.history.TestRunHistoryView;

public class DeleteTestRunHandler extends AbstractTestRunBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(DeleteTestRunHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		LOG.info("Delete test run ");
		if(!MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Delete","Do you want to delete ?"))
			return null;
		try {
			for(Object pkgObj :selectedObjects){
				((TestRunModel)pkgObj).delete();
			}
		} catch (Exception e) {
			LOG.error(e);
		}

		BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunHistoryView.ID);
		try {
			view.refresh();
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	@Override
	public boolean isEnabled(List<?> selectedObjects) {

		if(!selectedObjects.isEmpty())
		{
			Object sample = selectedObjects.get(0);
			for (Object object : selectedObjects) {
				if(!(sample.getClass() == object.getClass()))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}



}
