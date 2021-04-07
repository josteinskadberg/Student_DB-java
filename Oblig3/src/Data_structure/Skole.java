package Data_structure;

import java.util.ArrayList;


/**
 * A Skole class- "Mock entity bean"
 * represents a school.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class Skole {
    private String navn;
    private ArrayList<Kull> kull;
    private ArrayList<Kurs> kurs;


    public Skole(String navn) {
        this.kull = new ArrayList<>();
        this.kurs = new ArrayList<>();
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void addKurs(Kurs kurs) {
        getKurs().add(kurs);
    }

    public void addKull(Kull kull) {
        getKull().add(kull);
    }

    public ArrayList<Kull> getKull() {
        return kull;
    }

    public ArrayList<Kurs> getKurs() {
        return kurs;
    }
}