package in.bbat.configuration;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.Path;

/**
 * This class is the interface for reading/writing the MasterConfig.xml file.
 * It uses JAXB to read and write the MasterConfig.Xml. 
 * @author syed
 */
public class ConfigXml {


	/**
	 * Object corresponding to the root element in the masterConfig.xml
	 */
	private  Config masterConfig_;

	private static ConfigXml instance_;
	private String masterConfigFilePath;
	private JAXBContext context;

	private ConfigXml(final String masterConfigFilePath) throws JAXBException, FileNotFoundException{
		masterConfig_ = new Config();
		context = JAXBContext.newInstance(Config.class);
		Unmarshaller um = context.createUnmarshaller();
		masterConfig_ = (Config) um.unmarshal(new FileReader(masterConfigFilePath));
		this.masterConfigFilePath = masterConfigFilePath;
	}

	/**
	 * This method initializes the class with the context.
	 * <br><b>NOTE:This method should be called first and only then the other methods of this class can be called.
	 * If this method is not called then calling othr methods of the class will throw {@link NullPointerException}</b>
	 * @param masterConfigFilePath  Path of the masterconfig.xml file
	 * @throws JAXBException
	 * @throws FileNotFoundException If the input file is not found
	 */
	public static void init(final String masterConfigFilePath) throws JAXBException, FileNotFoundException {
		if(instance_!=null)
			return;
		instance_ = new ConfigXml(masterConfigFilePath);
	}
	/**
	 * This method will return the {@link ConfigXml} object reference;
	 * <br><b>NOTE: This method will throw {@link NullPointerException} if the class has not been initialized.
	 * Call init() method once to initialize the class</b>
	 * @return
	 */
	public static ConfigXml getInstance(){
		if(instance_==null)
			throw new NullPointerException("The init() method should be called once before using this method");
		return instance_;
	}

	/**
	 * Saves all the changes made to the masterConfig.xml file
	 * <br><b>NOTE: This method will throw {@link NullPointerException} if the class has not been initialized.
	 * Call init() method once to initialize the class</b>
	 * @throws JAXBException
	 * @throws IOException
	 */
	public void save() throws JAXBException, IOException{
		if(context==null)
			throw new NullPointerException("The init() method should be called once before using this method");

		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		//		m.marshal(masterConfig_, System.out);

		Writer w = null;
		try 
		{
			w = new FileWriter(masterConfigFilePath);
			m.marshal(masterConfig_, w);
		}
		finally
		{
			try 
			{
				w.close();
			}
			catch (Exception e) 
			{
				LOG.error("Failled to close Masterconfig.xml file",e);
			}
		}
	}

	public String getPhone_AdbPath() {
		return convertToCorrectProgramFilesPath(masterConfig_.getPhone().getAdbPath());
	}


	public String getDatabase_UserName() {
		return masterConfig_.getDatabase().getUserName();
	}

	public void setDatabase_UserName(String database_UserName) {
		masterConfig_.getDatabase().setUserName(database_UserName);
	}

	public boolean getDatabase_IsExternal() {
		return masterConfig_.getDatabase().getIsExternal();
	}

	public void setDatabase_IsExternal(boolean database_IsExternal) {
		masterConfig_.getDatabase().setIsExternal(database_IsExternal);
	}

	public String getDatabase_Pwd() {

		return EncryptDecrypt.getDecryptedData(masterConfig_.getDatabase().getPwd());
	}

	public void setDatabase_Pwd(String database_Pwd) {
		masterConfig_.getDatabase().setPwd(EncryptDecrypt.getEncryptedData(database_Pwd));
	}

	public String getDatabase_Port() {
		return masterConfig_.getDatabase().getPort();
	}

	public void setDatabase_Port(String database_Port) {
		masterConfig_.getDatabase().setPort(database_Port);
	}

	public String getDatabase_Name() {
		return masterConfig_.getDatabase().getName();
	}

	
	public void setDatabase_Name(String database_Name) {
		masterConfig_.getDatabase().setName(database_Name);
	}
	
	public void setDatabase_Update(boolean isUpdate) {
		masterConfig_.getDatabase().setUpdate(isUpdate);
	}

	public boolean getDatabase_Update() {
		return masterConfig_.getDatabase().getUpdate();
	}
	public String getDatabase_IpAddress() {
		return masterConfig_.getDatabase().getIpAddress();
	}

	public void setDatabase_IpAddress(String database_IpAddress) {
		masterConfig_.getDatabase().setIpAddress(database_IpAddress);
	}
	public void setDatabase_PageSize(int database_IpAddress) {
		masterConfig_.getDatabase().setPageSize(String.valueOf(database_IpAddress));
	}

	public int getDatabase_PageSize() {
		if(masterConfig_.getDatabase().getPageSize().isEmpty()){
			return 0;
		}
		return Integer.parseInt(masterConfig_.getDatabase().getPageSize());
	}

	public String getLicence_Key() {
		return masterConfig_.getLicence().getKey();
	}

	public void setLicence_Key(String licence_Key) {
		masterConfig_.getLicence().setKey(licence_Key);
	}

	public String getUser_Email() {
		return  masterConfig_.getUser().getEmail();
	}

	public void setUser_Email(String user_Email) {
		masterConfig_.getUser().setEmail(user_Email);
	}

	public void setPhone_AndroidPath(String androidPath){
		masterConfig_.getPhone().setAndroidPath(convertToCorrectProgramFilesPath(androidPath));
	}
	
	

	private static String convertToCorrectProgramFilesPath(String pathString) {
		final String PROGRAM_FILES = "Program Files";
		String newPath=pathString.replace(PROGRAM_FILES, "progra~1");
		return newPath.toString();
	}
}
