/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import reporter.xml.SEnumDataType;
import reporter.xml.SElementDataSourceColumn;

/**
 *
 * @author Alfredo Pérez
 */
public class SReporterDataSourceColumn {
    
    private final String msName;
    private final SEnumDataType meDataType;
    
    /**
     * Creates a reporter data source column.
     * @param name Column name.
     * @param dataType Column data type.
     * @throws Exception 
     */
    public SReporterDataSourceColumn(final String name, final SEnumDataType dataType) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument name!");
        }
        
        if (dataType == null) {
            throw new IllegalArgumentException("Invalid argument data type!");
        }
        
        msName = name;
        meDataType = dataType;
    }

    /**
     * Creates a reporter data source column from XML element data source column.
     * @param elementDataSourceColumn XML element data source column.
     * @throws Exception 
     */
    public SReporterDataSourceColumn(final SElementDataSourceColumn elementDataSourceColumn) throws Exception {
        this(elementDataSourceColumn.getAttribute(SElementDataSourceColumn.ATTRIB_NAME).getValue().toString(),
                SEnumDataType.valueOf(elementDataSourceColumn.getAttribute(SElementDataSourceColumn.ATTRIB_DATA_TYPE).getValue().toString()));
    }

    public String getName() {
        return msName;
    }

    public SEnumDataType getDataType() {
        return meDataType;
    }
}
