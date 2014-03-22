package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class DeleteTestCaseHandler extends AbstractTestCaseBrowserHandler {

	private static final Logger LOG = BBATLogger.getLogger(DeleteTestCaseHandler.class.getName());
	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		LOG.info("Delete test case ");
		if(!MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Delete","Do you want to delete ?"))
			return null;
		try {
			for(Object pkgObj :selectedObjects){
				List<TestRunModel> refrencedTestRuns = ((AbstractProjectTree)pkgObj).getRefrencedTestRuns();
				if(!refrencedTestRuns.isEmpty()){
					StringBuilder runs = new StringBuilder();
					for (TestRunModel testRunModel : refrencedTestRuns) {
						runs.append(testRunModel.getName()+"\n");
					}
					MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
							"Referenced Test Runs", ((AbstractProjectTree)pkgObj).getName() + " is part of the test runs  \n"+runs.toString() +
							"Please delete the test runs first.");
					continue;
				}
				((AbstractProjectTree)pkgObj).delete();
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
		try {
			view.refresh();
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	@Override
	public boolean isEnabled(List<?> selectedObjects) {

		if(TestRunExecutionManager.getInstance().isExecuting())
			return false;
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
