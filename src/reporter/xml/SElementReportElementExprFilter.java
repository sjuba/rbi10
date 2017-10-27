package reporter.xml;

import sa.lib.xml.SXmlAttribute;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
 */
public class SElementReportElementExprFilter extends SXmlElement {
    
    public static final String ELEMENT = "DataSourceFilter";
    public static final String ATTRIB_TYPE = "type";
    public static final String ATTRIB_TERM1_TYPE = "term1Type";
    public static final String ATTRIB_TERM1_DATA_TYPE = "term1DataType";
    public static final String ATTRIB_TERM1_VALUE = "term1Value";
    public static final String ATTRIB_COMPARISON = "comparison";
    public static final String ATTRIB_TERM2_TYPE = "term2Type";
    public static final String ATTRIB_TERM2_DATA_TYPE = "term2DataType";
    public static final String ATTRIB_TERM2_VALUE = "term2Value";
    public static final String ATTRIB_OPERATOR = "operator";
    
    private SXmlAttribute moAttribType;             // posible values defined in SEnumFilterType
    private SXmlAttribute moAttribTerm1Type;        // posible values defined in SEnumFilterTermType
    private SXmlAttribute moAttribTerm1DataType;    // posible values defined in SEnumDataType
    private SXmlAttribute moAttribTerm1Value;
    private SXmlAttribute moAttribComparison;      // posible values defined in SEnumFilterComparisson
    private SXmlAttribute moAttribTerm2Type;        // posible values defined in SEnumFilterTermType
    private SXmlAttribute moAttribTerm2DataType;    // posible values defined in SEnumDataType
    private SXmlAttribute moAttribTerm2Value;
    private SXmlAttribute moAttribOperator;         // posible values defined in SEnumFilterOperator

    /**
     * Creates a Template.Report.Elements.Element.Expression.DataSourceFilter node.
     */
    public SElementReportElementExprFilter() {
        super(ELEMENT);
        
        moAttribType = new SXmlAttribute(ATTRIB_TYPE);
        moAttribTerm1Type = new SXmlAttribute(ATTRIB_TERM1_TYPE);
        moAttribTerm1DataType = new SXmlAttribute(ATTRIB_TERM1_DATA_TYPE);
        moAttribTerm1Value = new SXmlAttribute(ATTRIB_TERM1_VALUE);
        moAttribComparison = new SXmlAttribute(ATTRIB_COMPARISON);
        moAttribTerm2Type = new SXmlAttribute(ATTRIB_TERM2_TYPE);
        moAttribTerm2DataType = new SXmlAttribute(ATTRIB_TERM2_DATA_TYPE);
        moAttribTerm2Value = new SXmlAttribute(ATTRIB_TERM2_VALUE);
        moAttribOperator = new SXmlAttribute(ATTRIB_OPERATOR);
        
        mvXmlAttributes.add(moAttribType);
        mvXmlAttributes.add(moAttribTerm1Type);
        mvXmlAttributes.add(moAttribTerm1DataType);
        mvXmlAttributes.add(moAttribTerm1Value);
        mvXmlAttributes.add(moAttribComparison);
        mvXmlAttributes.add(moAttribTerm2Type);
        mvXmlAttributes.add(moAttribTerm2DataType);
        mvXmlAttributes.add(moAttribTerm2Value);
        mvXmlAttributes.add(moAttribOperator);
    }
}
