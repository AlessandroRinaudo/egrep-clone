import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Exception;

public class RegEx implements Macros {

  // REGEX
  private static String regEx;

  // MAIN
  public static void main(String arg[]) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("\n>> Please enter a regEx: ");
    regEx = scanner.next();
    regEx = regEx.replace(".", ""); // remove . concatenation
    scanner.close();
    System.out.println(">> Parsing regEx \"" + regEx + "\".");
    System.out.println(">> ...");

    if (regEx.length() < 1) {
      System.err.println(">> ERROR: empty regEx.");
    } else {
      System.out.print(">> ASCII codes: [" );
      for (int i = 0; i < regEx.length(); i++)
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
      // NDFAutomaton.step2_AhoUllman2(ret); // étape deux de l'algorithme
      NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(ret); // étape deux de l'algorithme
      System.out.println("NDFA construction:\n\nBEGIN NDFA\n" + ndfa.toString() + "END NDFA.\n");
      ArrayList<DFA> det = Determinisation.step3Determinisation(0,ndfa);
      for(int i = 0; i < det.size(); i++) {
        System.out.println(det);
      }

    }
  }

  // FROM REGEX TO SYNTAX TREE (debug deleted)
  /**
   * il prends la string donnée en paramètre et il renvoie une RegExTree (au
   * départ il crée une liste vide contenante que des feuilles)
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
}