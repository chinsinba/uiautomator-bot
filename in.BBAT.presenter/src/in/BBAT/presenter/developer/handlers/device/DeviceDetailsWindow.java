package in.BBAT.presenter.developer.handlers.device;

import in.BBAT.abstrakt.presenter.run.model.AutomatorLogModel;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class DeviceDetailsWindow extends ApplicationWindow {

	private Set<Entry<String, String>> propertySet ;
	private Text searchText;
	private DeviceDetailsFilter filter;
	private TableViewer devDetailsViewer;

	public DeviceDetailsWindow(Shell parentShell, Set<Entry<String, String>> deviceProperties) {
		super(parentShell);
		this.propertySet = deviceProperties;
	}

	@Override
	protected Control createContents(Composite parent) {
		configureShell(parent);
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		createTextFilter(parent);
		createDevDetailsTable(parent);

		return parent;
	}

	private void createDevDetailsTable(Composite parent) {
		Composite comp = new Composite(parent, SWT.BORDER);
		comp.setLayout(new FillLayout());
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		devDetailsViewer = new TableViewer(comp, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		devDetailsViewer.setContentProvider(new ArrayContentProvider());
		devDetailsViewer.getTable().setLinesVisible(true);
		devDetailsViewer.getTable().setHeaderVisible(true);
		createRunColumns(comp, devDetailsViewer);
		devDetailsViewer.setInput(propertySet);
		devDetailsViewer.setLabelProvider(new PropertyLabelProvider());
		filter = new DeviceDetailsFilter();
		devDetailsViewer.addFilter(filter);
		devDetailsViewer.getTable().setFocus();
	}

	private void createTextFilter(Composite parent) {
		Composite comp1 = new Composite(parent, SWT.NONE);
		comp1.setLayout(new FillLayout());
		comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		searchText = new Text(parent, SWT.BORDER);
		searchText.setMessage("Search device properties");
		searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				filter.setSearchText(searchText.getText());
				try {
					refresh();
				} catch (Exception e1) {
				}
			}
		});

	}

	public void refresh(){
		devDetailsViewer.refresh();
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
		parent.setSize(600,700);
	}


	class DeviceDetailsFilter extends ViewerFilter
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

			Entry<String, String> entry = (Map.Entry<String, String>)element;
			if(element instanceof Map.Entry<?,?>){

				if(String.valueOf(((Map.Entry) element).getKey()).toLowerCase().matches(searchString)||
						String.valueOf(((Map.Entry) element).getValue()).toLowerCase().matches(searchString)){
					return true;
				}
			}
			return false;
		}

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
