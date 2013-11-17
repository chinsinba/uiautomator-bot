package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.presenter.run.model.AutomatorLogModel;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class AutoLogLabelProvider extends LabelProvider implements ITableLabelProvider {

	private static final Logger LOG = BBATLogger.getLogger(AutoLogLabelProvider.class.getName());
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return ((AutomatorLogModel)element).getMessage();
		}
		return null;
	}
}
