package in.BBAT.presenter.dialogs;

import in.bbat.configuration.BBATProperties;
import in.bbat.logger.BBATLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ActivationCodeDialog extends TrayDialog
{
	private Text emailAddressText;
	private Text userNameText;
	private Text companyText;
	private Text designationText;
	private Text activationCodeText;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
	private static final String ACTIVATION_CODE_PATTERN = "\\w{5}-\\w{5}-\\w{5}-\\w{5}";
	private Pattern activationCodePattern = Pattern.compile(ACTIVATION_CODE_PATTERN);
	private boolean firstTimeRun;
	private static final Logger LOG = BBATLogger.getLogger(ActivationCodeDialog.class.getName());

	public ActivationCodeDialog(Shell paramShell)
	{
		super(paramShell);
		firstTimeRun = true;
	}

	public ActivationCodeDialog(Shell paramShell, boolean paramBoolean)
	{
		super(paramShell);
		firstTimeRun = paramBoolean;
	}

	public void create()
	{
		super.create();
		getShell().setText("UIautomator-bot");
	}

	protected Control createDialogArea(Composite paramComposite)
	{
		final Composite mainComposite = new Composite(paramComposite, 0);
		GridLayout mainGridLayout = new GridLayout();
		mainGridLayout.numColumns = 3;
		mainComposite.setLayout(mainGridLayout);

		Label labl = new Label(mainComposite, 16384);
		labl.setText("Welcome to BBAT!");
		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = 16777216;
		gd.horizontalSpan = 3;
		gd.widthHint = labl.computeSize(-1, -1).x;
		labl.setLayoutData(gd);



		Composite freeTrialComp = new Composite(mainComposite, 0);
		mainGridLayout = new GridLayout();
		mainGridLayout.numColumns = 2;
		freeTrialComp.setLayout(mainGridLayout);
		gd = new GridData(4, 4, true, false);
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = 16777216;
		gd.horizontalAlignment = 16777216;
		freeTrialComp.setLayoutData(gd);
		Listener listener = new Listener()
		{
			public void handleEvent(Event paramAnonymousEvent)
			{
				if (paramAnonymousEvent.widget != null)
					((Text)paramAnonymousEvent.widget).setText("");
			}
		};

		Label freeTrialLabel = new Label(freeTrialComp, 0);
		String freeTrialMsg = "";
		freeTrialMsg = freeTrialMsg + "To get started, please enter the details below.\n\n";
		freeTrialLabel.setText(freeTrialMsg);
		freeTrialLabel.setAlignment(16777216);
		gd = new GridData(16777216, 4, true, false);
		gd.horizontalSpan = 2;
		freeTrialLabel.setLayoutData(gd);

		Label emailLabel = new Label(freeTrialComp, 0);
		emailLabel.setText("Email *:");
		emailLabel.setLayoutData(new GridData(16384, 1024, false, false));
		emailAddressText = new Text(freeTrialComp, 18432);
		if (!firstTimeRun)
			emailAddressText.setEnabled(false);
		gd = new GridData(16777216, 4, true, false);
		gd.widthHint = 200;
		emailAddressText.setLayoutData(gd);
		emailLabel = new Label(freeTrialComp, 0);
		emailLabel.setText("Your Name:");
		emailLabel.setLayoutData(new GridData(16384, 1024, false, false));
		userNameText = new Text(freeTrialComp, 18432);
		if (!firstTimeRun)
			userNameText.setEnabled(false);
		gd = new GridData(16777216, 4, true, false);
		gd.widthHint = 200;
		userNameText.setLayoutData(gd);

		Label companyLabel = new Label(freeTrialComp, 0);
		companyLabel.setText("Company Name:");
		companyLabel.setLayoutData(new GridData(16384, 1024, false, false));
		companyText = new Text(freeTrialComp, 18432);
		if (!firstTimeRun)
			companyText.setEnabled(false);
		gd = new GridData(16777216, 4, true, false);
		gd.widthHint = 200;
		companyText.setLayoutData(gd);

		Label designationLabel = new Label(freeTrialComp, 0);
		designationLabel.setText("Your Designation:");
		designationLabel.setLayoutData(new GridData(16384, 1024, false, false));
		designationText = new Text(freeTrialComp, 18432);
		if (!firstTimeRun)
			designationText.setEnabled(false);
		gd = new GridData(16777216, 4, true, false);
		gd.widthHint = 200;
		designationText.setLayoutData(gd);
		final Button startButton = new Button(freeTrialComp, 8);
		//		freeTrailButton.setText("Start 3 month trial!");
		startButton.setText(" Start ");
		gd = new GridData(16777216, 4, true, false);
		gd.horizontalSpan = 2;
		if (!firstTimeRun)
			startButton.setEnabled(false);
		startButton.setLayoutData(gd);
		emailAddressText.addListener(14, new Listener()
		{
			public void handleEvent(Event e)
			{
				startButton.notifyListeners(13, e);
			}
		});

		//		middleImagePart(mainComposite);


		freeTrialComp = new Composite(mainComposite, 0);
		mainGridLayout = new GridLayout();
		mainGridLayout.numColumns = 1;
		(freeTrialComp).setLayout(mainGridLayout);
		gd = new GridData(4, 4, true, false);
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = 16777216;
		gd.horizontalAlignment = 16777216;
		(freeTrialComp).setLayoutData(gd);


		startButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent paramAnonymousSelectionEvent)
			{
				final String email = emailAddressText.getText().trim();
				final String userName = userNameText.getText().trim();
				final String companyName = companyText.getText().trim();
				final String designation = designationText.getText().trim();
				Matcher emailMatcher = emailPattern.matcher(email);
				if (!emailMatcher.matches())
				{
					emailMatcher = activationCodePattern.matcher(email);
					if (emailMatcher.matches())
					{
						emailAddressText.setText("");
						activationCodeText.setText(email);
						return;
					}
					MessageDialog.openError(Display.getDefault().getActiveShell(), "Oops!", "Please enter a valid email id!");
					return;
				}
				try
				{
					emailAddressText.getDisplay().update();
					BBATProperties.getInstance().setUserCompany(companyName);
					BBATProperties.getInstance().setUserEmailId(email);
					HttpURLConnectionExample http = new HttpURLConnectionExample();
					http.sendGet(email, companyName, designation, userName);
					okPressed();
				}catch (Exception e) {
				}

			}
		});

		return mainComposite;
	}

	protected void createButtonsForButtonBar(Composite paramComposite)
	{
	}

	protected boolean isResizable()
	{
		return false;
	}
}
