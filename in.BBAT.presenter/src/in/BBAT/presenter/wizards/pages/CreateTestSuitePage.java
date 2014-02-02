package in.BBAT.presenter.wizards.pages;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.BBAT.abstrakt.presenter.pkg.model.TestProjectModel;
import in.BBAT.abstrakt.presenter.pkg.model.TestSuiteModel;
import in.bbat.logger.BBATLogger;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CreateTestSuitePage extends WizardPage {

	private TestProjectModel parentProj;
	private TestSuiteModel suite;
	private static final Logger LOG = BBATLogger.getLogger(CreateTestSuitePage.class.getName());

	public CreateTestSuitePage(String pageName,TestProjectModel project) {
		super(pageName);
		setTitle(pageName);
		this.parentProj = project;
	}
	public CreateTestSuitePage(String pageName,TestProjectModel project,TestSuiteModel suite) {
		this(pageName, project);
		this.suite = suite;
	}
	
	private NameAndDescriptionComponent nameDescComp;

	private boolean nameValid;
	private boolean descValid;



	@Override
	public void createControl(Composite parent) {
		//		getContainer().getShell().setSize(500, 500);
		parent.setLayout(new GridLayout(1,false));
		createUpperArea(parent);
		if(suite!=null){
			nameDescComp = new NameAndDescriptionComponent(parent,suite.getName(),suite.getDescription(),false);
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

				if(!matches){
					setMessage("Invalid test suite name",WizardPage.ERROR);
					nameValid= false;
					pageComplete();
					return;

				}
				if(isDuplicate(((Text)e.getSource()).getText().trim())){
					setMessage("Test suite with name "+((Text)e.getSource()).getText()+" exists",WizardPage.ERROR);
					nameValid= false;
					pageComplete();
					return;
				}
				else{
					setMessage("Wizard to create Test Suite", WizardPage.INFORMATION);
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

	public String getSuiteName(){
		return nameDescComp.getName();
	}

	private boolean isDuplicate(String enteredName){
		if(parentProj!=null){
			try {
				List<AbstractTreeModel> children = parentProj.getChildren();
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
