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
    return "\n"+ line + " --> " + (char) column + " --> " + valeur;
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

  public static ArrayList<Integer> setInitialLetter (Determinisation det,ArrayList<Automate> automateDeterminise) {
    ArrayList<Integer> firstState = det.firstState;
    ArrayList<Integer> initialsLetter = new ArrayList<Integer>();
    for(int i = 0; i<automateDeterminise.size(); i++) {
      if(!initialsLetter.contains(automateDeterminise.get(i).column)) {
        if(firstState.contains(automateDeterminise.get(i).line)) 
          initialsLetter.add(automateDeterminise.get(i).column);
      }
    }
    return initialsLetter;   
  } 
  public static ArrayList<Integer> setFinalsLetter (Determinisation det,ArrayList<Automate> automateDeterminise) {
    ArrayList<Integer> finalState = det.finalState;
    ArrayList<Integer> finalsLetter = new ArrayList<Integer>();
    for(int i = 0; i<automateDeterminise.size(); i++) {
      if(!finalsLetter.contains(automateDeterminise.get(i).column)) {
        if(finalState.contains(automateDeterminise.get(i).valeur)) 
          finalsLetter.add(automateDeterminise.get(i).column);
      }
    }
    return finalsLetter;
  } 

}
