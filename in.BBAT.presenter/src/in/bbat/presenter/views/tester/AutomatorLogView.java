package in.bbat.presenter.views.tester;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import in.BBAT.abstrakt.presenter.run.model.AutomatorLogModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.BBAT.presenter.labelProviders.AutoLogLabelProvider;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import com.android.ddmlib.logcat.LogCatMessage;
import com.android.ddmuilib.ImageLoader;

public class AutomatorLogView extends BBATViewPart {

	private TableViewer viewer;
	private static final Logger LOG = BBATLogger.getLogger(AutomatorLogView.class.getName());

	private AutoLogFilter filter;

	private Text searchText;
	private TestRunInstanceModel selectedModel;
	private String mLogFileExportFolder;

	private static final String IMAGE_SAVE_LOG_TO_FILE = "save.png";

	public static final String ID= "in.BBAT.presenter.tester.AutoLogView";
	@Override
	public void refresh() throws Exception {
		viewer.refresh();
	}

	@Override
	public ISelection getSelectedElements() {
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		createTextFilter(parent);

		createLogTable(parent);

		getViewSite().setSelectionProvider(viewer);

	}

	private void createLogTable(Composite parent) {

		Composite comp2 = new Composite(parent, SWT.BORDER);
		comp2.setLayout(new GridLayout());
		comp2.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer = new TableViewer(comp2, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setContentProvider(new ArrayContentProvider());
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);
		createColumns(comp2, viewer);
		viewer.setLabelProvider(new AutoLogLabelProvider());
		viewer.getTable().addMouseMoveListener(getMouseMoveListener());

		filter = new AutoLogFilter();
		viewer.addFilter(filter);
	}

	private void createTextFilter(Composite parent) {
		Composite comp1 = new Composite(parent, SWT.BORDER);
		comp1.setLayout(new GridLayout(2,false));
		comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		searchText = new Text(comp1, SWT.BORDER);
		searchText.setMessage("Search for messages");
		searchText.setLayoutData(new GridData(GridData.FILL_BOTH));
		searchText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				filter.setSearchText(searchText.getText());
				try {
					refresh();
				} catch (Exception e1) {
					LOG.error(e1);
				}
			}
		});

		ToolBar toolBar = new ToolBar(comp1, SWT.FLAT);

		ToolItem saveToLog = new ToolItem(toolBar, SWT.PUSH);
		saveToLog.setImage(ImageLoader.getDdmUiLibLoader().loadImage(IMAGE_SAVE_LOG_TO_FILE,toolBar.getDisplay()));
		saveToLog.setToolTipText("Export Selected Items To Text File..");
		saveToLog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveLogToFile();
			}
		});

	}

	/**
	 * Save logcat messages selected in the table to a file.
	 */
	private void saveLogToFile() {
		/* show dialog box and get target file name */
		final String fName = getLogFileTargetLocation();
		if (fName == null) {
			return;
		}

		try {
			selectedModel.exportUIAutoLogs(fName);
		} catch (IOException e) {
			LOG.error(e);
		}

	}

	private String getLogFileTargetLocation() {
		DirectoryDialog fd = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);

		fd.setText("Save Log..");
		//		fd.setFileName("log.txt");

		if (mLogFileExportFolder == null) {
			mLogFileExportFolder = System.getProperty("user.home");
		}
		fd.setFilterPath(mLogFileExportFolder);
		/*
		fd.setFilterNames(new String[] {
				"Text Files (*.txt)"
		});
		fd.setFilterExtensions(new String[] {
				"*.txt"
		});*/

		String fName = fd.open();
		if (fName != null) {
			mLogFileExportFolder = fd.getFilterPath();  /* save path to restore on future calls */
		}

		return fName;

	}


	public void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "Message" };

		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);

		TableColumn col =new TableColumn(viewer.getTable(), SWT.None);
		col.setText(titles[0]);
		layout.setColumnData(col, new ColumnWeightData(100,true));

	}


	MouseMoveListener getMouseMoveListener() {
		MouseMoveListener listener = new MouseMoveListener() {

			@Override
			public void mouseMove(MouseEvent event) {
				ViewerCell cell = viewer.getCell(new Point(event.x, event.y));
				if (cell != null)
					viewer.getTable().setToolTipText(cell.getText());
			}
		};
		return listener;
	}

	public void setInput(TestRunInstanceModel model){
		//		if(model.getStatus().equalsIgnoreCase(TestStatus.EXECUTING.getStatus()))
		/*viewer.setInput(model.getAutoLogs());
		else*/
		selectedModel = model;
		viewer.setInput(model.getAutoLogsFromDB());
		setPartName("UiAutomator Logs: "+model.getTestCaseEntity().getName());


	}

	@Override
	public void setFocus() {
	}



	class AutoLogFilter extends ViewerFilter
	{

		private String searchString ="";

		public void setSearchText(String s)
		{
			this.searchString = ".*" + s.trim().toLowerCase() + ".*";
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement,Object element) {
			if(searchString==null || searchString.isEmpty())
				return true;

			if(element instanceof AutomatorLogModel){

				return ((AutomatorLogModel) element).getMessage().toLowerCase().matches(searchString);
			}
			return false;
		}

	}

}
