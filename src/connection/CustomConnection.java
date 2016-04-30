package connection;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by sndil on 4/25/2016.
 */
public class CustomConnection implements Connection {

    private Connection customConnection;

    public CustomConnection(Connection connection) {
        customConnection = connection;
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new CustomStatement(this, customConnection.createStatement());
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return customConnection.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        customConnection.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return customConnection.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        customConnection.commit();
        ;
    }

    @Override
    public void rollback() throws SQLException {
        customConnection.rollback();
    }

    @Override
    public void close() throws SQLException {
        customConnection.close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return customConnection.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return customConnection.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        customConnection.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return customConnection.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        customConnection.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return customConnection.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        customConnection.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return customConnection.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return customConnection.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        customConnection.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return new CustomStatement(this, customConnection.createStatement(resultSetType, resultSetConcurrency));
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return customConnection.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        customConnection.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        customConnection.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return customConnection.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return customConnection.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return customConnection.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        customConnection.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        customConnection.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return new CustomStatement(this, customConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return customConnection.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        customConnection.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        customConnection.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return customConnection.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return customConnection.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return customConnection.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return customConnection.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        customConnection.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return customConnection.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        customConnection.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        customConnection.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return customConnection.getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            if ("java.sql.Connection".equals(iface.getName())
                    || "java.sql.Wrapper.class".equals(iface.getName())) {
                return iface.cast(this);
            }

            return customConnection.unwrap(iface);
        } catch (ClassCastException cce) {
            throw new SQLException("Unable to unwrap to " + iface.toString(), cce);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if ("java.sql.Connection".equals(iface.getName())
                || "java.sql.Wrapper.class".equals(iface.getName())) {
            return true;
        }
        return customConnection.isWrapperFor(iface);
    }
}
