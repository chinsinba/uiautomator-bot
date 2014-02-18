package in.BBAT.presenter.wizards.pages;

import in.bbat.logger.BBATLogger;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NameAndDescriptionComponent {


	private Text nameText;
	private Text descText;

	private static final Logger LOG = BBATLogger.getLogger(NameAndDescriptionComponent.class.getName());


	public NameAndDescriptionComponent(Composite parent,String name, String description,boolean editable) {
		Group group = new Group(parent, SWT.BORDER);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		group.setLayout(new GridLayout(2, false));
		group.setText("Details");

		Label nameLabel = new Label(group, SWT.NULL);
		nameLabel.setText("Name:");
		setNameText(new Text(group, SWT.BORDER));
		getNameText().setFocus();
		getNameText().setMessage("Enter Name");
		if(!editable){
			getNameText().setText(name);
			getNameText().setEditable(editable);
		}

		GridData gdPkgName = new GridData(GridData.FILL_HORIZONTAL);
		getNameText().setLayoutData(gdPkgName);
		Label commentLabel = new Label(group, SWT.NULL);
		commentLabel.setText("Description:");
		setDescText(new Text(group, SWT.MULTI | SWT.BORDER));
		if(!editable){
			getDescText().setText(description);
			getDescText().setEditable(editable);
		}
		GridData gdcommentText = new GridData(GridData.FILL_BOTH);
		gdcommentText.grabExcessVerticalSpace = true;

		getDescText().setLayoutData(gdcommentText);
		getDescText().setMessage("Enter description");

	}

	public String getDescription(){
		return getDescText().getText();
	}

	public String getName(){
		return getNameText().getText();
	}

	public Text getNameText() {
		return nameText;
	}

	public void setNameText(Text nameText) {
		this.nameText = nameText;
	}

	public Text getDescText() {
		return descText;
	}

	public void setDescText(Text descText) {
		this.descText = descText;
	}
}
