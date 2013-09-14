package in.BBAT.presenter.wizards.pages;

import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class CreateTestCasePage extends CreatePage {

	public CreateTestCasePage(String pageName,TestSuiteModel suite) {
		super(pageName);
	}

	protected void createUpperArea(Composite parent) {
		Composite comp = new Composite(parent, SWT.None);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		comp.setLayout(new GridLayout(2, false));

		Label nameLabel = new Label(comp, SWT.NULL);
		nameLabel.setText("TestSuite :");
		Text valueText = new Text(comp, SWT.BORDER);
		valueText.setText("");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		valueText.setLayoutData(gd);

	}

}
