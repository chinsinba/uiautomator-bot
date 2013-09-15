package in.BBAT.presenter.wizards.pages;

import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class CreateTestCasePage extends CreatePage {

	private TestSuiteModel parentSuite;

	public CreateTestCasePage(String pageName,TestSuiteModel suite) {
		super(pageName);
		this.parentSuite = suite;
	}

	protected void createUpperArea(Composite parent) {
		Composite comp = new Composite(parent, SWT.None);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
		comp.setLayout(new GridLayout(2, false));

		Label projectNameLabel = new Label(comp, SWT.NULL);
		projectNameLabel.setText("TestProject :");
		Text projectNameText = new Text(comp, SWT.BORDER|SWT.READ_ONLY);
		projectNameText.setText(parentSuite.getParent().getName());
		projectNameText.setEditable(false);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		projectNameText.setLayoutData(gd);

		Label suiteNameLabel = new Label(comp, SWT.NULL);
		suiteNameLabel.setText("TestSuite :");
		Text suiteNameText = new Text(comp, SWT.BORDER|SWT.READ_ONLY);
		suiteNameText.setText(parentSuite.getName());
		suiteNameText.setEditable(false);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		suiteNameText.setLayoutData(gd);
	}

}
