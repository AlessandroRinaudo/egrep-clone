import java.util.Map;
import java.util.ArrayList;

public class Determinisation {

  protected ArrayList<DFA> FromNdfaToDfa;

  public Determinisation(ArrayList<DFA> FromNdfaToDfa) {
    this.FromNdfaToDfa = FromNdfaToDfa;
  }

  public static int step3Determinisation(int etat, NDFAutomaton matriceEtape2) {
    // dans l'étape 1 la variable etat est 0
    if (!findOccurenceEpsilonTable(etat, matriceEtape2.epsilonTransitionTable).isEmpty()) {
      // etape 1
      ArrayList<Integer> listATraiter = findOccurenceEpsilonTable(0, matriceEtape2.epsilonTransitionTable);
      listATraiter.add(0, 0);
      ArrayList<Integer> listValues = new ArrayList<Integer>();
      for (int i = 1; i < listATraiter.size(); i++) {
        int line = listATraiter.get(i);
        System.out.println("line : " + line);
        int coloumn = getArrayIndexColoumnNumber(line, matriceEtape2.transitionTable);
        if (coloumn == -1) {
          continue;
        }
        System.out.println("coloumn : " + (char)coloumn);
        int value = getTransitionTableValue(line, coloumn, matriceEtape2.transitionTable);
        System.out.println("value : " + value);
      }

    }

    return -1;
  }

  private void matrixValueCOnstructor(int line) {

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
