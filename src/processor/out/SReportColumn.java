/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import reporter.xml.SElementReportColumn;
import sa.lib.SLibUtils;

/**
 *
 * @author Alphalapz
 */
public class SReportColumn {
    
    private final SElementReportColumn moXmlReportColumn;
    
    public SReportColumn(final SElementReportColumn xmlReportColumn) {
        moXmlReportColumn = xmlReportColumn;
    }
    
    /*
     * Private methods:
     */
    
    /*
     * Public methods:
     */
    
    public int getColumnNumber() {
        return SLibUtils.parseInt(moXmlReportColumn.getAttribute(SElementReportColumn.ATTRIB_NUM).getValue().toString());
    }
    
    public int getColumnWidth() {
        return SLibUtils.parseInt(moXmlReportColumn.getAttribute(SElementReportColumn.ATTRIB_WIDTH).getValue().toString());
    }
}
