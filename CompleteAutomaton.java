import java.util.ArrayList;

public class CompleteAutomaton {

  protected ArrayList<Automate> regexAutomaton;
  protected ArrayList<Integer> initialLEtters;
  protected ArrayList<Integer> finalLetters;

  public CompleteAutomaton(ArrayList<Automate> regexAutomaton,ArrayList<Integer> initialLEtters,ArrayList<Integer> finalLetters) {
    this.regexAutomaton = regexAutomaton;
    this.initialLEtters= initialLEtters;
    this.finalLetters=finalLetters;
  }

  @Override
  public String toString() {
    String res = "AUTOMATE PROPRE :\n";
    for (int i = 0; i < regexAutomaton.size(); i++) {
      res += regexAutomaton.get(i);
    }
    res+="\n initial letters : "+  initialLEtters;
    res+="\n final letters : "+finalLetters;
    res += "\n\nEND AUTOMATE PROPRE\n";
    return res;
  }



}
