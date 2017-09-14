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
public class SElementDataSourceColumn extends SXmlElement {
    
    public static final String ELEMENT = "Column";
    public static final String ATTRIB_NAME = "name";
    public static final String ATTRIB_DATA_TYPE = "dataType";
    
    private SXmlAttribute moAttribName;
    private SXmlAttribute moAttribDataType;

    /**
     * Creates a Template.DataSources.DataSource.Column node.
     */
    public SElementDataSourceColumn() {
        super(ELEMENT);
        
        moAttribName = new SXmlAttribute(ATTRIB_NAME);
        moAttribDataType = new SXmlAttribute(ATTRIB_DATA_TYPE);
        
        mvXmlAttributes.add(moAttribName);
        mvXmlAttributes.add(moAttribDataType);
    }
}
