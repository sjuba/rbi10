/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import java.util.ArrayList;
import reporter.SReporterProcessor;
import reporter.SReporterUtils;
import reporter.xml.SElementReportElementExpr;
import reporter.xml.SElementReportElementExprFilter;
import reporter.xml.SEnumDataType;
import reporter.xml.SEnumElementExprType;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alphalapz
 */
public class SReportElementExpr {
    
    private final SReporterProcessor moReporterProcessor;
    private final SElementReportElementExpr moXmlExpr;
    private final ArrayList<SReportElementExprFilter> maElementExprFilters;
    
    public SReportElementExpr(final SReporterProcessor reporterProcessor, final SElementReportElementExpr xmlExpr) {
        moReporterProcessor = reporterProcessor;
        moXmlExpr = xmlExpr;
        
        // create filters of current element expression:
        maElementExprFilters = new ArrayList<>();
        for (SXmlElement element : moXmlExpr.getXmlElements()) {
            maElementExprFilters.add(new SReportElementExprFilter(reporterProcessor, (SElementReportElementExprFilter) element));
        }
    }
    
    /*
     * Private methods:
     */
    
    /*
     * Public methods:
     */
    
    public SElementReportElementExpr getXmlExpr() {
        return moXmlExpr;
    }

    public ArrayList<SReportElementExprFilter> getElementExprFilters() {
        return maElementExprFilters;
    }

    public Object computeValue() throws Exception {
        Object computedValue = null;
        
        switch (SEnumElementExprType.valueOf(moXmlExpr.getAttribute(SElementReportElementExpr.ATTRIB_TYPE).getValue().toString())) {
            case OPERAND_CONST:
                computedValue = SReporterUtils.castDataType(
                        SEnumDataType.valueOf(moXmlExpr.getAttribute(SElementReportElementExpr.ATTRIB_DATA_TYPE).getValue().toString()),
                        moXmlExpr.getAttribute(SElementReportElementExpr.ATTRIB_VALUE).getValue().toString());
                break;
            case OPERAND_ELEMENT:
                computedValue = moReporterProcessor.getReportElement(moXmlExpr.getAttribute(SElementReportElementExpr.ATTRIB_VALUE).getValue().toString()).getValue();
                break;
            case OPERAND_PARAM:
                computedValue = moReporterProcessor.getReporterTemplate().getParam(moXmlExpr.getAttribute(SElementReportElementExpr.ATTRIB_VALUE).getValue().toString()).getValue();
                break;
            case OPERAND_DATA:
                break;
            case OPERATOR:
                break;
            default:
        }
        
        return computedValue;
    }
}
