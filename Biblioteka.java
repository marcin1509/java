/**
 * Program obslugujacy biblioteke - nowa wersja
 * 1.wypozyczanie oddawanie ksiazek
 * 2.tworzenie ksiazek
 * 3. Tworzenie czytelnikow i ich obsluga
 */

package mszlab5;
class Ksiazka {
    String autor;
    String tytul;
    boolean wypozyczona;
    Czytelnik czytelnik;
    
    public Ksiazka (String a, String t)
    {
        a = autor;
        t = tytul;
        wypozyczona = false;
        czytelnik = null;
    }
}

class Czytelnik{
    String imie;
    String nazwisko;
    
    public Czytelnik(String im, String n) {
        im = imie;
        n = nazwisko;
    }
    
    public Stan() {
        Ksiazka wyp_ksiazki = new Ksiazka[4];
        for (int k = 0; k < ksiazki.length; k++) {
            if (ksiazki[k].czytelnik != null) {
                wyp_ksiazki[k] = ksiazki[k];
            }
        }
    } 
    
    boolean wypozycz(Ksiazka ksiazka){
        if (ksiazka2 != null && ksiazka1 != null){
            System.out.println("Nie mozna wypozyczyc ksiazki");
            return false;
        }
        if (ksiazka1 == null) {
            ksiazka1 = ksiazka;
            ksiazka1.wypozyczona = true;
            System.out.println("Wypozyczono ksiazke");
            return true;
        }
        ksiazka2 = ksiazka;
        ksiazka2.wypozyczona = true;
        System.out.println("Wypozyczono ksiazke");
        return true;
    }
    
    boolean zwroc(Ksiazka ksiazka)
    {
        if (ksiazka == ksiazka1 && ksiazka1 != null){
            ksiazka.wypozyczona = false;
            ksiazka1 = null;
            System.out.println("Oddano ksiazke. ");
            return true;
        }
        else if (ksiazka == ksiazka2 && ksiazka2 != null)
        {
            ksiazka2.wypozyczona = false;
            ksiazka2 = null;
            System.out.println("Oddano ksiazke. ");
            return true;
        }
        else{
            System.out.println("Zadna ksiazka nie jest wypozyczona");
            return false;
        }
    }
}

public class Biblioteka {
    static Ksiazka ksiazki[] = new Ksiazka[10];
    static Czytelnik Czytelnicy[] = new Czytelnik[10];
    
    static void zwroc(Ksiazka ksiazka){
        for (int i = 0; i < ksiazki.length; i++)
        {
            if (ksiazki[i] == ksiazka)
            {
                ksiazki[i].wypozyczona = false;
                ksiazki[i].czytelnik = null;
            }
        }
    }
    
    static Czytelnik ktoMa(Ksiazka ksiazka){
        for (int j=0; j < ksiazki.length; j++)
            if (ksiazki[j].czytelnik != null)
            {
                return ksiazki[j].czytelnik;
            }
            return null;
    }
    
    
    public static void main(String[] args) {
        
        /*Ksiazka ksiazka = new Ksiazka("Sapkowski Andrzej","Wiedzmin");
        Ksiazka ksiazka2 = new Ksiazka("Peter V. Brett","Malowany Czlowiek");
        ksiazka.wypozyczona = false;
        ksiazka2.wypozyczona = false;
        Czytelnik czytelnik = new Czytelnik("Kowalski");
        Czytelnik czytelnik2 = new Czytelnik("Nowak");
        boolean wypozyczono = czytelnik.wypozycz(ksiazka);
        boolean wypozyczono2 = czytelnik.wypozycz(ksiazka2);
        boolean zwrocono = czytelnik.zwroc(ksiazka);
        boolean zwrocono2 = czytelnik.zwroc(ksiazka2); 
        boolean wypozyczono3 = czytelnik2.wypozycz(ksiazka2);
        boolean wypozyczono4 = czytelnik.wypozycz(ksiazka2);
        boolean zwrocono3 = czytelnik2.zwroc(ksiazka2);
        boolean zwrocono4 = czytelnik.zwroc(ksiazka2); */
    }
    
}
