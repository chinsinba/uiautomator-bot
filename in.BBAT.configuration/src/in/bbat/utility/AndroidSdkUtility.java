package in.bbat.utility;

import in.bbat.configuration.ConfigXml;

public class AndroidSdkUtility {

	public  final static  String  ANDROID_SDK = ConfigXml.getInstance().getAndroid_SdkPath();
	public  final static  String PLATFORM = "platforms";
	public  final static  String UIAUTOMATOR_JAR ="uiautomator.jar";
	public  final static  String ANDROID_JAR ="android.jar";
	

	private final static String UIAUTOPATH="/home/syed/Documents/Android_SDK_21/sdk/platforms/android-18/uiautomator.jar";
	private final static String ANDROPATH="/home/syed/Documents/Android_SDK_21/sdk/platforms/android-18/android.jar";
	private final static String ADBPATH ="/home/syed/Documents/Android_SDK_21/sdk/platform-tools/adb";
	private final static String USERWKSPC="/home/syed/Documents/Macac";

	public static String getUiautopath() {
		return ANDROID_SDK;
	}

	public static String getAndropath() {
		return ANDROPATH;
	}

	public static String getAdbpath() {
		return ADBPATH;
	}

	public static String getUserwkspc() {
		return USERWKSPC;
	}
}
