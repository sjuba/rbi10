/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter.xml;

import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alphalapz
 */
public class SElementReportColumns extends SXmlElement {
    
    public static final String ELEMENT = "Columns";
    
    /**
     * Creates a Template.Report.Columns node.
     * Can contain Template.Report.Columns.Column nodes.
     */
    public SElementReportColumns() {
        super(ELEMENT);
    }
}
