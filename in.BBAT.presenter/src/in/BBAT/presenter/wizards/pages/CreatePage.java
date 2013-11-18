package in.BBAT.presenter.wizards.pages;

import java.util.List;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.pkg.model.AbstractProjectTree;
import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
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

	private static final Logger LOG = BBATLogger.getLogger(CreatePage.class.getName());
	private AbstractProjectTree parent;

	protected CreatePage(String pageName,AbstractProjectTree parent) {
		super(pageName);
		setTitle(pageName);
		this.parent = parent;
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

				if(!matches){
					setMessage("Not a valid name",WizardPage.ERROR);
					nameValid= false;
				}
				if(isDuplicate( ((Text)e.getSource()).getText())){
					setMessage("Name already exists",WizardPage.ERROR);
					nameValid= false;
				}
				else{
					setMessage("Create a New Test Case", WizardPage.INFORMATION);
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

	private boolean isDuplicate(String enteredName){
		if(parent!=null){
			try {
				List<AbstractTreeModel> children = parent.getChildren();
				for(AbstractTreeModel model : children){
					if(model.getName().equalsIgnoreCase(enteredName)){
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
