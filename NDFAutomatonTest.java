import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;

public class NDFAutomatonTest {

  @BeforeClass
  public static void initAll() {
    System.out.println("Test NDF Automaton begining");
  }

  @Test
  public void ndfaWithNothing() {
    // Arrange
    String expression = "abc";
    RegExTree ret = null;
    // initial state always 0
    int expectedFinalState = 5;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transitionTable = ndfa.getTransition();
    int finalState = transitionTable.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();
    int expectedEpsilon[][] = { { -1 }, { 2 }, { -1 }, { 4 } };
    int expectedTransition[] = { 1, -1, 3, -1, 5 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      for (int state : epsilonTransitionTable[i]) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          if (expectedEpsilon[i][j] != -1) {
            assertEquals(expectedEpsilon[i][j], state);
          }
        }
      }
    }
    // Test for transition step
    for (int i = 0; i < transitionTable.length; i++) {
      for (int col = 0; col < 256; col++) {
        if (transitionTable[i][col] != -1) {
          assertEquals(expectedTransition[i], transitionTable[i][col]);
        }
      }
    }
    assertEquals(expectedFinalState, finalState);
  }

  @Test
  public void ndfaWithDot() {
    String expression = "a.b";
    RegExTree ret = null;
    int expectedFinalState = 3;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transitionTable = ndfa.getTransition();
    int finalState = transitionTable.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();
    int expectedEpsilon[][] = { { -1 }, { 2 } };
    int expectedTransition[] = { 1, -1, 3 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      for (int state : epsilonTransitionTable[i]) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          if (expectedEpsilon[i][j] != -1) {
            assertEquals(expectedEpsilon[i][j], state);
          }
        }
      }
    }
    // Test for transition step
    for (int i = 0; i < transitionTable.length; i++) {
      for (int col = 0; col < 256; col++) {
        if (transitionTable[i][col] != -1) {
          assertEquals(expectedTransition[i], transitionTable[i][col]);
        }
      }
    }
    assertEquals(expectedFinalState, finalState);
  }

  // not working
  @Test
  public void ndfaWithAlternAndEtoile() {
    String expression = "a|bc*";
    RegExTree ret = null;
    int expectedFinalState = 9;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transitionTable = ndfa.getTransition();
    int finalState = transitionTable.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();
    int expectedEpsilon[][] = { { 1, 3 }, { -1 }, { 9 }, { -1 }, { 5 }, { 6, 8 }, { -1 }, { 8, 6 }, { 9 } };
    int expectedTransition[] = { -1, 2, -1, 4, -1, -1, 7 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      for (int state : epsilonTransitionTable[i]) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          if (expectedEpsilon[i][j] != -1) {
            System.out.println("state " + state);
            System.out.println("expectedEpsilon[i][j] " + expectedEpsilon[i][j]);
            // assertEquals(expectedEpsilon[i][j], state);
          }
        }
      }
    }
    // Test for transition step
    for (int i = 0; i < transitionTable.length; i++) {
      for (int col = 0; col < 256; col++) {
        if (transitionTable[i][col] != -1) {
          assertEquals(expectedTransition[i], transitionTable[i][col]);
        }
      }
    }
    assertEquals(expectedFinalState, finalState);
  }
}
