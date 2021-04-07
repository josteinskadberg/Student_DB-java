package DAO;

import Data_structure.Kull;
import Data_structure.Skole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Dao for the Kull class and Kull table in DB. PreparedStatements define sqlite statements for
 * each functionality.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class KullDao implements Dao<Kull, String> {
    private PreparedStatement saveStmt;
    private PreparedStatement loadAllStmt;
    private PreparedStatement loadStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;
    private PreparedStatement loadBySkoleStmt;

    /**
     * Sets a prepared SQL statement to each field. Placeholder "?" is replaced with input.
     *
     * @param conn Connection object from the Singleton class CM. Connects dao to DB.
     */
    public KullDao(Connection conn) {
        try {
            if (conn != null) {
                this.saveStmt = conn.prepareStatement("INSERT INTO kull(kode,skole) VALUES(?,?)");
                this.loadStmt = conn.prepareStatement("SELECT * FROM kull WHERE kode = ?");
                this.loadAllStmt = conn.prepareStatement("SELECT * FROM kull");
                this.updateStmt = conn.prepareStatement("UPDATE kull SET kode = ?, skole = ? WHERE kode = ?");
                this.deleteStmt = conn.prepareStatement("DELETE FROM kull WHERE kode = ?");
                this.loadBySkoleStmt = conn.prepareStatement("SELECT * FROM kull WHERE skole = ?");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }

    }

    /**
     * Execute "SELECT * FROM kull WHERE kode = ?"
     *
     * @param kode uniqe kull kode
     * @return A Kull object. Kull Constructor takes result from query.
     */
    @Override
    public Kull get(String kode) {
        Kull match = null;
        try {
            loadStmt.setString(1, kode);
            ResultSet resultSet = loadStmt.executeQuery();
            match = new Kull(resultSet.getString("kode"), resultSet.getString("skole"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return match;
    }

    /**
     * Execute: "SELECT * FROM kull"
     *
     * @return All the records in kull table as Kull objects
     */
    @Override
    public ArrayList<Kull> getAll() {
        ArrayList<Kull> result = new ArrayList<>();
        try {
            ResultSet resultSet = loadAllStmt.executeQuery();
            while (resultSet.next()) {
                Kull kull = new Kull(resultSet.getString("kode"), resultSet.getString("skole"));
                result.add(kull);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * execute:"INSERT INTO kull(kode,skole) VALUES(?,?)"
     *
     * @param kull object to insert in DB.
     */
    @Override
    public void save(Kull kull) {
        try {
            saveStmt.setString(1, kull.getKode());
            saveStmt.setString(2, kull.getSkole());
            saveStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Execute: "UPDATE kull SET kode = ?, skole = ? WHERE kode = ?"
     *
     * @param kull   finds kull with maching kull-kode
     * @param values new values(kode,skole)
     */
    @Override
    public void update(Kull kull, String[] values) {

        try {

            updateStmt.setString(2, values[1]);
            updateStmt.setString(3, kull.getKode());
            updateStmt.setString(1, values[0]);
            updateStmt.executeUpdate();

            kull.setKode(values[0]);
            kull.setSkole(values[1]);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute: "DELETE FROM kull WHERE kode = ?"
     *
     * @param kull kull to be deleted( on kullkode)
     */
    @Override
    public void delete(Kull kull) {
        try {
            deleteStmt.setString(1, kull.getKode());
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get all kull that belongs to a skole.
     * Execute: "SELECT * FROM kull WHERE skole = ?"
     *
     * @param skole skole with kull.(on name)
     * @return arrayList of all kull with a shared skole.
     */
    public ArrayList<Kull> getBySkule(Skole skole) {
        ArrayList<Kull> matches = new ArrayList<>();
        try {
            loadBySkoleStmt.setString(1, skole.getNavn());
            ResultSet resultSet = loadBySkoleStmt.executeQuery();
            while (resultSet.next()) {
                Kull match = new Kull(resultSet.getString("kode"), resultSet.getString("skole"));
                matches.add(match);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }
}
