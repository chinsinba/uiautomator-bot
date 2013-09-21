package in.bbat.presenter.views.developer;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.presenter.DND.listeners.TestCaseDragListener;
import in.BBAT.presenter.contentProviders.TestCaseBrowserContentProvider;
import in.BBAT.presenter.labelProviders.TestCaseLabelProvider;
import in.bbat.presenter.views.BBATViewPart;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

public class TestCaseBrowserView extends BBATViewPart {
	public static final String ID = "in.BBAT.presenter.developer.testcaseBrowserView";

	private TreeViewer viewer;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new TestCaseBrowserContentProvider());
		viewer.setLabelProvider(new TestCaseLabelProvider());
		// Provide the input to the ContentProvider
		try {
			viewer.setInput(TestProjectManager.getInstance().getTestProjects());
		} catch (Exception e) {
			e.printStackTrace();
		}
		addMenuManager(viewer);
		createDragSupport();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	@Override
	public void refresh() throws Exception {
		viewer.setInput(TestProjectManager.getInstance().getTestProjects());
		viewer.refresh();
	}

	@Override
	public ISelection getSelectedElements() {
		return viewer.getSelection();
	}
	
	protected void createDragSupport() 
	{
		int operations = DND.DROP_COPY| DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[]{LocalSelectionTransfer.getTransfer()};
		viewer.addDragSupport(operations, transferTypes, new TestCaseDragListener(viewer));
	}
}