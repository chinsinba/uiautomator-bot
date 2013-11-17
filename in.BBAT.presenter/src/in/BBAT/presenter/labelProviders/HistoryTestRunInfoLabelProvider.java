package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class HistoryTestRunInfoLabelProvider extends LabelProvider implements
ITableLabelProvider {

	private static final Logger LOG = BBATLogger.getLogger(HistoryTestRunInfoLabelProvider.class.getName());
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if(element instanceof TestRunInstanceModel){
			switch (columnIndex) {
			case 0:
				return  ((TestRunInstanceModel) element).getImage();
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof TestRunInstanceModel){
			switch (columnIndex) {
			case 0:
				return ((TestRunInstanceModel) element).getTestCaseEntity().getName();
			case 1:
				return ((TestRunInstanceModel) element).getTestCaseEntity().getSuite().getName();
			case 2:
				return ((TestRunInstanceModel) element).getTestCaseEntity().getSuite().getTestProject().getName();
			case 3:
				return ((TestRunInstanceModel) element).getStatus();
			case 4:
				return String.valueOf(((TestRunInstanceModel) element).getTimeTaken()/1000f);			default:
					break;
			}
		}
		return "";
	}

}
