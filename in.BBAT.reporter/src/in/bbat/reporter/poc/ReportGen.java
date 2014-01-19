package in.bbat.reporter.poc;
import in.BBAT.dataMine.manager.MineManagerHelper;
import in.bbat.reporter.Activator;
import in.bbat.utility.BBATPluginUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.core.runtime.Path;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportGen {

	public static JasperPrint generateReport(String reportName, Map parameters){
		Connection conn = null;
		JasperPrint myJPrint = null;

		try {

			//Connecting to the database
			conn = MineManagerHelper.getInstance().getConnection();

			//Loading my jasper file
			JasperReport jasperReport = null;
			jasperReport = (JasperReport) JRLoader.loadObject(new FileInputStream(BBATPluginUtility.getInstance().getPluginDir(Activator.PLUGIN_ID)+Path.SEPARATOR+"reports"+Path.SEPARATOR+reportName));

			//Filling the report with data from
			//the database based on the parameters passed.
			myJPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

			//Closing the database connection
			conn.close();

		}catch(JRException jrExp){
			jrExp.printStackTrace();
		} catch (SQLException e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

		}
		return myJPrint;
	}
}