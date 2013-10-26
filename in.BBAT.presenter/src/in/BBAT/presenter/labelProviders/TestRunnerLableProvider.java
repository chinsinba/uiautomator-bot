package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestRunnerLableProvider extends LabelProvider implements
ITableLabelProvider {

	public TestRunnerLableProvider() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if(element instanceof TestRunCaseModel){
			switch (columnIndex) {
			case 0: 
				return ((TestRunCaseModel) element).getTestcase().getImage();
			}
			}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof TestRunCaseModel){
			switch (columnIndex) {
			case 2:
				return ((TestRunCaseModel) element).getTestcase().getParent().getParent().getLabel();
			case 1:
				return ((TestRunCaseModel) element).getTestcase().getParent().getLabel();
			case 0:
				return ((TestRunCaseModel) element).getTestcase().getLabel();
			
			default:
				break;
			}
		}
		return "";
	}

}
