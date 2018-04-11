package org.flowable.app.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;


public class CockroachUtil {
    
    public static boolean isCockroach(Logger logger, Connection conn) throws SQLException {
        
        DatabaseMetaData metaData;
        try {
            metaData = conn.getMetaData();
        } catch (Exception e) {
            return false;
        }
        if(metaData == null)
            return false; // wouldn't be postgres driver
        if(! "PostgreSQL".equals(metaData.getDatabaseProductName())) {
            return false;
        }
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select version()");
            if(rs.next()) {
                String version = rs.getString(1);
                if(version.trim().startsWith("CockroachDB")) {
                   return true; 
                }
            }
        } finally {
            if(st != null) {
                st.close();
            }
            if(rs != null) {
                rs.close();
            }
        }
        return false;
    }
}
