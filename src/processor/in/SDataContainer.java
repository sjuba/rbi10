/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.in;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import processor.out.SReportElementExprFilter;
import reporter.SReporterDataSource;
import reporter.SReporterProcessor;
import reporter.SReporterUtils;
import reporter.xml.SEnumDataSourceOperation;
import sa.lib.db.SDbConsts;
import sa.lib.db.SDbDatabase;

/**
 *
 * @author Alphalapz
 */
public class SDataContainer {

    private final SReporterProcessor moReporterProcessor;
    private final SReporterDataSource moReporterDataSource;
    private final ArrayList<SDataRow> maRows;

    public SDataContainer(final SReporterProcessor reporterProcessor, final SReporterDataSource reporterDataSource) throws Exception {
        moReporterProcessor = reporterProcessor;
        moReporterDataSource = reporterDataSource;
        maRows = new ArrayList<>();
        extractData();
    }

    /*
     * Private methods:
     */
    private void extractDataMySql() throws SQLException, Exception {
        SDbDatabase database = new SDbDatabase(SDbConsts.DBMS_MYSQL);
        database.connect(moReporterDataSource.getHost(), moReporterDataSource.getPort(), moReporterDataSource.getDatabase(), moReporterDataSource.getUser(), moReporterDataSource.getPassword());

        Statement statement = database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(moReporterDataSource.composeQuery());
        ResultSetMetaData metaData = resultSet.getMetaData();

        maRows.clear();

        while (resultSet.next()) {
            ArrayList<Object> values = new ArrayList<>();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String value = resultSet.getString(i);
                values.add(value == null ? new SNull() : SReporterUtils.castDataType(moReporterDataSource.getColumns().get(i - 1).getDataType(), value));
            }
            maRows.add(new SDataRow(moReporterProcessor, moReporterDataSource, values));
        }
    }

    private void extractData() throws Exception {
        switch (moReporterDataSource.getDataSourceType()) {
            case MYSQL:
                extractDataMySql();
                break;
            default:
        }
    }

    /*
     * Public methods:
     */
    /**
     * Gets data source name.
     *
     * @return
     */
    public String getName() {
        return moReporterDataSource.getName();
    }

    /**
     * Gets value.
     *
     * @param columnName Column name.
     * @param filters Required filters.
     * @param operation Data source operation.
     * <br>When <code>SEnumDataSourceOperation.NONE</code>, then only a value
     * from first row is retriebed.
     * <br>When <code>SEnumDataSourceOperation.SUM</code>, then a sum of values
     * of all rows that satisfy filters is retriebed.
     * @return
     * @throws Exception
     */
    public Object getValue(final String columnName, final ArrayList<SReportElementExprFilter> filters, final SEnumDataSourceOperation operation) throws Exception {
        Object value = null;
        double valueDouble = 0d;

        for (SDataRow row : maRows) {
            if (row.satisfiesFilters(filters)) {
                switch (operation) {
                    case NONE:
                        value = row;
                        break;
                    case SUM:
                        valueDouble = valueDouble + Double.parseDouble(row.getValue(columnName).toString());
                        value = valueDouble;
                        break;
                    default:
                }

            }
        }
        return value;
    }
}
