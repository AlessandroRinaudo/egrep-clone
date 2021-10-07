import java.util.ArrayList;

public class Determinisation {

  protected ArrayList<DFA> FromNdfaToDfa;
  protected ArrayList<Integer> firstState;
  protected ArrayList<Integer> finalState;
  public static boolean moreEpsilonState;

  public Determinisation(ArrayList<DFA> FromNdfaToDfa, ArrayList<Integer> finalState) {
    this.FromNdfaToDfa = FromNdfaToDfa;
    firstState = new ArrayList<Integer>();
    firstState.add(0);
    this.finalState = finalState;
    moreEpsilonState=false;
  }

  @Override
  public String toString() {
    String res = "BEGIN DETERMINISATION :\n";
    for (int i = 0; i < FromNdfaToDfa.size(); i++) {
      res += FromNdfaToDfa.get(i);
    }
    res += "\nfirst state : ";
    for (int i = 0; i < firstState.size(); i++)
      res += firstState.get(i);
    res += "\nfinal state : ";
    for (int i = 0; i < finalState.size() - 1; i++)
      res += finalState.get(i) + ", ";
    res += finalState.get(finalState.size() - 1);
    res += "\n\nEND DETERMINISATION\n";
    return res;
  }

  /**
   * Set a list with the last elements of an automata state
   */
  public static ArrayList<Integer> setLast(ArrayList<DFA> determination, NDFAutomaton matriceEtape2) {
    ArrayList<Integer> finalState = new ArrayList<Integer>();
    for (int i = 0; i < determination.size(); i++) {
      if (determination.get(i).valeur.contains(matriceEtape2.epsilonTransitionTable.length - 1)) {
        finalState.add(determination.get(i).valeur.get(0));
      }
    }
    // remove double elements
    for (int i = 0; i < finalState.size() - 1; i++) {
      if (finalState.get(i) == (finalState.get(i + 1)))
        finalState.remove(finalState.get(i));
    }
    return finalState;
  }

  public static ArrayList<DFA> DeterminisationFinalisation(int etat, NDFAutomaton matriceEtape2) {
    ArrayList<DFA> determinisationStep1 = step3Determinisation(etat, matriceEtape2);
    determinisationStep1.addAll(toLoop(determinisationStep1.get(0).valeur, matriceEtape2));
    for (int i = 1; i < determinisationStep1.size(); i++) {
      if (determinisationStep1.get(i).valeur.equals(determinisationStep1.get(i - 1).valeur)) {
        continue;
      }
      determinisationStep1.addAll(toLoop(determinisationStep1.get(i).valeur, matriceEtape2));
      // to whatch
      if(moreEpsilonState){
        if (determinisationStep1.contains(determinisationStep1.get(i))) {
          break;
      }
      }
    }
    return determinisationStep1;
  }

  public static ArrayList<DFA> step3Determinisation(int etat, NDFAutomaton matriceEtape2) {
    // dans l'étape 1 la variable etat est 0
    if (!findOccurenceEpsilonTable(etat, matriceEtape2.epsilonTransitionTable).isEmpty()) {
      // etape 1
      ArrayList<Integer> listATraiter = findOccurenceEpsilonTable(etat, matriceEtape2.epsilonTransitionTable);
      if (needMoreEpsilon(listATraiter, matriceEtape2)) {
        ArrayList<Integer> newList = aggiungiEpsilon(listATraiter, matriceEtape2.epsilonTransitionTable);
        if (etat == 0)
          newList.add(0, 0);
        moreEpsilonState=true;
        return toLoop(newList, matriceEtape2);
      }
      if (etat == 0)
        listATraiter.add(0, 0);
      return toLoop(listATraiter, matriceEtape2);
    }
    ArrayList<Integer> listATraiter = new ArrayList<Integer>();
    listATraiter.add(0);
    return toLoop(listATraiter, matriceEtape2);

  }

  public static boolean needMoreEpsilon(ArrayList<Integer> listATraiter, NDFAutomaton matriceEtape2) {
    ArrayList<Integer>[] epsilonTable = matriceEtape2.epsilonTransitionTable;
    for (int i = 0; i < listATraiter.size(); i++) {
      if (!epsilonTable[listATraiter.get(i)].isEmpty()) {
        return true;
      }
    }
    return false;
  }

  public static ArrayList<Integer> addEpsilonFirstStep(ArrayList<Integer> listATraiter, NDFAutomaton matriceEtape2) {
    ArrayList<Integer> res = new ArrayList<Integer>();
    res.addAll(listATraiter);
    ArrayList<Integer>[] epsilonTable = matriceEtape2.epsilonTransitionTable;

    while (!listATraiter.isEmpty()) {

      for (int i = 0; i < listATraiter.size(); i++) {
        if (!epsilonTable[listATraiter.get(i)].isEmpty()) {

          listATraiter.addAll(epsilonTable[listATraiter.get(i)]);
          res.addAll(epsilonTable[listATraiter.get(i)]);
          listATraiter.remove(i);
        } else {
          try {
            listATraiter.remove(i);
          } catch (Exception e) {
          }
        }
      }
    }
    return res;
  }

  private static ArrayList<Integer> aggiungiEpsilon(ArrayList<Integer> listATraiter,
      ArrayList<Integer>[] epsilonTable) {
    for (int i = 0; i < listATraiter.size(); i++) {
      if (!epsilonTable[listATraiter.get(i)].isEmpty()) {
        listATraiter.addAll(epsilonTable[listATraiter.get(i)]);
      }
    }
    return listATraiter;
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
      int value = getTransitionTableValue(courentElementList, coloumn, matriceEtape2.transitionTable);
      res = addEpsilon(value, matriceEtape2);
      return new DFA(listLine, coloumn, res);
    }
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
        listATraiter = res;
      }
    }
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
