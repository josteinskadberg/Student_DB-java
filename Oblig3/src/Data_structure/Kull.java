package Data_structure;

import java.util.ArrayList;

/**
 * A Kull class- "Mock entity bean"
 * represents a kull for at a school.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class Kull {
    private String kode;
    private String skole;
    private ArrayList<Student> studenter;

    public Kull(String kode, String skole) {
        this.studenter = new ArrayList<>();
        this.kode = kode;
        this.skole = skole;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getSkole() {
        return skole;
    }

    public void setSkole(String skole) {
        this.skole = skole;
    }

    public ArrayList<Student> getStudenter() {
        return studenter;
    }

    public void addStudent(Student student) {
        studenter.add(student);
    }

}

