package in.bbat.presenter.views.tester;

import in.BBAT.presenter.DND.listeners.TestRunDropListener;
import in.BBAT.presenter.labelProviders.DeviceViewLabelProvider;
import in.BBAT.presenter.labelProviders.TestRunnerLableProvider;
import in.bbat.presenter.internal.TestRunExecutionManager;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

public class TestRunnerView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.tester.TestRunnerView";
	private TableViewer viewer;
	private TableColumnLayout tableLayout;
	private TableViewer testDeviceViewer;

	public TestRunnerView() {
	}

	@Override
	public void refresh() throws Exception {
		viewer.refresh();
		testDeviceViewer.refresh();
	}

	@Override
	public ISelection getSelectedElements() {
		return viewer.getSelection();
	}

	@Override
	public void createPartControl(Composite parent) {

		Composite outer = new Composite( parent, SWT.BORDER );

		FormLayout formLayout = new FormLayout();
		formLayout.marginHeight = 5;
		formLayout.marginWidth = 5;
		formLayout.spacing = 5;
		outer.setLayout( formLayout );

		Composite innerLeft = new Composite( outer, SWT.BORDER );
		innerLeft.setLayout( new GridLayout() );

		FormData fData = new FormData();
		fData.top = new FormAttachment( 0 );
		fData.left = new FormAttachment( 0 );
		fData.right = new FormAttachment(12  ); // Locks on 10% of the view
		fData.bottom = new FormAttachment( 100 );
		innerLeft.setLayoutData( fData );
		innerLeft.setLayout(new FillLayout());

		createDeviceViewer(innerLeft);

		Composite innerRight = new Composite( outer, SWT.BORDER );
		innerRight.setLayout( new FillLayout() );

		fData = new FormData();
		fData.top = new FormAttachment( 0 );
		fData.left = new FormAttachment( innerLeft );
		fData.right = new FormAttachment( 100 );
		fData.bottom = new FormAttachment( 100 );
		innerRight.setLayoutData( fData );



		viewer = new TableViewer(innerRight, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		createColumns(innerRight, viewer);
		viewer.setLabelProvider(new TestRunnerLableProvider());
		try {
			viewer.setInput(TestRunExecutionManager.getInstance().getTestRunCases());
		} catch (Exception e) {
			e.printStackTrace();
		}

		getViewSite().setSelectionProvider(viewer);
		addMenuManager(viewer);
		createDropSupport();
	}

	private void createDeviceViewer(Composite innerLeft) {

		testDeviceViewer = new TableViewer(innerLeft, SWT.H_SCROLL| SWT.V_SCROLL);
		testDeviceViewer.setContentProvider(new ArrayContentProvider());
		testDeviceViewer.setLabelProvider(new DeviceViewLabelProvider());
		// Provide the input to the ContentProvider
		testDeviceViewer.setInput(TestRunExecutionManager.getInstance().getSelectedDevices());

		createDropSupportForDevice();

	}


	private void createDropSupportForDevice() {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
		testDeviceViewer.addDropSupport(operations, transferTypes,new TestRunDropListener(testDeviceViewer));

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
