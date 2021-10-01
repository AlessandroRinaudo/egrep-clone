import java.util.ArrayList;

public class Determinisation {

  protected ArrayList<DFA> FromNdfaToDfa;

  public Determinisation(ArrayList<DFA> FromNdfaToDfa) {
    this.FromNdfaToDfa = FromNdfaToDfa;
  }

  public static ArrayList<DFA> continuoAlgo(int etat, NDFAutomaton matriceEtape2) {
    // ArrayList<DFA> determinisationStep1 = new ArrayList<DFA>();
    ArrayList<DFA> determinisationStep1 = step3Determinisation(etat, matriceEtape2);
    determinisationStep1.addAll(toLoop(determinisationStep1.get(0).valeur, matriceEtape2));
    for (int i = 1; i < determinisationStep1.size(); i++) {
      System.err.println(determinisationStep1.get(i).valeur);
      if (determinisationStep1.get(i).valeur.equals(determinisationStep1.get(i - 1).valeur)) {
        break;
      }
      determinisationStep1.addAll(toLoop(determinisationStep1.get(i).valeur, matriceEtape2));

    }
    return determinisationStep1;
  }

  public static ArrayList<DFA> step3Determinisation(int etat, NDFAutomaton matriceEtape2) {
    // dans l'étape 1 la variable etat est 0
    if (!findOccurenceEpsilonTable(etat, matriceEtape2.epsilonTransitionTable).isEmpty()) {
      // etape 1
      ArrayList<Integer> listATraiter = findOccurenceEpsilonTable(0, matriceEtape2.epsilonTransitionTable);
      // listATraiter.add(0, 0);
      // ArrayList<Integer> listValues = new ArrayList<Integer>();
      return toLoop(listATraiter, matriceEtape2);
    }
    ArrayList<Integer> listATraiter = new ArrayList<Integer>();
    listATraiter.add(0);
    return toLoop(listATraiter, matriceEtape2);

  }

  public static ArrayList<DFA> toLoop(ArrayList<Integer> listATraiter, NDFAutomaton matriceEtape2) {
    ArrayList<DFA> res = new ArrayList<DFA>();
    for (int i = 0; i < listATraiter.size(); i++) {
      int courentElementList = listATraiter.get(i);
      DFA verify = setCase(courentElementList, matriceEtape2, listATraiter);
      if (verify.column != -1)
        res.add(setCase(courentElementList, matriceEtape2, listATraiter));
    }
    return res;
  }

  public static DFA setCase(int courentElementList, NDFAutomaton matriceEtape2, ArrayList<Integer> listLine) {
    ArrayList<Integer> res = new ArrayList<Integer>();
    int coloumn = getArrayIndexColoumnNumber(courentElementList, matriceEtape2.transitionTable);
    if (coloumn != -1) {
      // System.out.println("coloumn : " + (char) coloumn);
      int value = getTransitionTableValue(courentElementList, coloumn, matriceEtape2.transitionTable);
      // System.out.println("value : " + value);
      res = addEpsilon(value, matriceEtape2);
      return new DFA(listLine, coloumn, res);
    }
    // System.out.println("Value not found");
    return new DFA(listLine, coloumn, res);
  }

  public static ArrayList<Integer> addEpsilon(int nb, NDFAutomaton matriceEtape2) {
    ArrayList<Integer> listATraiter = new ArrayList<Integer>();
    ArrayList<Integer> res = new ArrayList<Integer>();
    listATraiter.add(nb);
    res.add(nb);
    boolean continua = true;
    while (continua == true) {
      for (int i = 0; i < listATraiter.size(); i++) {
        if (findOccurenceEpsilonTable(listATraiter.get(i), matriceEtape2.epsilonTransitionTable).isEmpty()) {
          continua = false;
          continue;
        }
        res.addAll(findOccurenceEpsilonTable(listATraiter.get(i), matriceEtape2.epsilonTransitionTable));
        continua = true;
        listATraiter = findOccurenceEpsilonTable(listATraiter.get(i), matriceEtape2.epsilonTransitionTable);
      }
    }
    // for (int i = 0; i < res.size(); i++)
    // System.out.println(res.get(i));
    return res;
  }

  /**
   * It returns the index of column number
   * 
   * @param occurence       index of lines
   * @param transitionTable array of transition states
   * @return the coloumn number
   */
  private static int getArrayIndexColoumnNumber(int occurence, int[][] transitionTable) {
    for (int col = 0; col < 256; col++) {
      if (transitionTable[occurence][col] != -1)
        return col;
    }
    return -1;
  }

  /**
   * @param lines           the line
   * @param col             the coloumn
   * @param transitionTable a transition table
   * @return the transition table value
   */
  private static int getTransitionTableValue(int lines, int col, int[][] transitionTable) {
    return transitionTable[lines][col];
  }

  /**
   * @param occurence       the index of epsilon array
   * @param transitionTable the epsilon transition table
   * @return the value
   */
  private static ArrayList<Integer> findOccurenceEpsilonTable(int occurence, ArrayList<Integer>[] transitionTable) {
    return transitionTable[occurence];
  }

}
