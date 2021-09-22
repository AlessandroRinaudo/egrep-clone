import java.util.Scanner;
import java.util.ArrayList;

import java.lang.Exception;

public class RegEx {
  // MACROS
  static final int CONCAT = 0xC04CA7;
  static final int ETOILE = 0xE7011E;
  static final int ALTERN = 0xA17E54;
  static final int PROTECTION = 0xBADDAD;

  static final int PARENTHESEOUVRANT = 0x16641664;
  static final int PARENTHESEFERMANT = 0x51515151;
  static final int DOT = 0xD07;

  // REGEX
  private static String regEx;

  // MAIN
  public static void main(String arg[]) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("\n>> Please enter a regEx: ");
    regEx = scanner.next();
    System.out.println(">> Parsing regEx \"" + regEx + "\".");
    System.out.println(">> ...");

    if (regEx.length() < 1) {
      System.err.println(">> ERROR: empty regEx.");
    } else {
      System.out.print(">> ASCII codes: [" + (int) regEx.charAt(0));
      for (int i = 1; i < regEx.length(); i++)
        System.out.print("," + (int) regEx.charAt(i));
      System.out.println("].");
      System.out.println(">> Strign result: " + regEx);

      // l'algo commence ici
      try {
        RegExTree ret = parse(); // fonction parse de la string regEx
        System.out.println(">> Tree result: " + ret.toString() + "\n");
      } catch (Exception e) {
        System.err.println(">> ERROR: syntax error for regEx \"" + regEx + "\".");
      }

    }
  }

  // FROM REGEX TO SYNTAX TREE (debug deleted)
  /**
   * il prend la string donnée en paramètre et il renvoie une RegExTree (au départ
   * il crée une liste vide contenante que des feuilles)
   * 
   * @return RegExTree
   */
  private static RegExTree parse() throws Exception {
    ArrayList<RegExTree> result = new ArrayList<RegExTree>(); // arrayList de type RegExTree
    for (int i = 0; i < regEx.length(); i++) // pour chaque élément de la string regEx
      result.add(new RegExTree(charToRoot(regEx.charAt(i)), new ArrayList<RegExTree>())); // ajout dans la liste de type
                                                                                          // RegExTree chaque char de la
                                                                                          // string regEx avec des sous
                                                                                          // liste vides (soit ascii
                                                                                          // soit un symbole)

    return parse2(result); // c'est grâce à l'appelle de cette fonction qu'il crée le RegExTree
  }

  private static int charToRoot(char c) {
    if (c == '.')
      return DOT;
    if (c == '*')
      return ETOILE;
    if (c == '|')
      return ALTERN;
    if (c == '(')
      return PARENTHESEOUVRANT;
    if (c == ')')
      return PARENTHESEFERMANT;
    return (int) c;
  }

  /**
   * il commence à traiter dans l'orde : les parentheses, les étoiles, les concats, les altern
   * @param result une List de RegExTree contenante que des feuilles (de l'objet
   *               RegExTree il contient que root)
   * @return un RegExTree complet (constitué par un root et une list de RegExTree)
   */
  private static RegExTree parse2(ArrayList<RegExTree> result) throws Exception {
    while (containParenthese(result))
      result = processParenthese(result);
    while (containEtoile(result))
      result = processEtoile(result);
    while (containConcat(result))
      result = processConcat(result);
    while (containAltern(result))
      result = processAltern(result);

    if (result.size() > 1)
      throw new Exception();

    return removeProtection(result.get(0));
  }

  private static boolean containParenthese(ArrayList<RegExTree> trees) {
    for (RegExTree t : trees)
      if (t.root == PARENTHESEFERMANT || t.root == PARENTHESEOUVRANT)
        return true;
    return false;
  }

  /**
   * @param trees une liste de RegExTree 
   * @return une liste de RegExTree 
   */
  private static ArrayList<RegExTree> processParenthese(ArrayList<RegExTree> trees) throws Exception {
    ArrayList<RegExTree> result = new ArrayList<RegExTree>();
    boolean found = false;
    for (RegExTree t : trees) {
      if (!found && t.root == PARENTHESEFERMANT) {
        boolean done = false;
        ArrayList<RegExTree> content = new ArrayList<RegExTree>();
        while (!done && !result.isEmpty())
          if (result.get(result.size() - 1).root == PARENTHESEOUVRANT) {
            done = true;
            result.remove(result.size() - 1);
          } else
            content.add(0, result.remove(result.size() - 1));
        if (!done)
          throw new Exception();
        found = true;
        ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
        subTrees.add(parse2(content));
        result.add(new RegExTree(PROTECTION, subTrees));
      } else {
        result.add(t);
      }
    }
    if (!found)
      throw new Exception();
    return result;
  }

  private static boolean containEtoile(ArrayList<RegExTree> trees) {
    for (RegExTree t : trees)
      if (t.root == ETOILE && t.subTrees.isEmpty())
        return true;
    return false;
  }

  private static ArrayList<RegExTree> processEtoile(ArrayList<RegExTree> trees) throws Exception {
    System.out.println("processEtoile");
    ArrayList<RegExTree> result = new ArrayList<RegExTree>();
    boolean found = false;
    for (RegExTree t : trees) {
      if (!found && t.root == ETOILE && t.subTrees.isEmpty()) {
        if (result.isEmpty())
          throw new Exception();
        found = true;
        RegExTree last = result.remove(result.size() - 1);
        ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
        subTrees.add(last);
        result.add(new RegExTree(ETOILE, subTrees));
      } else {
        result.add(t);
      }
    }
    System.out.println(result.toString());
    return result;
  }

  private static boolean containConcat(ArrayList<RegExTree> trees) {
    boolean firstFound = false;
    for (RegExTree t : trees) {
      if (!firstFound && t.root != ALTERN) {
        firstFound = true;
        continue;
      }
      if (firstFound)
        if (t.root != ALTERN)
          return true;
        else
          firstFound = false;
    }
    return false;
  }

  private static ArrayList<RegExTree> processConcat(ArrayList<RegExTree> trees) throws Exception {
    System.out.println("processConcat");
    ArrayList<RegExTree> result = new ArrayList<RegExTree>();
    boolean found = false;
    boolean firstFound = false;
    for (RegExTree t : trees) {
      if (!found && !firstFound && t.root != ALTERN) {
        firstFound = true;
        result.add(t);
        continue;
      }
      if (!found && firstFound && t.root == ALTERN) {
        firstFound = false;
        result.add(t);
        continue;
      }
      if (!found && firstFound && t.root != ALTERN) {
        found = true;
        RegExTree last = result.remove(result.size() - 1);
        ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
        subTrees.add(last);
        subTrees.add(t);
        result.add(new RegExTree(CONCAT, subTrees));
      } else {
        result.add(t);
      }
    }
    System.out.println(result.toString());
    return result;
  }

  private static boolean containAltern(ArrayList<RegExTree> trees) {
    for (RegExTree t : trees)
      if (t.root == ALTERN && t.subTrees.isEmpty())
        return true;
    return false;
  }

  private static ArrayList<RegExTree> processAltern(ArrayList<RegExTree> trees) throws Exception {
    System.out.println("processAltern");
    ArrayList<RegExTree> result = new ArrayList<RegExTree>();
    boolean found = false;
    RegExTree gauche = null;
    boolean done = false;
    for (RegExTree t : trees) {
      if (!found && t.root == ALTERN && t.subTrees.isEmpty()) {
        if (result.isEmpty())
          throw new Exception();
        found = true;
        gauche = result.remove(result.size() - 1);
        continue;
      }
      if (found && !done) {
        if (gauche == null)
          throw new Exception();
        done = true;
        ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
        subTrees.add(gauche);
        subTrees.add(t);
        result.add(new RegExTree(ALTERN, subTrees));
      } else {
        result.add(t);
      }
    }
    System.out.println(result.toString());
    return result;
  }

  private static RegExTree removeProtection(RegExTree tree) throws Exception {
    if (tree.root == PROTECTION && tree.subTrees.size() != 1)
      throw new Exception();
    if (tree.subTrees.isEmpty())
      return tree;
    if (tree.root == PROTECTION)
      return removeProtection(tree.subTrees.get(0));

    ArrayList<RegExTree> subTrees = new ArrayList<RegExTree>();
    for (RegExTree t : tree.subTrees)
      subTrees.add(removeProtection(t));
    return new RegExTree(tree.root, subTrees);
  }

}

// UTILITARY CLASS
class RegExTree {
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
    if (root == RegEx.CONCAT)
      return ".";
    if (root == RegEx.ETOILE)
      return "*";
    if (root == RegEx.ALTERN)
      return "|";
    if (root == RegEx.DOT)
      return ".";
    return Character.toString((char) root);
  }
}