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
public class SElementParams extends SXmlElement {
    
    public static final String ELEMENT = "Params";
    
    /**
     * Creates a Template.Params node.
     * Can contain Template.Params.Param nodes.
     */
    public SElementParams() {
        super(ELEMENT);
    }
}
