package in.BBAT.presenter.wizards.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public abstract class CreatePage extends WizardPage{

	private NameAndDescriptionComponent nameDescComp;

	private boolean nameValid;
	private boolean descValid;

	protected CreatePage(String pageName) {
		super(pageName);
		setTitle(pageName);
		
	}

	@Override
	public void createControl(Composite parent) {
//		getContainer().getShell().setSize(500, 500);
		parent.setLayout(new GridLayout(1,false));
		createUpperArea(parent);
		nameDescComp = new NameAndDescriptionComponent(parent);
		nameDescComp.getNameText().addModifyListener( new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String className = "[\\p{L}_$][\\p{L}\\p{N}_$]*";
				boolean matches = ((Text)e.getSource()).getText().matches(className);
				System.out.println(matches);
				if(!matches){
					setMessage("Not a valid name",WizardPage.ERROR);
					nameValid= false;
				}
				else{
					setMessage("Create a new TestCase", WizardPage.INFORMATION);
					nameValid = true;
				}
				pageComplete();
			}
		});

		nameDescComp.getDescText().addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if(!((Text)e.getSource()).getText().isEmpty())	
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

	protected abstract void createUpperArea(Composite parent);
	public String getDescription(){
		return nameDescComp.getDescription();
	}

	public String getName(){
		return nameDescComp.getName();
	}

}
