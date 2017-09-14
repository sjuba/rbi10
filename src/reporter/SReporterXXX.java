/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import java.io.IOException;
import java.util.HashMap;
import reporter.xml.SEnumReportType;

/**
 *
 * @author Alphalapz
 */
public class SReporterXXX {

    /**
     *
     * @param args
     * @throws IOException
     * @throws Exception
     */
    public static void main(String[] args) throws IOException, Exception {

        // user params for test:
        HashMap<String, SUserParam> userParams = new HashMap<>();

//        SUserParam up1 = new SUserParam("valor",            SEnumDataType.STRING,       "persona");
//        SUserParam up2 = new SUserParam("inicio",           SEnumDataType.LONG,         150L);
//        SUserParam up3 = new SUserParam("fin",              SEnumDataType.DOUBLE,       1500.85);
//        SUserParam up4 = new SUserParam("StringparamName4", SEnumDataType.DOUBLE,       666d);
//        SUserParam up5 = new SUserParam("StringparamName5", SEnumDataType.DATE,         new Date());
//        SUserParam up6 = new SUserParam("StringparamName1.1", SEnumDataType.BOOLEAN, null);
//        SUserParam up7 = new SUserParam("StringparamName2.1", SEnumDataType.LONG, null);
//        SUserParam up8 = new SUserParam("StringparamName3.1", SEnumDataType.DOUBLE, null);
//        SUserParam up9 = new SUserParam("StringparamName4.1", SEnumDataType.STRING, null);
//        SUserParam up10 = new SUserParam("StringparamName5.1", SEnumDataType.DATE, null);
//
//        userParams.put(up1.getName(), up1);
//        userParams.put(up2.getName(), up2);
//        userParams.put(up3.getName(), up3);
//        userParams.put(up4.getName(), up4);
//        userParams.put(up5.getName(), up5);
//        userParams.put(up6.getName(), up6);
//        userParams.put(up7.getName(), up7);
//        userParams.put(up8.getName(), up8);
//        userParams.put(up9.getName(), up9);
//        userParams.put(up10.getName(), up10);

        // user data sources for test:
        HashMap<String, SUserDataSource> userDataSources = new HashMap<>();

//        SUserDataSource uds1 = new SUserDataSource("PERSONA", SEnumDataSourceType.MYSQL, "127.0.0.1", "3306", "ALV", "root", "msroot");

//        userDataSources.put(uds1.getName(), uds1);

        SReporter reporter = new SReporter();
        reporter.generateReport("xml2.xml", "MyXmlFile.xls", SEnumReportType.EXCEL, userParams, userDataSources);

    }
}
