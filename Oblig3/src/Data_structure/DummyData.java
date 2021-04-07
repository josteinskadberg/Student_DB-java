package Data_structure;

/**
 * Class with data for testing functionality if DB is not accessible.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class DummyData {

    public static void main(String[] args) {
        Student Per = new Student("1", "Per", "V2019");
        Student Kari = new Student("2", "Kari", "V2019");
        Skole UiB = new Skole("Universitetet i Bergen");
        Kull UIB2019V = new Kull("V2019", UiB.getNavn());
        Kurs info233 = new Kurs("info233", "avansert programmering", UiB.getNavn());
        Karakter vudering0 = new Karakter(5, "A", 2019, Per.getStudentNummer(), info233.getKode());
        Karakter vudering1 = new Karakter(6, "A", 2019, Kari.getStudentNummer(), info233.getKode());
    }
}
