package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestProjectEntity;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.Path;


public class JaxbExportImport {

	public JaxbExportImport() {

	}

	/**
	 * 
	 * @param exportPath
	 * @param entity
	 * @throws Exception
	 */
	public void export(final String exportPath, TestProjectEntity entity) throws Exception{
		JAXBContext context = JAXBContext.newInstance(entity.getClass());
		Writer writer = null;
		try 
		{
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			writer = new FileWriter(exportPath+Path.SEPARATOR+entity.getName()+".bbat");
			marshaller.marshal(entity, writer);
		} catch (PropertyException e) {
			throw e;
		} catch (JAXBException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} 
		finally
		{
			try 
			{
				writer.close();
			}
			catch (Exception e) 
			{
				//				LOG.error("Error occured while export",e);
				throw e;
			}
		}
	}

	public TestProjectEntity imp0rt(String testProjectXmlFilePath) throws Exception{
		JAXBContext context = JAXBContext.newInstance(TestProjectEntity.class);
		Unmarshaller um = context.createUnmarshaller();
		Object dbObj =um.unmarshal(new FileReader(testProjectXmlFilePath));
		return (TestProjectEntity) dbObj;
	}

}
