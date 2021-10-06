import java.util.ArrayList;

public class CompleteAutomaton {

  protected ArrayList<Automate> regexAutomaton;
  protected ArrayList<Integer> initialLEtters;
  protected ArrayList<Integer> finalLetters;

  public CompleteAutomaton(ArrayList<Automate> regexAutomaton) {
    this.regexAutomaton = regexAutomaton;
    initialLEtters= new ArrayList<Integer>();
    finalLetters=new ArrayList<Integer>();;
  }

  @Override
  public String toString() {
    String res = "AUTOMATE PROPRE :\n";
    for (int i = 0; i < regexAutomaton.size(); i++) {
      res += regexAutomaton.get(i);
    }
    res += "\n\nEND AUTOMATE PROPRE\n";
    return res;
  }



}
