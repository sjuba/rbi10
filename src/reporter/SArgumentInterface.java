/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alfredo Pérez
 */
public interface SArgumentInterface {

    public boolean isComplete();
    public void combine(final SXmlElement element) throws Exception;
}
