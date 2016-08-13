package slownik;

public class Slowo {
    private int id;
    private String slowpl;
    private String slowlatv;
    private String czesc_mowy;
    private String wymowa;
    
    public Slowo(int id, String czesc_mowy, String wymowa, String slowpl, String slowlatv) {
        this.id = id;
        this.czesc_mowy = czesc_mowy;
        this.wymowa = wymowa;
        this.slowpl = slowpl;
        this.slowlatv = slowlatv;
    }
    
    public int getId() {
        return id;
    }
 
    public void setId(int nr) {
        this.id = id;
    }
 
    public String getSlowopl() {
        return slowpl;
    }
 
    public void setSlowopl(String slowpl) {
        this.slowpl = slowpl;
    }
 
    public String getSlowlatv() {
        return slowlatv;
    }
 
    public void setSlowlatv(String slowlatv) {
        this.slowlatv = slowlatv;
    }
 
    public String getCzescMowy() {
        return czesc_mowy;
    }
 
    public void setCzescMowy(String czesc_mowy) {
        this.czesc_mowy = czesc_mowy;
    }
    
    public String getWymowa() {
        return wymowa;
    } 
    
    public void setWymowa(String wymowa){
        this.wymowa = wymowa;
    }
 
}
