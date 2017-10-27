/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter.xml;

import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import sa.lib.SLibUtils;
import sa.lib.xml.SXmlAttribute;
import sa.lib.xml.SXmlDocument;
import sa.lib.xml.SXmlUtils;

/**
 *
 * @author Alfredo Pérez
 */
public class STemplate extends SXmlDocument {

    public static final String ELEMENT = "Template";
    public static final String ATTRIB_NAME = "name";
    
    private static final String MSG_NODE = "El nodo ";
    private static final String MSG_NODE_ROOT = "El nodo raíz ";
    private static final String MSG_NODE_CONT_ONE = " debe contener un nodo ";
    private static final String MSG_NODE_CONT_ONLY_ONE = " debe contener solamente un nodo ";
    private static final String MSG_NODE_CONT_LEAST_ONE = " debe contener al menos un nodo ";
    private static final String MSG_ATTRIB = "El atributo ";
    private static final String MSG_ATTRIB_OVERFLOW = " está fuera de rango.";
    private static final String MSG_MUST_CONT = " debe contener ";

    private SXmlAttribute moAttribName;

    private SElementParams moElementParams;
    private SElementDataSources moElementDataSources;
    private SElementReport moElementReport;

    public STemplate() {
        super(ELEMENT);

        moAttribName = new SXmlAttribute(ATTRIB_NAME);

        mvXmlAttributes.add(moAttribName);

        moElementParams = new SElementParams();
        moElementDataSources = new SElementDataSources();
        moElementReport = new SElementReport();

        mvXmlElements.add(moElementParams);
        mvXmlElements.add(moElementDataSources);
        mvXmlElements.add(moElementReport);
    }

    /*
     * Private methods
     */
    
    private void processElementParams(final Node template) throws Exception {
        // Read Template.Params node:
        
        if (!SXmlUtils.hasChildElement(template, SElementParams.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + ELEMENT + "'" + MSG_NODE_CONT_ONE + "'" + SElementParams.ELEMENT + "'.");
        }

        Vector<Node> paramsNodes = SXmlUtils.extractChildElements(template, SElementParams.ELEMENT);

        if (paramsNodes.size() != 1) {
            throw new Exception(MSG_NODE + "'" + ELEMENT + "'" + MSG_NODE_CONT_ONLY_ONE + "'" + SElementParams.ELEMENT + "'.");
        }
        
        Node params = paramsNodes.get(0);

        // Read all Template.Params.Param nodes in Template.Params node:
        
        if (SXmlUtils.hasChildElement(params, SElementParam.ELEMENT)) {
            Vector<Node> paramNodes = SXmlUtils.extractChildElements(params, SElementParam.ELEMENT);

            for (Node paramNode : paramNodes) {
                NamedNodeMap paramNodeMap = paramNode.getAttributes();
                SElementParam param = new SElementParam();
                
                param.getAttribute(SElementParam.ATTRIB_NAME).setValue(SXmlUtils.extractAttributeValue(paramNodeMap, SElementParam.ATTRIB_NAME, true));
                param.getAttribute(SElementParam.ATTRIB_DATA_TYPE).setValue(SXmlUtils.extractAttributeValue(paramNodeMap, SElementParam.ATTRIB_DATA_TYPE, true));
                SEnumParamType.valueOf(param.getAttribute(SElementParam.ATTRIB_DATA_TYPE).getValue().toString());    // check enum
                param.getAttribute(SElementParam.ATTRIB_DEFAULT_VALUE).setValue(SXmlUtils.extractAttributeValue(paramNodeMap, SElementParam.ATTRIB_DEFAULT_VALUE, false));
                
                moElementParams.getXmlElements().add(param);
            }
        }
    }
    
    private void processElementDataSources(final Node template) throws Exception {
        // Read Template.DataSources node:
        
        if (!SXmlUtils.hasChildElement(template, SElementDataSources.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + ELEMENT + "'" + MSG_NODE_CONT_ONE + "'" + SElementDataSources.ELEMENT + "'.");
        }

        Vector<Node> dataSourcesNodes = SXmlUtils.extractChildElements(template, SElementDataSources.ELEMENT);

        if (dataSourcesNodes.size() != 1) {
            throw new Exception(MSG_NODE + "'" + ELEMENT + "'" + MSG_NODE_CONT_ONLY_ONE + "'" + SElementDataSources.ELEMENT + "'.");
        }
        
        Node dataSources = dataSourcesNodes.get(0);

        // Read all Template.DataSources.DataSource nodes in Template.DataSources node:
        
        if (!SXmlUtils.hasChildElement(dataSources, SElementDataSource.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + SElementDataSources.ELEMENT + "'" + MSG_NODE_CONT_LEAST_ONE + "'" + SElementDataSource.ELEMENT + "'.");
        }

        Vector<Node> dataSourceNodes = SXmlUtils.extractChildElements(dataSources, SElementDataSource.ELEMENT);

        for (Node dataSourceNode : dataSourceNodes) {
            NamedNodeMap dataSourceNodeMap = dataSourceNode.getAttributes();
            SElementDataSource dataSource = new SElementDataSource();
            
            dataSource.getAttribute(SElementDataSource.ATTRIB_NAME).setValue(SXmlUtils.extractAttributeValue(dataSourceNodeMap, SElementDataSource.ATTRIB_NAME, true));
            dataSource.getAttribute(SElementDataSource.ATTRIB_TYPE).setValue(SXmlUtils.extractAttributeValue(dataSourceNodeMap, SElementDataSource.ATTRIB_TYPE, true));
            SEnumDataSourceType.valueOf(dataSource.getAttribute(SElementDataSource.ATTRIB_TYPE).getValue().toString()); // check enum
            dataSource.getAttribute(SElementDataSource.ATTRIB_HOST).setValue(SXmlUtils.extractAttributeValue(dataSourceNodeMap, SElementDataSource.ATTRIB_HOST, false));
            dataSource.getAttribute(SElementDataSource.ATTRIB_PORT).setValue(SXmlUtils.extractAttributeValue(dataSourceNodeMap, SElementDataSource.ATTRIB_PORT, false));
            dataSource.getAttribute(SElementDataSource.ATTRIB_DATABASE).setValue(SXmlUtils.extractAttributeValue(dataSourceNodeMap, SElementDataSource.ATTRIB_DATABASE, false));
            dataSource.getAttribute(SElementDataSource.ATTRIB_USER).setValue(SXmlUtils.extractAttributeValue(dataSourceNodeMap, SElementDataSource.ATTRIB_USER, false));
            dataSource.getAttribute(SElementDataSource.ATTRIB_PASSWORD).setValue(SXmlUtils.extractAttributeValue(dataSourceNodeMap, SElementDataSource.ATTRIB_PASSWORD, false));
            dataSource.getAttribute(SElementDataSource.ATTRIB_QUERY).setValue(SXmlUtils.extractAttributeValue(dataSourceNodeMap, SElementDataSource.ATTRIB_QUERY, true));
            
            moElementDataSources.getXmlElements().add(dataSource);

            // Read all Template.DataSources.DataSource.Column nodes in current Template.DataSources.DataSource node:
            
            if (!SXmlUtils.hasChildElement(dataSourceNode, SElementDataSourceColumn.ELEMENT)) {
                throw new Exception(MSG_NODE + "'" + SElementDataSource.ELEMENT + "'" + MSG_NODE_CONT_LEAST_ONE + "'" + SElementDataSourceColumn.ELEMENT + "'.");
            }
            
            Vector<Node> dataSourceColumnNodes = SXmlUtils.extractChildElements(dataSourceNode, SElementDataSourceColumn.ELEMENT);

            for (Node dataSourceColumnNode : dataSourceColumnNodes) {
                NamedNodeMap dataSourceColumnNodeMap = dataSourceColumnNode.getAttributes();
                SElementDataSourceColumn dataSourceColumn = new SElementDataSourceColumn();
                
                dataSourceColumn.getAttribute(SElementDataSourceColumn.ATTRIB_NAME).setValue(SXmlUtils.extractAttributeValue(dataSourceColumnNodeMap, SElementDataSourceColumn.ATTRIB_NAME, true));
                dataSourceColumn.getAttribute(SElementDataSourceColumn.ATTRIB_DATA_TYPE).setValue(SXmlUtils.extractAttributeValue(dataSourceColumnNodeMap, SElementDataSourceColumn.ATTRIB_DATA_TYPE, true));
                SEnumDataType.valueOf(dataSourceColumn.getAttribute(SElementDataSourceColumn.ATTRIB_DATA_TYPE).getValue().toString());  // check enum
                
                dataSource.getXmlElements().add(dataSourceColumn);
            }
        }
    }
    
    private void processElementReport(final Node template) throws Exception {
        // Read Template.Report node:
        
        if (!SXmlUtils.hasChildElement(template, SElementReport.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + ELEMENT + "'" + MSG_NODE_CONT_ONE + "'" + SElementReport.ELEMENT + "'.");
        }

        Vector<Node> reportNodes = SXmlUtils.extractChildElements(template, SElementReport.ELEMENT);
        
        if (reportNodes.size() != 1) {
            throw new Exception(MSG_NODE + "'" + ELEMENT + "'" + MSG_NODE_CONT_ONLY_ONE + "'" + SElementReport.ELEMENT + "'.");
        }
        
        Node report = reportNodes.get(0);

        // Read Template.Report node attributes:

        NamedNodeMap reportNodeMap = report.getAttributes();
        moElementReport.getAttribute(SElementReport.ATTRIB_NUM_OF_COLS).setValue(SXmlUtils.extractAttributeValue(reportNodeMap, SElementReport.ATTRIB_NUM_OF_COLS, true));
        moElementReport.getAttribute(SElementReport.ATTRIB_NUM_OF_ROWS).setValue(SXmlUtils.extractAttributeValue(reportNodeMap, SElementReport.ATTRIB_NUM_OF_ROWS, true));
        
        /*
         * Template.Report.Columns node
         */
        
        if (!SXmlUtils.hasChildElement(report, SElementReportColumns.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + SElementReport.ELEMENT + "'" + MSG_NODE_CONT_ONE + "'" + SElementReportColumns.ELEMENT + "'.");
        }

        Vector<Node> reportColumnsNodes = SXmlUtils.extractChildElements(report, SElementReportColumns.ELEMENT);
        
        if (reportColumnsNodes.size() != 1) {
            throw new Exception(MSG_NODE + "'" + SElementReport.ELEMENT + "'" + MSG_NODE_CONT_ONLY_ONE + "'" + SElementReportColumns.ELEMENT + "'.");
        }
        
        Node reportColumns = reportColumnsNodes.get(0);

        // Read all Template.Report.Columns.Column elements in Template.Report.Columns element:
        
        if (!SXmlUtils.hasChildElement(reportColumns, SElementReportColumn.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + SElementReportColumns.ELEMENT + "'" + MSG_NODE_CONT_LEAST_ONE + "'" + SElementReportColumn.ELEMENT + "'.");
        }

        Vector<Node> reportColumnNodes = SXmlUtils.extractChildElements(reportColumns, SElementReportColumn.ELEMENT);
        
        int cols = SLibUtils.parseInt(moElementReport.getAttribute(SElementReport.ATTRIB_NUM_OF_COLS).getValue().toString());
        if (reportColumnNodes.size() != cols) {
            throw new Exception(MSG_NODE + "'" + SElementReportColumns.ELEMENT + "'" + MSG_MUST_CONT + cols + " '" + SElementReportColumn.ELEMENT + "'.");
        }
        
        for (Node reportColumnNode : reportColumnNodes) {
            NamedNodeMap reportColumnNodeMap = reportColumnNode.getAttributes();
            SElementReportColumn reportColumn = new SElementReportColumn();
            
            reportColumn.getAttribute(SElementReportColumn.ATTRIB_NUM).setValue(SXmlUtils.extractAttributeValue(reportColumnNodeMap, SElementReportColumn.ATTRIB_NUM, true));
            reportColumn.getAttribute(SElementReportColumn.ATTRIB_WIDTH).setValue(SXmlUtils.extractAttributeValue(reportColumnNodeMap, SElementReportColumn.ATTRIB_WIDTH, true));
            
            moElementReport.getElementReportColumns().getXmlElements().add(reportColumn);
        }

        /*
         * Template.Report.Rows node
         */
        
        if (!SXmlUtils.hasChildElement(report, SElementReportRows.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + SElementReport.ELEMENT + "'" + MSG_NODE_CONT_ONE + "'" + SElementReportRows.ELEMENT + "'.");
        }
        
        Vector<Node> reportRowsNodes = SXmlUtils.extractChildElements(report, SElementReportRows.ELEMENT);
        
        if (reportRowsNodes.size() != 1) {
            throw new Exception(MSG_NODE + "'" + SElementReport.ELEMENT + "'" + MSG_NODE_CONT_ONLY_ONE + "'" + SElementReportRows.ELEMENT + "'.");
        }
        
        Node reportRows = reportRowsNodes.get(0);
        
        // Read all Template.Report.Rows.Row elements in Template.Report.Rows element:
        
        if (!SXmlUtils.hasChildElement(reportRows, SElementReportRow.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + SElementReportRows.ELEMENT + "'" + MSG_NODE_CONT_LEAST_ONE + "'" + SElementReportRow.ELEMENT + "'.");
        }

        Vector<Node> reportRowNodes = SXmlUtils.extractChildElements(reportRows, SElementReportRow.ELEMENT);

        int rows = SLibUtils.parseInt(moElementReport.getAttribute(SElementReport.ATTRIB_NUM_OF_ROWS).getValue().toString());
        if (reportRowNodes.size() != rows) {
            throw new Exception(MSG_NODE + "'" + SElementReportRows.ELEMENT + "'" + MSG_MUST_CONT + rows + " '" + SElementReportRow.ELEMENT + "'.");
        }
        
        for (Node reportRowNode : reportRowNodes) {
            NamedNodeMap reportRowNodeMap = reportRowNode.getAttributes();
            SElementReportRow reportRow = new SElementReportRow();
            
            reportRow.getAttribute(SElementReportRow.ATTRIB_NUM).setValue(SXmlUtils.extractAttributeValue(reportRowNodeMap, SElementReportRow.ATTRIB_NUM, true));
            reportRow.getAttribute(SElementReportRow.ATTRIB_HEIGHT).setValue(SXmlUtils.extractAttributeValue(reportRowNodeMap, SElementReportRow.ATTRIB_HEIGHT, true));
            
            moElementReport.getElementReportRows().getXmlElements().add(reportRow);
        }

        /*
         * Template.Report.Elements node
         */
        
        if (!SXmlUtils.hasChildElement(report, SElementReportElements.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + SElementReport.ELEMENT + "'" + MSG_NODE_CONT_ONE + "'" + SElementReportElements.ELEMENT + "'.");
        }
        
        Vector<Node> reportElementsNodes = SXmlUtils.extractChildElements(report, SElementReportElements.ELEMENT);

        if (reportElementsNodes.size() != 1) {
            throw new Exception(MSG_NODE + "'" + SElementReport.ELEMENT + "'" + MSG_NODE_CONT_ONLY_ONE + "'" + SElementReportElements.ELEMENT + "'.");
        }
        
        Node reportElements = reportElementsNodes.get(0);

        // Read all Template.Report.Elements.Element elements in Template.Report.Elements element:
        
        if (!SXmlUtils.hasChildElement(reportElements, SElementReportElement.ELEMENT)) {
            throw new Exception(MSG_NODE + "'" + SElementReportElements.ELEMENT + "'" + MSG_NODE_CONT_LEAST_ONE + "'" + SElementReportElement.ELEMENT + "'.");
        }
        
        Vector<Node> reportElementNodes = SXmlUtils.extractChildElements(reportElements, SElementReportElement.ELEMENT);

        for (Node reportElementNode : reportElementNodes) {
            NamedNodeMap reportElementNodeMap = reportElementNode.getAttributes();
            SElementReportElement reportElement = new SElementReportElement();
            
            reportElement.getAttribute(SElementReportElement.ATTRIB_COL_NUM).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_COL_NUM, true));
            int col = SLibUtils.parseInt(reportElement.getAttribute(SElementReportElement.ATTRIB_COL_NUM).getValue().toString());
            if (col < 0 || col > cols) {
                throw new Exception(MSG_ATTRIB + "'" + SElementReportElement.ATTRIB_COL_NUM + "'" + MSG_ATTRIB_OVERFLOW);   // check column
            }
            reportElement.getAttribute(SElementReportElement.ATTRIB_ROW_NUM).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_ROW_NUM, true));
            int row = SLibUtils.parseInt(reportElement.getAttribute(SElementReportElement.ATTRIB_ROW_NUM).getValue().toString());
            if (row < 0 || row > rows) {
                throw new Exception(MSG_ATTRIB + "'" + SElementReportElement.ATTRIB_ROW_NUM + "'" + MSG_ATTRIB_OVERFLOW);   // check row
            }
            reportElement.getAttribute(SElementReportElement.ATTRIB_NAME).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_NAME, false));
            reportElement.getAttribute(SElementReportElement.ATTRIB_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_TYPE, true));
            SEnumElementType.valueOf(reportElement.getAttribute(SElementReportElement.ATTRIB_TYPE).getValue().toString());      // check enum
            reportElement.getAttribute(SElementReportElement.ATTRIB_DATA_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_DATA_TYPE, true));
            SEnumDataType.valueOf(reportElement.getAttribute(SElementReportElement.ATTRIB_DATA_TYPE).getValue().toString());    // check enum
            reportElement.getAttribute(SElementReportElement.ATTRIB_DATA_FORMAT).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_DATA_FORMAT, false));
            reportElement.getAttribute(SElementReportElement.ATTRIB_FONT_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_FONT_TYPE, false));
            reportElement.getAttribute(SElementReportElement.ATTRIB_FONT_SIZE).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_FONT_SIZE, false));
            reportElement.getAttribute(SElementReportElement.ATTRIB_FONT_BOLD).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_FONT_BOLD, false));
            reportElement.getAttribute(SElementReportElement.ATTRIB_FONT_CURSIVE).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_FONT_CURSIVE, false));
            reportElement.getAttribute(SElementReportElement.ATTRIB_FONT_UNDERLINE).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_FONT_UNDERLINE, false));
            reportElement.getAttribute(SElementReportElement.ATTRIB_FONT_ALIGN).setValue(SXmlUtils.extractAttributeValue(reportElementNodeMap, SElementReportElement.ATTRIB_FONT_ALIGN, false));
            if (!reportElement.getAttribute(SElementReportElement.ATTRIB_FONT_ALIGN).getValue().toString().isEmpty()) {
                SEnumFontAlign.valueOf(reportElement.getAttribute(SElementReportElement.ATTRIB_FONT_ALIGN).getValue().toString());  // check enum
            }
            
            moElementReport.getElementReportElements().getXmlElements().add(reportElement);

            // Read all Template.Report.Elements.Element.Expression elements in current Template.Report.Elements.Element element:

            if (!SXmlUtils.hasChildElement(reportElementNode, SElementReportElementExpr.ELEMENT)) {
                throw new Exception(MSG_NODE + "'" + SElementReportElement.ELEMENT + "'" + MSG_NODE_CONT_LEAST_ONE + "'" + SElementReportElementExpr.ELEMENT + "'.");
            }
            
            Vector<Node> reportElementExprNodes = SXmlUtils.extractChildElements(reportElementNode, SElementReportElementExpr.ELEMENT);

            for (Node reportElementExprNode : reportElementExprNodes) {
                NamedNodeMap reportElementExprNodeMap = reportElementExprNode.getAttributes();
                SElementReportElementExpr reportElementExpr = new SElementReportElementExpr();
                
                // read element-expression type:
                reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementExprNodeMap, SElementReportElementExpr.ATTRIB_TYPE, true));
                SEnumElementExprType elementExprType = SEnumElementExprType.valueOf(reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_TYPE).getValue().toString());      // check enum and preserve type
                
                // read remaining attributes according to element-expression type:
                switch (elementExprType) {
                    case OPERAND_CONST:
                        reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_DATA_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementExprNodeMap, SElementReportElementExpr.ATTRIB_DATA_TYPE, true));
                        SEnumDataType.valueOf(reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_DATA_TYPE).getValue().toString());    // check enum
                        // falls down...
                    case OPERAND_ELEMENT:
                    case OPERAND_PARAM:
                        reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_VALUE).setValue(SXmlUtils.extractAttributeValue(reportElementExprNodeMap, SElementReportElementExpr.ATTRIB_VALUE, true));
                        break;
                        
                    case OPERAND_DATA:
                        reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_DATA_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementExprNodeMap, SElementReportElementExpr.ATTRIB_DATA_TYPE, true));
                        reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_DS_NAME).setValue(SXmlUtils.extractAttributeValue(reportElementExprNodeMap, SElementReportElementExpr.ATTRIB_DS_NAME, true));
                        reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_DS_COL_NAME).setValue(SXmlUtils.extractAttributeValue(reportElementExprNodeMap, SElementReportElementExpr.ATTRIB_DS_COL_NAME, true));
                        reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_DS_OPERATION).setValue(SXmlUtils.extractAttributeValue(reportElementExprNodeMap, SElementReportElementExpr.ATTRIB_DS_OPERATION, true));
                        break;
                        
                    case OPERATOR:
                        reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_VALUE).setValue(SXmlUtils.extractAttributeValue(reportElementExprNodeMap, SElementReportElementExpr.ATTRIB_VALUE, true));
                        SEnumElementExprOperator.valueOf(reportElementExpr.getAttribute(SElementReportElementExpr.ATTRIB_VALUE).getValue().toString()); // check enum
                        break;
                        
                    default:
                }
                
                reportElement.getXmlElements().add(reportElementExpr);

                // Read all Template.Report.Elements.Element.Expression.DataSourceFilter elements in current Template.Report.Elements.Element.Expression element, when needed:
                
                if (elementExprType == SEnumElementExprType.OPERAND_DATA) {
                    if (!SXmlUtils.hasChildElement(reportElementExprNode, SElementReportElementExprFilter.ELEMENT)) {
                        throw new Exception(MSG_NODE + "'" + SElementReportElementExpr.ELEMENT + "'" + MSG_NODE_CONT_LEAST_ONE + "'" + SElementReportElementExprFilter.ELEMENT + "'.");
                    }
                    
                    Vector<Node> reportElementExprFilterNodes = SXmlUtils.extractChildElements(reportElementExprNode, SElementReportElementExprFilter.ELEMENT);
                    
                    for (Node reportElementExprFilterNode : reportElementExprFilterNodes) {
                        NamedNodeMap reportElementExprFilterNodeMap = reportElementExprFilterNode.getAttributes();
                        SElementReportElementExprFilter reportElementExprFilter = new SElementReportElementExprFilter();
                        
                        // read data-source-filter type:
                        reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_TYPE, true));
                        SEnumFilterType filterType = SEnumFilterType.valueOf(reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TYPE).getValue().toString());   // check enum and preserve type
                        
                        // read remaining attributes according to data-source-filter type:
                        switch (filterType) {
                            case OPERAND:
                                // read term 1 of current operand (left side of comparisson):
                                reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM1_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_TERM1_TYPE, true));
                                SEnumFilterTermType term1FilterTermType = SEnumFilterTermType.valueOf(reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM1_TYPE).getValue().toString());    // check enum and preserve type
                                switch (term1FilterTermType) {
                                    case CONST:
                                        SEnumDataType.valueOf(reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM1_DATA_TYPE).getValue().toString());   // check enum
                                        // falls down...
                                    case ELEMENT:
                                    case PARAM:
                                    case DATA:
                                        reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM1_DATA_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_TERM1_DATA_TYPE, true));
                                        reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM1_VALUE).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_TERM1_VALUE, true));
                                        break;
                                    default:
                                }
                                
                                // read comparisson of current operand:
                                reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_COMPARISON).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_COMPARISON, true));
                                SEnumFilterComparison.valueOf(reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_COMPARISON).getValue().toString());
                                
                                // read term 2 of current operand (right side of comparisson):
                                reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM2_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_TERM2_TYPE, true));
                                SEnumFilterTermType term2FilterTermType = SEnumFilterTermType.valueOf(reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM2_TYPE).getValue().toString());    // check enum and preserve type
                                switch (term2FilterTermType) {
                                    case CONST:
                                        reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM2_DATA_TYPE).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_TERM2_DATA_TYPE, true));
                                        SEnumDataType.valueOf(reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM2_DATA_TYPE).getValue().toString());   // check enum
                                        // falls down...
                                    case ELEMENT:
                                    case PARAM:
                                    case DATA:
                                        reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM2_VALUE).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_TERM2_VALUE, true));
                                        break;
                                    default:
                                }
                                break;
                                
                            case OPERATOR:
                                reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_OPERATOR).setValue(SXmlUtils.extractAttributeValue(reportElementExprFilterNodeMap, SElementReportElementExprFilter.ATTRIB_OPERATOR, true));
                                SEnumFilterOperator.valueOf(reportElementExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_OPERATOR).getValue().toString());
                                break;
                                
                            default:
                        }
                        
                        reportElementExpr.getXmlElements().add(reportElementExprFilter);
                    }
                }
            }
        }
    }
    
    /*
     * Public methods
     */
    
    public SElementParams getElementParams() {
        return moElementParams;
    }

    public SElementDataSources getElementDataSources() {
        return moElementDataSources;
    }

    public SElementReport getElementReport() {
        return moElementReport;
    }

    @Override
    public void processXml(final String xml) throws Exception {
        // clear existing elements:
        moElementParams.getXmlElements().clear();
        moElementDataSources.getXmlElements().clear();
        moElementReport.getXmlElements().clear();

        // start parsing XML file:
        Document document = SXmlUtils.parseDocument(xml);

        // read Template node:
        if (!SXmlUtils.hasChildElement(document, ELEMENT)) {
            throw new Exception(MSG_NODE_ROOT + "'" + ELEMENT + "' no existe en el archivo XML de la plantilla del reporte.");
        }
        Node template = SXmlUtils.extractElements(document, ELEMENT).item(0);
        
        // read Template node attributes:
        NamedNodeMap templateNodeMap = template.getAttributes();
        moAttribName.setValue(templateNodeMap.getNamedItem(ATTRIB_NAME).getNodeValue());

        // read remaining Template node elements:
        processElementParams(template);
        processElementDataSources(template);
        processElementReport(template);
    }
}
