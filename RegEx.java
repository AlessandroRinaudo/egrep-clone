import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Exception;

public class RegEx {
  // MACROS
  static final int CONCAT = 0xC04CA7;
  static final int ETOILE = 0xE7011E;
  static final int ALTERN = 0xA17E54;
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
    scanner.close();
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
      NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(ret);
      System.out.println("  >> NDFA construction:\n\nBEGIN NDFA\n"+ndfa.toString()+"END NDFA.\n");
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
}