/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.out;

import processor.in.SDataRow;
import reporter.SReporterProcessor;
import reporter.SReporterUtils;
import reporter.xml.SElementReportElementExprFilter;
import reporter.xml.SEnumDataType;
import reporter.xml.SEnumFilterComparison;
import reporter.xml.SEnumFilterTermType;

/**
 *
 * @author Alfredo Pérez
 */
public class SReportElementExprFilter {
    
    private final SReporterProcessor moReporterProcessor;
    private final SElementReportElementExprFilter moXmlExprFilter;
    
    public SReportElementExprFilter(final SReporterProcessor reporterProcessor, final SElementReportElementExprFilter xmlExprFilter) {
        moReporterProcessor = reporterProcessor;
        moXmlExprFilter = xmlExprFilter;
    }
    
    /*
     * Private methods:
     */
    
    private Object computeValue(final SDataRow dataRow, SEnumFilterTermType filterTermType, SEnumDataType filterDataType, String filterValue) throws Exception {
        Object computedValue = null;
        
        switch (filterTermType) {
            case CONST:
                computedValue = SReporterUtils.castDataType(filterDataType, filterValue);
                break;
            case ELEMENT:
                computedValue = moReporterProcessor.getReportElement(filterValue).getValue();
                break;
            case PARAM:
                computedValue = moReporterProcessor.getReporterTemplate().getParam(filterValue).getValue();
                break;
            case DATA:
                computedValue = dataRow.getValue(filterValue);
                break;
            default:
        }
        
        return computedValue;
    }
    
    /*
     * Public methods:
     */
    
    public SElementReportElementExprFilter getExprFilter() {
        return moXmlExprFilter;
    }
    
    /**
     * Checks expression filter on supplied data row.
     * @param dataRow Supplied data row to check expression filter.
     * @return Check result.
     * @throws Exception 
     */
    public boolean checkExprFilter(final SDataRow dataRow) throws Exception {
        String dataType1 = moXmlExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM1_DATA_TYPE).getValue().toString();
        Object value1 = computeValue(
                dataRow, 
                SEnumFilterTermType.valueOf(moXmlExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM1_TYPE).getValue().toString()), 
                dataType1.isEmpty() ? null : SEnumDataType.valueOf(dataType1), 
                moXmlExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM1_VALUE).getValue().toString());
        
        String dataType2 = moXmlExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM2_DATA_TYPE).getValue().toString();
        Object value2 = computeValue(
                dataRow, 
                SEnumFilterTermType.valueOf(moXmlExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM2_TYPE).getValue().toString()), 
                dataType2.isEmpty() ? null : SEnumDataType.valueOf(dataType2), 
                moXmlExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_TERM2_VALUE).getValue().toString());
        
        return SReporterUtils.compareValues(
                value1, 
                value2, 
                SEnumFilterComparison.valueOf(moXmlExprFilter.getAttribute(SElementReportElementExprFilter.ATTRIB_COMPARISON).getValue().toString()));
    }
}
