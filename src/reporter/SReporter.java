/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import reporter.xml.SEnumReportType;
import java.util.HashMap;
import processor.out.SGeneratorExcel;

/**
 *
 * @author Alphalapz
 */
public class SReporter {

    private SReporterProcessor moReporterProcessor;

    public SReporter() {
        
    }

    /*
     * Public methods:
     */
    public void generateReport(final String fileTemplate, final String fileReport, final SEnumReportType reportType, final HashMap<String, SUserParam> params, final HashMap<String, SUserDataSource> dataSources) throws Exception {
        if (fileTemplate == null || fileTemplate.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument file name!");
        }

        if (fileReport == null || fileReport.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument file report!");
        }

        if (reportType == null) {
            throw new IllegalArgumentException("Invalid argument report type!");
        }

        if (params == null) {
            throw new IllegalArgumentException("Invalid argument params!");
        }

        if (dataSources == null) {
            throw new IllegalArgumentException("Invalid argument data sources!");
        }

        moReporterProcessor = new SReporterProcessor(new SReporterTemplate(fileTemplate, params, dataSources));
        
        switch (reportType) {
            case EXCEL:
                SGeneratorExcel reportGeneratorExcel = new SGeneratorExcel(moReporterProcessor);
                reportGeneratorExcel.process(fileReport);
                break;
            default:
        }
    }
}
