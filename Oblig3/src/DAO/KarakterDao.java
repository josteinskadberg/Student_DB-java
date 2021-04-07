package DAO;

import Data_structure.Karakter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Dao for the Karakter class and karakter table in DB. PreparedStatements define sqlite statements for
 * each functionality.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class KarakterDao implements Dao<Karakter, Integer> {
    private PreparedStatement saveStmt;
    private PreparedStatement loadAllStmt;
    private PreparedStatement loadStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;
    private PreparedStatement loadByStudNR;


    /**
     * Sets a prepared SQL statement to each field. Placeholder "?" is replaced with input.
     *
     * @param conn Connection object from the Singleton class CM. Connects dao to DB.
     */
    public KarakterDao(Connection conn) {
        try {
            if (conn != null) {
                this.saveStmt = conn.prepareStatement("INSERT INTO karakter(id,karakter,ar,student, kurs) VALUES(?,?,?,?,?)");
                this.loadStmt = conn.prepareStatement("SELECT * FROM karakter WHERE id = ?");
                this.loadAllStmt = conn.prepareStatement("SELECT * FROM karakter");
                this.updateStmt = conn.prepareStatement("UPDATE karakter SET karakter = ?, ar = ?, student = ?, kurs = ? WHERE id = ?");
                this.deleteStmt = conn.prepareStatement("DELETE FROM karakter WHERE id = ?");
                this.loadByStudNR = conn.prepareStatement("SELECT * FROM karakter WHERE student = ?");
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }

    }


    /**
     * execute: "SELECT * FROM karakter WHERE id = ?"
     * Get an instance that mach a PK-value
     *
     * @param id uniqe id of karakter
     * @return A karakter object. Karakter Constructor takes result from query.
     */
    @Override
    public Karakter get(Integer id) {
        Karakter match = null;
        try {
            loadStmt.setInt(1, id);
            ResultSet resultSet = loadStmt.executeQuery();
            match = new Karakter(resultSet.getInt("id"), resultSet.getString("karakter"),
                    resultSet.getInt("ar"), resultSet.getString("student"), resultSet.getString("kurs"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return match;
    }


    /**
     * execute: "SELECT * FROM karakter"
     *
     * @return All the records in karakter table as Karakter objects
     */
    @Override
    public ArrayList<Karakter> getAll() {
        ArrayList<Karakter> result = new ArrayList<>();
        try {
            ResultSet resultSet = loadAllStmt.executeQuery();
            while (resultSet.next()) {
                Karakter karakter = new Karakter(resultSet.getInt("id"), resultSet.getString("karakter"),
                        resultSet.getInt("ar"), resultSet.getString("student"), resultSet.getString("kurs"));
                result.add(karakter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * execute: "INSERT INTO karakter(id,karakter,ar,student, kurs) VALUES(?,?,?,?,?)"
     *
     * @param karakter object to insert to in DB.
     */
    @Override
    public void save(Karakter karakter) {
        try {
            saveStmt.setInt(1, karakter.getId());
            saveStmt.setString(2, karakter.getKarakter());
            saveStmt.setInt(3, karakter.getÅr());
            saveStmt.setString(4, karakter.getStudentnummer());
            saveStmt.setString(5, karakter.getKurs());
            saveStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * exequte: "UPDATE karakter SET karakter = ?, ar = ?, student = ?, kurs = ? WHERE id = ?"
     *
     * @param karakter to be updated (on id)
     * @param values   new values(karakter, ar, studentnr, kurs)
     */
    @Override
    public void update(Karakter karakter, String[] values) {

        try {
            updateStmt.setInt(5, karakter.getId());
            updateStmt.setInt(2, Integer.parseInt(values[1]));
            updateStmt.setString(4, values[3]);
            updateStmt.setString(3, values[2]);
            updateStmt.setString(1, values[0]);
            updateStmt.executeUpdate();

            karakter.setKarakter(values[0]);
            karakter.setÅr(Integer.parseInt(values[1]));
            karakter.setStudentnummer(values[2]);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * execute: "DELETE FROM karakter WHERE id = ?")
     *
     * @param karakter to be deleted(on id)
     */
    @Override
    public void delete(Karakter karakter) {
        try {
            Integer id = karakter.getId();
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * execute: "SELECT * FROM karakter WHERE student = ?"
     * Get all karakterer that belongs to a student.
     *
     * @param nr studentnummer
     * @return a list a students grades.
     */
    public ArrayList<Karakter> getByStudNR(String nr) {
        ArrayList<Karakter> matches = new ArrayList<>();
        try {
            loadByStudNR.setString(1, nr);
            ResultSet resultSet = loadByStudNR.executeQuery();
            while (resultSet.next()) {
                Karakter match = new Karakter(resultSet.getInt("id"), resultSet.getString("karakter"),
                        resultSet.getInt("ar"), resultSet.getString("student")
                        , resultSet.getString("kurs"));
                matches.add(match);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }
}


