package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class TestCaseLabelProvider extends LabelProvider {

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