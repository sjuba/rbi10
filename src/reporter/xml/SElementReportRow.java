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
public class SElementReportRow extends SXmlElement {
    
    public static final String ELEMENT = "Row";
    public static final String ATTRIB_NUM = "number";
    public static final String ATTRIB_HEIGHT = "height";
    
    private SXmlAttribute moAttribNumber;
    private SXmlAttribute moAttribHeight;

    /**
     * Creates a Template.Report.Rows.Row node.
     */
    public SElementReportRow(){
        super(ELEMENT);
        
        moAttribNumber = new SXmlAttribute(ATTRIB_NUM);
        moAttribHeight = new SXmlAttribute(ATTRIB_HEIGHT);
        
        mvXmlAttributes.add(moAttribNumber);
        mvXmlAttributes.add(moAttribHeight);
    }
}
