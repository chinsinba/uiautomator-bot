package in.bbat.configuration;

import in.bbat.utility.AndroidSdkUtility;
import in.bbat.utility.DefaultValueSetter;
import in.bbat.utility.IBBATConstants;

import java.awt.color.CMMException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.eclipse.core.runtime.Path;

public class BBATProperties {

	public static final String ANDROID_SDK_PATH ="ANDROID.SDK.PATH";

	public static final String DATABASE_USER_NAME ="DATABASE.USER.NAME";
	public static final String DATABASE_PASSWORD ="DATABASE.PASSWORD";
	public static final String DATABASE_NAME ="DATABASE.NAME";
	public static final String DATABASE_UPDATE ="DATABASE.UPDATE";
	public static final String DATABASE_IPADDRESS ="DATABASE.IPADDRESS";
	public static final String DATABASE_PORT ="DATABASE.PORT";
	public static final String DATABASE_EXTERNAL ="DATABASE.EXTERNAL";

	public static final String BBAT_VERSION ="BBAT.VERSION";
	public static final String BBAT_USER ="BBAT.USER.NAME";
	public static final String BBAT_EMAIL ="BBAT.USER.EMAIL";
	public static final String BBAT_COMPANY ="BBAT.USER.COMPANY";
	public static final String BBAT_DESIGNATION ="BBAT.USER.DESIGNATION";

	public static final String LICENSE_ACTIVATION_ENC="license.activation.enc";
	public static final String USER_ID = "user.id";
	public static final String USER_INITIAL_VERSION="user.initial.version";
	public static final String USER_CURRENT_VERSION="user.version";
	public static final String USER_CREATED_ON="user.created.on";

	public static final String SCREEN_SHOT_DIR="screenshot.dir";

	public static final String UIAUOTOMATOR_PATH ="BBAT.UIAUTOMATOR.PATH";

	private static final String FILE = "bbat.properties";

	private static  BBATProperties instance;
	private Properties bbatProperty;

	private BBATProperties() {

		bbatProperty = new Properties();
		try {
			File file2 = new File(FILE);
			if(!file2.exists())
				file2.createNewFile();
			bbatProperty.load(new FileInputStream(file2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BBATProperties getInstance(){
		if(instance==null){
			instance = new BBATProperties();
		}
		return instance;
	}

	/**
	 * Gets the value of the sdkPath property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 * @throws IOException 
	 * @throws JAXBException 
	 *     
	 */
	public String getAndroid_SdkPath()  {
		if(bbatProperty.getProperty(ANDROID_SDK_PATH)==null ||bbatProperty.getProperty(ANDROID_SDK_PATH).isEmpty()){
			String sdkPath = null;
			try {
				sdkPath = DefaultValueSetter.initializeSDKPath();
			} catch (JAXBException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
			return sdkPath;
		}
		if(DefaultValueSetter.isValidSDK(bbatProperty.getProperty(ANDROID_SDK_PATH))){
			return bbatProperty.getProperty(ANDROID_SDK_PATH);
		}
		return null;
	}

	/**
	 * Sets the value of the sdkPath property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setAndroid_SdkPath(String value) {
		bbatProperty.setProperty(ANDROID_SDK_PATH, value);
	}


	public String getAndroid_UiAutomatorPath(int apiLevel){
		return AndroidSdkUtility.getUiautopath(apiLevel);
	}

	public String getAndroid_AndroidJarPath(int apiLevel){
		return AndroidSdkUtility.getAndropath(apiLevel);
	}

	public String getAndroid_AdbPath(){
		return AndroidSdkUtility.getADBpath();
	}



	/**
	 * Gets the value of the ipAddress property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getDatabase_IpAddress() {
		return bbatProperty.getProperty(DATABASE_IPADDRESS,"127.0.0.1");
	}

	/**
	 * Sets the value of the ipAddress property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setDatabase_IpAddress(String value) {
		bbatProperty.setProperty(DATABASE_IPADDRESS, value);
	}

	/**
	 * Gets the value of the isExternal property.
	 * 
	 */
	public boolean database_isExternal() {
		return Boolean.parseBoolean(bbatProperty.getProperty(DATABASE_EXTERNAL,"false"));
	}

	/**
	 * Sets the value of the isExternal property.
	 * 
	 */
	public void setDatabase_IsExternal(boolean value) {
		bbatProperty.setProperty(DATABASE_EXTERNAL, String.valueOf(value));
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getDatabase_Name() {

		try {
			return bbatProperty.getProperty(DATABASE_NAME,DefaultValueSetter.initializeDataBasePath());
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;


	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setDatabase_Name(String value) {
		bbatProperty.setProperty(DATABASE_NAME, value);
	}

	/**
	 * Gets the value of the port property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link int }
	 *     
	 */
	public int getDatabase_Port() {
		return Integer.parseInt(bbatProperty.getProperty(DATABASE_PORT,"1527"));
	}

	/**
	 * Sets the value of the port property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link int }
	 *     
	 */
	public void setDatabase_Port(int value) {
		bbatProperty.setProperty(DATABASE_PORT, String.valueOf(value));
	}

	/**
	 * Gets the value of the pwd property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getDatabase_Pwd() {
		return bbatProperty.getProperty(DATABASE_PASSWORD,"app");
	}

	/**
	 * Sets the value of the pwd property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setDatabase_Pwd(String value) {
		bbatProperty.setProperty(DATABASE_PASSWORD, value);
	}

	/**
	 * Gets the value of the update property.
	 * 
	 */
	public boolean database_isUpdate() {
		return Boolean.parseBoolean(bbatProperty.getProperty(DATABASE_UPDATE,"false"));
	}

	/**
	 * Sets the value of the update property.
	 * 
	 */
	public void setDatabase_Update(boolean value) {
		bbatProperty.setProperty(DATABASE_UPDATE, String.valueOf(value));
	}

	/**
	 * Gets the value of the userName property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getDatabase_UserName() {
		return bbatProperty.getProperty(DATABASE_USER_NAME, "app");
	}

	/**
	 * Sets the value of the userName property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setDatabase_UserName(String value) {
		bbatProperty.setProperty(DATABASE_USER_NAME, value);
	}


	/**
	 * Gets the value of the uiAutomator property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getWkspc_UiAutomator() {
		String initializeUIAutoWorkspace =null;
		if(bbatProperty.getProperty(UIAUOTOMATOR_PATH)==null||bbatProperty.getProperty(UIAUOTOMATOR_PATH).isEmpty()){

			try {
				initializeUIAutoWorkspace = DefaultValueSetter.initializeUIAutoWorkspace();
			} catch (JAXBException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
			return initializeUIAutoWorkspace;
		}
		return bbatProperty.getProperty(UIAUOTOMATOR_PATH);
	}

	/**
	 * Sets the value of the uiAutomator property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setWkspc_UiAutomator(String value) {
		bbatProperty.setProperty(UIAUOTOMATOR_PATH,value);
	}

	public void save() throws FileNotFoundException, IOException {
		bbatProperty.store(new FileOutputStream(FILE), "");		
	}

	public String getScreenShotDirectory(){
		return bbatProperty.getProperty(SCREEN_SHOT_DIR, System.getProperty("user.home")+Path.SEPARATOR+IBBATConstants.BBAT_HIDDEN_FOLDER+Path.SEPARATOR+IBBATConstants.SCREEN_SHOTS+Path.SEPARATOR);
	}

	public void setScreenShotDirectory(String screenShotDir){
		bbatProperty.setProperty(SCREEN_SHOT_DIR, screenShotDir);
	}

	public void setUserEmailId(String emailID){
		bbatProperty.setProperty(BBAT_EMAIL, emailID);
	}

	public void setUserCompany(String company){
		bbatProperty.setProperty(BBAT_COMPANY, company);
	}


	public String getUserEmailId(){
		return bbatProperty.getProperty(BBAT_EMAIL);
	}

	public String getUserCompany(){
		return bbatProperty.getProperty(BBAT_COMPANY);
	}

	public void setUserDesignation(String designation){
		bbatProperty.setProperty(BBAT_DESIGNATION, designation);
	}

}
