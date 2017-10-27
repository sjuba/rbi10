/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import java.util.ArrayList;
import processor.in.SDataContainer;
import processor.out.SReportColumn;
import processor.out.SReportElement;
import processor.out.SReportRow;
import reporter.xml.SElementReportColumn;
import reporter.xml.SElementReportElement;
import reporter.xml.SElementReportRow;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
 */
public class SReporterProcessor {
    
    private final SReporterTemplate moReporterTemplate;
    private final ArrayList<SDataContainer> maDataContainers;
    private final ArrayList<SReportColumn> maReportColumns;
    private final ArrayList<SReportRow> maReportRows;
    private final ArrayList<SReportElement> maReportElements;
    
    public SReporterProcessor(final SReporterTemplate reporterTemplate) throws Exception {
        moReporterTemplate = reporterTemplate;
        
        // compute data containers:
        maDataContainers = new ArrayList<>();
        for (SReporterDataSource dataSource : moReporterTemplate.getDataSourcesMap().values()) {
            maDataContainers.add(new SDataContainer(this, dataSource));
        }
        
        // prepare report columns:
        maReportColumns = new ArrayList<>();
        for (SXmlElement element: moReporterTemplate.getTemplate().getElementReport().getElementReportColumns().getXmlElements()) {
            maReportColumns.add(new SReportColumn((SElementReportColumn) element));
        }
        
        // prepare report rows:
        maReportRows = new ArrayList<>();
        for (SXmlElement element: moReporterTemplate.getTemplate().getElementReport().getElementReportRows().getXmlElements()) {
            maReportRows.add(new SReportRow((SElementReportRow) element));
        }
        
        // compute report elements:
        maReportElements = new ArrayList<>();
        /*Only for Debug */
        //int cont=0;
        for (SXmlElement element : moReporterTemplate.getTemplate().getElementReport().getElementReportElements().getXmlElements()) {
            maReportElements.add(new SReportElement(this, (SElementReportElement) element));
        /*Only for Debug */
//            System.out.println("---START---");
//            System.out.println(maReportElements.get(cont).getName());
//            System.out.println(maReportElements.get(cont).getValue());
//            System.out.println("Pos: " + ((cont++)-1));
//            System.out.println("Col: " + maReportElements.get(cont-1).getColumnNumber());
//            System.out.println("Row: " + maReportElements.get(cont-1).getRowNumber());
//            System.out.println("---END---");
        }
    }

    /*
     * Private methods:
     */

    /*
     * Public methods:
     */

    public SReporterTemplate getReporterTemplate() {
        return moReporterTemplate;
    }

    public ArrayList<SDataContainer> getDataContainers() {
        return maDataContainers;
    }

    public SDataContainer getDataContainer(final String name) {
        SDataContainer dataContainer = null;
        
        for (SDataContainer dc : maDataContainers) {
            if (dc.getName().compareTo(name) == 0) {
                dataContainer = dc;
                break;
            }
        }
        
        return dataContainer;
    }
    
    public ArrayList<SReportColumn> getReportColumns() {
        return maReportColumns;
    }
    
    public ArrayList<SReportRow> getReportRows() {
        return maReportRows;
    }
    
    public ArrayList<SReportElement> getReportElements() {
        return maReportElements;
    }
    
    public SReportElement getReportElement(final String name) {
        SReportElement element = null;
        
        for (SReportElement e : maReportElements) {
            if (e.getName().compareTo(name) == 0) {
                element = e;
                break;
            }
        }
        
        return element;
    }
}
