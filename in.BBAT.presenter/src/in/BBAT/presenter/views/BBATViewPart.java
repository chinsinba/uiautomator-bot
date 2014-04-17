package in.BBAT.presenter.views;


import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public abstract class BBATViewPart extends ViewPart {

	private static final Logger LOG = BBATLogger.getLogger(BBATViewPart.class.getName());
	public void addMenuManager(Viewer viewer){

		final MenuManager menuManager = new MenuManager();
		getSite().registerContextMenu(menuManager, viewer);
		menuManager.setRemoveAllWhenShown(true);
		viewer.getControl().setMenu(menuManager.createContextMenu(viewer.getControl()));

	}

	protected void createDragSupport(){

	}

	protected void createDropSupport(){

	}

	abstract public void refresh() throws Exception;

	abstract public ISelection getSelectedElements();

	public static void hideView(String viewId){
		IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(viewId);
		if(view !=null)
		{
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(view);
		}
	}

	public static IViewPart openView(String viewId) throws PartInitException{
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewId);
	}
}
