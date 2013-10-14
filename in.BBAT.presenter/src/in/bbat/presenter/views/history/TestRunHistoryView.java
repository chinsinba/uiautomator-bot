package in.bbat.presenter.views.history;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import in.BBAT.abstrakt.presenter.device.model.TestDeviceManager;
import in.BBAT.abstrakt.presenter.pkg.model.TestProjectManager;
import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.presenter.contentProviders.TestCaseBrowserContentProvider;
import in.BBAT.presenter.contentProviders.TestRunHistoryContentProvider;
import in.BBAT.presenter.labelProviders.DeviceViewLabelProvider;
import in.BBAT.presenter.labelProviders.TestCaseLabelProvider;
import in.BBAT.presenter.labelProviders.TestRunHistoryLabelProvider;
import in.bbat.presenter.views.BBATViewPart;

public class TestRunHistoryView extends BBATViewPart {

	public static final String ID="in.BBAT.presenter.history.TestRunHistoryView";
	private TreeViewer viewer;
	@Override
	public void refresh() throws Exception {

	}

	@Override
	public ISelection getSelectedElements() {
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.setContentProvider(new TestRunHistoryContentProvider());
		viewer.setLabelProvider(new TestRunHistoryLabelProvider());
		viewer.setAutoExpandLevel(1);
		// Provide the input to the ContentProvider
		try {
			viewer.setInput(TestRunManager.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(sel instanceof TestRunModel){
					
				}
				if(sel instanceof TestDeviceRunModel){
					try {
						TestRunInfoView view  = (TestRunInfoView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TestRunInfoView.ID);
						view.setInput(((TestDeviceRunModel) sel).getChildren());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		addMenuManager(viewer);}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
