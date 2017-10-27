/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import reporter.xml.SElementReportRow;
import sa.lib.SLibUtils;

/**
 *
 * @author Alfredo Pérez
 */
public class SReportRow {
    
    private final SElementReportRow moXmlReportRow;
    
    public SReportRow(final SElementReportRow xmlReportRow) {
        moXmlReportRow = xmlReportRow;
    }
    
    /*
     * Private methods:
     */
    
    /*
     * Public methods:
     */
    
    public int getRowNumber() {
        return SLibUtils.parseInt(moXmlReportRow.getAttribute(SElementReportRow.ATTRIB_NUM).getValue().toString());
    }
    
    public int getRowHeight() {
        return SLibUtils.parseInt(moXmlReportRow.getAttribute(SElementReportRow.ATTRIB_HEIGHT).getValue().toString());
    }
}
