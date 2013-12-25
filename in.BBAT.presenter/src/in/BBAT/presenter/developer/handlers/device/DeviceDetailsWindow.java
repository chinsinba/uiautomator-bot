package in.BBAT.presenter.developer.handlers.device;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;

public class DeviceDetailsWindow extends ApplicationWindow {

	private Set<Entry<String, String>> propertySet ;
	public DeviceDetailsWindow(Shell parentShell, Set<Entry<String, String>> deviceProperties) {
		super(parentShell);
		this.propertySet = deviceProperties;
	}

	@Override
	protected Control createContents(Composite parent) {
		configureShell(parent);
		Composite comp = new Composite(parent, SWT.BORDER);

		TableViewer executionViewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		executionViewer.setContentProvider(new ArrayContentProvider());
		executionViewer.getTable().setLinesVisible(true);
		executionViewer.getTable().setHeaderVisible(true);
		createRunColumns(comp, executionViewer);
		executionViewer.setInput(propertySet);
		executionViewer.setLabelProvider(new PropertyLabelProvider());
		return comp;
	}


	public void createRunColumns(final Composite parent, final TableViewer viewer) {

		TableColumnLayout lay = new TableColumnLayout();
		parent.setLayout(lay);

		TableColumn col = new TableColumn(viewer.getTable(), SWT.LEFT);
		col.setText("Properties");
		lay.setColumnData(col, new ColumnWeightData(40));

		col = new TableColumn(viewer.getTable(), SWT.LEFT);
		col.setText("Values");
		parent.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(60));

	}


	private void configureShell(Composite parent) {

		getShell().setText("Device Details");
		getShell().setMaximized(true);
		parent.setSize(600,600);
	}

	class PropertyLabelProvider  extends LabelProvider implements ITableLabelProvider
	{

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {

			Entry<String, String> entry = (Map.Entry<String, String>)element;
			switch(columnIndex){
			case 0:
				return entry.getKey();
			case 1:
				return entry.getValue();
			}
			return null;
		}

	}
}
