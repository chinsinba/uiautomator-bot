package in.BBAT.dataMine.manager;

import in.BBAT.data.model.Entities.TestProjectEntity;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.eclipse.core.runtime.Path;


public class JaxbExportImport {

	private String exportPath;
	private JAXBContext context;
	private TestProjectEntity entity;

	public JaxbExportImport(final String exportDirPath, TestProjectEntity projectEntity) throws JAXBException, FileNotFoundException{
		context = JAXBContext.newInstance(TestProjectEntity.class);
		this.exportPath = exportDirPath;
		this.entity = projectEntity;
	}


	public void export() throws JAXBException, PropertyException,
	IOException {
		Writer writer = null;
		try 
		{
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			writer = new FileWriter(exportPath+Path.SEPARATOR+entity.getName());
			marshaller.marshal(entity, writer);
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
			}
		}
	}
}
