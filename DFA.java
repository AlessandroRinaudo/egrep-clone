import java.util.ArrayList;

public class DFA {
  protected ArrayList<Integer> line;
  protected int column;
  protected ArrayList<Integer> valeur;

  public DFA(ArrayList<Integer> line, int column, ArrayList<Integer> valeur) {
    this.line = line;
    this.column = column;
    this.valeur = valeur;
  }

  @Override
  public String toString() {
    return "\n\nline=" + line + ", column=" + (char) column + ", valeur=" + valeur +"\n\n";
  }
}
