package in.bbat.configuration;

import in.bbat.utility.BBATPluginUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.Path;

public class BBATInternalProperties {

	public static final String DATABASE_CREATE ="DATABASE.CREATE";

	public static final String DATABASE_UPDATE ="DATABASE.UPDATE";


	private static BBATInternalProperties instance;

	private Properties bbatInternalProperty;

	private static final String FILE = BBATPluginUtility.getInstance().getPluginDir(Activator.PLUGIN_ID)+Path.SEPARATOR +"lib"+Path.SEPARATOR+"internal.properties";

	private BBATInternalProperties() {

		bbatInternalProperty = new Properties();
		try {
			File file2 = new File(FILE);
			if(!file2.exists())
				file2.createNewFile();
			bbatInternalProperty.load(new FileInputStream(file2));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BBATInternalProperties getInstance(){
		if(instance==null){
			instance = new BBATInternalProperties();
		}
		return instance;
	}

	public void setDatabaseCreate(boolean create){
		bbatInternalProperty.setProperty(DATABASE_CREATE, String.valueOf(create));
	}

	public boolean getDatabaseCreate(){
		return Boolean.parseBoolean(bbatInternalProperty.getProperty(DATABASE_CREATE,"true"));
	}

	public void save() throws FileNotFoundException, IOException {
		bbatInternalProperty.store(new FileOutputStream(FILE), "");		
	}

}
