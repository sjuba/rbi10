/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import java.util.ArrayList;
import java.util.Stack;
import processor.in.SDataContainer;
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
import reporter.xml.SEnumFontAlign;
import sa.lib.SLibUtils;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
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
    private void computeValueConst() throws Exception {
        moValue = maElementExprs.get(0).computeValue();
    }

    private void computeValueFormula() throws Exception {
        int tokens = 0;
        boolean isNonNumberValue = false;
        Object value = null;

        // proceed implementing RPN:
        Stack<Double> stack = new Stack<>();

        for (SReportElementExpr expr : maElementExprs) {
            tokens++;

            SEnumElementExprType exprType = SEnumElementExprType.valueOf(expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_TYPE).getValue().toString());
            SEnumDataType exprDataType = null;
            String exprValue = expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_VALUE).getValue() == null ? "" : expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_VALUE).getValue().toString();
            SReportElement element = null;
            SReporterParam param = null;
            SDataContainer dataContainer = null;

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
                            dataContainer = moReporterProcessor.getDataContainer(dataSourceName);
                            exprDataType = dataContainer.getReporterDataSource().getColumnDataType(columnName);
                            value = dataContainer.getValue(
                                    columnName,
                                    expr.getElementExprFilters(),
                                    SEnumDataSourceOperation.valueOf(expr.getXmlExpr().getAttribute(SElementReportElementExpr.ATTRIB_DS_OPERATION).getValue().toString()));
                            break;
                        default:
                    }

                    if (value instanceof Number) {
                        stack.push(((Number) value).doubleValue());
                    }
                    else {
                        isNonNumberValue = true;
                        if (tokens > 1) {
                            throw new Exception("Invalid formula: multiple non-numeric operands are not allowed!");
                        }
                    }

                    break;
                    
                case OPERATOR:
                    double d1 = stack.pop();
                    double d2 = stack.pop();

                    switch (SEnumElementExprOperator.valueOf(exprValue)) {
                        case ADDITION:
                            stack.push(d2 + d1);
                            break;
                        case SUBTRACTION:
                            stack.push(d2 - d1);
                            break;
                        case MULTIPLICATION:
                            stack.push(d2 * d1);
                            break;
                        case DIVISION:
                            stack.push(d2 / d1);
                            break;
                        default:
                    }

                    break;

                default:
            }
        }

        if (isNonNumberValue) {
            moValue = value;
        }
        else {
            moValue = stack.pop();
        }
    }

    private void computeValue() throws Exception {
        switch (SEnumElementType.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_TYPE).getValue().toString())) {
            case CONST:
                computeValueConst();
                break;
            case FORMULA:
                computeValueFormula();
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

    public String getDataFormat() {
        return moXmlElement.getAttribute(SElementReportElement.ATTRIB_DATA_FORMAT).getValue().toString();
    }

    public String getFontType() {
        return moXmlElement.getAttribute(SElementReportElement.ATTRIB_FONT_TYPE).getValue().toString();
    }

    public short getFontSize() {
        return (short) SLibUtils.parseInt(moXmlElement.getAttribute(SElementReportElement.ATTRIB_FONT_SIZE).getValue().toString());
    }

    public boolean isBold() {
        return SEnumBoolean.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_FONT_BOLD).getValue().toString()) == SEnumBoolean.TRUE;
    }

    public boolean isCursive() {
        return SEnumBoolean.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_FONT_CURSIVE).getValue().toString()) == SEnumBoolean.TRUE;
    }

    public byte isUnderline() {
        return (byte) (SEnumBoolean.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_FONT_UNDERLINE).getValue().toString()) == SEnumBoolean.TRUE ? 1 : 0);
    }

    public SEnumFontAlign getFontAlign() {
        return SEnumFontAlign.valueOf(moXmlElement.getAttribute(SElementReportElement.ATTRIB_FONT_ALIGN).getValue().toString());
    }

    public Object getValue() {
        return moValue;
    }
}
