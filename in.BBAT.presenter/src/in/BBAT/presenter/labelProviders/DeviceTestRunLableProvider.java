package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.TestRunCaseModel;
import in.bbat.presenter.internal.DeviceTestRun;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class DeviceTestRunLableProvider extends LabelProvider implements
ITableLabelProvider {


	public DeviceTestRunLableProvider() {
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
		if(element  instanceof DeviceTestRun){
			switch (columnIndex) {
			case 0:
				return ((DeviceTestRun) element).getImage();
			}
		}
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if(element instanceof TestRunCaseModel){
			switch (columnIndex) {
			case 0:
				return ((TestRunCaseModel) element).getTestcase().getLabel();
			case 1:
				return ((TestRunCaseModel) element).getTestcase().getParent().getParent().getLabel();
			case 2:
				return ((TestRunCaseModel) element).getTestcase().getParent().getLabel();
			case 3:
				return "";


			default:
				break;
			}
		}
		if(element  instanceof DeviceTestRun){
			switch (columnIndex) {
			case 0:
				return ((DeviceTestRun) element).getDevice().getLabel();
			case 1:
				return String.valueOf(((DeviceTestRun) element).getCases().size());
			case 2:
				return String.valueOf(((DeviceTestRun) element).noOfPassedCases());
			case 3:
				return String.valueOf(((DeviceTestRun) element).noOfFailedCases()+((DeviceTestRun) element).noOfErrorCases());
			case 4:
				return ((DeviceTestRun) element).getStatus().getStatus();
			default:
				break;
			}
		}
		return "";
	}

}
