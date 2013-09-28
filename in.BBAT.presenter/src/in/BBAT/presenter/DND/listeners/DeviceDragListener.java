package in.BBAT.presenter.DND.listeners;

import java.util.List;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

public class DeviceDragListener implements DragSourceListener{

	private Viewer viewer;

	public DeviceDragListener(TableViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void dragStart(DragSourceEvent event) {
		LocalSelectionTransfer.getTransfer().setSelection(viewer.getSelection());
		List<IStructuredSelection> selList = ((IStructuredSelection) viewer.getSelection()).toList();
		event.doit = true;
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
