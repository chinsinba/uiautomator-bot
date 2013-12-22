package in.bbat.utility;

import in.bbat.configuration.BBATConfigXml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

public class DefaultValueSetter
{
	private static final String USER_HOME_PROPERTY = "user.home";
	private static final String ADT_SDK_PREFERENCE_KEY = "com.android.ide.eclipse.adt.sdk";
	private static final String ADT_PLUGIN_PREFERENCE = "com.android.ide.eclipse.adt";
	public  final static  String PLATFORM_TOOLS = "platform-tools";
	public  final static  String  UNIX_ADB ="adb";
	public  final static  String  WINDOWS_ADB ="adb.exe";
	public final static String DATA_FOLDER =".bbat";
	public final static String UIWKSPCNAME ="UIAutoTestCases";
	public final static String DATA ="data";

	public static void initializeSDKPath() throws JAXBException, IOException
	{

		IScopeContext[] arrayOfIScopeContext = { InstanceScope.INSTANCE, DefaultScope.INSTANCE };
		String sdkPathFromPreference = Platform.getPreferencesService().getString(ADT_PLUGIN_PREFERENCE, ADT_SDK_PREFERENCE_KEY, null, arrayOfIScopeContext);
		if ((sdkPathFromPreference != null) && (isValidSDK(sdkPathFromPreference)))
		{
			BBATConfigXml.getInstance().setAndroid_SdkPath(sdkPathFromPreference);
			BBATConfigXml.getInstance().save();
			return;
		}
		String homeDirPath = System.getProperty(USER_HOME_PROPERTY);
		if (homeDirPath != null)
		{
			File androidHiddenFile = new File(homeDirPath, ".android");
			if ((androidHiddenFile.exists()) && (androidHiddenFile.isDirectory()))
			{
				File ddmsConfigFile = new File(androidHiddenFile, "ddms.cfg");
				if (ddmsConfigFile.exists())
					try
				{
						Properties localProperties = new Properties();
						FileInputStream localFileInputStream = new FileInputStream(ddmsConfigFile);
						localProperties.load(localFileInputStream);
						sdkPathFromPreference = (String)localProperties.get("lastSdkPath");
						BBATConfigXml.getInstance().setAndroid_SdkPath(sdkPathFromPreference);
						BBATConfigXml.getInstance().save();

				}
				catch (IOException localIOException)
				{
					//            LOG.E(DDMSJarSetter.class.getName(), "IOException while reading ddms.cfg");
				}
			}
		}
	}

	public static boolean isValidSDK(String paramString)
	{
		if (paramString == null)
			return false;
		File localFile1 = new File(paramString);
		if ((localFile1.exists()) && (localFile1.isDirectory()))
		{
			File localFile2 = new File(localFile1 , Path.SEPARATOR+"tools"+Path.SEPARATOR+"lib" + Path.SEPARATOR + "ddmlib.jar");
			if ((localFile2 != null) && (localFile2.exists()))
				return true;
		}
		return false;
	}


	public static void initializeUIAutoWorkspace() throws JAXBException, IOException{
		String s = System.getProperty(USER_HOME_PROPERTY)+Path.SEPARATOR+DATA_FOLDER+Path.SEPARATOR+UIWKSPCNAME;
		BBATConfigXml.getInstance().setWkspc_UiAutomator(s);
		BBATConfigXml.getInstance().save();
	}

	public static void initializeDataBasePath() throws JAXBException, IOException{
		String s = System.getProperty(USER_HOME_PROPERTY)+Path.SEPARATOR+DATA_FOLDER+Path.SEPARATOR+DATA;
		BBATConfigXml.getInstance().setDatabase_Name(s);
		BBATConfigXml.getInstance().save();
	}


	public static String getAdbPath()
	{
		File localFile1 = new File(BBATConfigXml.getInstance().getAndroid_SdkPath(), PLATFORM_TOOLS);
		File localFile2 = new File(localFile1, UNIX_ADB);
		if (!localFile2.exists())
			localFile2 = new File(localFile1, WINDOWS_ADB);
		return localFile2.exists() ? localFile2.getAbsolutePath() : null;
	}
}
