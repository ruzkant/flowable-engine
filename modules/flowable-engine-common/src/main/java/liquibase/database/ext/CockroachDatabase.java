package liquibase.database.ext;

import java.sql.SQLException;

import org.flowable.engine.common.impl.db.CockroachUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.database.DatabaseConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;

public class CockroachDatabase extends PostgresDatabase {
    private static Logger LOGGER = LoggerFactory.getLogger(CockroachDatabase.class);

    @Override
    public boolean isCorrectDatabaseImplementation(DatabaseConnection conn) throws DatabaseException {
        if(conn instanceof JdbcConnection) {
            JdbcConnection jdbcConnection = (JdbcConnection) conn;
            try {
                return CockroachUtil.isCockroach(LOGGER, jdbcConnection.getUnderlyingConnection());
            } catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
        return false;
    }
    
    @Override
    public boolean supportsDDLInTransaction() {
        return false;
    }

    @Override
    public int getPriority() {
        return 100;
    }
}
