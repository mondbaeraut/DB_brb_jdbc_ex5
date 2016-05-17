package domain;

/**
 * Created by mond on 10.05.2016.
 * Domain Object. Represents the "Orchestermitglied" in the DB
 */
public class Member extends DomainObject{
    private String fname ="";
    private String lname = "";
    private Boolean replacement = false;


    public void setReplacement(Boolean replacement) {
        this.replacement = replacement;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public String toString() {
        String shortfname = fname.toCharArray()[0] + ".";
        String returnString = shortfname + "\t\t" + lname+"\t\t";
        if(replacement){
            returnString += " :E";
        }
        return returnString;
    }
}
