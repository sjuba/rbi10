package reporter.xml;

import sa.lib.xml.SXmlAttribute;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
 */
public class SElementParam extends SXmlElement {

    public static final String ELEMENT = "Param";
    public static final String ATTRIB_NAME = "name";
    public static final String ATTRIB_DATA_TYPE = "dataType";
    public static final String ATTRIB_DEFAULT_VALUE = "defaultValue";

    private SXmlAttribute moAttribName;
    private SXmlAttribute moAttribType; // possible values defined in SEnumAttribParamType
    private SXmlAttribute moAttribDefaultValue;

    /**
     * Creates a Template.Params.Param node.
     */
    public SElementParam() {
        super(ELEMENT);

        moAttribName = new SXmlAttribute(ATTRIB_NAME);
        moAttribType = new SXmlAttribute(ATTRIB_DATA_TYPE);
        moAttribDefaultValue = new SXmlAttribute(ATTRIB_DEFAULT_VALUE);

        mvXmlAttributes.add(moAttribName);
        mvXmlAttributes.add(moAttribType);
        mvXmlAttributes.add(moAttribDefaultValue);
    }
}
