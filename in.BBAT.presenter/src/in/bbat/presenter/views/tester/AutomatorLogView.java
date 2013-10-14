package in.bbat.presenter.views.tester;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestStatus;
import in.BBAT.presenter.labelProviders.AutoLogLabelProvider;
import in.bbat.presenter.views.BBATViewPart;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class AutomatorLogView extends BBATViewPart {

	private TableViewer viewer;

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
	}

	private void createTextFilter(Composite parent) {
		Composite comp1 = new Composite(parent, SWT.BORDER);
		comp1.setLayout(new GridLayout(1,false));
		comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Text text = new Text(comp1, SWT.BORDER);
		text.setMessage("Search for messages");
		text.setLayoutData(new GridData(GridData.FILL_BOTH));

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
		if(model.getStatus().equalsIgnoreCase(TestStatus.EXECUTING.getStatus()))
			viewer.setInput(model.getAutoLogs());
		else
			viewer.setInput(model.getAutoLogsFromDB());

	}

	@Override
	public void setFocus() {
	}

}
