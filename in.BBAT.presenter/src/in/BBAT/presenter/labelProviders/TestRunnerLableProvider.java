package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.TestRunCase;

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
		if(element instanceof TestRunCase){
			switch (columnIndex) {
			case 0:
				return ((TestRunCase) element).getTestcase().getParent().getParent().getLabel();
			case 1:
				return ((TestRunCase) element).getTestcase().getParent().getLabel();
			case 2:
				return ((TestRunCase) element).getLabel();
			case 3:
				return ((TestRunCase) element).getTestcase().getParent().getParent().getLabel();

			default:
				break;
			}
		}
		return "";
	}

}
