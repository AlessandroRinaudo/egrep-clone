import java.util.ArrayList;

public class RegExTree {
  protected int root;
  protected ArrayList<RegExTree> subTrees;

  public RegExTree(int root, ArrayList<RegExTree> subTrees) {
    this.root = root;
    this.subTrees = subTrees;
  }

  // FROM TREE TO PARENTHESIS

  /**
   * Every char does not contain any subtree list
   */
  public String toString() {
    if (subTrees.isEmpty()) {
      return rootToString();
    }
    String result = rootToString() + "(" + subTrees.get(0).toString();
    for (int i = 1; i < subTrees.size(); i++)
      result += "," + subTrees.get(i).toString();
    return result + ")";
  }

  private String rootToString() {
    if (root == Step1then2.CONCAT)
      return ".";
    if (root == Step1then2.ETOILE)
      return "*";
    if (root == Step1then2.ALTERN)
      return "|";
    if (root == Step1then2.DOT)
      return ".";
    return Character.toString((char) root);
  }
}