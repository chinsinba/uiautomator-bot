package in.bbat.utility;

import java.io.File;
import java.io.FilenameFilter;

import org.eclipse.core.runtime.Path;

import in.bbat.configuration.BBATProperties;

public class AndroidSdkUtility {

	public  final static  String  ANDROID_SDK = BBATProperties.getInstance().getAndroid_SdkPath();
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

	public static int[] availablePlatforms(){
		File andPlatForm_ = new File(ANDROID_SDK+Path.SEPARATOR+PLATFORM+Path.SEPARATOR);
		String temp= "";
		int[] apiLevel = null ;
		if(andPlatForm_.exists()){
			String[] foldNames = andPlatForm_.list(new FilenameFilter() {
				@Override
				public boolean accept(File file, String fileName) {
					if(fileName.matches("android-"+"(\\d)+"))
					{
						return true;
					}
					return false;
				}
			});
			apiLevel = new int[foldNames.length];
			for(int i=0;i<foldNames.length;i++)
			{
				apiLevel[i]=Integer.parseInt(foldNames[i].replaceAll("android-", ""));
			}
		}
		return apiLevel;

	}

	public static String[] availablePlatformsInString(){

		String[] apis = new String[availablePlatforms().length];
		int count =0;
		for(int i : availablePlatforms()){
			apis[count] =String.valueOf(i);
			count++;
		}
		return apis;

	}

	public static boolean isPlatformPresent(int apiLevel){
		int[] availablePlatforms = availablePlatforms();
		for(int i =0;i<availablePlatforms.length;i++){
			if(apiLevel ==availablePlatforms[i])
				return true;
		}
		return false;
	}

	public static int maximumApiLevel(){
		int max =0;
		int[] availablePlatforms = availablePlatforms();
		for(int i =0;i<availablePlatforms.length;i++){
			if(availablePlatforms[i]>max)
				max =availablePlatforms[i];
		}
		return max;
	}

	public static int minimumApiLevel(){
		int min =0;
		int[] availablePlatforms = availablePlatforms();
		if(availablePlatforms.length>0){
			min = availablePlatforms[0];
		}
		for(int i =0;i<availablePlatforms.length;i++){
			if(availablePlatforms[i]<min)
				min =availablePlatforms[i];
		}
		return min;
	}


	public static boolean isUiAutoSupportingPlatformPresent(){
		int uiAuto =16;
		int[] availablePlatforms = availablePlatforms();
		for(int i =0;i<availablePlatforms.length;i++){
			if(availablePlatforms[i]>=uiAuto)
				return true;
		}
		return false;
	}

	public String getAaptPath(){

		File platFormToolsDir = new File(BBATProperties.getInstance().getAndroid_SdkPath(), IBBATConstants.PLATFORM_TOOLS);
		File adbFile = new File(platFormToolsDir, IBBATConstants.UNIX_AAPT);
		if (!adbFile.exists())
			adbFile = new File(platFormToolsDir, IBBATConstants.WINDOWS_AAPT);
		return adbFile.exists() ? adbFile.getAbsolutePath() : null;

	}

}
