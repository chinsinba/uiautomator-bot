package in.bbat.presenter.views.tester;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import in.BBAT.presenter.labelProviders.TestRunnerLableProvider;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.BBATViewPart;

public class AutomatorLogView extends BBATViewPart {

	private TableViewer viewer;

	@Override
	public void refresh() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public ISelection getSelectedElements() {
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		createColumns(parent, viewer);
		viewer.setLabelProvider(new TestRunnerLableProvider());
		try {
			viewer.setInput(TestRunExecutionManager.getInstance().getTestRunCases());
		} catch (Exception e) {
			e.printStackTrace();
		}

		getViewSite().setSelectionProvider(viewer);

	}
	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Message" };

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableColumn col =new TableColumn(viewer.getTable(), SWT.None);
		col.setText(titles[0]);
		layout.setColumnData(col, new ColumnWeightData(100));

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
