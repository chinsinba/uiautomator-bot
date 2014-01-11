package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestRunInfoLabelProvider extends LabelProvider implements
ITableLabelProvider{

	private static final Logger LOG = BBATLogger.getLogger(TestRunInfoLabelProvider.class.getName());
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if(element instanceof TestRunInstanceModel){
			switch (columnIndex) {
			case 0:
				return ((TestRunInstanceModel) element).getImage();
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof TestRunInstanceModel){
			switch (columnIndex) {
			case 0:
				return ((TestRunInstanceModel) element).getTestCaseModel().getName();
			case 1:
				return ((TestRunInstanceModel) element).getTestCaseModel().getParent().getName();
			case 2:
				return ((TestRunInstanceModel) element).getTestCaseModel().getParent().getParent().getLabel();
			case 3:
				return ((TestRunInstanceModel) element).getStatus();


			default:
				break;
			}
		}

		return null;
	}
	

}
