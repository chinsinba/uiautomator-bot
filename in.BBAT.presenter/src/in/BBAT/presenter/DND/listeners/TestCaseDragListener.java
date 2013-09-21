package in.BBAT.presenter.DND.listeners;

import java.util.List;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

public class TestCaseDragListener implements DragSourceListener{

	private TreeViewer viewer;

	public TestCaseDragListener(TreeViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		LocalSelectionTransfer.getTransfer().setSelection(viewer.getSelection());
		List<IStructuredSelection> selList = ((IStructuredSelection) viewer.getSelection()).toList();

		event.doit = canBeDragged(event, selList);
	}

	private boolean canBeDragged(DragSourceEvent event,
			List<IStructuredSelection> selectedObjects) {
		if(!selectedObjects.isEmpty())
		{
			Object sample = selectedObjects.get(0);
			for (Object object : selectedObjects) {
				if(!(sample.getClass() == object.getClass()))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection(); 
		if (selection!= null) {
			event.data =  selection.getFirstElement();
		}
	}

	@Override
	public void dragFinished(DragSourceEvent event) {

	}

}
