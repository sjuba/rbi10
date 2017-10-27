/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import java.util.HashMap;
import reporter.xml.SElementDataSource;
import reporter.xml.SElementParam;
import reporter.xml.STemplate;
import sa.lib.xml.SXmlElement;
import sa.lib.xml.SXmlUtils;

/**
 *
 * @author Alfredo Pérez
 */
public class SReporterTemplate {
    
    private final STemplate moTemplate;
    private final HashMap<String, SReporterParam> moParamsMap;              // all parameters have name, so them can be stored in hash map
    private final HashMap<String, SReporterDataSource> moDataSourcesMap;    // all data sources have name, so them can be stored in hash map
    
    public SReporterTemplate(final String fileTemplate, final HashMap<String, SUserParam> userParams, final HashMap<String, SUserDataSource> userDataSources) throws Exception {
        moTemplate = new STemplate();
        moTemplate.processXml(SXmlUtils.readXml(fileTemplate));
        
        moParamsMap = new HashMap<>();
        moDataSourcesMap = new HashMap<>();
        
        processReportParams(userParams);
        processReportDataSources(userDataSources);
    }

    /*
     * Private methods:
     */
    /**
     * Validates and creates reporter parameters map. Must be called right
     * before call to method processReportDataSources().
     *
     * @param userParams User parameters.
     * @throws Exception
     */
    private void processReportParams(final HashMap<String, SUserParam> userParams) throws Exception {
        // validate user params:
        for (SXmlElement element : moTemplate.getElementParams().getXmlElements()) {
            SElementParam elementParam = (SElementParam) element;
            String paramName = elementParam.getAttribute(SElementParam.ATTRIB_NAME).getValue().toString();
            
            SUserParam userParam = userParams.get(paramName);
            if (userParam != null) {
                // element param exists in user params:
                SReporterParam reporterParam = new SReporterParam(userParam);
                
                if (reporterParam.isComplete()) {
                    // user param is complete:
                    moParamsMap.put(paramName, reporterParam);
                }
                else {
                    reporterParam.combine(elementParam);
                    if (reporterParam.isComplete()) {
                        // user param is complete:
                        moParamsMap.put(paramName, reporterParam);
                    }
                    else {
                        throw new Exception("El parámetro '" + paramName + "' no pudo ser completado.");
                    }
                }
            }
            else {
                // element param does not exist in user params:
                SReporterParam reporterParam = new SReporterParam(elementParam);
                if (reporterParam.isComplete()) {
                    // user param is complete:
                    moParamsMap.put(paramName, reporterParam);
                }
                else {
                    throw new Exception("El parámetro '" + paramName + "' no existe.");
                }
            }
        }
    }

    /**
     * Validate and creates reporter data sources map. Must be called right
     * after call to method processReportParams().
     *
     * @param userDataSources User data sources.
     * @throws Exception
     */
    private void processReportDataSources(final HashMap<String, SUserDataSource> userDataSources) throws Exception {
        // validate user data sources:
        for (SXmlElement element : moTemplate.getElementDataSources().getXmlElements()) {
            SElementDataSource elementDataSource = (SElementDataSource) element;
            String dataSourceName = elementDataSource.getAttribute(SElementDataSource.ATTRIB_NAME).getValue().toString();
            
            SUserDataSource userDataSource = userDataSources.get(dataSourceName);
            if (userDataSource != null) {
                // element data source exists in user data sources:
                SReporterDataSource reporterDataSource = new SReporterDataSource(elementDataSource, moParamsMap);
                
                reporterDataSource.combine(elementDataSource);
                if (reporterDataSource.isComplete()) {
                    // user data source is complete:
                    moDataSourcesMap.put(dataSourceName, reporterDataSource);
                }
                else {
                    throw new Exception("El data source '" + dataSourceName + "' no pudo ser completado.");
                }
            }
            else {
                // element data source does not exist in user data sources:
                SReporterDataSource reporterDataSource = new SReporterDataSource(elementDataSource, moParamsMap);
                if (reporterDataSource.isComplete()) {
                    // user data source is complete:
                    moDataSourcesMap.put(dataSourceName, reporterDataSource);
                }
                else {
                    throw new Exception("El data source '" + dataSourceName + "' no existe.");
                }
            }
        }
    }

    /*
     * Public methods:
     */
    public STemplate getTemplate() {
        return moTemplate;
    }
    
    public HashMap<String, SReporterParam> getParamsMap() {
        return moParamsMap;
    }
    
    public HashMap<String, SReporterDataSource> getDataSourcesMap() {
        return moDataSourcesMap;
    }
    
    public SReporterParam getParam(final String name) throws Exception {
        if (moParamsMap.get(name) == null) {
            throw new Exception("Error, the param \"" + name +"\" doesnt exist!");
        }else{
            return moParamsMap.get(name);
        }
    }
    
    public SReporterDataSource getDataSource(final String name) {
        return moDataSourcesMap.get(name);
    }
}
