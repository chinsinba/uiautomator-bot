package in.BBAT.presenter.contentProviders;

import in.BBAT.abstrakt.presenter.run.model.TestRunManager;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

import javax.lang.model.element.Element;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestRunHistoryContentProvider implements ITreeContentProvider {

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
			return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof TestRunManager){
			try {
				return ((TestRunManager) parentElement).getTestRuns().toArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(parentElement instanceof TestRunModel){
			try {
				return ((TestRunModel) parentElement).getChildren().toArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		// TODO Auto-generated method stub
		return true;
	}

}
