package Data_structure;

import java.util.ArrayList;

/**
 * A Student class- "Mock entity bean"
 * represents a student in a kull.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class Student {
    private String studentNummer;
    private String navn;
    private String kull;
    private ArrayList<Karakter> karakterer;

    public Student(String studentNummer, String navn, String kull) {
        this.karakterer = new ArrayList<>();
        this.studentNummer = studentNummer;
        this.navn = navn;
        this.kull = kull;
    }

    public void addKarakter(Karakter karakter) {
        karakterer.add(karakter);
    }

    public String getStudentNummer() {
        return studentNummer;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getKull() {
        return kull;
    }

    ;

    public void setKull(String kull) {
        this.kull = kull;
    }
}
