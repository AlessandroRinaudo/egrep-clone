import java.util.ArrayList;

public class DFA {
  protected ArrayList<Integer> line;
  protected int column;
  protected ArrayList<Integer> valeur;
  protected boolean isFirst;
  protected boolean isLast;

  public DFA(ArrayList<Integer> line, int column, ArrayList<Integer> valeur, boolean isFirst, boolean isLast) {
    this.line = line;
    this.column = column;
    this.valeur = valeur;
    this.isFirst = isFirst;
    this.isLast = isLast;
  }


}
