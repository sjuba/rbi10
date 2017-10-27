/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellUtil;
import reporter.xml.SEnumFontAlign;
import reporter.SReporterProcessor;
import reporter.SReporterXXX;

/**
 *
 * @author Alfredo Pérez
 */
public class SGeneratorExcel {

    private final SReporterProcessor moReporterProcessor;

    public SGeneratorExcel(SReporterProcessor reporterProcessor) {
        moReporterProcessor = reporterProcessor;
    }

    public void process(String fileReport) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Test");
        HSSFCellStyle style;
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        HSSFFont font = null;

        int colNumber;
        int colWidth;
        int rowHeight;

        HSSFRow currentRow;
        HSSFCell currentCell;

        currentRow = sheet.createRow(0);
        // Creation of workspace
        for (int row = 0; row < moReporterProcessor.getReportRows().size(); row++) {
            for (int col = 0; col < moReporterProcessor.getReportColumns().size(); col++) {
                currentRow = sheet.createRow(row);
                colNumber = moReporterProcessor.getReportColumns().get(col).getColumnNumber();
                colWidth = moReporterProcessor.getReportColumns().get(col).getColumnWidth();
                currentCell = currentRow.createCell(colNumber);
                //Set Column Width
                sheet.setColumnWidth(colNumber - 1, colWidth);
            }
            //Set Height Row
            rowHeight = moReporterProcessor.getReportRows().get(row).getRowHeight();
            currentRow.setHeightInPoints(rowHeight);
        }
        //creation of Style format for all cases

        for (SReportElement element : moReporterProcessor.getReportElements()) {
            style = workbook.createCellStyle();
            style.setDataFormat(dataFormat.getFormat("[Black]###,##0.00;[Red](###,###.##);"));
            currentRow = sheet.getRow(element.getRowNumber());

            currentCell = currentRow.createCell(element.getColumnNumber());
            font = workbook.createFont();
            SEnumFontAlign align = element.getFontAlign();
            CellUtil.setAlignment(currentCell, workbook, SGeneratorExcelUtils.getAlignCellShort(align));
           // CellUtil.setAlignment(currentCell, SGeneratorExcelUtils.getAlignCell(align));
            font.setFontHeightInPoints(element.getFontSize());
            font.setBoldweight(element.isBold() ? HSSFFont.BOLDWEIGHT_BOLD : HSSFFont.BOLDWEIGHT_NORMAL);
            font.setItalic(element.isCursive());
            font.setUnderline(element.isUnderline());
            font.setFontHeightInPoints(element.getFontSize());
            Object value = null;
            SReportElement rep = new SReportElement(moReporterProcessor, element.getXmlElement());
            value = rep.getValue();
            style.setFont(font);
            CellUtil.setFont((Cell) currentCell, workbook, (Font) font);
            currentCell.setCellStyle(style);
            try {
                currentCell.setCellValue((double) value);
            } catch (Exception e) {
                currentCell.setCellValue((String) value);
            }
        }
        // Save the file:
        try {
            FileOutputStream file = new FileOutputStream(fileReport = fileReport.endsWith(".xls") ? fileReport : fileReport + ".xls");
            workbook.write(file);
            file.close();
            System.out.println("The file was generated correctly; output file : " + fileReport);
        } catch (IOException e) {
            Logger.getLogger(SReporterXXX.class.getName()).log(Level.SEVERE, "The file is in use or you don't have permission to write in the directory file.\n");
        }
    }
}
