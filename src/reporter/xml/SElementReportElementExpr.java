/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter.xml;

import java.util.ArrayList;
import sa.lib.xml.SXmlAttribute;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
 */
public class SElementReportElementExpr extends SXmlElement {
    
    public static final String ELEMENT = "Expression";
    public static final String ATTRIB_TYPE = "type";
    public static final String ATTRIB_DATA_TYPE = "dataType";
    public static final String ATTRIB_VALUE = "value";
    public static final String ATTRIB_DS_NAME = "dataSourceName";
    public static final String ATTRIB_DS_COL_NAME = "dataSourceColumnName";
    public static final String ATTRIB_DS_OPERATION = "dataSourceOperation";
    
    private SXmlAttribute moAttribType;     // posible values defined in SEnumExpressionType
    private SXmlAttribute moAttribDataType; // posible values defined in SEnumDataType
    private SXmlAttribute moAttribValue;
    private SXmlAttribute moAttribDataSourceName;
    private SXmlAttribute moAttribDataSourceColumnName;
    private SXmlAttribute moAttribDataSourceOperation;  // posible values defined in SEnumDataSourceOperation

    /**
     * Creates a Template.Report.Elements.Element.Expression node.
     * Can contain Template.Report.Elements.Element.Expression.DataSourceFilter nodes.
     */
    public SElementReportElementExpr() {
        super(ELEMENT);
        
        moAttribType = new SXmlAttribute(ATTRIB_TYPE);
        moAttribDataType = new SXmlAttribute(ATTRIB_DATA_TYPE);
        moAttribValue = new SXmlAttribute(ATTRIB_VALUE);
        moAttribDataSourceName = new SXmlAttribute(ATTRIB_DS_NAME);
        moAttribDataSourceColumnName = new SXmlAttribute(ATTRIB_DS_COL_NAME);
        moAttribDataSourceOperation = new SXmlAttribute(ATTRIB_DS_OPERATION);
        
        mvXmlAttributes.add(moAttribType);
        mvXmlAttributes.add(moAttribDataType);
        mvXmlAttributes.add(moAttribValue);
        mvXmlAttributes.add(moAttribDataSourceName);
        mvXmlAttributes.add(moAttribDataSourceColumnName);
        mvXmlAttributes.add(moAttribDataSourceOperation);
    }
    
    public ArrayList<SElementReportElementExprFilter> getFilters() {
        ArrayList<SElementReportElementExprFilter> filters = new ArrayList<>();
        
        for (SXmlElement element : mvXmlElements) {
            filters.add((SElementReportElementExprFilter) element);
        }
        
        return filters;
    }
}
