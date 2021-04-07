package DAO;

import Data_structure.Student;

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
public class StudentDao implements Dao<Student, String> {
    private PreparedStatement saveStmt;
    private PreparedStatement loadAllStmt;
    private PreparedStatement loadStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;
    private PreparedStatement loadByKullStmt;


    /**
     * Sets a prepared SQL statement to each field. Placeholder "?" is replaced with input.
     *
     * @param conn Connection object from the Singleton class CM. Connects dao to DB.
     */
    public StudentDao(Connection conn) {
        try {
            if (conn != null) {
                this.saveStmt = conn.prepareStatement("INSERT INTO student(nr,navn,kull) VALUES(?,?,?)");
                this.loadStmt = conn.prepareStatement("SELECT * FROM student WHERE nr = ?");
                this.loadAllStmt = conn.prepareStatement("SELECT * FROM student");
                this.updateStmt = conn.prepareStatement("UPDATE student SET navn = ?,kull = ? WHERE nr = ?");
                this.deleteStmt = conn.prepareStatement("DELETE FROM student where nr = ?");
                this.loadByKullStmt = conn.prepareStatement("SELECT * FROM student WHERE kull = ?");
            } else {
                System.out.println("no connection");
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }


    /**
     * Execute: ""SELECT * FROM student WHERE nr = ?""
     *
     * @param id Unique student if
     * @return A Student object. DB Constructor called on query result
     */
    @Override
    public Student get(String id) {
        Student match = null;
        try {
            loadStmt.setString(1, id);
            ResultSet resultSet = loadStmt.executeQuery();
            match = new Student(resultSet.getString("nr"), resultSet.getString("navn"),
                    resultSet.getString("kull"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return match;
    }

    /**
     * Execute "SELECT * FROM student"
     *
     * @return All the records in student table as Student objects
     */
    @Override
    public ArrayList<Student> getAll() {
        ArrayList<Student> result = new ArrayList<>();
        try {
            ResultSet resultSet = loadAllStmt.executeQuery();
            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("nr"),
                        resultSet.getString("navn"), resultSet.getString("kull"));
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Execute: "INSERT INTO student(nr,navn,kull) VALUES(?,?,?)"
     *
     * @param student object to be saved in DB.
     */
    @Override
    public void save(Student student) {
        try {
            saveStmt.setString(1, student.getStudentNummer());
            saveStmt.setString(2, student.getNavn());
            saveStmt.setString(3, student.getKull());
            saveStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Execute: "UPDATE student SET navn = ?,kull = ? WHERE nr = ?"
     *
     * @param student object with studNR that match a the record to be updated.
     * @param values  new values(name,kull)
     */
    @Override
    public void update(Student student, String[] values) {

        try {
            updateStmt.setString(1, values[0]);
            updateStmt.setString(2, values[1]);
            updateStmt.setString(3, student.getStudentNummer());
            updateStmt.executeUpdate();

            student.setNavn(values[0]);
            student.setKull(values[1]);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param student Maching studentnummer will delete record in DB.
     */
    @Override
    public void delete(Student student) {
        try {
            String id = student.getStudentNummer();
            deleteStmt.setString(1, id);
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get all student that belong to a Kull instance.
     * Execute: "SELECT * FROM student WHERE kull = ?"
     *
     * @param kull the kull to be queried.
     * @return an ArrayList of students in a specific kull.
     */
    public ArrayList<Student> getWhereKull(String kull) {
        ArrayList<Student> matches = new ArrayList<>();
        try {
            loadByKullStmt.setString(1, kull);
            ResultSet resultSet = loadByKullStmt.executeQuery();
            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("nr"),
                        resultSet.getString("navn"), resultSet.getString("kull"));
                matches.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }

}
