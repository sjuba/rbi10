/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter.xml;

import sa.lib.xml.SXmlAttribute;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
 */

public class SElementReport extends SXmlElement {
    
    public static final String ELEMENT = "Report";
    public static final String ATTRIB_NUM_OF_COLS = "numberOfColumns";
    public static final String ATTRIB_NUM_OF_ROWS = "numberOfRows";
    
    private SXmlAttribute moAttribNumberOfColumns;
    private SXmlAttribute moAttribNumberOfRows;
    
    private SElementReportColumns moElementReportColumns;
    private SElementReportRows moElementReportRows;
    private SElementReportElements moElementReportElements;
    
    public SElementReport() {
        super(ELEMENT);
        
        moAttribNumberOfColumns = new SXmlAttribute(ATTRIB_NUM_OF_COLS);
        moAttribNumberOfRows = new SXmlAttribute(ATTRIB_NUM_OF_ROWS);
        
        mvXmlAttributes.add(moAttribNumberOfColumns);
        mvXmlAttributes.add(moAttribNumberOfRows);
        
        moElementReportColumns = new SElementReportColumns();
        moElementReportRows = new SElementReportRows();
        moElementReportElements = new SElementReportElements();
        
        mvXmlElements.add(moElementReportColumns);
        mvXmlElements.add(moElementReportRows);
        mvXmlElements.add(moElementReportElements);
    }

    public SElementReportColumns getElementReportColumns() {
        return moElementReportColumns;
    }
    
    public SElementReportRows getElementReportRows() {
        return moElementReportRows;
    }
    
    public SElementReportElements getElementReportElements() {
        return moElementReportElements;
    }
}
