package in.bbat.presenter.views.tester;

import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.BBAT.presenter.DND.listeners.TestRunDropListener;
import in.BBAT.presenter.labelProviders.TestRunnerLableProvider;
import in.bbat.presenter.views.BBATViewPart;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

public class TestRunnerView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.tester.TestRunnerView";
	private TableViewer viewer;
	private TableColumnLayout tableLayout;

	public TestRunnerView() {
	}

	@Override
	public void refresh() throws Exception {
		viewer.refresh();
	}

	@Override
	public ISelection getSelectedElements() {
		return viewer.getSelection();
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		tableLayout = new TableColumnLayout();
		parent.setLayout(tableLayout);
		createColumns(parent, viewer);
		viewer.setLabelProvider(new TestRunnerLableProvider());
		// Provide the input to the ContentProvider
		try {
			viewer.setInput(TestRunManager.getInstance().getTestRunCases());
		} catch (Exception e) {
			e.printStackTrace();
		}
		addMenuManager(viewer);
		createDropSupport();
	}


	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Project","TestSuite","TestCase","Status" };
		int[] bounds = { 17, 20, 23, 35, 15 };

		TableViewerColumn col =createTableViewerColumn(viewer,null, titles[0],bounds[0]);

		int width = col.getColumn().getWidth();
		tableLayout= new TableColumnLayout();
		tableLayout.setColumnData(col.getColumn(), new ColumnWeightData(bounds[0],width));

		TableViewerColumn col1 = createTableViewerColumn(viewer, null, titles[1],bounds[1]);
		tableLayout= new TableColumnLayout();
		width = col1.getColumn().getWidth();
		tableLayout.setColumnData(col1.getColumn(), new ColumnWeightData(bounds[1],width));

		TableViewerColumn col2 = createTableViewerColumn(viewer, null, titles[2],bounds[2]);
		tableLayout= new TableColumnLayout();
		width = col2.getColumn().getWidth();
		tableLayout.setColumnData(col2.getColumn(), new ColumnWeightData(bounds[2],width));

		TableViewerColumn col3 = createTableViewerColumn(viewer, null, titles[3],bounds[3]);
		tableLayout= new TableColumnLayout();
		width = col3.getColumn().getWidth();
		tableLayout.setColumnData(col3.getColumn(), new ColumnWeightData(bounds[3],width));

	}
	@Override
	protected void createDropSupport() {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
		viewer.addDropSupport(operations, transferTypes,new TestRunDropListener(viewer));
	}


	private TableViewerColumn createTableViewerColumn(TableViewer viewer,
			Image imag, String titles, int bounds) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(titles);
		column.setToolTipText(titles);
		column.setImage(imag);
		column.setWidth(bounds);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
	@Override
	public void setFocus() {

	}

}
