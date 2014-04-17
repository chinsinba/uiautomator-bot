package in.BBAT.presenter.dialogs;

import in.BBAT.abstrakt.gui.BBATImageManager;
import in.bbat.license.ActivationCodeValidationException;
import in.bbat.license.LicenseInfo;
import in.bbat.license.LicenseManager;
import in.bbat.logger.BBATLogger;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

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
		getShell().setText("BBAT Licensing");
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

		LicenseInfo licenseInfo = LicenseManager.getCurrentLicense();
		if ((licenseInfo != null) && (licenseInfo.isExpired()))
		{
			Link  licPurchaseLink = new Link(mainComposite, 16384);
			licPurchaseLink.setText("Your License has expired! Please <A HREF=\"http://www.BBAT.in/pricing.php\">click here to purchase BBAT</A>!");
			licPurchaseLink.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent paramAnonymousSelectionEvent)
				{
					System.out.println("You have selected: " + paramAnonymousSelectionEvent.text);
					try
					{
						PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(paramAnonymousSelectionEvent.text));
					}
					catch (Exception localException)
					{
					}
				}
			});
			gd = new GridData();
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalAlignment = 16777216;
			gd.horizontalSpan = 3;
			gd.widthHint = licPurchaseLink.computeSize(-1, -1).x;
			licPurchaseLink.setLayoutData(gd);
		}

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
		freeTrialMsg = freeTrialMsg + "To get started with your free 3 month trial, please enter the details below.\n\n";
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
		final Button freeTrailButton = new Button(freeTrialComp, 8);
		freeTrailButton.setText("Start 3 month trial!");
		gd = new GridData(16777216, 4, true, false);
		gd.horizontalSpan = 2;
		if (!firstTimeRun)
			freeTrailButton.setEnabled(false);
		freeTrailButton.setLayoutData(gd);
		emailAddressText.addListener(14, new Listener()
		{
			public void handleEvent(Event e)
			{
				freeTrailButton.notifyListeners(13, e);
			}
		});

		Label bbatImageLabel = new Label(mainComposite, 16384);
		bbatImageLabel.setImage(BBATImageManager.getInstance().getImage(BBATImageManager.ANDROID_DEVICE));
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalAlignment = 16777216;
		gd.verticalAlignment = 16777216;
		gd.verticalSpan = 3;
		gd.widthHint = bbatImageLabel.computeSize(-1, -1).x;
		bbatImageLabel.setLayoutData(gd);
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

		Label actCodeLabel = new Label(freeTrialComp, 0);
		String actCodeMessage = "If you already have purchased BBAT\n and/or have an activation code,\nplease enter it here";
		actCodeLabel.setText(actCodeMessage);
		actCodeLabel.setAlignment(16777216);
		gd = new GridData(4, 4, true, false);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = 16777216;
		actCodeLabel.setLayoutData(gd);
		activationCodeText = new Text(freeTrialComp, 16779264);
		activationCodeText.setText("     00000-00000-00000-00000     ");
		activationCodeText.setSize(400, 100);
		activationCodeText.setSelection(0, activationCodeText.getText().length());
		activationCodeText.addListener(15, listener);
		activationCodeText.addListener(3, listener);
		gd = new GridData(4, 4, true, false);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = 16777216;
		activationCodeText.setLayoutData(gd);
		final Button registerCodeButton = new Button(freeTrialComp, 8);
		registerCodeButton.setText("Register Code");
		gd = new GridData(4, 4, true, false);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = 16777216;
		registerCodeButton.setLayoutData(gd);
		activationCodeText.addListener(14, new Listener()
		{
			public void handleEvent(Event paramAnonymousEvent)
			{
				registerCodeButton.notifyListeners(13, paramAnonymousEvent);
			}
		});
		registerCodeButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent paramAnonymousSelectionEvent)
			{
				final String activationCode = activationCodeText.getText().trim().toUpperCase();
				try
				{
					activationCodeText.setText("Please Wait...");
					activationCodeText.getDisplay().update();
					new ProgressMonitorDialog(mainComposite.getShell()).run(true, false, new IRunnableWithProgress()
					{
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException, InterruptedException
								{
							monitor.beginTask("Validating your activation code...", 0);
							try
							{
								LicenseManager.registerActivationCode(activationCode);
							}
							catch (ActivationCodeValidationException localActivationCodeValidationException)
							{
								throw new InvocationTargetException(localActivationCodeValidationException);
							}
								}
					});
					MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Thank you for using BBAT!", "Your activation code was successfully verified!\nYou can now jump right in and start using BBAT!");
					//          LicenseAnalytics.queue("Activation Code Validated");
					okPressed();
				}
				catch (InvocationTargetException localInvocationTargetException)
				{
					activationCodeText.setText(activationCode);
					activationCodeText.getDisplay().update();
					//          LicenseAnalytics.queue("Actication Code Validation Failure");
					if (localInvocationTargetException.getCause().getMessage() == null)
					{
						if (askProxyConfiguration(false, null))
							okPressed();
					}
					else
					{
						String errMessage = "\n\nThe Server responded: \"" + localInvocationTargetException.getCause().getMessage() + "\"" + "\n\nPlease try again!";
						MessageDialog.openError(Display.getDefault().getActiveShell(), "Oops!", "I could not validate your activation code. " + errMessage);
					}
				}
				catch (InterruptedException localInterruptedException)
				{
					LOG.error("The activation code verification operation was interruped!",localInterruptedException);
				}
			}
		});
		freeTrailButton.addSelectionListener(new SelectionAdapter()
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
						registerCodeButton.notifyListeners(13, new Event());
						return;
					}
					MessageDialog.openError(Display.getDefault().getActiveShell(), "Oops!", "Please enter a valid email id!");
					return;
				}
				try
				{
					emailAddressText.getDisplay().update();
					new ProgressMonitorDialog(mainComposite.getShell()).run(true, false, new IRunnableWithProgress()
					{
						public void run(IProgressMonitor paramAnonymous2IProgressMonitor)
								throws InvocationTargetException, InterruptedException
								{
							paramAnonymous2IProgressMonitor.beginTask("Registering your email address...", 0);
							try
							{
								LicenseManager.registerNewTrial(email, userName, companyName, designation);
							}
							catch (ActivationCodeValidationException localActivationCodeValidationException)
							{
								throw new InvocationTargetException(localActivationCodeValidationException);
							}
								}
					});
					//          LicenseAnalytics.queue("Trial Started");
					MessageDialog.openInformation(Display.getDefault().getActiveShell(), "Thank you for trying out BBAT!", "Thank you for trying out BBAT! You can now jump right in and start exploring!");
					okPressed();
				}
				catch (InvocationTargetException e)
				{
					if (e.getCause().getMessage() == null)
					{
						if (askProxyConfiguration(true, email))
							okPressed();
					}
					else
					{
						String errMsg = "The Server responded:\n\"" + e.getCause().getMessage() + "\"" + "\n";
						MessageDialog.openError(Display.getDefault().getActiveShell(), "Oops!", "Could not start your trial as there was an error while connecting to our License server.\n" + errMsg);
					}
					if (!emailAddressText.isDisposed())
					{
						emailAddressText.setText(email);
						emailAddressText.getDisplay().update();
					}
				}
				catch (InterruptedException e)
				{
					LOG.error( "The email address check operation was interruped!");
				}
			}
		});
		if (licenseInfo != null)
		{
			actCodeLabel = new Label(freeTrialComp, 1);
			actCodeLabel.setText("\nYour current activation code is\n" + licenseInfo.getActivationCode());
			actCodeLabel.setAlignment(16777216);
			gd = new GridData(4, 4, true, false);
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalAlignment = 16777216;
			actCodeLabel.setLayoutData(gd);
		}
		if (firstTimeRun)
		{
			final Button checkUsageButton = new Button(mainComposite, SWT.CHECK);
			gd = new GridData(4, 4, true, false);
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalAlignment = 16777216;
			gd.horizontalSpan = 3;
			gd.verticalIndent = 20;
			checkUsageButton.setText("usage statistics collenction as anonymous");
			checkUsageButton.setFont(JFaceResources.getDialogFont());
			checkUsageButton.setLayoutData(gd);
			checkUsageButton.setSelection(true);
			//      PreferenceUtil.setValue("in.bbat.eclipseide.bbat.collectStatistics.2", true);
			checkUsageButton.addSelectionListener(new SelectionAdapter()
			{
				public void widgetSelected(SelectionEvent paramAnonymousSelectionEvent)
				{
					//          PreferenceUtil.setValue("in.bbat.eclipseide.littlebbat.collectStatistics.2", Boolean.toString(localButton3.getSelection()));
				}
			});
		}
		return mainComposite;
	}

	private boolean askProxyConfiguration(boolean paramBoolean, String paramString)
	{
		String host = "";//PreferenceServiceUtil.getValue("in.bbat.eclipseide.bbat.proxy.host");
		if ((!paramBoolean) || ((paramBoolean) && ((host == null) || (host.trim().isEmpty()))))
		{
			String str2 = "Please check your internet connection!\n\n" + "";//Messages.ActivationCodeDialog_3;
			boolean bool = MessageDialog.openQuestion(Display.getDefault().getActiveShell(), "Oops!", "I could not validate your activation code as I could not reach my Activation server!\n" + str2);
			if (bool)
			{
				/*PreferenceDialog localPreferenceDialog = PreferencesUtil.createPreferenceDialogOn(getParentShell(), ProxyPreferencePage.ID, null, null);
				localPreferenceDialog.open();*/
			}
		}
		if ((paramBoolean) && (host != null) && (!host.trim().isEmpty()))
		{
			if (MessageDialog.openQuestion(Display.getDefault().getActiveShell(), "Internet Not Reachable!", "It looks like we can't reach the internet! Do you want to enable a 30-day trial and send an email to support@bbat.in for help debugging this issue.\n\n"))
			{
				//				LicenseManager.storeLicenseFromString(LicenseManager.getLocallyGeneratedLicense(paramString).toString());
				//				openMailToLink("mailto:support@bbat.in&subject=Trouble Getting a License&body=Hello,\r\nI'm having trouble getting a bbat license because of some internet connectivity issues");
			}
			return true;
		}
		return false;
	}

	private void openMailToLink(String paramString)
	{
		try
		{
			PlatformUI.getWorkbench().getBrowserSupport().getExternalBrowser().openURL(new URL(paramString));
		}
		catch (PartInitException localPartInitException)
		{
			LOG.error( "PartInitException ( " + localPartInitException.getMessage() + " ) while opening the browser to download the update", localPartInitException);
		}
		catch (MalformedURLException localMalformedURLException)
		{
			LOG.error( "MalformedURLException ( " + localMalformedURLException.getMessage() + " ) while opening the browser to download the update", localMalformedURLException);
		}
	}

	protected void createButtonsForButtonBar(Composite paramComposite)
	{
	}

	protected boolean isResizable()
	{
		return false;
	}
}
