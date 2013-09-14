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
	List<? extends IGUITreeNode> getChildren() throws Exception;
	IGUITreeNode getParent();
	void addChild(IGUITreeNode childNode);
}
