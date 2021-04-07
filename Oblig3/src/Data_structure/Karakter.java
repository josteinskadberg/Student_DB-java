package Data_structure;

/**
 * A Grade class- "Mock entity bean"
 * represents a grade for one student in one subject with an unique id.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class Karakter {
    private Integer id;
    private String karakter;
    private Integer år;
    private String studentnummer;
    private String kurs;

    public Karakter(Integer id, String karakter, Integer år, String studentnummer, String kurs) {
        this.id = id;
        this.karakter = karakter;
        this.år = år;
        this.studentnummer = studentnummer;
        this.kurs = kurs;
    }

    public String getKurs() {
        return kurs;
    }

    public String getKarakter() {
        return karakter;
    }

    public void setKarakter(String karakter) {
        this.karakter = karakter;
    }

    public Integer getÅr() {
        return år;
    }

    public void setÅr(Integer år) {
        this.år = år;
    }

    public String getStudentnummer() {
        return studentnummer;
    }

    public void setStudentnummer(String studentnummer) {
        this.studentnummer = studentnummer;
    }

    public Integer getId() {
        return id;
    }
}


