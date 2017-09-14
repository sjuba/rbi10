/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter.xml;

import sa.lib.xml.SXmlAttribute;
import sa.lib.xml.SXmlElement;

/**
 *
 * @author Alphalapz
 */
public class SElementDataSource extends SXmlElement {
    
    public static final String ELEMENT = "DataSource";
    public static final String ATTRIB_NAME = "name";
    public static final String ATTRIB_TYPE = "type";
    public static final String ATTRIB_HOST = "host";
    public static final String ATTRIB_PORT = "port";
    public static final String ATTRIB_DATABASE = "database";
    public static final String ATTRIB_USER = "user";
    public static final String ATTRIB_PASSWORD = "password";
    public static final String ATTRIB_QUERY = "query";
    
    private SXmlAttribute moAttribName;
    private SXmlAttribute moAttribType; // possible values defined in SEnumAttribDataSourceType
    private SXmlAttribute moAttribHost;
    private SXmlAttribute moAttribPort;
    private SXmlAttribute moAttribDatabase;
    private SXmlAttribute moAttribUser;
    private SXmlAttribute moAttribPassword;
    private SXmlAttribute moAttribQuery;
    
    /**
     * Creates a Template.DataSources.DataSource node.
     * Can contain Template.DataSources.DataSource.Column nodes.
     */
    public SElementDataSource() {
        super(ELEMENT);
        
        moAttribName = new SXmlAttribute(ATTRIB_NAME);
        moAttribType = new SXmlAttribute(ATTRIB_TYPE);
        moAttribHost = new SXmlAttribute(ATTRIB_HOST);
        moAttribPort = new SXmlAttribute(ATTRIB_PORT);
        moAttribDatabase = new SXmlAttribute(ATTRIB_DATABASE);
        moAttribUser = new SXmlAttribute(ATTRIB_USER);
        moAttribPassword = new SXmlAttribute(ATTRIB_PASSWORD);
        moAttribQuery = new SXmlAttribute(ATTRIB_QUERY);
        
        mvXmlAttributes.add(moAttribName);
        mvXmlAttributes.add(moAttribType);
        mvXmlAttributes.add(moAttribHost);
        mvXmlAttributes.add(moAttribPort);
        mvXmlAttributes.add(moAttribDatabase);
        mvXmlAttributes.add(moAttribUser);
        mvXmlAttributes.add(moAttribPassword);
        mvXmlAttributes.add(moAttribQuery);
    }
}
