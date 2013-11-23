package in.bbat.presenter.views.developer;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.presenter.DND.listeners.TestCaseDragListener;
import in.BBAT.presenter.contentProviders.TestCaseBrowserContentProvider;
import in.BBAT.presenter.labelProviders.TestCaseLabelProvider;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;

import org.apache.log4j.Logger;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.pshelf.PShelf;
import org.eclipse.nebula.widgets.pshelf.PShelfItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class TestCaseBrowserView extends BBATViewPart {
	public static final String ID = "in.BBAT.presenter.developer.testcaseBrowserView";
	private static final Logger LOG = BBATLogger.getLogger(TestCaseBrowserView.class.getName());
	
	private TreeViewer viewer;

	private PShelf testCaseShelf;

	private PShelfItem automatorShelfItem;

	private PShelfItem monkeyShelfItem;

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		testCaseShelf = new PShelf(parent, SWT.NONE);
		automatorShelfItem = new PShelfItem(testCaseShelf, SWT.NONE);
		automatorShelfItem.setText("UIAutomator");
		automatorShelfItem.getBody().setLayout(new FillLayout());	
		viewer = new TreeViewer(automatorShelfItem.getBody(), SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new TestCaseBrowserContentProvider());
		viewer.setLabelProvider(new TestCaseLabelProvider());
		viewer.setAutoExpandLevel(2);
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(sel instanceof TestCaseModel){
					try {
						((TestCaseModel) sel).openEditor();
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		});
		
		// Provide the input to the ContentProvider
		try {
			viewer.setInput(TestProjectManager.getInstance().getTestProjects());
		} catch (Exception e) {
			LOG.error(e);
		}
		addMenuManager(viewer);
		createDragSupport();
		
		
		{
			monkeyShelfItem = new PShelfItem(testCaseShelf, SWT.NONE);
			monkeyShelfItem.setText("MonkeyRunner");
			monkeyShelfItem.getBody().setLayout(new FillLayout());	
		}
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