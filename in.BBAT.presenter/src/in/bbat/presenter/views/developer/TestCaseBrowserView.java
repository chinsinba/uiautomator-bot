package in.bbat.presenter.views.developer;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.presenter.contentProviders.TestCaseBrowserContentProvider;
import in.BBAT.presenter.labelProviders.TestCaseLabelProvider;
import in.bbat.presenter.views.BBATViewPart;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class TestCaseBrowserView extends BBATViewPart {
	public static final String ID = "in.BBAT.presenter.developer.testcaseBrowserView";

	private TreeViewer viewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
			return new Object[0];
		}
	}



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
}