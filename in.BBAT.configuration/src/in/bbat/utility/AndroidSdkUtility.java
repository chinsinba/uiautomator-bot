package in.bbat.utility;

import java.io.File;
import java.io.FilenameFilter;

import org.eclipse.core.runtime.Path;

import in.bbat.configuration.BBATConfigXml;

public class AndroidSdkUtility {

	public  final static  String  ANDROID_SDK = BBATConfigXml.getInstance().getAndroid_SdkPath();
	public  final static  String PLATFORM = "platforms";

	public  final static  String UIAUTOMATOR_JAR ="uiautomator.jar";
	public  final static  String ANDROID_JAR ="android.jar";

	public static String getUiautopath(int apiLevel) {
		return getJarPath(UIAUTOMATOR_JAR,apiLevel);
	}

	private static String getJarPath(String uiautomatorJar,int apiLevel) {
		String temp = getTheLatestPlatformAvailable(uiautomatorJar,apiLevel);
		return ANDROID_SDK+Path.SEPARATOR+PLATFORM+Path.SEPARATOR+"android-"+apiLevel+Path.SEPARATOR+uiautomatorJar;
	}

	private static String getTheLatestPlatformAvailable(final String jarName,int apiLevel) {
		File andPlatForm_ = new File(ANDROID_SDK+Path.SEPARATOR+PLATFORM+Path.SEPARATOR);
		String temp= "";
		if(andPlatForm_.exists()){
			String[] foldNames = andPlatForm_.list(new FilenameFilter() {
				@Override
				public boolean accept(File file, String fileName) {
					if(fileName.matches("android-"+"(\\d)+"))
					{
						File uiauto = new File(file,Path.SEPARATOR+fileName+Path.SEPARATOR+jarName);
						if(uiauto.exists())
							return true;
					}
					return false;
				}
			});
			int i = 0;
			for(String fold : foldNames)
			{
				if(i<Integer.parseInt(fold.replaceAll("android-", ""))){
					i=Integer.parseInt(fold.replaceAll("android-", ""));
					temp = fold;
				}
			}
		}
		return temp;
	}

	public static String getAndropath(int apiLevel) {
		return getJarPath(ANDROID_JAR,apiLevel);
	}

	public static String getADBpath(){
		return DefaultValueSetter.getAdbPath();
	}

}
