package in.BBAT.presenter.labelProviders;

import in.BBAT.abstrakt.gui.model.IGUITreeNode;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public class ViewLabelProvider extends LabelProvider implements
ITableLabelProvider {
	public String getColumnText(Object obj, int index) {
		if(obj instanceof IGUITreeNode)
			return ((IGUITreeNode) obj).getLabel();
		return obj.toString();
	}

	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}

	public Image getImage(Object obj) {
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_ELEMENT);
	}
}