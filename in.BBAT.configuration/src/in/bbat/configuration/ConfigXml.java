package in.bbat.configuration;


import in.bbat.utility.BBATPluginUtility;

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
 * This class is the interface for reading/writing the bbat.xml file.
 * It uses JAXB to read and write the bbat.Xml. 
 * @author syed
 */
public class ConfigXml {


	public final static String CONFIG_XML ="BBAT.xml";
	/**
	 * Object corresponding to the root element in the masterConfig.xml
	 */
	private  BbatConfig masterConfig_;

	private static ConfigXml instance_;
	private String bbatConfigFilePath;
	private JAXBContext context;

	private ConfigXml(final String bbatConfigFilePath) throws JAXBException, FileNotFoundException{
		masterConfig_ = new BbatConfig();
		context = JAXBContext.newInstance(BbatConfig.class);
		Unmarshaller um = context.createUnmarshaller();
		masterConfig_ = (BbatConfig) um.unmarshal(new FileReader(bbatConfigFilePath));
		this.bbatConfigFilePath = bbatConfigFilePath;
	}

	/**
	 * This method initializes the class with the context.
	 * <br><b>NOTE:This method should be called first and only then the other methods of this class can be called.
	 * If this method is not called then calling othr methods of the class will throw {@link NullPointerException}</b>
	 * @throws JAXBException
	 * @throws FileNotFoundException If the input file is not found
	 */
	public static void init() throws JAXBException, FileNotFoundException {
		if(instance_!=null)
			return;
		instance_ = new ConfigXml(BBATPluginUtility.getInstance().getPluginDir(Activator.PLUGIN_ID)+Path.SEPARATOR+CONFIG_XML);
	}
	/**
	 * This method will return the {@link ConfigXml} object reference;
	 * <br><b>NOTE: This method will throw {@link NullPointerException} if the class has not been initialized.
	 * Call init() method once to initialize the class</b>
	 * @return
	 */
	public static ConfigXml getInstance(){
		try {
			init();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
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
			w = new FileWriter(bbatConfigFilePath);
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
				//				LOG.error("Failled to close Masterconfig.xml file",e);
			}
		}
	}


	/**
	 * Gets the value of the sdkPath property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getAndroid_SdkPath() {
		return masterConfig_.getAndroid().getSdkPath();
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
		masterConfig_.getAndroid().setSdkPath(value);
	}

	/**
	 * Gets the value of the toolVersion property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getToolVersion() {
		return   masterConfig_.getToolVersion();
	}

	/**
	 * Sets the value of the toolVersion property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setToolVersion(String value) {
		masterConfig_.setToolVersion(value);
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
        return masterConfig_.getDatabase().getIpAddress();
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
    	masterConfig_.getDatabase().setIpAddress(value);
    }

    /**
     * Gets the value of the isExternal property.
     * 
     */
    public boolean database_isExternal() {
        return masterConfig_.getDatabase().isIsExternal();
    }

    /**
     * Sets the value of the isExternal property.
     * 
     */
    public void setDatabase_IsExternal(boolean value) {
    	masterConfig_.getDatabase().setIsExternal(value);
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
        return masterConfig_.getDatabase().getName();
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
    	masterConfig_.getDatabase().setName(value);
    }

    /**
     * Gets the value of the pageSize property.
     * 
     * @return
     *     possible object is
     *     {@link int }
     *     
     */
    public int getDatabase_PageSize() {
        return masterConfig_.getDatabase().getPageSize();
    }

    /**
     * Sets the value of the pageSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link int }
     *     
     */
    public void setDatabase_PageSize(int value) {
    	masterConfig_.getDatabase().setPageSize(value);;
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
        return masterConfig_.getDatabase().getPort();
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
    	masterConfig_.getDatabase().setPort(value);
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
        return masterConfig_.getDatabase().getPwd();
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
    	masterConfig_.getDatabase().setPwd(value);
    }

    /**
     * Gets the value of the update property.
     * 
     */
    public boolean database_isUpdate() {
        return masterConfig_.getDatabase().isUpdate();
    }

    /**
     * Sets the value of the update property.
     * 
     */
    public void setDatabase_Update(boolean value) {
        masterConfig_.getDatabase().setUpdate(value);
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
        return masterConfig_.getDatabase().getUserName();
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
        masterConfig_.getDatabase().setUserName(value);
    }
	
    
    public String getLicence_Key() {
        return masterConfig_.getLicence().getKey();
    }

    /**
     * Sets the value of the licence key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicence_Key(String value) {
       masterConfig_.getLicence().setKey(value);
    }

    /**
     * Gets the value of the monKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWkspc_MonKey() {
        return masterConfig_.getWorkspace().getMonKey();
    }

    /**
     * Sets the value of the monKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWkspc_MonKey(String value) {
       masterConfig_.getWorkspace().setMonKey(value);
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
        return masterConfig_.getWorkspace().getUiAutomator();
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
        masterConfig_.getWorkspace().setUiAutomator(value);
    }
	
	private static String convertToCorrectProgramFilesPath(String pathString) {
		final String PROGRAM_FILES = "Program Files";
		String newPath=pathString.replace(PROGRAM_FILES, "progra~1");
		return newPath.toString();
	}
}
