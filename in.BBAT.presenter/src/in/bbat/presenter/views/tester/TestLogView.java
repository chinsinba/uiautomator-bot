package in.bbat.presenter.views.tester;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import in.bbat.presenter.views.BBATViewPart;

public class TestLogView extends BBATViewPart {

	TableViewer viewer ;
	@Override
	public void refresh() throws Exception {

	}

	@Override
	public ISelection getSelectedElements() {
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent,SWT.H_SCROLL|SWT.V_SCROLL);
	}

	@Override
	public void setFocus() {

	}

}
