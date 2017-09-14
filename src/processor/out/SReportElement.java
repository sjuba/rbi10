/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import java.util.ArrayList;
import java.util.Stack;
import processor.in.SNull;
import reporter.SReporterDataSource;
import reporter.SReporterParam;
import reporter.SReporterProcessor;
import reporter.SReporterUtils;
import reporter.xml.SElementReportElement;
import reporter.xml.SElementReportElementExpr;
import reporter.xml.SEnumBoolean;
import reporter.xml.SEnumDataSourceOperation;
import reporter.xml.SEnumDataType;
import reporter.xml.SEnumElementExprOperator;
import reporter.xml.SEnumElementExprType;
import reporter.xml.SEnumElementType;
import sa.lib.SLibUtils;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alphalapz
 */
public class SReportElement {

    private final SReporterProcessor moReporterProcessor;
    private final SElementReportElement moXmlElement;
    private final ArrayList<SReportElementExpr> maElementExprs;
    private Object moValue;

    public SReportElement(final SReporterProcessor reporterProcessor, final SElementReportElement xmlElement) throws Exception {
        moReporterProcessor = reporterProcessor;
        moXmlElement = xmlElement;

        // create expressions of current element:
        maElementExprs = new ArrayList<>();
        for (SXmlElement element : moXmlElement.getXmlElements()) {
            maElementExprs.add(new SReportElementExpr(moReporterProcessor, (SElementReportElementExpr) element));
        }

        // compute value of current elemement:
        computeValue();
    }

    /*
     * Private methods:
     */
    private Object computeValueConst() throws Exception {
        return maElementExprs.get(0).computeValue();
    }

    private Object computeValueFormula() throws Exception {
        int operands = 0;
        boolean isUniqueOperandRequired = false;
        Object computedValue = null;

        // proceed implementing RPN:
        Stack<Double> stack = new Stack<>();

        for (SReportElementExpr expr : maElementExprs) {
            operands++;

            SEnumElementExprType exprType = SEnumElementExprType.valueOf(expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_TYPE).getValue().toString());
            SEnumDataType exprDataType = null;
            String exprValue = expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_VALUE).getValue() == null ? new SNull().toString() : expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_VALUE).getValue().toString();
            Object value = null;
            SReportElement element = null;
            SReporterParam param = null;
            SReporterDataSource dataSource = null;

            switch (exprType) {
                case OPERAND_CONST:
                case OPERAND_ELEMENT:
                case OPERAND_PARAM:
                case OPERAND_DATA:

                    switch (exprType) {
                        case OPERAND_CONST:
                            exprDataType = SEnumDataType.valueOf(expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_DATA_TYPE).getValue().toString());
                            value = SReporterUtils.castDataType(exprDataType, exprValue);
                            break;
                        case OPERAND_ELEMENT:
                            element = moReporterProcessor.getReportElement(exprValue);
                            exprDataType = element.getDataType();
                            value = element.getValue();
                            break;
                        case OPERAND_PARAM:
                            param = moReporterProcessor.getReporterTemplate().getParam(exprValue);
                            exprDataType = param.getDataType();
                            value = param.getValue();
                            break;
                        case OPERAND_DATA:
                            String dataSourceName = expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_DS_NAME).getValue().toString();
                            String columnName = expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_DS_COL_NAME).getValue().toString();
                            dataSource = moReporterProcessor.getReporterTemplate().getDataSource(dataSourceName);
                            exprDataType = dataSource.getColumnDataType(columnName);
                            value = moReporterProcessor.getDataContainer(dataSourceName).getValue(
                                    columnName,
                                    expr.getElementExprFilters(),
                                    SEnumDataSourceOperation.valueOf(expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_DS_OPERATION).getValue().toString()));
                            break;
                        default:
                    }

                    // check if only one parameter is allowed in formula:
                    if (!isUniqueOperandRequired) {
                        isUniqueOperandRequired = !(value instanceof Number);
                    }

                    if (isUniqueOperandRequired && operands > 1) {
                        throw new Exception("Invalid formula!");
                    }

                    if (value instanceof Number) {
                        stack.push((Double) ((Number) value).doubleValue());
                    }

                    break;
                case OPERATOR:
                    double d1 = stack.pop();
                    double d2 = stack.pop();

                    switch (SEnumElementExprOperator.valueOf(exprValue)) {
                        case ADDITION:
                            stack.push(d1 + d2);
                            break;
                        case SUBTRACTION:
                            stack.push(d1 - d2);
                            break;
                        case MULTIPLICATION:
                            stack.push(d1 * d2);
                            break;
                        case DIVISION:
                            stack.push(d1 / d2);
                            break;
                        default:
                    }

                    break;
                default:
            }
        }

        return stack.pop();
    }

    private void computeValue() throws Exception {
        switch (SEnumElementType.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_TYPE).getValue().toString())) {
            case CONST:
                moValue = computeValueConst();
                break;
            case FORMULA:
                moValue = computeValueFormula();
                break;
            default:
        }
    }

    /*
     * Public methods:
     */
    public SElementReportElement getXmlElement() {
        return moXmlElement;
    }

    /*
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
     */

    public int getColumnNumber() {
        return SLibUtils.parseInt(moXmlElement.getAttribute(SElementReportElement.ATTRIB_COL_NUM).getValue().toString());
    }

    public int getRowNumber() {
        return SLibUtils.parseInt(moXmlElement.getAttribute(SElementReportElement.ATTRIB_ROW_NUM).getValue().toString());
    }

    public String getName() {
        return moXmlElement.getAttribute(SElementReportElement.ATTRIB_NAME).getValue().toString();
    }

    public SEnumElementType getType() {
        return SEnumElementType.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_TYPE).getValue().toString());
    }

    public SEnumDataType getDataType() {
        return SEnumDataType.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_DATA_TYPE).getValue().toString());
    }

    public boolean isBold() {
        return SEnumBoolean.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_FONT_BOLD).getValue().toString()) == SEnumBoolean.TRUE;
    }

    public Object getValue() {
        return moValue;
    }
}
