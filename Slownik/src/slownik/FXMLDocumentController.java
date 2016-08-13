/**
 * Slownik polsko-łotewski v.1.0
 */

package slownik;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {
    
    static String tlumaczenie; // przechowuje tlumaczenie slowka
    
    @FXML Label iloscSlow; // przechowuje ilosc slowek dostepnych w bazie, liczba ta jest obliczana
                           // co uruchomienie
    @FXML
    private TextField slowo; // pole do wprowadzenia przez uzytkownika slowa
    
    @FXML
    private TextArea pole; // pole w ktorym wyswietla sie tlumaczenie
    
    public static Connection polacz(String baza) { // polaczenie z baza
        Connection polaczenie = null;
        try {
            // Wskazanie jaki rodzaj bazy danych bęzie wykorzystany, tu sqlite
            Class.forName("org.sqlite.JDBC");
            // Połączenie, wskazujemy rodzaj bazy i jej nazwę
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            System.out.println("Połączyłem się z bazą " + baza);
        } catch (Exception e) {
            System.err.println("Błąd w połączeniu z bazą: \n" + e.getMessage());
            return null;
        }
        return polaczenie;
    }
 
    /**
     * tworzenie tabeli, jesli nie jest utworzona
     */
    public static void stworzTablice(Connection polaczenie, String tablica) { 
        // Obiekt odpowiadający za wykonanie instrukcji
        Statement stat = null;
        try {
            stat = polaczenie.createStatement();
            // polecenie SQL tworzące tablicę
            String tablicaSQL = "CREATE TABLE " + tablica
                    + " (ID INT PRIMARY KEY     NOT NULL,"
                    + " CZESC_MOWY CHAR(20) NOT NULL,"
                    + " WYMOWA      CHAR(50)  NOT NULL,"
                    + " SLOWOPL        CHAR(50)    NOT NULL,"
                    + " SLOWOLATV        CHAR(50)     NOT NULL); ";
            // wywołanie polecenia
            stat.executeUpdate(tablicaSQL);
            // zamykanie wywołania i połączenia
            System.out.println("Utworzono tablice. ");
            stat.close();
            polaczenie.close();
        } catch (SQLException e) {
            System.out.println("Nie mogę stworzyć tablicy " + e.getMessage());
        }
    }
 
    /**
     * Dodawanie rekordu
     */
    public static void dodajDane(Slowo slowo, String baza) { 
        Connection polaczenie = null;
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
 
            stat = polaczenie.createStatement();
            String dodajSQL = "INSERT INTO " + baza + " (ID, CZESC_MOWY, WYMOWA, SLOWOPL, SLOWOLATV) "
                    + "VALUES ("
                    + slowo.getId() + ","
                    + "'" + slowo.getCzescMowy() + "',"
                    + "'" + slowo.getWymowa() + "',"
                    + "'" + slowo.getSlowopl() + "',"
                    + "'" + slowo.getSlowlatv() + "'"
                    + "  );";
            stat.executeUpdate(dodajSQL);
            stat.close();
            polaczenie.close();
            // Komunikat i wydrukowanie końcowej formy polecenia SQL
            System.out.println("Polecenie: \n" + dodajSQL + "\n wykonane.");
        } catch (Exception e) {
            System.out.println("Nie mogę dodać danych " + e.getMessage());
        }
 
    }
 
    /**
     * Wyszukanie danych
     */
    public static String szukaj(String baza, String pole, String wartosc) { // wyszukiwanie rekordu

        Connection polaczenie = null;
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            stat = polaczenie.createStatement();
            // Polecenie wyszukania
            String szukajSQL = "SELECT * FROM " + baza
                    + " WHERE " + pole + "='" + wartosc + "';";
 
            ResultSet wynik = stat.executeQuery(szukajSQL);
            System.out.println("Wynik polecenia:\n" + szukajSQL);
            while (wynik.next()) {
                int id = wynik.getInt("id");
                System.out.println("ID:       " + id);
                System.out.println("Czesc mowy:   "+wynik.getString("czesc_mowy"));
                System.out.println("Wymowa:     "+ wynik.getString("wymowa"));
                System.out.println("Slowopl:   " + wynik.getString("Slowopl"));
                System.out.println("Slowolatv  " + wynik.getString("Slowolatv"));
                System.out.println(" ---------------------- ");
                tlumaczenie = wynik.getString("czesc_mowy") + " " + wynik.getString("wymowa") + " " + wynik.getString("Slowolatv");
            }
           
            wynik.close();
            stat.close();
            polaczenie.close();
            return tlumaczenie;
        } catch (Exception e) {
            System.out.println("Nie mogę wyszukać danych " + e.getMessage());
        }
        return null;
    }
 
    /**
     * Edycja danych
     */
    public static void zmien(String baza, String poleSzukane, String wartoscSzukana,
                             String poleZmieniane, String nowaWartosc) { // zmiana danych
        Connection polaczenie = null;
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            stat = polaczenie.createStatement();
            // Polecenie zmiany
            String zmienSQL = "UPDATE " + baza + " SET "
                    + poleZmieniane + " = '" + nowaWartosc
                    + "' WHERE " + poleSzukane + "='" + wartoscSzukana + "';";
 
            ResultSet wynik = stat.executeQuery(zmienSQL);
            System.out.println("Polecenie:\n" + zmienSQL);
            wynik.close();
            stat.close();
            polaczenie.close();
        } catch (Exception e) {
            System.out.println("Nie mogę poprawić danych " + e.getMessage());
        }
 
    }
    
     /**
     * Usuwanie danych
     */
    public static void usun(String baza, String pole, String wartosc) { // usuwanie danych
        Connection polaczenie = null;
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            stat = polaczenie.createStatement();
            // Polecenie usunięcia
            String usunSQL = "DELETE FROM "+ baza + " WHERE " + pole +
                    "='" + wartosc + "';";
            System.out.println("Polecenie:\n" + usunSQL);
            ResultSet wynik = stat.executeQuery(usunSQL);
 
            wynik.close();
            stat.close();
            polaczenie.close();
        } catch (Exception e) {
            System.out.println("Nie mogę usunąć danych " + e.getMessage());
        }
 
    }
    
    @FXML
    private void about(ActionEvent event){
        pole.setText("Slownik Polsko-Łotewski v.1.0 \n Marcin Szynkowski");
    }
    
    @FXML
    private void zamknij(ActionEvent event){
        exit(0);
    }
    
    @FXML
    private void opis(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new File("src\\slownik\\opis.txt").toURI());
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * zdarzenie - wyszukiwanie po klikieciu przycisku "Szukaj"
     */
    @FXML
    private void handleButtonAction(ActionEvent event) { 
           String baza = "Slownik_pol_lv";
           Connection polaczenie = polacz(baza);
//           stworzTablice(polaczenie, baza);
//           Slowo slowo1 = new Slowo(1, "fraza", "labri:t", "dzień dobry", "Labrīt");
//           dodajDane(slowo1, baza);
 

            policzSlowka();
            
           
           String slowko = slowo.getText();
           if (szukaj("Slownik_pol_lv", "SLOWOPL", slowko) == null) {
               pole.setText("Nie ma takiego hasla w slowniku.");
           }
           else {
               pole.setText(szukaj("Slownik_pol_lv", "SLOWOPL", slowko));
           }
    }
    
    private void policzSlowka() { // metoda liczaca slowka, wyswietla liczbe slowek dostepnych w bazie
        Connection polaczenie = null;
        Statement stat = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String baza = "Slownik_pol_lv";
            polaczenie = DriverManager.getConnection("jdbc:sqlite:" + baza + ".db");
            stat = polaczenie.createStatement();
            String policzSQL = "SELECT COUNT(ID) FROM " + baza;
            ResultSet wynik = stat.executeQuery(policzSQL);
            System.out.println("Polecenie:\n" + policzSQL);
            iloscSlow.setText("Ilość słów w bazie: "+wynik.getString("COUNT(ID)"));
            System.out.println(wynik.getString("COUNT(ID)"));
            wynik.close();
            stat.close();
            polaczenie.close();
        }
        catch (Exception e) {
            System.out.println("Nie mogę wykonac obliczen." + e.getMessage());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       policzSlowka(); // liczenie slowek przy uruchomieniu programu
    }    
    
}
