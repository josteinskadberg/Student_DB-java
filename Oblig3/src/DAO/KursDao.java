package DAO;

import Data_structure.Kurs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Dao for the Kurs class and kurs table in DB. PreparedStatements define sqlite statements for
 * each functionality.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class KursDao implements Dao<Kurs, String> {
    private PreparedStatement saveStmt;
    private PreparedStatement loadAllStmt;
    private PreparedStatement loadStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    /**
     * Sets a prepared SQL statement to each field. Placeholder "?" is replaced with input.
     *
     * @param conn Connection object from the Singleton class CM. Connects dao to DB.
     */
    public KursDao(Connection conn) {
        try {
            if (conn != null) {
                this.saveStmt = conn.prepareStatement("INSERT INTO kurs(kode,navn,skole) VALUES(?,?,?)");
                this.loadStmt = conn.prepareStatement("SELECT * FROM kurs WHERE kode = ?");
                this.loadAllStmt = conn.prepareStatement("SELECT * FROM kurs");
                this.updateStmt = conn.prepareStatement("UPDATE kurs SET kode = ?,navn = ?, skole = ? WHERE kode = ?");
                this.deleteStmt = conn.prepareStatement("DELETE FROM kode WHERE kode = ?");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }

    }


    /**
     * Get an instance that mach a PK-value
     * Execute: "SELECT * FROM kurs WHERE kode = ?"
     *
     * @param kode Unique kurs kode
     * @return A Kurs object. DB Constructor called on query result
     */
    @Override
    public Kurs get(String kode) {
        Kurs match = null;
        try {
            loadStmt.setString(1, kode);
            ResultSet resultSet = loadStmt.executeQuery();
            match = new Kurs(resultSet.getString("kode"),
                    resultSet.getString("navn"), resultSet.getString("skole"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return match;
    }

    /**
     * Exexcute: "SELECT * FROM kurs"
     *
     * @return All the records in kurs table as Kurs objects
     */
    @Override
    public ArrayList<Kurs> getAll() {
        ArrayList<Kurs> result = new ArrayList<>();
        try {
            ResultSet resultSet = loadAllStmt.executeQuery();
            while (resultSet.next()) {
                Kurs kurs = new Kurs(resultSet.getString("kode"), resultSet.getString("navn"),
                        resultSet.getString("skole"));
                result.add(kurs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Execute: "INSERT INTO kurs(kode,navn,skole) VALUES(?,?,?)"
     *
     * @param kurs object to be inserted in DB.
     */
    @Override
    public void save(Kurs kurs) {
        try {
            saveStmt.setString(1, kurs.getKode());
            saveStmt.setString(2, kurs.getNavn());
            saveStmt.setString(3, kurs.getSkole());
            saveStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Execute: "UPDATE kurs SET kode = ?,navn = ?, skole = ? WHERE kode = ?"
     *
     * @param kurs   kurs to be updated(on kurskkode)
     * @param values new values(navn,skole)
     */
    @Override
    public void update(Kurs kurs, String[] values) {

        try {
            updateStmt.setString(1, values[0]);
            updateStmt.setString(4, kurs.getKode());
            updateStmt.setString(2, values[1]);
            updateStmt.setString(3, values[2]);
            updateStmt.executeUpdate();

            kurs.setKode(values[0]);
            kurs.setNavn(values[1]);
            kurs.setSkole(values[2]);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute: "DELETE FROM kode WHERE kode = ?"
     *
     * @param kurs to be delted (on kurskode)
     */
    @Override
    public void delete(Kurs kurs) {
        try {
            deleteStmt.setString(1, kurs.getKode());
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
