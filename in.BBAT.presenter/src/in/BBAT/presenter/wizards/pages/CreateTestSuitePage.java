package in.BBAT.presenter.wizards.pages;

import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;



public class CreateTestSuitePage extends CreatePage {

	private TestProjectModel parentProj;
	private static final Logger LOG = BBATLogger.getLogger(CreateTestSuitePage.class.getName());
	

	public CreateTestSuitePage(String pageName,TestProjectModel project) {
		super(pageName);
		this.parentProj = project;
	}

	protected void createUpperArea(Composite parent) {
		Composite comp = new Composite(parent, SWT.None);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		comp.setLayout(new GridLayout(2, false));

		Label nameLabel = new Label(comp, SWT.NULL);
		nameLabel.setText("Test Project :");
		Text valueText = new Text(comp, SWT.BORDER|SWT.READ_ONLY);
		valueText.setText(parentProj.getName());
		valueText.setEditable(false);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessVerticalSpace = true;
		valueText.setLayoutData(gd);

	}

}
