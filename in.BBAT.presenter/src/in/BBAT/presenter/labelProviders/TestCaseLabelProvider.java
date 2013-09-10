package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.gui.model.IGUITreeNode;

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
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_ELEMENT);
	}
}