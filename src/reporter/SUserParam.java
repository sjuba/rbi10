/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import reporter.xml.SEnumDataType;

/**
 *
 * @author Alphalapz
 */
public class SUserParam {

    protected final String msName;
    protected final SEnumDataType meDataType;
    protected Object moValue;
    
    /**
     * Creates an user parameter from another user parameter.
     * @param param User parameter.
     * @throws Exception 
     */
    public SUserParam(final SUserParam param) throws Exception {
        this(param.getName(), param.getDataType(), param.getValue());
    }

    /**
     * Creates an user parameter.
     * @param name Parameter name.
     * @param dataType Parameter data type.
     * @param value Parameter value. Can be null.
     * @throws Exception 
     */
    public SUserParam(final String name, final SEnumDataType dataType, final Object value) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument name!");
        }
        
        if (dataType == null) {
            throw new IllegalArgumentException("Invalid argument data type!");
        }
        
        if (value != null) {
            SReporterUtils.checkDataType(dataType, value);
        }
        
        msName = name;
        meDataType = dataType;
        moValue = value;
    }

    public String getName() {
        return msName;
    }

    public SEnumDataType getDataType() {
        return meDataType;
    }

    public Object getValue() {
        return moValue;
    }
}
