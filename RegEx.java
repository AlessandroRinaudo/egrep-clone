import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    scanner.close();
    System.out.println(">> Parsing regEx \"" + regEx + "\".");
    System.out.println(">> ...");
    toASCII(regEx);
    RegExTree ret = null;
    // l'algo commence ici
    ret = toRegexTree(ret, regEx);

    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(ret); // étape deux de l'algorithme
    System.out.println("NDFA construction:\n\nBEGIN NDFA\n" + ndfa.toString() + "END NDFA.\n");
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    System.out.println(det);

    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);
    CompleteAutomaton automateAfterDeterminisationStep = new CompleteAutomaton(automateDeterminise,
        Automate.setInitialLetter(det, automateDeterminise), Automate.setFinalsLetter(det, automateDeterminise),
        Determinisation.setLast(determinationList, ndfa));
    System.out.println(automateAfterDeterminisationStep);
    // String mot = "codduebastaboloabcrtopascodduefinalmenteabctabcdadef";
    // String mot = "ciaoSargonaq";
    // String mot = "dzfeabczdfe";
    // String mot = "ciabSargfgfgfgono";
    // String mot = "staSoutherns";
    // String mot = "ciaoSlargonso";
    // System.out
    //     .println("mot validator : " + CompleteAutomaton.regexValidator(mot, automateAfterDeterminisationStep, regEx));

    // le text
    ArrayList<String> text = new ArrayList<String>();
    try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
      String sCurrentLine;
      while ((sCurrentLine = br.readLine()) != null) {
        text.add(sCurrentLine);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // split and execute search
    long startTime = System.currentTimeMillis();
    String resultatParser = BookSearch.searchRegexInBook(text, automateAfterDeterminisationStep, regEx);
    long endTime = System.currentTimeMillis();
    System.out.println("Temps d'exécution " + (endTime - startTime) + " milliseconds");
    System.out.println("Lignes contenantes l'expression : " + regEx + "\n\n" + resultatParser);
  }

  public static void toASCII(String regEx) {
    if (regEx.length() < 1) {
      System.err.println(">> ERROR: empty regEx.");
    } else {
      System.out.print(">> ASCII codes: [");
      for (int i = 0; i < regEx.length(); i++)
        System.out.print("," + (int) regEx.charAt(i));
      System.out.println("].");
      System.out.println(">> String result: " + regEx);
    }
  }

  public static RegExTree toRegexTree(RegExTree ret, String regEx) {
    try {
      regEx = regEx.replace(".", ""); // remove . concatenation
      ret = parse(regEx); // fonction parse de la string regEx
      // System.out.println(">> Regex: " + regEx);
      System.out.println(">> Tree result: " + ret.toString());
    } catch (Exception e) {
      System.err.println(">> ERROR: syntax error for regEx \"" + regEx + "\".");
    }
    return ret;
  }

  // FROM REGEX TO SYNTAX TREE (debug deleted)
  /**
   * il prends la string donnée en paramètre et il renvoie une RegExTree (au
   * départ il crée une liste vide contenante que des feuilles)
   * 
   * @return RegExTree
   */
  private static RegExTree parse(String regEx) throws Exception {
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