package in.bbat.presenter;


import in.BBAT.abstrakt.presenter.pkg.model.BBATProject;
import in.bbat.configuration.BBATProperties;
import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;
import in.bbat.utility.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * 
 * @author Syed Mehtab
 *
 */
public class SettingsWindow extends ApplicationWindow  {

	//device params
	private Button browseSDKPathButton;
	private Text sdkPathText;

	private Button updateBut;
	private Button cancelBut;

	//db params
	private Text dbPortText;
	private Form form;
	private ArrayList<TextModifyListener> listeners = new ArrayList<SettingsWindow.TextModifyListener>();
	private Text wkspcPathText;
	private Button browseWkspcPathButton;

	private static final Logger LOG = BBATLogger.getLogger(SettingsWindow.class.getName());
	public SettingsWindow(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(Shell shell) {

		shell.setMaximized(true);
	}

	@Override
	protected Control createContents(Composite parent) {

		parent.setLayout(new GridLayout());
		FormToolkit confToolkit = new FormToolkit(parent.getDisplay());

		form = confToolkit.createForm(parent);
		form.setText("Configurations");

		GridLayout formLay = new GridLayout();
		form.getBody().setLayout(formLay);
		form.getBody().setLayoutData(new GridData(GridData.FILL_BOTH));
		confToolkit.decorateFormHeading(form);

		createBottomPart(confToolkit, form);
		createButtons(confToolkit, form);

		return parent;
	}

	private void addTextFocuslisteners(Text textWidget ) {
		TextModifyListener listener =new TextModifyListener(textWidget, textWidget.getText());
		listeners.add(listener);
		textWidget.addFocusListener(listener);
		textWidget.addModifyListener(listener);
	}

	class TextModifyListener implements ModifyListener,FocusListener{

		Text Widget ;
		String oldVal = "";
		boolean saveButtonState =false;
		TextModifyListener(Text textWidget,String oldValue){
			Widget = textWidget;
			this.oldVal =oldValue;
		}

		@Override
		public void modifyText(ModifyEvent e) {
			toggleSaveButton();

		}

		@Override
		public void focusLost(FocusEvent e) {
			toggleSaveButton();
		}

		private void toggleSaveButton() {
			if(!Widget.getText().trim().equals(oldVal)){
				saveButtonState=true;
				updateBut.setEnabled(saveButtonState);
			}
			else 
			{
				if(!isAnyValueChanged(this)){
					saveButtonState = false;
					updateBut.setEnabled(saveButtonState);
				}
				saveButtonState = false;
			}
		}

		@Override
		public void focusGained(FocusEvent e) {
		}



		public boolean isSaveButtonEnabled(){
			return saveButtonState;
		}
	}

	/**
	 * this method checks whether any on the text values has been changed 
	 * execpt for the text widget which is currently being changed.
	 * @param modified current text widget listener
	 * @return true if the save button is enabled by any other widget.
	 */
	private boolean isAnyValueChanged(TextModifyListener modified){
		for(TextModifyListener listener : listeners){
			if(modified!=null && modified.equals(listener)){
				//skip the text widget currently being changed.
				continue;
			}
			if(listener.isSaveButtonEnabled()){
				return true;
			}
		}
		return false;
	}


	private void createButtons(FormToolkit confToolkit, Form form) {

		Composite rightComp = confToolkit.createComposite(form.getBody(),SWT.WRAP);
		GridLayout repLayout = new GridLayout(2, true);
		rightComp.setLayout(repLayout);
		rightComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		updateBut =	confToolkit.createButton(rightComp," Update ",  SWT.PUSH);
		GridData saveButData = new GridData();
		saveButData.horizontalAlignment= GridData.END;
		saveButData.grabExcessHorizontalSpace = true;
		updateBut.setLayoutData(saveButData);
		updateBut.setEnabled(false);
		updateBut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveConfigurationsToFile();
			}
		});

		cancelBut = confToolkit.createButton(rightComp,"Cancel",  SWT.PUSH);
		GridData canButData = new GridData();
		canButData.horizontalAlignment= GridData.BEGINNING;
		canButData.grabExcessHorizontalSpace=true;
		cancelBut.setLayoutData(canButData);
		cancelBut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				close();
			}
		});
	}

	protected void saveConfigurationsToFile() {
		/*if(!saveDbSettings())
			return;*/
		if(!saveDeviceSettings())
			return;
		try {
			BBATProperties.getInstance().save();
			form.setMessage("Updated all changes.", IMessageProvider.INFORMATION);
		} catch (IOException e) {
			form.setMessage("Failled to update the changes.", IMessageProvider.ERROR);
		}
		close();
		MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Restart", "You will need to restart the tool for the settings changes to take effect.");
	}


	protected boolean saveDeviceSettings() {
		if(!validateSettings())
		{
			return false;
		}
		form.setMessage("", IMessageProvider.NONE);

		BBATProperties.getInstance().setAndroid_SdkPath(sdkPathText.getText().trim());
		copyScriptsFromOlderWorkspace(BBATProperties.getInstance().getWkspc_UiAutomator(),wkspcPathText.getText().trim());

		return true;

	}

	private void copyScriptsFromOlderWorkspace(String oldWkspcPath, String newWkspcPath) {
		if(oldWkspcPath.equalsIgnoreCase(newWkspcPath))
			return;
		final File oldUiautoWkspc = new File(oldWkspcPath);
		final File newUiautoWkspc = new File(newWkspcPath);
		BusyIndicator.showWhile(Display.getDefault(), new Runnable() {
			@Override
			public void run() {
				try {
					FileUtils.copyFolder(oldUiautoWkspc, newUiautoWkspc);
				} catch (IOException e) {
					LOG.error(e);
				}	
			}
		});
		BBATProject.deleteAllProjects();
		BBATProperties.getInstance().setWkspc_UiAutomator(newWkspcPath);
	}

	private boolean validateSettings() {

		if(sdkPathText.getText().isEmpty()){
			form.setMessage("Android: SDK path cannot be Empty", IMessageProvider.ERROR);
			return false;
		}
		else
		{
			String errMsg = ApplicationWorkbenchWindowAdvisor.validateAndroidSdkLocation(sdkPathText.getText());
			if(errMsg!=null){
				form.setMessage("Invalid Android SDK path", IMessageProvider.ERROR);
				return false;
			}
		}

		if(wkspcPathText.getText().isEmpty()){
			form.setMessage("UiAutomator scripts path cannot be empty", IMessageProvider.ERROR);
			return false;
		}

		return true;
	}

	protected boolean saveDbSettings() {
		if(!validateDbSettings()){
			return false;
		}

		form.setMessage("", IMessageProvider.NONE);
		BBATProperties.getInstance().setDatabase_Port(Integer.parseInt(dbPortText.getText().trim()));

		return true;
	}

	private boolean validateDbSettings() {

		if(dbPortText.getText().isEmpty()){
			form.setMessage("DATA: Port cannot be Empty", IMessageProvider.ERROR);
			return false;
		}

		return true;
	}

	private void createBottomPart(FormToolkit confToolkit, Form form) {
		Composite bottomComp = confToolkit.createComposite(form.getBody(),SWT.None);
		bottomComp.setLayout(new GridLayout());
		bottomComp.setLayoutData(new GridData(GridData.FILL_BOTH));
		createDeviceSettings(confToolkit, bottomComp);
	}

	private void createDeviceSettings(FormToolkit confToolkit,	Composite rightComp) {

		Section deviceSettingSection = confToolkit.createSection(rightComp,Section.COMPACT);
		Composite devClentComp = confToolkit.createComposite(deviceSettingSection);
		devClentComp.setLayout(new GridLayout(3,false));
		devClentComp.setLayoutData(new GridData(GridData.FILL_BOTH));

		{
			confToolkit.createLabel(devClentComp, "* Android SDK Path : ");
			sdkPathText = confToolkit.createText(devClentComp,BBATProperties.getInstance().getAndroid_SdkPath(),SWT.BORDER|SWT.READ_ONLY);
			sdkPathText.setToolTipText(sdkPathText.getText());
			sdkPathText.setLayoutData(new GridData(GridData.FILL_BOTH));
			browseSDKPathButton = confToolkit.createButton(devClentComp, "Browse", SWT.PUSH);
			addListnerForBrowseButton( browseSDKPathButton, sdkPathText);
			addTextFocuslisteners(sdkPathText);
		}
		{
			confToolkit.createLabel(devClentComp, "* UIAutomator scripts path : ");
			wkspcPathText = confToolkit.createText(devClentComp,BBATProperties.getInstance().getWkspc_UiAutomator(),SWT.BORDER|SWT.READ_ONLY);
			wkspcPathText.setToolTipText(wkspcPathText.getText());
			wkspcPathText.setLayoutData(new GridData(GridData.FILL_BOTH));
			browseWkspcPathButton = confToolkit.createButton(devClentComp, "Browse", SWT.PUSH);
			addListnerForBrowseButton( browseWkspcPathButton, wkspcPathText);
			addTextFocuslisteners(wkspcPathText);
		}

		//		createPortComponent(confToolkit, devClentComp);


		deviceSettingSection.setClient(devClentComp);
	}

	private void createPortComponent(FormToolkit confToolkit,
			Composite devClentComp) {
		confToolkit.createLabel(devClentComp, "Data Port : ");
		dbPortText =confToolkit.createText(devClentComp,String.valueOf(BBATProperties.getInstance().getDatabase_Port()),SWT.BORDER);
		dbPortText.setToolTipText(dbPortText.getText());
		dbPortText.setLayoutData(new GridData(GridData.FILL_BOTH));
		addNumberFilter(dbPortText);
		addTextFocuslisteners(dbPortText);
	}

	public void addNumberFilter(Text text)
	{
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {
				String string = e.text;
				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (!('0' <= chars[i] && chars[i] <= '9')) {
						e.doit = false;
						return;
					}
				}
			}
		});
	}

	public void addIPaddrFilter(Text text){
		text.addListener(SWT.Verify, new Listener() {
			public void handleEvent(Event e) {

				String string = e.text;
				if((!string.isEmpty())&&((Text)e.widget).getText().trim().length()>14){
					e.doit=false;
					return;
				}

				char[] chars = new char[string.length()];
				string.getChars(0, chars.length, chars, 0);
				for (int i = 0; i < chars.length; i++) {
					if (('a' <= chars[i] && chars[i] <= 'z')
							|| ('A' <= chars[i] && chars[i] <= 'Z')) {
						e.doit = false;
						return;
					}
				}
				e.doit = true;
			}
		});
	}

	private void addListnerForBrowseButton(final Button button,final Text updateText) {
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog importDialog = new DirectoryDialog(getShell(),SWT.OPEN);
				String text = updateText.getText();
				if(text==null||text.isEmpty() )	
					importDialog.setFilterPath(System.getProperty("user.home"));
				else
					importDialog.setFilterPath(text.trim());
				String selectedFile = importDialog.open();
				if (selectedFile != null) {
					updateText.setText(selectedFile);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}

	private boolean isIPAddress(String str) {
		Pattern ipPattern = Pattern
				.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		return ipPattern.matcher(str).matches();
	}

}
