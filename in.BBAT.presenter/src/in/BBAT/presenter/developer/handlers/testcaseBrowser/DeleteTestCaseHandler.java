package in.BBAT.presenter.developer.handlers.testcaseBrowser;

import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.dataMine.manager.MineManager;
import in.bbat.presenter.views.BBATViewPart;
import in.bbat.presenter.views.developer.TestCaseBrowserView;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

public class DeleteTestCaseHandler extends AbstractTestCaseBrowserHandler {

	@Override
	public Object run(ExecutionEvent event, List<?> selectedObjects) {

		if(!MessageDialog.openQuestion(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Delete","Do you want to delete ?"))
			return null;

		MineManager.getInstance().beginTransaction();
		for(Object pkgObj :selectedObjects){
			try {
				((AbstractProjectTree)pkgObj).delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		MineManager.getInstance().commitTransaction();

		BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
		try {
			view.refresh();
		} catch (Exception e) {
			e.printStackTrace();
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
