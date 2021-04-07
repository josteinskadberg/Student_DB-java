package DB_utility;

import java.sql.DriverManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

/**
 * Connection manager class. Creates a Database file with tables and manages the connection to the DB file. The DB
 * driver uses SQLlite.
 * <p>
 * Class is constructed as a Singleton to ensure that there only can be one instance of CM at a at a time.
 * This is a security measure. The JDBC driver will lock the database
 * if different Connection instances tries to access the DB simultaneously. DAO objects call
 * "getinstance()" in constructor.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class CM {
    private static File db = new File("oblig3_database.db");
    private static String sql = "Opprettdatabase.sql";
    private static String url = "jdbc:sqlite:" + db.getPath();
    private static Connection connection;
    private static CM cm = null;

    private CM() {
        this.connection = connect();
    }

    public static CM getInstance() {
        if (cm == null) {
            cm = new CM();

        }
        return cm;
    }

    public static boolean dbExists() {
        return db.exists();
    }

    public static void createNewTables(String[] sql) {
        try (Connection conn = DriverManager.getConnection(getUrl());
             Statement stmt = conn.createStatement()) {
            for (String s : sql) {
                stmt.execute(s);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    /**
     * @param sqlPath
     * @return
     */
    public static String[] extraxtSql(String sqlPath) {
        try {
            String sql_string = new String(Files.readAllBytes(Paths.get(sqlPath)));
            String[] sql_commands = sql_string.replace(";", ";#").split("#");
            return sql_commands;

        } catch (IOException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static void insert(String table, String[] columns, String[] values) {
        String stmt = prepeareInsertString(table, columns, values);

    }

    public static String prepeareInsertString(String table, String[] columns, String[] values) {

        String insert_stmt = "INSERT INTO " + table;
        insert_stmt += prepare_paranteces(columns);
        insert_stmt += " VALUES";
        insert_stmt += prepare_paranteces(values);
        return insert_stmt;
    }

    private static String prepare_paranteces(String[] values) {
        String result = "(";
        for (int i = 0; i < values.length; i++) {
            result += " " + values[i];
            if (i != values.length - 1) {
                result += ",";
            }
        }
        result += ")";
        return result;
    }

    public static String getSql() {
        return sql;
    }

    public static String getUrl() {
        return url;
    }

    private Connection connect() {
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(getUrl());
            if (connect != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Could not establish connection ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public Connection getConnection() {
        return connection;
    }

}



