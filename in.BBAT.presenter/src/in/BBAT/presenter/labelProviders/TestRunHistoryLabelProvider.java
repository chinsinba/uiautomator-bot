package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestRunHistoryLabelProvider extends LabelProvider {

	@Override
	public Image getImage(Object element) {

		return super.getImage(element);
	}
	@Override
	public String getText(Object element) {
		if(element instanceof TestRunModel){
			return	((TestRunModel) element).getStartTime().toString();
		}

		if(element instanceof TestDeviceRunModel)
		{
			return ((TestDeviceRunModel) element).getDeviceName();
		}
		return super.getText(element);
	}
}
