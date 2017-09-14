/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import processor.out.SReportElement;
import java.util.ArrayList;
import processor.in.SDataContainer;
import processor.out.SReportColumn;
import processor.out.SReportRow;
import reporter.xml.SElementReportColumn;
import reporter.xml.SElementReportElement;
import reporter.xml.SElementReportRow;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alphalapz
 */
public class SReporterProcessor {
    
    private final SReporterTemplate moReporterTemplate;
    private final ArrayList<SDataContainer> maDataContainers;
    private final ArrayList<SReportColumn> maReportColumns;
    private final ArrayList<SReportRow> maReportRows;
    private final ArrayList<SReportElement> maReportElements;
    
    public SReporterProcessor(final SReporterTemplate reporterTemplate) throws Exception {
        moReporterTemplate = reporterTemplate;
        
        maDataContainers = new ArrayList<>();
        computeReportDataContainers();
        
        maReportColumns = new ArrayList<>();
        for (SXmlElement element: moReporterTemplate.getTemplate().getElementReport().getElementReportColumns().getXmlElements()) {
            maReportColumns.add(new SReportColumn((SElementReportColumn) element));
        }
        
        maReportRows = new ArrayList<>();
        for (SXmlElement element: moReporterTemplate.getTemplate().getElementReport().getElementReportRows().getXmlElements()) {
            maReportRows.add(new SReportRow((SElementReportRow) element));
        }
        
        maReportElements = new ArrayList<>();
        computeReportElements();
    }
    
    /*
     * Private methods:
     */
    
    private void computeReportDataContainers() throws Exception {
        for (SReporterDataSource dataSource : moReporterTemplate.getDataSourcesMap().values()) {
            maDataContainers.add(new SDataContainer(this, dataSource));
        }
    }
    
    private void computeReportElements() throws Exception {
        for (SXmlElement element : moReporterTemplate.getTemplate().getElementReport().getElementReportElements().getXmlElements()) {
            maReportElements.add(new SReportElement(this, (SElementReportElement) element));
        }
    }
    
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
