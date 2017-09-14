package reporter.xml;

import sa.lib.xml.SXmlAttribute;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alphalapz
 */
public class SElementReportElement extends SXmlElement {
    
    public static final String ELEMENT = "Element";
    public static final String ATTRIB_COL_NUM = "columnNumber";
    public static final String ATTRIB_ROW_NUM = "rowNumber";
    public static final String ATTRIB_NAME = "name";
    public static final String ATTRIB_TYPE = "type";
    public static final String ATTRIB_DATA_TYPE = "dataType";
    public static final String ATTRIB_DATA_FORMAT = "dataFormat";
    public static final String ATTRIB_FONT_TYPE = "fontType";
    public static final String ATTRIB_FONT_SIZE = "fontSize";
    public static final String ATTRIB_FONT_BOLD = "fontBold";
    public static final String ATTRIB_FONT_CURSIVE = "fontCursive";
    public static final String ATTRIB_FONT_UNDERLINE = "fontUnderline";
    public static final String ATTRIB_FONT_ALIGN = "fontAlign";
    
    private SXmlAttribute moAttribColumnNumber;
    private SXmlAttribute moAttribRowNumber;
    private SXmlAttribute moAttribName;
    private SXmlAttribute moAttribType;         // possible values defined in SEnumElementType
    private SXmlAttribute moAttribDataType;     // possible values defined in SEnumDataType
    private SXmlAttribute moAttribDataFormat;
    private SXmlAttribute moAttribFontType;
    private SXmlAttribute moAttribFontSize;
    private SXmlAttribute moAttribFontBold;
    private SXmlAttribute moAttribFontCursive;
    private SXmlAttribute moAttribFontUnderline;
    private SXmlAttribute moAttribFontAlign;    // possible values defined in SEnumFontAlign
    
    /**
     * Creates a Template.Report.Elements.Element node.
     * Can contain Template.Report.Elements.Element.Expression nodes.
     */
    public SElementReportElement() {
        super(ELEMENT);
        
        moAttribColumnNumber = new SXmlAttribute(ATTRIB_COL_NUM);
        moAttribRowNumber = new SXmlAttribute(ATTRIB_ROW_NUM);
        moAttribName = new SXmlAttribute(ATTRIB_NAME);
        moAttribType = new SXmlAttribute(ATTRIB_TYPE);
        moAttribDataType = new SXmlAttribute(ATTRIB_DATA_TYPE);
        moAttribDataFormat = new SXmlAttribute(ATTRIB_DATA_FORMAT);
        moAttribFontType = new SXmlAttribute(ATTRIB_FONT_TYPE);
        moAttribFontSize = new SXmlAttribute(ATTRIB_FONT_SIZE);
        moAttribFontBold = new SXmlAttribute(ATTRIB_FONT_BOLD);
        moAttribFontCursive = new SXmlAttribute(ATTRIB_FONT_CURSIVE);
        moAttribFontUnderline = new SXmlAttribute(ATTRIB_FONT_UNDERLINE);
        moAttribFontAlign = new SXmlAttribute(ATTRIB_FONT_ALIGN);
        
        mvXmlAttributes.add(moAttribColumnNumber);
        mvXmlAttributes.add(moAttribRowNumber);
        mvXmlAttributes.add(moAttribName);
        mvXmlAttributes.add(moAttribType);
        mvXmlAttributes.add(moAttribDataType);
        mvXmlAttributes.add(moAttribDataFormat);
        mvXmlAttributes.add(moAttribFontType);
        mvXmlAttributes.add(moAttribFontSize);
        mvXmlAttributes.add(moAttribFontBold);
        mvXmlAttributes.add(moAttribFontCursive);
        mvXmlAttributes.add(moAttribFontUnderline);
        mvXmlAttributes.add(moAttribFontAlign);
    }
}
