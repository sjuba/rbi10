/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import reporter.xml.SElementDataSource;
import reporter.xml.SEnumDataSourceType;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import reporter.xml.SElementDataSourceColumn;
import reporter.xml.SEnumDataType;
import sa.lib.SLibUtils;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
 */
public class SReporterDataSource extends SUserDataSource implements SArgumentInterface {
    
    private static final String PARAM_PREFIX = "$P";
    
    private final String msRawQuery;
    private final ArrayList<SReporterDataSourceColumn> maColumns;
    private final HashMap<String, Integer> moColumnIndexes;
    private final HashMap<String, SEnumDataType> moColumnDataTypes;
    private final HashMap<String, SReporterParam> moParamsMap;

    /**
     * Creates a reporter data source from user data source.
     * @param userDataSource User data source.
     * @param rawQuery Raw query.
     * @param columns Columns.
     * @param paramsMap HashMap (String, SReporterParam).
     * @throws Exception 
     */
    public SReporterDataSource(final SUserDataSource userDataSource, final String rawQuery, ArrayList<SReporterDataSourceColumn> columns, final HashMap<String, SReporterParam> paramsMap) throws Exception {
        super(userDataSource);
        
        msRawQuery = rawQuery;
        maColumns = new ArrayList<>(columns);
        moColumnIndexes = createColumnIndexes();
        moColumnDataTypes = createColumnDataTypes();
        moParamsMap = paramsMap;
    }

    /**
     * Creates a reporter data source from XML element data source.
     * @param elementDataSource XML element data source.
     * @param paramsMap HashMap (String, SReporterParam).
     * @throws Exception 
     */
    public SReporterDataSource(final SElementDataSource elementDataSource, HashMap<String, SReporterParam> paramsMap) throws Exception {
        super(
                elementDataSource.getAttribute(SElementDataSource.ATTRIB_NAME).getValue().toString(),
                SEnumDataSourceType.valueOf(elementDataSource.getAttribute(SElementDataSource.ATTRIB_TYPE).getValue().toString()),
                elementDataSource.getAttribute(SElementDataSource.ATTRIB_HOST).getValue().toString(),
                elementDataSource.getAttribute(SElementDataSource.ATTRIB_PORT).getValue().toString(),
                elementDataSource.getAttribute(SElementDataSource.ATTRIB_DATABASE).getValue().toString(),
                elementDataSource.getAttribute(SElementDataSource.ATTRIB_USER).getValue().toString(),
                elementDataSource.getAttribute(SElementDataSource.ATTRIB_PASSWORD).getValue().toString()
        );

        msRawQuery = elementDataSource.getAttribute(SElementDataSource.ATTRIB_QUERY).getValue().toString();
        maColumns = createColumns(elementDataSource);
        moColumnIndexes = createColumnIndexes();
        moColumnDataTypes = createColumnDataTypes();
        moParamsMap = paramsMap;
    }

    /*
     * Private methods:
     */

    private ArrayList<SReporterDataSourceColumn> createColumns(final SElementDataSource elementDataSource) throws Exception {
        ArrayList<SReporterDataSourceColumn> columns = new ArrayList<>();
        
        for (SXmlElement element : elementDataSource.getXmlElements()) {
            columns.add(new SReporterDataSourceColumn((SElementDataSourceColumn) element));
        }
        
        return columns;
    }

    private HashMap<String, Integer> createColumnIndexes() throws Exception {
        HashMap<String, Integer> map = new HashMap<>();

        for (int index = 0; index < maColumns.size(); index++) {
            String name = maColumns.get(index).getName();
            if (map.get(name) == null) {
                map.put(name, index);
            }
            else {
                throw new Exception("Column name '" + name + "' already exists.");
            }
        }
        
        return map;
    }
    
    private HashMap<String, SEnumDataType> createColumnDataTypes() throws Exception {
        HashMap<String, SEnumDataType> map = new HashMap<>();

        for (SReporterDataSourceColumn column: maColumns) {
            String name = column.getName();
            if (map.get(name) == null) {
                map.put(name, column.getDataType());
            }
            else {
                throw new Exception("Column name '" + name + "' already exists.");
            }
        }
        
        return map;
    }
    
    private String composeQueryMySql() throws Exception {
        String query = msRawQuery;
        
        // while parameter prefix exists in query:
        while (query.contains(PARAM_PREFIX)) {
            int paramStart = query.indexOf(PARAM_PREFIX);
            int paramEnd = query.indexOf("}");
            String param = query.substring(paramStart, paramEnd + 1);
            String paramName = param.substring(PARAM_PREFIX.length() + 1, param.length() - 1);
            SReporterParam reporterParam = moParamsMap.get(paramName);
            
            if (reporterParam == null) {
                throw new Exception("Invalid parameter name '" + paramName + "'.");
            }
            
            String value = "";
            switch (reporterParam.getDataType()) {
                case BOOLEAN:
                    value = (Boolean) reporterParam.getValue() ? "1" : "0";
                    break;
                case LONG:
                    value = "" + (Long) reporterParam.getValue();
                    break;
                case DOUBLE:
                    value = "" + (Double) reporterParam.getValue();
                    break;
                case STRING:
                    value = "'" + ((String) reporterParam.getValue()) + "'";
                    break;
                case DATE:
                    value = "'" + SLibUtils.DbmsDateFormatDate.format((Date) reporterParam.getValue()) + "'";
                    break;
                case DATETIME:
                    value = "'" + SLibUtils.DbmsDateFormatDatetime.format((Date) reporterParam.getValue()) + "'";
                    break;
                case TIME:
                    value = "'" + SLibUtils.DbmsDateFormatTime.format((Date) reporterParam.getValue()) + "'";
                    break;
                default:
            }

            query = query.replace(param, value);
        }

        return query;
    }

    /*
     * Public methods:
     */

    public String getRawQuery() {
        return msRawQuery;
    }

    public ArrayList<SReporterDataSourceColumn> getColumns() {
        return maColumns;
    }

    /**
     * Gets column index of column name.
     * @param columnName Desired column name.
     * @return Column's index.
     */
    public int getColumnIndex(final String columnName) {
        return moColumnIndexes.get(columnName);
    }
    
    /**
     * Gets column data type of column name.
     * @param columnName Desired column name.
     * @return Column's data type.
     */
    public SEnumDataType getColumnDataType(final String columnName) {
        return moColumnDataTypes.get(columnName);
    }
    
    /**
     * Composes a ready-to-use query from raw query.
     * @return Ready-to-use query.
     * @throws Exception 
     */
    public String composeQuery() throws Exception {
        String query = null;
        
        switch (meDataSourceType) {
            case MYSQL:
                query = composeQueryMySql();
                break;
            default:
        }
        
        return query;
    }

    @Override
    public boolean isComplete() {
        return !msHost.isEmpty() && !msPort.isEmpty() && !msDatabase.isEmpty() && !msUser.isEmpty() && !msPassword.isEmpty() && !msRawQuery.isEmpty() && !maColumns.isEmpty();
    }

    @Override
    public void combine(final SXmlElement element) throws Exception {
        if (!(element instanceof SElementDataSource)) {
            throw new IllegalArgumentException("Invalid argument data!");
        }
        
        SElementDataSource elementDataSource = (SElementDataSource) element;
        
        if (msName.compareTo(elementDataSource.getAttribute(SElementDataSource.ATTRIB_NAME).getValue().toString()) != 0) {
            throw new IllegalArgumentException("Attribute '" + SElementDataSource.ATTRIB_NAME + "' does not match!");
        }
        
        if (meDataSourceType != SEnumDataSourceType.valueOf(elementDataSource.getAttribute(SElementDataSource.ATTRIB_TYPE).getValue().toString())) {
            throw new IllegalArgumentException("Attribute '" + SElementDataSource.ATTRIB_TYPE + "' does not match!");
        }

        if (msHost == null || msHost.isEmpty()) {
            String host = elementDataSource.getAttribute(SElementDataSource.ATTRIB_HOST).getValue().toString();
            if (host == null || host.isEmpty()) {
                throw new IllegalArgumentException("Attribute '" + SElementDataSource.ATTRIB_HOST + "' does not match!");
            }
            else {
                msHost = host;
            }
        }
        
        if (msPort == null || msPort.isEmpty()) {
            String port = elementDataSource.getAttribute(SElementDataSource.ATTRIB_PORT).getValue().toString();
            if (port == null || port.isEmpty()) {
                throw new IllegalArgumentException("Attribute '" + SElementDataSource.ATTRIB_PORT+ "' does not match!");
            }
            else {
                msPort = port;
            }
        }
        
        if (msDatabase == null || msDatabase.isEmpty()) {
            String database = elementDataSource.getAttribute(SElementDataSource.ATTRIB_DATABASE).getValue().toString();
            if (database == null || database.isEmpty()) {
                throw new IllegalArgumentException("Attribute '" + SElementDataSource.ATTRIB_DATABASE + "' does not match!");
            }
            else {
                msDatabase = database;
            }
        }
        
        if (msUser == null || msUser.isEmpty()) {
            String user = elementDataSource.getAttribute(SElementDataSource.ATTRIB_USER).getValue().toString();
            if (user == null || user.isEmpty()) {
                throw new IllegalArgumentException("Attribute '" + SElementDataSource.ATTRIB_USER + "' does not match!");
            }
            else {
                msUser = user;
            }
        }
        
        if (msPassword == null || msPassword.isEmpty()) {
            String password = elementDataSource.getAttribute(SElementDataSource.ATTRIB_PASSWORD).getValue().toString();
            if (password == null || password.isEmpty()) {
                throw new IllegalArgumentException("Attribute '" + SElementDataSource.ATTRIB_PASSWORD + "' does not match!");
            }
            else {
                msPassword = password;
            }
        }
    }
}
