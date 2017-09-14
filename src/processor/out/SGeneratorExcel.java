/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;
import org.apache.poi.ss.util.CellUtil;
import reporter.xml.SElementReportElement;
import reporter.xml.SEnumBoolean;
import reporter.xml.SEnumFontAlign;
import reporter.SReporterProcessor;
import reporter.SReporterXXX;
import static reporter.xml.SEnumBoolean.TRUE;

/**
 *
 * @author AlphaLapz
 */
public class SGeneratorExcel {

    private final SReporterProcessor moReporterProcessor;

    public SGeneratorExcel(SReporterProcessor reporterProcessor) {
        moReporterProcessor = reporterProcessor;
    }

    /**
     *
     * @param value value to compare posibles values: SEnumFontAlign
     * @return
     * <datos>
     * posibles values for alignments short ALIGN_LEFT = 1;<br>
     * short ALIGN_CENTER = 2;<br>
     * short ALIGN_RIGHT = 3;<br>
     * short ALIGN_FILL = 4;<br>
     * short ALIGN_JUSTIFY = 5;
     * </datos>
     */
    private static short setAlignment(final Object value) {
        switch (SEnumFontAlign.valueOf((String) value)) {
            case CENTER:
                return ALIGN_CENTER;
            case LEFT:
                return 1;
            case RIGHT:
                return 3;
            default:
                return 5;
        }
    }

    /**
     *
     * @param value value to compare, posibles values: SEnumBoolean
     * @return
     * <datos>
     * short BOLDWEIGHT_NORMAL = 400; <br>
     * short BOLDWEIGHT_BOLD = 700;
     * </datos>
     */
    private static short setFontBold(final Object value) {
        switch (SEnumBoolean.valueOf((String) value)) {
            case TRUE:
                return 700;
            case FALSE:
            //falls down
            default:
                return 400;
        }
    }

    /**
     *
     * @param value
     * @return
     * <datos>
     * byte U_NONE = 0;<br>
     * byte U_SINGLE = 1;<br>
     * byte U_DOUBLE = 2;
     * </datos>
     */
    private static byte setFontUnderline(final Object value) {
        switch (SEnumBoolean.valueOf((String) value)) {
            case TRUE:
                return 1;
            case FALSE:
            //.... fall down
            default:
                return 0;
        }
    }

    /**
     *
     * <new> Es el nuevo</new>
     */
    public void process(String fileReport) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Test");
        HSSFCellStyle style;
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        HSSFFont font = null;
        //flags
        boolean isElement = false;
        boolean isFormula = false;
        boolean isForOperandData = false;

        int colNumber;
        int colWidth;
        int rowHeight;

        String dataSourceName = "";
        String temp;
        HSSFRow currentRow;
        HSSFCell CurrentCell;

        currentRow = sheet.createRow(0);
        // Creation of workspace
        for (int row = 0; row < moReporterProcessor.getReportRows().size(); row++) {
            for (int col = 0; col < moReporterProcessor.getReportColumns().size(); col++) {
                currentRow = sheet.createRow(row);
                colNumber = moReporterProcessor.getReportColumns().get(col).getColumnNumber();
                colWidth = moReporterProcessor.getReportColumns().get(col).getColumnWidth();
                CurrentCell = currentRow.createCell(colNumber);
                //Set Column Width
                sheet.setColumnWidth(colNumber - 1, colWidth);
            }
            //Set Height Row
            rowHeight = moReporterProcessor.getReportRows().get(row).getRowHeight();
            currentRow.setHeightInPoints(rowHeight);
        }
        //creation of Style format for all cases

        style = workbook.createCellStyle();
        style.setDataFormat(dataFormat.getFormat("[Black]#.##;[Red]#.##;"));
        for (int currentElementPosition = 0; currentElementPosition < moReporterProcessor.getReportElements().size(); currentElementPosition++) {
            Stack<Object> pila = new Stack<>();
            currentRow = sheet.getRow(moReporterProcessor.getReportElements().get(currentElementPosition).getRowNumber());

            //CurrentCell = currentRow.getCell(moReporterProcessor.getReportElements().get(currentElementPosition).getColumnNumber());
            //create new Cell
            CurrentCell = currentRow.createCell(moReporterProcessor.getReportElements().get(currentElementPosition).getColumnNumber());
            //Clear Font
            font = workbook.createFont();
            //Font Align
            SEnumFontAlign align = SEnumFontAlign.valueOf(moReporterProcessor.getReportElements().get(currentElementPosition).getXmlElement().getAttribute(SElementReportElement.ATTRIB_FONT_ALIGN).getValue().toString());
            CellUtil.setAlignment(CurrentCell, SGeneratorExcelUtils.getAlignCell(align));
            //Font Size
            font.setFontHeightInPoints((short) Integer.parseInt((String) moReporterProcessor.getReportElements().get(currentElementPosition).getXmlElement().getAttribute(SElementReportElement.ATTRIB_FONT_SIZE).getValue()));
            //Font Bold
            font.setBold((moReporterProcessor.getReportElements().get(currentElementPosition).getXmlElement().getAttribute(SElementReportElement.ATTRIB_FONT_ALIGN).getValue() == SEnumBoolean.TRUE));
            //Font Italic
            font.setItalic(moReporterProcessor.getReportElements().get(currentElementPosition).getXmlElement().getAttribute(SElementReportElement.ATTRIB_FONT_CURSIVE).getValue() == SEnumBoolean.TRUE);
            //Font Underline
            font.setUnderline(setFontUnderline(moReporterProcessor.getReportElements().get(currentElementPosition).getXmlElement().getAttribute(SElementReportElement.ATTRIB_FONT_UNDERLINE).getValue()));
            //Apply Font in CellUnit
            CellUtil.setFont(CurrentCell, font);
            Object value = null;
            SReportElement rep = new SReportElement(moReporterProcessor, moReporterProcessor.getReportElements().get(currentElementPosition).getXmlElement());
            value = rep.getValue();
            CellUtil.setFont(CurrentCell, font);
            CurrentCell.setCellStyle(style);
            try {
                CurrentCell.setCellValue((double) value);
            } catch (Exception e) {
                CurrentCell.setCellValue((String) value);
            }
        }
        // Save the file:
        try {
            workbook.write(new FileOutputStream(fileReport = fileReport.endsWith(".xls") ? fileReport : fileReport + ".xls"));
            System.out.println("The file was generated correctly; output file : " + fileReport);
        } catch (IOException e) {
            Logger.getLogger(SReporterXXX.class.getName()).log(Level.SEVERE, "The file is in use Or you do not have permission to write in the directory.\n");
        }
    }
}
