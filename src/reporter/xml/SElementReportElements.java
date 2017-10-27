/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter.xml;

import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
 */
public class SElementReportElements extends SXmlElement {

    public static final String ELEMENT = "Elements";

    /**
     * Creates a Template.Report.Elements node.
     * Can contain Template.Report.Elements.Element nodes.
     */
    public SElementReportElements() {
        super(ELEMENT);
    }
}
