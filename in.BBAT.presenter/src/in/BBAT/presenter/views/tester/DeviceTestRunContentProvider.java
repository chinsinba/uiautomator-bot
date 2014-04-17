package in.BBAT.presenter.views.tester;

import in.BBAT.presenter.internal.DeviceTestRun;
import in.BBAT.presenter.internal.TestRunExecutionManager;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class DeviceTestRunContentProvider implements ITreeContentProvider {

	private static final Logger LOG = BBATLogger.getLogger(DeviceTestRunContentProvider.class.getName());
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {

		if(parentElement instanceof TestRunExecutionManager){
			return ((TestRunExecutionManager) parentElement).getSelectedDevices().toArray();
		}
		if(parentElement instanceof DeviceTestRun )
		{
			return ((DeviceTestRun) parentElement).getCases().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof DeviceTestRun)
			return ((DeviceTestRun) element).hasChildren();
		return false;
	}

}
