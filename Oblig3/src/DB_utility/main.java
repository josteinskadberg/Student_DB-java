package DB_utility;


import DAO.*;
import Data_structure.*;

/**
 * Create a Db base is no DB at DB-url. populate the new DB with data, connect to DB.
 *
 * @Author Jostein Skadberg jsk026
 * @Author Arne Skjæveland wuc008
 */
public class main {

    public static void main(String[] args) {

        if (!CM.dbExists()) {
            CM.createNewTables(CM.extraxtSql(CM.getSql()));
            populateDB();
        }
    }

    /**
     * A prepared set of data to populate db. Populates with DAO objects.
     */
    public static void populateDB() {
        CM cm = CM.getInstance();
        Skole UiB = new Skole("UiB");
        Skole NHH = new Skole("NHH");
        Skole HVL = new Skole("HVL");
        Kull kull_V19_UIB = new Kull("2019V_UIB", UiB.getNavn());
        Kull kull_V18_UIB = new Kull("2018H_UIB", UiB.getNavn());
        Kull kull_V19_NHH = new Kull("2019V_NHH", NHH.getNavn());
        Kull kull_V18_NHH = new Kull("2018H_NHH", NHH.getNavn());
        Kull kull_V19_HVL = new Kull("2019V_HVL", HVL.getNavn());
        Kull kull_V18_HVL = new Kull("2018H_HVL", HVL.getNavn());
        Kurs kurs_INFO233 = new Kurs("INFO233", "Avansert Programering", UiB.getNavn());
        Kurs kurs_INFO132 = new Kurs("INFO132", "Innføring i programmering", UiB.getNavn());
        Kurs kurs_BED2 = new Kurs("BED2", "Finansregnskap", NHH.getNavn());
        Kurs kurs_SAM3 = new Kurs("SAM3", "Makroøkonomi", NHH.getNavn());
        Kurs kurs_DAT100 = new Kurs("DAT100", "Grunnleggende programmering", HVL.getNavn());
        Kurs kurs_DAT107 = new Kurs("DAT107", "Databaser", HVL.getNavn());
        Student per = new Student("1", "Per", kull_V19_UIB.getKode());
        Student kari = new Student("2", "Kari", kull_V19_UIB.getKode());
        Student jostein = new Student("3", "Jostein", kull_V18_UIB.getKode());
        Student Arne = new Student("4", "Arne", kull_V18_NHH.getKode());
        Student Jonas = new Student("5", "Jonas", kull_V18_NHH.getKode());
        Student Randi = new Student("6", "Randi", kull_V19_NHH.getKode());
        Student Rolf = new Student("7", "Rolf", kull_V19_NHH.getKode());
        Student Kristian = new Student("8", "Kristian", kull_V18_HVL.getKode());
        Student Kristine = new Student("9", "Kristine", kull_V18_HVL.getKode());
        Student Thea = new Student("10", "Thea", kull_V19_HVL.getKode());
        Student Peter = new Student("11", "Peter", kull_V19_HVL.getKode());
        Student Sasha = new Student("12", "Sasha", kull_V18_UIB.getKode());
        Karakter INFO233_grade_1 = new Karakter(1, "A", 2019, per.getStudentNummer(), kurs_INFO233.getKode());
        Karakter INFO233_grade_2 = new Karakter(2, "A", 2019, kari.getStudentNummer(), kurs_INFO233.getKode());
        Karakter INFO233_grade_3 = new Karakter(3, "E", 2019, jostein.getStudentNummer(), kurs_INFO233.getKode());
        Karakter INFO233_grade_4 = new Karakter(8, "C", 2017, Sasha.getStudentNummer(), kurs_INFO233.getKode());
        Karakter INFO132_grade_1 = new Karakter(4, "B", 2015, jostein.getStudentNummer(), kurs_INFO132.getKode());
        Karakter INFO132_grade_2 = new Karakter(5, "C", 2016, per.getStudentNummer(), kurs_INFO132.getKode());
        Karakter INFO132_grade_3 = new Karakter(6, "D", 2016, kari.getStudentNummer(), kurs_INFO132.getKode());
        Karakter INFO132_grade_4 = new Karakter(7, "A", 2019, Sasha.getStudentNummer(), kurs_INFO132.getKode());
        Karakter DAT100_grade_1 = new Karakter(9, "C", 2018, Kristian.getStudentNummer(), kurs_DAT100.getKode());
        Karakter DAT100_grade_2 = new Karakter(10, "B", 2018, Kristine.getStudentNummer(), kurs_DAT100.getKode());
        Karakter DAT100_grade_3 = new Karakter(11, "A", 2018, Peter.getStudentNummer(), kurs_DAT100.getKode());
        Karakter DAT100_grade_4 = new Karakter(12, "D", 2018, Thea.getStudentNummer(), kurs_DAT100.getKode());
        Karakter DAT107_grade_1 = new Karakter(13, "F", 2012, Kristian.getStudentNummer(), kurs_DAT107.getKode());
        Karakter DAT107_grade_2 = new Karakter(14, "C", 2012, Kristine.getStudentNummer(), kurs_DAT107.getKode());
        Karakter DAT107_grade_3 = new Karakter(24, "A", 2012, Peter.getStudentNummer(), kurs_DAT107.getKode());
        Karakter DAT107_grade_4 = new Karakter(15, "B", 2016, Thea.getStudentNummer(), kurs_DAT107.getKode());
        Karakter BED2_grade_1 = new Karakter(16, "A", 2015, Arne.getStudentNummer(), kurs_BED2.getKode());
        Karakter BED2_grade_2 = new Karakter(17, "B", 2015, Jonas.getStudentNummer(), kurs_BED2.getKode());
        Karakter BED2_grade_3 = new Karakter(18, "C", 2015, Randi.getStudentNummer(), kurs_BED2.getKode());
        Karakter BED2_grade_4 = new Karakter(19, "D", 2015, Rolf.getStudentNummer(), kurs_BED2.getKode());
        Karakter SAM_grade_1 = new Karakter(20, "C", 2018, Arne.getStudentNummer(), kurs_SAM3.getKode());
        Karakter SAM_grade_2 = new Karakter(21, "A", 2018, Jonas.getStudentNummer(), kurs_SAM3.getKode());
        Karakter SAM_grade_3 = new Karakter(22, "A", 2018, Randi.getStudentNummer(), kurs_SAM3.getKode());
        Karakter SAM_grade_4 = new Karakter(23, "C", 2018, Rolf.getStudentNummer(), kurs_SAM3.getKode());
        kurs_INFO233.addKarakter(INFO233_grade_1);
        kurs_INFO233.addKarakter(INFO233_grade_2);
        kurs_INFO233.addKarakter(INFO233_grade_3);
        SkoleDao skoleDao = new SkoleDao(cm.getConnection());
        KullDao kullDao = new KullDao(cm.getConnection());
        KursDao kursDao = new KursDao(cm.getConnection());
        StudentDao studDao = new StudentDao(cm.getConnection());

        KarakterDao karakterDao = new KarakterDao(cm.getConnection());
        skoleDao.save(UiB);
        skoleDao.save(NHH);
        skoleDao.save(HVL);
        kullDao.save(kull_V18_HVL);
        kullDao.save(kull_V19_HVL);
        kullDao.save(kull_V18_UIB);
        kullDao.save(kull_V19_UIB);
        kullDao.save(kull_V18_NHH);
        kullDao.save(kull_V19_NHH);
        kursDao.save(kurs_INFO132);
        kursDao.save(kurs_INFO233);
        kursDao.save(kurs_BED2);
        kursDao.save(kurs_SAM3);
        kursDao.save(kurs_DAT100);
        kursDao.save(kurs_DAT107);
        studDao.save(jostein);
        studDao.save(Arne);
        studDao.save(kari);
        studDao.save(Sasha);
        studDao.save(Kristian);
        studDao.save(Kristine);
        studDao.save(Jonas);
        studDao.save(Randi);
        studDao.save(Rolf);
        studDao.save(Peter);
        studDao.save(Thea);
        studDao.save(per);
        karakterDao.save(INFO132_grade_1);
        karakterDao.save(INFO132_grade_2);
        karakterDao.save(INFO132_grade_3);
        karakterDao.save(INFO132_grade_4);
        karakterDao.save(INFO233_grade_1);
        karakterDao.save(INFO233_grade_2);
        karakterDao.save(INFO233_grade_3);
        karakterDao.save(INFO233_grade_4);
        karakterDao.save(BED2_grade_1);
        karakterDao.save(BED2_grade_2);
        karakterDao.save(BED2_grade_3);
        karakterDao.save(BED2_grade_4);
        karakterDao.save(SAM_grade_1);
        karakterDao.save(SAM_grade_2);
        karakterDao.save(SAM_grade_3);
        karakterDao.save(SAM_grade_4);
        karakterDao.save(DAT100_grade_1);
        karakterDao.save(DAT100_grade_2);
        karakterDao.save(DAT100_grade_3);
        karakterDao.save(DAT100_grade_4);
        karakterDao.save(DAT107_grade_1);
        karakterDao.save(DAT107_grade_2);
        karakterDao.save(DAT107_grade_3);
        karakterDao.save(DAT107_grade_4);

    }
}
