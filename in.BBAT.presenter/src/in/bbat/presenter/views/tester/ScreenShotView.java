package in.bbat.presenter.views.tester;

import java.io.File;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.bbat.abstrakt.gui.BBATImageManager;
import in.bbat.presenter.views.BBATViewPart;

public class ScreenShotView extends BBATViewPart {

	public static final String ID = "in.BBAT.presenter.tester.screenShots";
	private TestRunInstanceModel runCase;
	private Label imageLabel;
	@Override
	public void refresh() throws Exception {

	}

	@Override
	public ISelection getSelectedElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout();
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
		imageLabel = new Label(parent, SWT.BORDER);
		imageLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	private void createScreenShot() throws Exception {
		if(runCase!=null)
		{
			File scrShotDir = new File(runCase.getScreenShotDir());
			if(scrShotDir.exists()){
				File[] listFiles = scrShotDir.listFiles();
				ImageData image = new ImageData(listFiles[0].getAbsolutePath()).scaledTo(200, 300);
				imageLabel.setImage(new Image(Display.getDefault(), image));
			}

		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void setInput(TestRunInstanceModel runInstance){
		this.runCase = runInstance;
		try {
			createScreenShot();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
