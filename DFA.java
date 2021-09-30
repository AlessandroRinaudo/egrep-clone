import java.util.ArrayList;

public class DFA {
  protected ArrayList<Integer> line;
  protected int column;
  protected ArrayList<Integer> valeur;
  protected boolean isFirst;
  protected boolean isLast;

  public DFA(ArrayList<Integer> line, int column, ArrayList<Integer> valeur) {
    this.line = line;
    this.column = column;
    this.valeur = valeur;
    isFirst=false;isLast=false;
    // this.isFirst = isFirst;
    // this.isLast = isLast;
  }


  @Override
  public String toString() {
    return "\n\nline=" + line +
            ", column=" + (char)column +
            ", valeur=" + valeur+"\n\n";
  }
}
