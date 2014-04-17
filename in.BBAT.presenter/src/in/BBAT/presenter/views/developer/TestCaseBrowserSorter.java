package in.BBAT.presenter.views.developer;

import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class TestCaseBrowserSorter extends ViewerSorter {

	@Override
	public int compare(Viewer viewer, Object suite1, Object suite2) {
		if(suite1 instanceof TestSuiteModel & suite2 instanceof TestSuiteModel){
			if(((TestSuiteModel)suite1).isHelper()){
				return 1;
			}
		}
		return 0;
	}

}
