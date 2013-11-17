package in.bbat.presenter.views.history;

import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;
import in.BBAT.presenter.contentProviders.TestRunHistoryContentProvider;
import in.BBAT.presenter.labelProviders.TestRunHistoryLabelProvider;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.logger.BBATLogger;
import in.bbat.presenter.views.BBATViewPart;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;

public class TestRunHistoryView extends BBATViewPart {

	public static final String ID="in.BBAT.presenter.history.TestRunHistoryView";
	private static final Logger LOG = BBATLogger.getLogger(TestRunHistoryView.class.getName());
	private TreeViewer viewer;
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
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		viewer.setContentProvider(new TestRunHistoryContentProvider());
		viewer.setLabelProvider(new TestRunHistoryLabelProvider());
		viewer.setAutoExpandLevel(1);
		createDeviceRunColumns(parent);
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

					
					BBATViewPart.hideView(HistoryDeviceLogView.ID);
					BBATViewPart.hideView(HistoryAutoLogView.ID);
					try {
						TestRunInfoView view  = (TestRunInfoView) BBATViewPart.openView(TestRunInfoView.ID);
						view.setInput(((TestDeviceRunModel) sel));
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		});

		addMenuManager(viewer);

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
		col.setText("Time(s)");
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

}
