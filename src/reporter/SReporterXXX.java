/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import reporter.xml.SEnumDataType;
import reporter.xml.SEnumReportType;
import sa.lib.SLibTimeUtils;

/**
 *
 * @author Alfredo Pérez
 */
public class SReporterXXX {

    /**
     *
     * @param args
     * @throws IOException
     * @throws Exception
     */
    public static void main(String[] args) throws IOException, Exception {

        HashMap<String, SUserParam> userParams = new HashMap<>();
        String[] months = SLibTimeUtils.createMonthsOfYearStd(Calendar.LONG);
            userParams.put("tFechaIni", new SUserParam("tFechaIni", SEnumDataType.STRING, "2017-09-01"));
            userParams.put("tFechaFin", new SUserParam("tFechaFin", SEnumDataType.STRING, "2017-09-30"));
            userParams.put("sMes", new SUserParam("sMes", SEnumDataType.STRING, "AGOSTO"));
            userParams.put("sCuentaTraspasoCompras", new SUserParam("sCuentaTraspasoCompras", SEnumDataType.STRING, "5100-9999-0000"));
            userParams.put("sCuentaGastosProd", new SUserParam("sCuentaGastosProd", SEnumDataType.STRING, "6000-0003-0000"));
            userParams.put("sCentroCostoRefacturacion", new SUserParam("sCentroCostoRefacturacion", SEnumDataType.STRING, "900-00-00-000"));
            userParams.put("sItemTraspasoGastosProd", new SUserParam("sItemTraspasoGastosProd", SEnumDataType.STRING, "GP9998"));

        HashMap<String, SUserDataSource> userDataSources = new HashMap<>();

        SReporter reporter = new SReporter();
        reporter.generateReport("C:\\Users\\USER\\Documents\\NetBeansProjects\\siie32\\reps\\rbi_fin_statements.xml", "MyXmlFile.xls", SEnumReportType.EXCEL, userParams, userDataSources);

    }
}
