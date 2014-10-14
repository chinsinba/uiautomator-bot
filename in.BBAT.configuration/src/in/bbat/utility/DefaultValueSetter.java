package in.bbat.utility;

import in.bbat.configuration.BBATProperties;

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
	
	public static String initializeSDKPath() throws JAXBException, IOException
	{

		IScopeContext[] arrayOfIScopeContext = { InstanceScope.INSTANCE, DefaultScope.INSTANCE };
		String sdkPathFromPreference = Platform.getPreferencesService().getString(IBBATConstants.ADT_PLUGIN_PREFERENCE, IBBATConstants.ADT_SDK_PREFERENCE_KEY, null, arrayOfIScopeContext);
		if ((sdkPathFromPreference != null) && (isValidSDK(sdkPathFromPreference)))
		{
			return sdkPathFromPreference;
		}
		String homeDirPath = System.getProperty(IBBATConstants.USER_HOME_PROPERTY);
		if (homeDirPath != null)
		{
			File androidHiddenFile = new File(homeDirPath, IBBATConstants.ANDROID_FOLDER);
			if ((androidHiddenFile.exists()) && (androidHiddenFile.isDirectory()))
			{
				File ddmsConfigFile = new File(androidHiddenFile, IBBATConstants.DDMS_CFG);
				if (ddmsConfigFile.exists())
					try
				{
						Properties localProperties = new Properties();
						FileInputStream localFileInputStream = new FileInputStream(ddmsConfigFile);
						localProperties.load(localFileInputStream);
						sdkPathFromPreference = (String)localProperties.get(IBBATConstants.LAST_SDK_PATH);
						return sdkPathFromPreference;

				}
				catch (IOException localIOException)
				{
					//            LOG.E(DDMSJarSetter.class.getName(), "IOException while reading ddms.cfg");
				}
			}
		}
		return sdkPathFromPreference;
	}

	public static boolean isValidSDK(String paramString)
	{
		if (paramString == null)
			return false;
		File localFile1 = new File(paramString);
		if ((localFile1.exists()) && (localFile1.isDirectory()))
		{
			File localFile2 = new File(localFile1 , Path.SEPARATOR+IBBATConstants.TOOLS+Path.SEPARATOR+IBBATConstants.LIB + Path.SEPARATOR + IBBATConstants.DDM_LIB_JAR);
			if ((localFile2 != null) && (localFile2.exists()))
				return true;
		}
		return false;
	}


	public static String initializeUIAutoWorkspace() throws JAXBException, IOException{
		String s = System.getProperty(IBBATConstants.USER_HOME_PROPERTY)+Path.SEPARATOR+IBBATConstants.BBAT_HIDDEN_FOLDER+Path.SEPARATOR+IBBATConstants.UIWKSPCNAME;
		return s;
	}

	public static String initializeDataBasePath(String workspaceDirectory) throws JAXBException, IOException{
		if(workspaceDirectory==null)
		{
			workspaceDirectory =System.getProperty(IBBATConstants.USER_HOME_PROPERTY);
		}
		String s = workspaceDirectory +Path.SEPARATOR+IBBATConstants.BBAT_HIDDEN_FOLDER+Path.SEPARATOR+IBBATConstants.DATA;
		return s;
	}


	public static String getAdbPath()
	{
		File platFormToolsDir = new File(BBATProperties.getInstance().getAndroid_SdkPath(), IBBATConstants.PLATFORM_TOOLS);
		File adbFile = new File(platFormToolsDir, IBBATConstants.UNIX_ADB);
		if (!adbFile.exists())
			adbFile = new File(platFormToolsDir, IBBATConstants.WINDOWS_ADB);
		return adbFile.exists() ? adbFile.getAbsolutePath() : null;
	}

	public static boolean isDatabasePresent() {
		File dataFile = new File(BBATProperties.getInstance().getWkspc_UiAutomator() +Path.SEPARATOR+IBBATConstants.BBAT_HIDDEN_FOLDER+Path.SEPARATOR+IBBATConstants.DATA);
		
		return dataFile.exists();
	}
}
