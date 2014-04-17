package in.BBAT.presenter.perstpectives;

import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.developer.TestCaseBrowserView;
import in.BBAT.presenter.views.history.TestRunHistoryView;
import in.BBAT.presenter.views.tester.TestRunnerView;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class BBATPerspectiveListener  implements IPerspectiveListener{

	private static final Logger LOG = BBATLogger.getLogger(BBATPerspectiveListener.class.getName());
	@Override
	public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		
		if(perspective.getId().equalsIgnoreCase(HistoryPerspective.ID)){
			BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunHistoryView.ID);
			try {
				view.refresh();
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		
		if(perspective.getId().equalsIgnoreCase(TesterPerspective.ID)){
			BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestRunnerView.ID);
			try {
				view.refresh();
			} catch (Exception e) {
				LOG.error(e);
			}
			
			BBATViewPart view1 = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(TestCaseBrowserView.ID);
			try {
				view1.refresh();
			} catch (Exception e) {
				LOG.error(e);
			}
			
		}

	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page,	IPerspectiveDescriptor perspective, String changeId) {

	}


}
