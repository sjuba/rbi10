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

public class SElementDataSources extends SXmlElement {
    
    public static final String ELEMENT = "DataSources";
    
    /**
     * Creates a Template.DataSources node.
     * Can contain Template.DataSources.DataSource nodes.
     */
    public SElementDataSources() {
        super(ELEMENT);
    }
}
