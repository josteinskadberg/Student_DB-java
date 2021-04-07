package GUI;

import DAO.*;
import DB_utility.CM;
import Data_structure.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for Application that displays information about students, and their grades, in different school and semesters.
 * The information is manages trough DAO-objects.
 * The application can save new students and edit existing students names and store this to the DB.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class Controller implements Initializable {

    //DB connection
    private static Connection conn = CM.getInstance().getConnection();
    //DAO
    private final SkoleDao skoleDao = new SkoleDao(getConn());
    private final StudentDao studentDao = new StudentDao(getConn());
    private final KullDao kullDao = new KullDao(getConn());
    private final KarakterDao karakterDao = new KarakterDao(getConn());
    private final KursDao kursDao = new KursDao(getConn());
    //FXML Elements
    @FXML
    private ComboBox<String> chooseSkole;
    @FXML
    private ComboBox<String> chooseKull;
    @FXML
    private ComboBox<String> chooseKull_add;
    @FXML
    private TableView<Karakter> karakterTableView;
    @FXML
    private TableColumn kurskode;
    @FXML
    private TableColumn kursnavn;
    @FXML
    private TableColumn karakter;
    @FXML
    private TableColumn år;
    @FXML
    private ListView currentStudenter;
    @FXML
    private TextArea studentInfo;
    @FXML
    private Button add_student;
    @FXML
    private Button updateNavn;
    @FXML
    private TextField studNR_add;
    @FXML
    private TextField name_add;
    @FXML
    private TextField nyttNavn_input;
    @FXML
    private Text errorPromt_updateNavn;
    @FXML
    private Text noGradesPrompt;
    @FXML
    private Tab karakterTab;
    //Temporary data storage
    private ArrayList<Student> studenter;
    private ArrayList<Skole> skolerListe;
    private ArrayList<Kull> kull;
    private ArrayList<Karakter> karakterer;
    private Skole skole;

    private static Connection getConn() {
        return conn;
    }


    /**
     * Runs when program starts and scene and children are initialized
     * Retrieves and places basic information from DB to gui.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSkoler();
        setTable();
        setSkoleChoice();
        populateChooseKull_ADD();
    }

    /**
     * Update name of student entry
     * Runs on click at updateNavn
     */
    public void onUpdateAction() {
        int index = getListViewIndex();
        if (index > -1) {
            Student student = getStudenter().get(index);
            String nyttNavn = nyttNavn_input.getText();
            updateNavn(student, nyttNavn);
            if (errorPromt_updateNavn.isVisible()) {
                errorPromt_updateNavn.setVisible(false);
            }
        } else {
            errorPromt_updateNavn.setVisible(true);
        }
    }

    /**
     * Makes sure that the correct information is shown when changing kull, this happens when
     * changing kull or skole in the comboBoxes
     */
    public void kullOnAction() {

        if (chooseKull.getSelectionModel().getSelectedItem() == null) {
            chooseKull.setValue(chooseKull.getItems().get(0));
            studentInfo.clear();
            karakterTableView.getColumns().clear();
        } else {
            String kull = getKull().get(chooseKull.getSelectionModel().getSelectedIndex()).getKode();
            setStudenter(kull);
            populateListView();
            showStudentInfo(0);
            onKarakterTabAction();
        }
    }

    /**
     * Runs when skole is chosen from ComboBox
     * Retrieves kull whom belongs to a skole from DB
     */
    public void onSkoleChoice() {
        setCurrentSkole();
        setKull();
        populateChooseKull();
        kullOnAction();
    }

    /**
     * Adds new student to DB, updates information in ListView and TextView
     */
    public void onAddAction() {
        String studentnummer = studNR_add.getText();
        String navn = name_add.getText();
        String kull = chooseKull_add.getValue();
        addStudent(studentnummer, navn, kull);
        setStudenter(kull);
        populateListView();
    }

    /**
     * Display information about chosen student from ListView in TextView
     */
    public void onListElementAction() {
        int index = getListViewIndex();
        if (index > -1) {
            showStudentInfo(index);
        }
        onKarakterTabAction();
    }

    public void onKarakterTabAction() {
        if (getKarakterer().size() > 1) {
            noGradesPrompt.setVisible(false);
        } else {
            noGradesPrompt.setVisible(true);
        }
    }

    /**
     * Prepare TableColumns. "kursnavn" not used because of some type problems.
     */
    @SuppressWarnings("unchecked")
    private void setTable() {
        kurskode.setCellValueFactory(new PropertyValueFactory<Karakter, String>("kurs"));
        karakter.setCellValueFactory(new PropertyValueFactory<Karakter, String>("karakter"));
        år.setCellValueFactory(new PropertyValueFactory<Karakter, String>("år"));
        kursnavn.setCellValueFactory(new PropertyValueFactory<Kurs, String>("navn"));
    }

    /**
     * Finds a course name that belongs to a grade.
     *
     * @param karakter
     * @return The name of a course who the grade belongs to.
     */
    private String getKursNavn(Karakter karakter) {
        Kurs kurs = kursDao.get(karakter.getKurs());
        return kurs.getNavn();
    }

    /**
     * Adds student to DB with StudentDao
     *
     * @param studentnummer
     * @param navn
     * @param kull
     */
    private void addStudent(String studentnummer, String navn, String kull) {
        Student newStudent = new Student(studentnummer, navn, kull);
        studentDao.save(newStudent);
    }


    /**
     * Updates a name of a student in DB trough StudentDao
     *
     * @param student
     * @param nyttNavn
     */
    private void updateNavn(Student student, String nyttNavn) {
        String[] updated = {nyttNavn, student.getKull()};
        studentDao.update(student, updated);
        populateListView();
    }

    /**
     * Populates TableView with information about a students grades.
     */
    @SuppressWarnings("unchecked")
    private void populateTable() {
        ObservableList karakterliste = FXCollections.observableArrayList();
        ObservableList kursnavnListe = FXCollections.observableArrayList();
        ArrayList<Karakter> karakterer = getKarakterer();
        for (Karakter k : karakterer) {
            karakterliste.add(k);
            kursnavnListe.add(getKursNavn(k));
        }
        karakterTableView.getColumns().clear();
        karakterTableView.setItems(karakterliste);
        karakterTableView.getColumns().addAll(kurskode, karakter, år);
        //karakterTableView.setItems(kursnavnListe);
        // karakterTableView.getColumns().add(kursnavn);
    }


    /**
     * Populates Table with information about chosen students grades
     *
     * @param index Index of chosen student from ListView
     */
    private void showStudentInfo(int index) {
        setKarakterer(studenter.get(index).getStudentNummer());
        populateTextArea(index);
        populateTable();
    }

    /**
     * Retrives information about student object.
     *
     * @param student
     * @return
     */
    private String getStudentInfo(Student student) {
        String navn = "Navn: " + student.getNavn() + "\n";
        String studetNR = "Studentnummer: " + student.getStudentNummer() + "\n";
        String kull = "Kull: " + student.getKull() + "\n";
        return navn + studetNR + kull;
    }

    /**
     * Adds students from a chosen kull to ListView
     */
    @SuppressWarnings("unchecked")
    private void populateListView() {
        ObservableList elements = FXCollections.observableArrayList();
        ArrayList<Student> students = getStudenter();
        for (Student student : students) {
            elements.add(student.getNavn());
        }
        this.currentStudenter.setItems(elements);

    }

    /**
     * Adds information about a student in TextView
     *
     * @param index
     */
    private void populateTextArea(int index) {
        String info = getStudentInfo(getStudenter().get(index));
        studentInfo.setText(info);

    }

    /**
     * @return Index of chosen element in ListView(ObservableList)
     */
    private int getListViewIndex() {
        return currentStudenter.getSelectionModel().getSelectedIndex();

    }

    /**
     * @return List of students in a kull
     */
    private ArrayList<Student> getStudenter() {
        return studenter;
    }

    /**
     * Get all students in a kull from DB
     *
     * @param kull Kullkode
     */
    private void setStudenter(String kull) {
        studenter = studentDao.getWhereKull(kull);
    }

    /**
     * @return Current chosen skole
     */
    private Skole getSkole() {
        return skole;
    }

    /**
     * @return List of all the schools
     */
    private ArrayList<Skole> getSkoler() {
        return skolerListe;
    }


    /**
     * Populates comboBox with list of schools
     */
    private void setSkoleChoice() {
        ObservableList<String> choices = FXCollections.observableArrayList();
        ArrayList<Skole> skoler = getSkoler();
        for (Skole skole : skoler) {
            choices.add(skole.getNavn());
        }
        chooseSkole.setItems(choices);

    }

    /**
     * Populates ComboBox for kull with kull at a school
     */
    private void populateChooseKull() {
        ObservableList<String> choices = FXCollections.observableArrayList();
        ArrayList<Kull> kullListe = getKull();
        for (Kull kull : kullListe) {
            choices.add(kull.getKode().split("_")[0]);
        }
        this.chooseKull.setItems(choices);
    }

    /**
     * Populates ComboBox for all kull that you can add a student inn
     */
    private void populateChooseKull_ADD() {
        ObservableList<String> choices = FXCollections.observableArrayList();
        ArrayList<Kull> kull_add = kullDao.getAll();
        for (Kull kull : kull_add) {
            choices.add(kull.getKode());
        }
        this.chooseKull_add.setItems(choices);
    }


    /**
     * Sets the choosen school as current school.
     */
    private void setCurrentSkole() {
        int index = chooseSkole.getSelectionModel().getSelectedIndex();
        skole = getSkoler().get(index);

    }

    /**
     * Sets list of kull based on choosen skole
     */
    private void setKull() {
        this.kull = kullDao.getBySkule(getSkole());
    }

    /**
     * @return kull based on current skole
     */
    private ArrayList<Kull> getKull() {
        return kull;
    }

    /**
     * @return List of karakterer based on students
     */
    private ArrayList<Karakter> getKarakterer() {
        return karakterer;
    }

    /**
     * @param nr sets karakterer based on the grades given to a student in DB
     */
    private void setKarakterer(String nr) {
        this.karakterer = karakterDao.getByStudNR(nr);
    }

    /**
     * Gets all the schools from the DB
     */
    private void setSkoler() {
        this.skolerListe = skoleDao.getAll();
    }


}
