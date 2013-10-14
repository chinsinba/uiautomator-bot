package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestRunnerLableProvider extends LabelProvider implements
ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof TestCaseModel){
			switch (columnIndex) {
			case 2:
				return ((TestCaseModel) element).getParent().getParent().getLabel();
			case 1:
				return ((TestCaseModel) element).getParent().getLabel();
			case 0:
				return ((TestCaseModel) element).getLabel();
			
			default:
				break;
			}
		}
		return "";
	}

}
