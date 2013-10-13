package in.bbat.presenter.views.tester;

import in.BBAT.abstrakt.presenter.device.model.AndroidDevice;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.presenter.DND.listeners.TestRunDropListener;
import in.BBAT.presenter.labelProviders.DeviceViewLabelProvider;
import in.BBAT.presenter.labelProviders.TestRunnerLableProvider;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.presenter.internal.DeviceTestRun;
import in.bbat.presenter.internal.TestRunExecutionManager;
import in.bbat.presenter.views.BBATViewPart;

import java.util.List;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

public class TestRunnerView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.tester.TestRunnerView";
	private TableViewer viewer;
	private TableViewer testDeviceViewer;
	public static CTabFolder testRunFolder;
	private CTabItem testRunItem;

	public TestRunnerView() {
	}

	@Override
	public void refresh() throws Exception {
		viewer.refresh();
		if(testDeviceViewer!=null)
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
		/*formLayout.marginHeight = 5;
		formLayout.marginWidth = 5;
		formLayout.spacing = 5;*/
		outer.setLayout( formLayout );

		Composite innerLeft = new Composite( outer, SWT.NONE );
		//		innerLeft.setLayout( new GridLayout() );

		FormData fData = new FormData();
		fData.top = new FormAttachment( 0 );
		fData.left = new FormAttachment( 0 );
		fData.right = new FormAttachment(20  ); // Locks on 10% of the view
		fData.bottom = new FormAttachment( 100 );
		innerLeft.setLayoutData( fData );
		innerLeft.setLayout(new FillLayout());

		createDeviceViewer(innerLeft);

		Composite innerRight = new Composite( outer, SWT.NONE );
		fData = new FormData();
		fData.top = new FormAttachment( 0 );
		fData.left = new FormAttachment( innerLeft );
		fData.right = new FormAttachment( 100 );
		fData.bottom = new FormAttachment( 100 );
		innerRight.setLayoutData( fData );
		innerRight.setLayout(new FillLayout());

		createTestRunViewer(innerRight);
		createDropSupport();
	}

	private void createTestRunViewer(Composite innerRight) {
		testRunFolder = new CTabFolder(innerRight, SWT.TOP);
		testRunItem = new CTabItem(testRunFolder, SWT.None);
		testRunItem.setText("Test Run");
		Composite comp = new Composite(testRunFolder, SWT.None);

		

		viewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		createColumns(comp, viewer);
		viewer.setLabelProvider(new TestRunnerLableProvider());
		try {
			viewer.setInput(TestRunExecutionManager.getInstance().getTestRunCases());
		} catch (Exception e) {
			e.printStackTrace();
		}

		getViewSite().setSelectionProvider(viewer);
		addMenuManager(viewer);
		testRunItem.setControl(comp);
		testRunFolder.setSelection(0);
	}

	public void createTestDeviceTab(AndroidDevice testDevice, List<TestRunInstanceModel> input){
		CTabItem testRunItem = new CTabItem(testRunFolder, SWT.None);
		testRunItem.setText("Test Devices");
		Composite comp = new Composite(testRunFolder, SWT.None);
		Button button = new Button(comp, SWT.PUSH);
		button.setText("Hello");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});


		testRunItem.setControl(comp);

	}

	private void createDeviceViewer(Composite innerLeft) {

		CTabFolder tabFolder = new CTabFolder(innerLeft, SWT.TOP);
		CTabItem testRunItem = new CTabItem(tabFolder, SWT.None);
		testRunItem.setText("Test Devices");
		Composite comp = new Composite(tabFolder, SWT.None);

		testDeviceViewer = new TableViewer(comp, SWT.H_SCROLL| SWT.V_SCROLL);
		testDeviceViewer.getTable().setHeaderVisible(true);
		testDeviceViewer.getTable().setLinesVisible(true);
		testDeviceViewer.setContentProvider(new ArrayContentProvider());
		testDeviceViewer.setLabelProvider(new DeviceViewLabelProvider());
		testDeviceViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection  =(IStructuredSelection) event.getSelection();
				DeviceTestRun run = (DeviceTestRun) selection.getFirstElement();
				if(run !=null)
					run.focus();
			}
		});
		TableColumn col = new TableColumn(testDeviceViewer.getTable(), SWT.None);
		col.setText("Devices");
		TableColumnLayout lay = new TableColumnLayout();
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(50));
		col = new TableColumn(testDeviceViewer.getTable(), SWT.None);
		col.setText("Status");
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(50));
		// Provide the input to the ContentProvider
		testDeviceViewer.setInput(TestRunExecutionManager.getInstance().getSelectedDevices());
		testRunItem.setControl(comp);
		tabFolder.setSelection(0);
		createDropSupportForDevice();

	}


	private void createDropSupportForDevice() {
		int operations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transferTypes = new Transfer[] { LocalSelectionTransfer.getTransfer() };
		testDeviceViewer.addDropSupport(operations, transferTypes,new TestRunDropListener(testDeviceViewer));

	}

	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "TestCase","TestSuite","Project" };
		int[] bounds = { 25,25,25,25 };

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableViewerColumn col =createTableViewerColumn(viewer,BBATImageManager.getInstance().getImage(BBATImageManager.TESTCASE_GIF_16), titles[0],bounds[0]);
		layout.setColumnData(col.getColumn(), new ColumnWeightData(bounds[0]));

		TableViewerColumn col1 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.TESTSUITE_GIF_16), titles[1],bounds[1]);
		layout.setColumnData(col1.getColumn(), new ColumnWeightData(bounds[1]));

		TableViewerColumn col2 = createTableViewerColumn(viewer, BBATImageManager.getInstance().getImage(BBATImageManager.PROJECT_GIF_16), titles[2],bounds[2]);
		layout.setColumnData(col2.getColumn(), new ColumnWeightData(bounds[2]));

		/*	TableViewerColumn col3 = createTableViewerColumn(viewer, null, titles[3],bounds[3]);
		layout.setColumnData(col3.getColumn(), new ColumnWeightData(bounds[3]));*/

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
