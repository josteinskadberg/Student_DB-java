package DAO;

import Data_structure.Skole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Dao for the Skole class and skole table in DB. PreparedStatements define sqlite statements for
 * each functionality.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class SkoleDao implements Dao<Skole, String> {
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
    public SkoleDao(Connection conn) {
        try {
            if (conn != null) {
                this.saveStmt = conn.prepareStatement("INSERT INTO skole(navn) VALUES(?)");
                this.loadStmt = conn.prepareStatement("SELECT * FROM skole WHERE navn = ?");
                this.loadAllStmt = conn.prepareStatement("SELECT * FROM skole");
                this.updateStmt = conn.prepareStatement("UPDATE skole SET navn = ? WHERE navn = ?");
                this.deleteStmt = conn.prepareStatement("DELETE FROM skole where navn = ?");
            } else {
                System.out.println("No connection");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    /**
     * Get an instance that mach a PK-value
     * Execute: "SELECT * FROM skole WHERE navn = ?"
     *
     * @param navn Unique school name
     * @return A Skole object. DB Constructor called on query result
     */
    @Override
    public Skole get(String navn) {
        Skole match = null;
        try {
            loadStmt.setString(1, navn);
            ResultSet resultSet = loadStmt.executeQuery();
            match = new Skole(resultSet.getString("navn"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return match;
    }

    /**
     * Execute: "SELECT * FROM skole"
     *
     * @return All the records in skole table as Skole objects
     */
    @Override
    public ArrayList<Skole> getAll() {
        ArrayList<Skole> result = new ArrayList<>();
        try {
            ResultSet resultSet = loadAllStmt.executeQuery();
            while (resultSet.next()) {
                Skole skole = new Skole(resultSet.getString("navn"));
                result.add(skole);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Execute: "INSERT INTO skole(navn) VALUES(?)"
     *
     * @param skole object to be inserted in DB.
     */
    @Override
    public void save(Skole skole) {
        try {
            saveStmt.setString(1, skole.getNavn());
            saveStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Execute: "UPDATE skole SET navn = ? WHERE navn = ?"
     *
     * @param skole  name of schole is queried to DB
     * @param values new values(navn)
     */
    @Override
    public void update(Skole skole, String[] values) {

        try {
            updateStmt.setString(1, values[0]);
            updateStmt.setString(2, skole.getNavn());
            updateStmt.executeUpdate();
            skole.setNavn(values[0]);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute: "DELETE FROM skole where navn = ?"
     *
     * @param skole Skole that will be deleted (on name)
     */
    @Override
    public void delete(Skole skole) {
        try {
            String navn = skole.getNavn();
            deleteStmt.setString(1, skole.getNavn());
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}