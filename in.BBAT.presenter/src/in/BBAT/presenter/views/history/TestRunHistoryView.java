package in.BBAT.presenter.views.history;

import in.BBAT.abstrakt.gui.BBATImageManager;
import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.presenter.contentProviders.TestRunHistoryContentProvider;
import in.BBAT.presenter.labelProviders.TestRunHistoryLabelProvider;
import in.BBAT.presenter.views.BBATViewPart;
import in.BBAT.presenter.views.tester.MemoryCPUUsageView;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;

public class TestRunHistoryView extends BBATViewPart {

	public static final String ID="in.BBAT.presenter.history.TestRunHistoryView";
	private static final Logger LOG = BBATLogger.getLogger(TestRunHistoryView.class.getName());
	private TreeViewer viewer;
	private Text searchText;
	private TestRunFilter filter;
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
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		createTextFilter(parent);

		createTestRunTree(parent);

		filter = new TestRunFilter();
		viewer.addFilter(filter);
		addMenuManager(viewer);

	}


	private void createTextFilter(Composite parent) {
		Composite comp1 = new Composite(parent, SWT.NONE);
		comp1.setLayout(new GridLayout(1,false));
		comp1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		searchText = new Text(comp1, SWT.BORDER);
		searchText.setMessage("Search for Test runs");
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

	}

	private void createTestRunTree(Composite parent) {
		Composite comp1 = new Composite(parent, SWT.BORDER);
		comp1.setLayout(new GridLayout());
		comp1.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer = new TreeViewer(comp1, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL|SWT.FULL_SELECTION);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		viewer.setContentProvider(new TestRunHistoryContentProvider());
		viewer.setLabelProvider(new TestRunHistoryLabelProvider());
		viewer.setAutoExpandLevel(1);
		createDeviceRunColumns(comp1);
		// Provide the input to the ContentProvider
		try {
			viewer.setInput(TestRunManager.getInstance());
		} catch (Exception e) {
			LOG.error(e);
		}
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object sel = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(sel instanceof TestRunModel){

				}
				if(sel instanceof TestDeviceRunModel){

					BBATViewPart.hideView(MemoryCPUUsageView.ID);
					BBATViewPart.hideView(HistoryDeviceLogView.ID);
					BBATViewPart.hideView(HistoryAutoLogView.ID);
					BBATViewPart.hideView(ScreenShotHistoryView.ID);
					try {
						TestRunInfoView view  = (TestRunInfoView) BBATViewPart.openView(TestRunInfoView.ID);
						view.setInput(((TestDeviceRunModel) sel));
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		});
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IContributionItem[] items = getViewSite().getActionBars().getToolBarManager().getItems();
				for(IContributionItem item : items){
					if(item instanceof CommandContributionItem){
						item.update();
					}
				}
			}
		});
	}
	private void createDeviceRunColumns(Composite comp) {
		TreeColumnLayout lay = new TreeColumnLayout();
		comp.setLayout(lay);

		TreeColumn col = new TreeColumn(viewer.getTree(), SWT.LEFT);
		col.setText("TestRun");
		col.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.ANDROID_DEVICE));
		lay.setColumnData(col, new ColumnWeightData(40));


		col = new TreeColumn(viewer.getTree(), SWT.LEFT);
		col.setText("Date");
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(20));

		col = new TreeColumn(viewer.getTree(), SWT.LEFT);
		col.setText("Status");
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(20));

		col = new TreeColumn(viewer.getTree(), SWT.LEFT);
		col.setText("Time");
		col.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.TIME));
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(20));

		/*col = new TreeColumn(viewer.getTree(), SWT.LEFT);
		col.setText("FAIL");
		col.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.FAIL));
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(12));

		col = new TreeColumn(viewer.getTree(), SWT.LEFT);
		col.setText("Status");
		comp.setLayout(lay);
		lay.setColumnData(col, new ColumnWeightData(28));*/
	}
	@Override
	public void setFocus() {
		//		viewer.refresh();

	}


	class TestRunFilter extends ViewerFilter
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

			if(element instanceof TestRunModel){

				if((((TestRunModel) element).getLabel()).toLowerCase().matches(searchString)){
					return true;
				}
			}
			if(element instanceof TestDeviceRunModel)
			{
				return true;
			}
			return false;
		}

	}

	public static void refreshView(){
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				BBATViewPart view = (BBATViewPart) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(ID);
				try {
					view.refresh();
				} catch (Exception e) {
					LOG.error(e);
				}

			}
		});
	}
}
