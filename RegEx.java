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
      RegExTree ret = null;
      System.out.println(">> Strign result: " + regEx);

      // l'algo commence ici
      try {
        ret = parse(); // fonction parse de la string regEx
        System.out.println(">> Tree result: " + ret.toString() + "\n");
      } catch (Exception e) {
        System.err.println(">> ERROR: syntax error for regEx \"" + regEx + "\".");
      }
      NDFAutomaton ndfa = step2_AhoUllman(ret);
      System.out.println("  >> NDFA construction:\n\nBEGIN NDFA\n" + ndfa.toString() + "END NDFA.\n");
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

    return RegExParser.parse2(result); // c'est grâce à l'appelle de cette fonction qu'il crée le RegExTree
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

  private static NDFAutomaton step2_AhoUllman(RegExTree ret) {

    if (ret.subTrees.isEmpty()) {
      // IMPLICIT REPRESENTATION HERE: INIT STATE IS ALWAYS 0; FINAL STATE IS ALWAYS
      // transitionTable.length-1
      int[][] tTab = new int[2][256];
      ArrayList<Integer>[] eTab = new ArrayList[2];

      // DUMMY VALUES FOR INITIALIZATION
      for (int i = 0; i < tTab.length; i++)
        for (int col = 0; col < 256; col++)
          tTab[i][col] = -1;
      for (int i = 0; i < eTab.length; i++)
        eTab[i] = new ArrayList<Integer>();

      if (ret.root != DOT)
        tTab[0][ret.root] = 1; // transition ret.root from initial state "0" to final state "1"
      else
        for (int i = 0; i < 256; i++)
          tTab[0][i] = 1; // transition DOT from initial state "0" to final state "1"

      return new NDFAutomaton(tTab, eTab);
    }

    if (ret.root == CONCAT) {
      // IMPLICIT REPRESENTATION HERE: INIT STATE IS ALWAYS 0; FINAL STATE IS ALWAYS
      // transitionTable.length-1
      NDFAutomaton gauche = step2_AhoUllman(ret.subTrees.get(0));
      int[][] tTab_g = gauche.transitionTable;
      ArrayList<Integer>[] eTab_g = gauche.epsilonTransitionTable;
      NDFAutomaton droite = step2_AhoUllman(ret.subTrees.get(1));
      int[][] tTab_d = droite.transitionTable;
      ArrayList<Integer>[] eTab_d = droite.epsilonTransitionTable;
      int lg = tTab_g.length;
      int ld = tTab_d.length;
      int[][] tTab = new int[lg + ld][256];
      ArrayList<Integer>[] eTab = new ArrayList[lg + ld];

      // DUMMY VALUES FOR INITIALIZATION
      for (int i = 0; i < tTab.length; i++)
        for (int col = 0; col < 256; col++)
          tTab[i][col] = -1;
      for (int i = 0; i < eTab.length; i++)
        eTab[i] = new ArrayList<Integer>();

      eTab[lg - 1].add(lg); // epsilon transition from old final state "left" to old initial state "right"

      for (int i = 0; i < lg; i++)
        for (int col = 0; col < 256; col++)
          tTab[i][col] = tTab_g[i][col]; // copy old transitions
      for (int i = 0; i < lg; i++)
        eTab[i].addAll(eTab_g[i]); // copy old transitions
      for (int i = lg; i < lg + ld - 1; i++)
        for (int col = 0; col < 256; col++)
          if (tTab_d[i - lg][col] != -1)
            tTab[i][col] = tTab_d[i - lg][col] + lg; // copy old transitions
      for (int i = lg; i < lg + ld - 1; i++)
        for (int s : eTab_d[i - lg])
          eTab[i].add(s + lg); // copy old transitions

      return new NDFAutomaton(tTab, eTab);
    }

    if (ret.root == ALTERN) {
      // IMPLICIT REPRESENTATION HERE: INIT STATE IS ALWAYS 0; FINAL STATE IS ALWAYS
      // transitionTable.length-1
      NDFAutomaton gauche = step2_AhoUllman(ret.subTrees.get(0));
      int[][] tTab_g = gauche.transitionTable;
      ArrayList<Integer>[] eTab_g = gauche.epsilonTransitionTable;
      NDFAutomaton droite = step2_AhoUllman(ret.subTrees.get(1));
      int[][] tTab_d = droite.transitionTable;
      ArrayList<Integer>[] eTab_d = droite.epsilonTransitionTable;
      int lg = tTab_g.length;
      int ld = tTab_d.length;
      int[][] tTab = new int[2 + lg + ld][256];
      ArrayList<Integer>[] eTab = new ArrayList[2 + lg + ld];

      // DUMMY VALUES FOR INITIALIZATION
      for (int i = 0; i < tTab.length; i++)
        for (int col = 0; col < 256; col++)
          tTab[i][col] = -1;
      for (int i = 0; i < eTab.length; i++)
        eTab[i] = new ArrayList<Integer>();

      eTab[0].add(1); // epsilon transition from new initial state to old initial state
      eTab[0].add(1 + lg); // epsilon transition from new initial state to old initial state
      eTab[1 + lg - 1].add(2 + lg + ld - 1); // epsilon transition from old final state to new final state
      eTab[1 + lg + ld - 1].add(2 + lg + ld - 1); // epsilon transition from old final state to new final state

      for (int i = 1; i < 1 + lg; i++)
        for (int col = 0; col < 256; col++)
          if (tTab_g[i - 1][col] != -1)
            tTab[i][col] = tTab_g[i - 1][col] + 1; // copy old transitions
      for (int i = 1; i < 1 + lg; i++)
        for (int s : eTab_g[i - 1])
          eTab[i].add(s + 1); // copy old transitions
      for (int i = 1 + lg; i < 1 + lg + ld - 1; i++)
        for (int col = 0; col < 256; col++)
          if (tTab_d[i - 1 - lg][col] != -1)
            tTab[i][col] = tTab_d[i - 1 - lg][col] + 1 + lg; // copy old transitions
      for (int i = 1 + lg; i < 1 + lg + ld; i++)
        for (int s : eTab_d[i - 1 - lg])
          eTab[i].add(s + 1 + lg); // copy old transitions

      return new NDFAutomaton(tTab, eTab);
    }

    if (ret.root == ETOILE) {
      // IMPLICIT REPRESENTATION HERE: INIT STATE IS ALWAYS 0; FINAL STATE IS ALWAYS
      // transitionTable.length-1
      NDFAutomaton fils = step2_AhoUllman(ret.subTrees.get(0));
      int[][] tTab_fils = fils.transitionTable;
      ArrayList<Integer>[] eTab_fils = fils.epsilonTransitionTable;
      int l = tTab_fils.length;
      int[][] tTab = new int[2 + l][256];
      ArrayList<Integer>[] eTab = new ArrayList[2 + l];

      // DUMMY VALUES FOR INITIALIZATION
      for (int i = 0; i < tTab.length; i++)
        for (int col = 0; col < 256; col++)
          tTab[i][col] = -1;
      for (int i = 0; i < eTab.length; i++)
        eTab[i] = new ArrayList<Integer>();

      eTab[0].add(1); // epsilon transition from new initial state to old initial state
      eTab[0].add(2 + l - 1); // epsilon transition from new initial state to new final state
      eTab[2 + l - 2].add(2 + l - 1); // epsilon transition from old final state to new final state
      eTab[2 + l - 2].add(1); // epsilon transition from old final state to old initial state

      for (int i = 1; i < 2 + l - 1; i++)
        for (int col = 0; col < 256; col++)
          if (tTab_fils[i - 1][col] != -1)
            tTab[i][col] = tTab_fils[i - 1][col] + 1; // copy old transitions
      for (int i = 1; i < 2 + l - 1; i++)
        for (int s : eTab_fils[i - 1])
          eTab[i].add(s + 1); // copy old transitions

      return new NDFAutomaton(tTab, eTab);
    }

    return null;
  }
}
