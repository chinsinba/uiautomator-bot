package in.BBAT.utils;

import in.BBAT.abstrakt.gui.model.AbstractTreeModel;
import in.BBAT.abstrakt.presenter.run.model.TestDeviceRunModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunInstanceModel;
import in.BBAT.abstrakt.presenter.run.model.TestRunModel;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class ExcelReportGenerator {

	private String inputFile;
	private TestRunModel model;


	public ExcelReportGenerator(TestRunModel run) {
		this.model= run;
	}

	public void setOutputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void write() throws Exception, WriteException {
		File file = new File(inputFile);
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
		workbook.createSheet("Consolidated Report", 0);
		WritableSheet excelSheet = workbook.getSheet(0);
		createTitleCell(excelSheet);
		createReportDetails(excelSheet);
		createCrossTableReport(excelSheet);

		workbook.write();
		workbook.close();
	}


	class TestRun {
		public String getName(){
			return "hello";
		}
	}
	class DeviceRun
	{
		public String getName(){
			return "hello";
		}

	}
	private void createCrossTableReport(WritableSheet sheet) throws RowsExceededException, WriteException,Exception {

		int row =8;
		int intCol = 0;
		int finCol=0;



		List<AbstractTreeModel> children = null;
		try {
			children = model.getChildren();
		} catch (Exception e) {
			e.printStackTrace();
		}


		sheet.mergeCells(intCol=finCol,row,finCol=intCol+4,row);
		createTableHeaderCell(sheet, intCol, row,"Test Case/Device ID");
		createTestCaseListCell(sheet, row, intCol, children);

		for(AbstractTreeModel devRunModel : children)
		{
			int caseRow =row+1;
			sheet.mergeCells(intCol=finCol+1,row,finCol=intCol+2,row);
			createTableHeaderCell(sheet, intCol, row,((TestDeviceRunModel)devRunModel).getDeviceLabel());

			createStatusCells(sheet, intCol, finCol, devRunModel, caseRow);
		}

	}

	private void createStatusCells(WritableSheet sheet, int intCol, int finCol,	AbstractTreeModel devRunModel, int caseRow) throws Exception,
	WriteException, RowsExceededException {

		List<AbstractTreeModel> testRunCaseList = ((TestDeviceRunModel)devRunModel).getChildren();
		for(int i=0;i<testRunCaseList.size();i++){
			sheet.mergeCells(intCol,caseRow,finCol,caseRow);
			createTestCaseCell(sheet,intCol,caseRow,((TestRunInstanceModel)testRunCaseList.get(i)).getStatus());
			caseRow++;
		}
	}

	private void createTestCaseListCell(WritableSheet sheet, int row,
			int intCol, List<AbstractTreeModel> children) {
		try{
			int caseRow =row+1;
			for(AbstractTreeModel devRunModel : children.get(0).getChildren())
			{
				sheet.mergeCells(intCol,caseRow,intCol+4,caseRow);
				createTestCaseCell(sheet,intCol,caseRow,((TestRunInstanceModel)devRunModel).getTestCaseEntity().getName());
				caseRow++;
			}

		}catch (Exception e) {
		}
	}

	private void createTestCaseCell(WritableSheet sheet, int intcol, int row, String cellText) throws WriteException {
		int fontPointSize = 10;
		int rowHeight = (int) ((1.5d * fontPointSize) * 20);
		sheet.setRowView(row, rowHeight, false);

		WritableFont font = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD);
		if(cellText.equalsIgnoreCase("FAIL")||cellText.equalsIgnoreCase("ERROR"))
			font.setColour(Colour.RED);
		else if(cellText.equalsIgnoreCase("PASS"))
				font.setColour(Colour.GREEN);
			
		WritableCellFormat reportDetails15PtFormat = new WritableCellFormat(font);
		// Lets automatically wrap the cells
		reportDetails15PtFormat.setWrap(true);
		reportDetails15PtFormat.setBorder(Border.ALL,BorderLineStyle.THICK);
		reportDetails15PtFormat.setAlignment(Alignment.LEFT);
		/*if(cellText.equalsIgnoreCase("PASS"))
			reportDetails15PtFormat.setBackground(Colour.RED);*/

		Label tableHeader  = new Label(intcol, row, cellText, reportDetails15PtFormat);
		sheet.addCell(tableHeader);		
	}

	private void createTableHeaderCell(WritableSheet sheet, int col, int row,String cellText)	throws RowsExceededException, WriteException {
		int fontPointSize = 30;
		int rowHeight = (int) ((1.5d * fontPointSize) * 20);
		sheet.setRowView(row, rowHeight, false);

		WritableFont times30ptBoldUnderline = new WritableFont(WritableFont.TIMES, 15, WritableFont.BOLD);
		WritableCellFormat reportDetails15PtFormat = new WritableCellFormat(times30ptBoldUnderline);
		// Lets automatically wrap the cells
		reportDetails15PtFormat.setWrap(true);
		reportDetails15PtFormat.setBorder(Border.ALL,BorderLineStyle.THICK);
		reportDetails15PtFormat.setAlignment(Alignment.CENTRE);

		Label tableHeader  = new Label(col, row, cellText, reportDetails15PtFormat);
		sheet.addCell(tableHeader);
	}

	private void createReportDetails(WritableSheet sheet)throws RowsExceededException, WriteException {
		sheet.mergeCells(0, 3, 2, 3);
		int fontPointSize = 15;
		int rowHeight = (int) ((1.5d * fontPointSize) * 20);
		sheet.setRowView(3, rowHeight, false);

		WritableFont times30ptBoldUnderline = new WritableFont(WritableFont.TIMES, 15, WritableFont.BOLD, false);
		WritableCellFormat reportDetails15PtFormat = new WritableCellFormat(times30ptBoldUnderline);
		// Lets automatically wrap the cells
		reportDetails15PtFormat.setWrap(false);
		reportDetails15PtFormat.setBorder(Border.ALL,BorderLineStyle.THICK);

		Label generatedByLabel = new Label(0, 3, "Generated By :", reportDetails15PtFormat);
		sheet.addCell(generatedByLabel);

		sheet.mergeCells(3, 3, 5, 3);
		Label nameLabel = new Label(3, 3, System.getProperty("user.name"), reportDetails15PtFormat);
		sheet.addCell(nameLabel);

		sheet.mergeCells(6, 3, 8, 3);
		Label generatedOnLabel = new Label(6, 3, "Generated On :", reportDetails15PtFormat);
		sheet.addCell(generatedOnLabel);


		sheet.mergeCells(9, 3, 12, 3);
		Label dateLabel = new Label(9, 3, new Date().toString(), reportDetails15PtFormat);
		sheet.addCell(dateLabel);

	}

	private void createTitleCell(WritableSheet sheet) throws RowsExceededException, WriteException {
		// TODO Auto-generated method stub

		sheet.mergeCells(0, 0, 11, 0);

		int fontPointSize = 30;
		int rowHeight = (int) ((1.5d * fontPointSize) * 20);
		sheet.setRowView(0, rowHeight, false);
		// create create a bold font with unterlines
		WritableFont times30ptBoldUnderline = new WritableFont(WritableFont.TIMES, 30, WritableFont.BOLD, false);
		WritableCellFormat titleCellFormat = new WritableCellFormat(times30ptBoldUnderline);
		titleCellFormat.setAlignment(Alignment.CENTRE);
		// Lets automatically wrap the cells
		titleCellFormat.setWrap(false);
		titleCellFormat.setBorder(Border.ALL, BorderLineStyle.MEDIUM);


		Label label;
		label = new Label(0, 0, "Test Execution Report-"+model.getName(), titleCellFormat);
		sheet.addCell(label);

	}

} 