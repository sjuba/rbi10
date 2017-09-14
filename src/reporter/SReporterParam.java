/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import reporter.xml.SEnumDataType;
import reporter.xml.SElementParam;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alphalapz
 */
public class SReporterParam extends SUserParam implements SArgumentInterface {
    
    /**
     * Creates a reporter param from user param.
     * @param userParam User param.
     * @throws Exception 
     */
    public SReporterParam(final SUserParam userParam) throws Exception {
        super(userParam);
    }
    
    /**
     * Creates a reporter param from XML element param.
     * @param elementParam XML element param.
     * @throws Exception 
     */
    public SReporterParam(final SElementParam elementParam) throws Exception {
        super(elementParam.getAttribute(SElementParam.ATTRIB_NAME).getValue().toString(), 
                SEnumDataType.valueOf(elementParam.getAttribute(SElementParam.ATTRIB_DATA_TYPE).getValue().toString()), 
                SReporterUtils.castDataType(SEnumDataType.valueOf(elementParam.getAttribute(SElementParam.ATTRIB_DATA_TYPE).getValue().toString()), 
                        elementParam.getAttribute(SElementParam.ATTRIB_DEFAULT_VALUE).getValue().toString()));
    }

    @Override
    public boolean isComplete() {
        return moValue != null;
    }

    @Override
    public void combine(final SXmlElement element) throws Exception {
        if (!(element instanceof SElementParam)) {
            throw new IllegalArgumentException("Invalid argument data!");
        }
        
        SElementParam elementParam = (SElementParam) element;
        
        if (msName.compareTo(elementParam.getAttribute(SElementParam.ATTRIB_NAME).getValue().toString()) != 0) {
            throw new IllegalArgumentException("Attribute '" + SElementParam.ATTRIB_NAME + "' does not match!");
        }
        
        if (meDataType != SEnumDataType.valueOf(elementParam.getAttribute(SElementParam.ATTRIB_DATA_TYPE).getValue().toString())) {
            throw new IllegalArgumentException("Attribute '" + SElementParam.ATTRIB_DATA_TYPE + "' does not match!");
        }
        
        if (moValue == null) {
            String defaultValue = elementParam.getAttribute(SElementParam.ATTRIB_DEFAULT_VALUE).getValue().toString();
            if (defaultValue == null || defaultValue.isEmpty()) {
                throw new IllegalArgumentException("Attribute '" + SElementParam.ATTRIB_DEFAULT_VALUE + "' does not exist for '" + elementParam.getAttribute(SElementParam.ATTRIB_NAME).getValue().toString() + "'.");
            }
            else {
                moValue = SReporterUtils.castDataType(meDataType, defaultValue);
            }
        }
    }
}
