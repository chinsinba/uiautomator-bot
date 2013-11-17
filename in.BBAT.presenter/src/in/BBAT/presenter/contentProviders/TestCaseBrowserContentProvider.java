package in.BBAT.presenter.contentProviders;

import in.BBAT.abstrakt.gui.model.IGUITreeNode;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TestCaseBrowserContentProvider implements ITreeContentProvider {

	private static final Logger LOG = BBATLogger.getLogger(TestCaseBrowserContentProvider.class.getName());
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return getChildren(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof IGUITreeNode){
			try {
				return ((IGUITreeNode) parentElement).getChildren().toArray();
			} catch (Exception e) {
				LOG.error(e);
			}
		}

		List<IGUITreeNode> children = (List<IGUITreeNode>) parentElement;
		if (children != null)
			return children.toArray();
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object parentElement) {
		if(parentElement instanceof IGUITreeNode){
			try {
				if (((IGUITreeNode) parentElement).getChildren() == null)
					return false;
			} catch (Exception e) {
				LOG.error(e);
			}
		}		return true;
	}

}
