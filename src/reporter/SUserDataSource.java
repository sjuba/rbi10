/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reporter;

import reporter.xml.SEnumDataSourceType;

/**
 *
 * @author Alphalapz
 */
public class SUserDataSource {

    protected final String msName;
    protected final SEnumDataSourceType meDataSourceType;
    protected String msHost;
    protected String msPort;
    protected String msDatabase;
    protected String msUser;
    protected String msPassword;

    /**
     * Creates an user data source from another data source.
     *
     * @param dataSource User data source.
     * @throws Exception
     */
    public SUserDataSource(final SUserDataSource dataSource) throws Exception {
        this(dataSource.getName(), dataSource.getDataSourceType(), dataSource.getHost(), dataSource.getPort(), dataSource.getDatabase(), dataSource.getUser(), dataSource.getPassword());
    }

    /**
     * Creates an user data source.
     *
     * @param name Data source name.
     * @param dataSourceType Data source type.
     * @param host Host.
     * @param port Port.
     * @param database Database name.
     * @param user User name.
     * @param password User password.
     * @throws Exception
     */
    public SUserDataSource(final String name, final SEnumDataSourceType dataSourceType, final String host, final String port, final String database, final String user, final String password) throws Exception {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument name!");
        }

        if (dataSourceType == null) {
            throw new IllegalArgumentException("Invalid argument data source type!");
        }

        if (host == null) {
            throw new IllegalArgumentException("Invalid argument host!");
        }

        if (port == null) {
            throw new IllegalArgumentException("Invalid argument port!");
        }

        if (database == null) {
            throw new IllegalArgumentException("Invalid argument database!");
        }

        if (user == null) {
            throw new IllegalArgumentException("Invalid argument user!");
        }

        if (password == null) {
            throw new IllegalArgumentException("Invalid argument password!");
        }

        msName = name;
        meDataSourceType = dataSourceType;
        msHost = host;
        msPort = port;
        msDatabase = database;
        msUser = user;
        msPassword = password;
    }

    public String getName() {
        return msName;
    }

    public SEnumDataSourceType getDataSourceType() {
        return meDataSourceType;
    }

    public String getHost() {
        return msHost;
    }

    public String getPort() {
        return msPort;
    }

    public String getDatabase() {
        return msDatabase;
    }

    public String getUser() {
        return msUser;
    }

    public String getPassword() {
        return msPassword;
    }
}
