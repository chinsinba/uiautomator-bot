package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class TestCaseLabelProvider extends LabelProvider {

	private static final Logger LOG = BBATLogger.getLogger(TestCaseLabelProvider.class.getName());
	@Override
	public String getText(Object element) {
		if(element instanceof IGUITreeNode)
			return ((IGUITreeNode) element).getLabel();
		return element.toString();
	}



	public Image getImage(Object obj) {

		if(obj instanceof AbstractProjectTree)
			return ((AbstractProjectTree) obj).getImage();
		return null;
	}
}