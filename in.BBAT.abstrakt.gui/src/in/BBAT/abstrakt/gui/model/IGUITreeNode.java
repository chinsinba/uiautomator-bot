package in.BBAT.abstrakt.gui.model;

import java.util.List;

import org.eclipse.swt.graphics.Image;
/**
 * 
 * @author syed mehtab
 *
 */
public interface IGUITreeNode {

	String getLabel();
	Image getImage();
	List<IGUITreeNode> getChildren();
	IGUITreeNode getParent();
}
