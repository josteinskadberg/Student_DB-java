package Data_structure;

import java.util.ArrayList;


/**
 * A Kurs class- "Mock entity bean"
 * represents a kull for at a school.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class Kurs {
    private String kode;
    private String navn;
    private String skole;
    private ArrayList<Integer> karakterer;

    public Kurs(String kode, String navn, String skole) {
        this.karakterer = new ArrayList<>();
        this.kode = kode;
        this.navn = navn;
        this.skole = skole;
    }

    public void addKarakter(Karakter karakter) {
        getKarakterer().add(karakter.getId());
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getSkole() {
        return skole;
    }

    public void setSkole(String skole) {
        this.skole = skole;
    }

    public ArrayList<Integer> getKarakterer() {
        return karakterer;
    }
}
