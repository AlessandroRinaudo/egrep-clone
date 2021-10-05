import java.util.ArrayList;

public class Automate {
  protected int line;
  protected int column;
  protected int valeur;

  public Automate(int line, int column, int valeur) {
    this.line = line;
    this.column = column;
    this.valeur = valeur;
  }

  @Override
  public String toString() {
    return "\n\n" + line + " --> " + (char) column + " --> " + valeur +"\n\n";
  }

  public static ArrayList<Automate> minimiseAutomaton (ArrayList<DFA> deterministAutomaton) {
    ArrayList<Automate> states = new ArrayList<Automate>();
    for(int i = 0; i < deterministAutomaton.size(); i++) {
      int line = deterministAutomaton.get(i).line.get(0);
      int column = deterministAutomaton.get(i).column;
      int valeur = deterministAutomaton.get(i).valeur.get(0);
      states.add(new Automate(line,column,valeur));
    }
    return states;
  }

}
