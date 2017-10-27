/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.in;

import java.util.ArrayList;
import java.util.Stack;
import processor.out.SReportElementExprFilter;
import reporter.SReporterDataSource;
import reporter.SReporterProcessor;
import reporter.xml.SElementReportElementExprFilter;
import reporter.xml.SEnumFilterOperator;
import reporter.xml.SEnumFilterType;

/**
 *
 * @author Alfredo Pérez
 */
public class SDataRow {

    private final SReporterProcessor moReporterProcessor;
    private final SReporterDataSource moReporterDataSource;
    private final ArrayList<Object> maValues;

    public SDataRow(final SReporterProcessor reporterProcessor, final SReporterDataSource reporterDataSource, final ArrayList<Object> values) {
        moReporterProcessor = reporterProcessor;
        moReporterDataSource = reporterDataSource;
        maValues = values;
    }

    /*
     * Private methods:
     */
    
    /*
     * Public methods:
     */
    
    /**
     * Gets value by column name.
     * @param columnName Desired column name.
     * @return Value.
     */
    public Object getValue(final String columnName) {
        Integer index = moReporterDataSource.getColumnIndex(columnName);

        if (index == null) {
            throw new IllegalArgumentException("Invalid column name '" + columnName + "'.");
        }

        return maValues.get(index);
    }
    
    public boolean satisfiesFilters(final ArrayList<SReportElementExprFilter> filters) throws Exception {
        // proceed implementing RPN:
        Stack<Boolean> stack = new Stack<>();
        
        for (SReportElementExprFilter filter : filters) {
            switch (SEnumFilterType.valueOf(filter.getExprFilter().getAttribute(SElementReportElementExprFilter.ATTRIB_TYPE).getValue().toString())) {
                case OPERAND:
                    stack.push(filter.checkExprFilter(this));
                    break;
                    
                case OPERATOR:
                    boolean b1 = stack.pop();
                    boolean b2 = stack.pop();
                    
                    switch (SEnumFilterOperator.valueOf(filter.getExprFilter().getAttribute(SElementReportElementExprFilter.ATTRIB_OPERATOR).getValue().toString())) {
                        case AND:
                            stack.push(b1 && b2);
                            break;
                        case OR:
                            stack.push(b1 || b2);
                            break;
                        default:
                    }
                    break;
                    
                default:
            }
        }
        
        if (stack.size() != 1) {
            throw new Exception("Invalid end of processing of RPN!");
        }
        
        return stack.pop();
    }
}
