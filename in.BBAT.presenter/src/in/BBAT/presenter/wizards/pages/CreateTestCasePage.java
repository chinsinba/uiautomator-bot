package in.BBAT.presenter.wizards.pages;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestCaseModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.corext.util.JavaConventionsUtil;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


public class CreateTestCasePage extends WizardPage {

	private TestSuiteModel parentSuite;
	private NameAndDescriptionComponent nameDescComp;

	private boolean nameValid;
	private boolean descValid;
	private TestCaseModel testCase;


	private static final Logger LOG = BBATLogger.getLogger(CreateTestCasePage.class.getName());


	public CreateTestCasePage(String pageName,TestSuiteModel suite) {
		super(pageName);
		setTitle(pageName);
		this.parentSuite = suite;
	}

	public CreateTestCasePage(String pageName,TestSuiteModel suite,TestCaseModel testcase) {
		this(pageName, suite);
		this.testCase = testcase;
	}

	@Override
	public void createControl(Composite parent) {
		//		getContainer().getShell().setSize(500, 500);
		parent.setLayout(new GridLayout(1,false));
		createUpperArea(parent);
		if(testCase!=null){
			nameDescComp = new NameAndDescriptionComponent(parent,testCase.getName(),testCase.getDescription(),false);
		}
		else
		{
			nameDescComp = new NameAndDescriptionComponent(parent,"","",true);
		}
		nameDescComp.getNameText().addModifyListener( new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String className = "[\\p{L}_$][\\p{L}\\p{N}_$]*";
				boolean matches = ((Text)e.getSource()).getText().trim().matches(className);

				if(JavaElementValidator.isReserverWord(((Text)e.getSource()).getText().trim())){
					setMessage("You cannot use java reserved keyword",WizardPage.ERROR);
					nameValid= false;
					pageComplete();
					return;
				}
				
				if(!matches){
					setMessage("Invalid  Test case name",WizardPage.ERROR);
					nameValid= false;
					pageComplete();
					return;
				}
				if(isDuplicate(((Text)e.getSource()).getText().trim())){
					setMessage("Name already exists",WizardPage.ERROR);
					nameValid= false;
					pageComplete();
					return;
				}
				else{
					setMessage("Wizard to create Test Case", WizardPage.INFORMATION);
					nameValid = true;
				}
				pageComplete();
			}
		});

		nameDescComp.getDescText().addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if(!((Text)e.getSource()).getText().trim().isEmpty())	
				{
					descValid = true;
				}
				else
					descValid = false;

				pageComplete();
			}
		});

		setPageComplete(false);

		setControl(parent);
	}

	public void pageComplete(){
		setPageComplete(nameValid && descValid);
	}

	public String getDeskription(){
		return nameDescComp.getDescription();
	}

	public String getCaseName(){
		return nameDescComp.getName();
	}

	private boolean isDuplicate(String enteredName){
		if(parentSuite!=null){
			try {
				List<AbstractTreeModel> children = parentSuite.getChildren();
				for(AbstractTreeModel model : children){
					if(model.getName().equalsIgnoreCase(enteredName)){
						return true;
					}
				}
			} catch (Exception e) {
				LOG.error(e);
			}
		}
		return false;
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
