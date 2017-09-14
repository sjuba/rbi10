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
 * @author Alphalapz
 */
public class SElementReportColumn extends SXmlElement {
    
    public static final String ELEMENT = "Column";
    public static final String ATTRIB_NUM = "number";
    public static final String ATTRIB_WIDTH = "width";
    
    private SXmlAttribute moNumber;
    private SXmlAttribute moWidth;

    /**
     * Creates a Template.Report.Columns.Column node.
     */
    public SElementReportColumn(){
        super(ELEMENT);
        
        moNumber = new SXmlAttribute(ATTRIB_NUM);
        moWidth = new SXmlAttribute(ATTRIB_WIDTH);
        
        mvXmlAttributes.add(moNumber);
        mvXmlAttributes.add(moWidth);
    }
}
