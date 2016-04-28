package main;

import java.sql.*;

/**
 * Created by sndil on 4/25/2016.
 */
public class CustomStatement implements Statement {

    private Connection parentConnection;
    private Statement customStatement;

    public CustomStatement(Connection parentConnection, Statement wrappedStatement) {
        this.parentConnection = parentConnection;
        this.customStatement = wrappedStatement;
    }

    public boolean countquotes(char[] arr) {
        int countSingle = 0, countDouble = 0, i = 0;
        int countsemicol = 0;
        while (i < arr.length) {
            if ((int) arr[i] == 34) {
                countDouble++;
            }
            if ((int) arr[i] == 39) {
                countSingle++;
            }
            if ((int) arr[i] == 59) {
                countsemicol++;
            }
            i++;
        }
        if (countsemicol > 1) {
            return false;
        }
        if (countSingle % 2 != 0 || countDouble % 2 != 0) {
            return false;
        }
        return true;
    }

    public boolean commentscheck(char[] arr) {
        boolean flag = false;
        int i = 0;
        String comment = new String();
        while (i < arr.length - 2) {
            if ((int) arr[i] == 34 || (int) arr[i] == 39) {
                flag = !flag;
            }
            comment = comment + arr[i] + arr[i + 1];
            if (comment.equals("--")  && !flag) {
                return false;
            }
            if (arr[i] == '#' && !flag) {// ignoring if last character of string is #
                return false;
            }
            comment = "";
            i++;
        }
        return true;
    }

    public boolean check(String nextToOr) {
        nextToOr = nextToOr.split("\'")[0];
        if (nextToOr == "true") {
            return false;
        }
        if (nextToOr == "1=1") {
            return false;
        }
        if(nextToOr.contains("=")){
            String[] parts=nextToOr.split("=");
            if(parts[0].equals(parts[1])){
                return false;
            }
        }
        try {
            if (nextToOr == "") {
                return false;
            }
            if (Integer.parseInt(nextToOr) != 0) {
                return false;
            }
        } catch (Exception e) {
            //not a number
        }
        return true;
    }

    public boolean oneorone(String query) {
        int i = 0;
        String mystr = new String(query);
        String split = "";
        if (query.toLowerCase().contains("or 1".toLowerCase()))
            return false;
        if (query.toLowerCase().contains("and 1".toLowerCase()))
            return false;
        while (i < query.length()) {
            int index = query.toLowerCase().indexOf("or".toLowerCase(), i);
            while (mystr.split("[Oo][Rr]").length > 1) {
                split = mystr.split("[Oo][Rr]")[1];
                mystr = split;
                String nextToOr = split.trim().split(" ")[0];
//				System.out.println("nextToOr:"+nextToOr);
                if (!check(nextToOr)) {
//					System.out.println(nextToOr);
                    return false;
                }
            }
            i++;
        }
        return true;
    }

    public boolean input_sanitization(String line) {
        char[] chArray = line.toCharArray();
//		System.out.println("input_san");
        if (!countquotes(chArray)) { // must be even
            return false;
        }
        if (!commentscheck(chArray)) {  // check for injection using comments must be inside quotes
            return false;
        }
        if (!oneorone(line)) {
            return false;
        }

        System.out.println(line);
        return true;
    }


    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        if (input_sanitization(sql)) {
            System.out.println("Clean");
        } else {
            System.out.println("Malicious code");
        }
        return new CustomResultSet(this, customStatement.executeQuery(sql));
    }


    @Override
    public int executeUpdate(String sql) throws SQLException {
        return customStatement.executeUpdate(sql);
    }

    @Override
    public void close() throws SQLException {
        customStatement.close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return customStatement.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        customStatement.setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return customStatement.getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        customStatement.setMaxRows(max);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        customStatement.setEscapeProcessing(enable);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return customStatement.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        customStatement.setQueryTimeout(seconds);
    }

    @Override
    public void cancel() throws SQLException {
        customStatement.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return customStatement.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        customStatement.clearWarnings();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        customStatement.setCursorName(name);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return customStatement.execute(sql);
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return customStatement.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return customStatement.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return customStatement.getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        customStatement.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return customStatement.getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        customStatement.setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return customStatement.getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return customStatement.getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return customStatement.getResultSetType();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        customStatement.addBatch(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        customStatement.clearBatch();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return customStatement.executeBatch();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return parentConnection;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return customStatement.getMoreResults(current);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return new CustomResultSet(this, customStatement.getGeneratedKeys());
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return customStatement.executeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return customStatement.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return customStatement.executeUpdate(sql, columnNames);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return customStatement.execute(sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return customStatement.execute(sql, columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return customStatement.execute(sql, columnNames);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return customStatement.getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return customStatement.isClosed();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        customStatement.setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return customStatement.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        customStatement.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return customStatement.isCloseOnCompletion();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        try {
            if ("java.sql.Statement".equals(iface.getName())
                    || "java.sql.Wrapper.class".equals(iface.getName())) {
                return iface.cast(this);
            }

            return customStatement.unwrap(iface);
        } catch (ClassCastException cce) {
            throw new SQLException("Unable to unwrap to " + iface.toString(), cce);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if ("java.sql.Statement".equals(iface.getName())
                || "java.sql.Wrapper.class".equals(iface.getName())) {
            return true;
        }
        return customStatement.isWrapperFor(iface);
    }
}
