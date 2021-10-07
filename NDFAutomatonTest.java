import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;

public class NDFAutomatonTest {

  @BeforeClass
  public static void initAll() {
    System.out.println("\nTest NDF Automaton begining");
  }

  @Test
  public void simpleNdfa() {
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
    int expectedEpsilon[][] = { { -1 }, { 2 }, { -1 }, { 4 }, { -1 }, { -1 } };
    int expectedTransition[] = { 1, -1, 3, -1, 5 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      if (expectedEpsilon[i][0] != -1) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          assertEquals(expectedEpsilon[i][j], (int) epsilonTransitionTable[i].get(j));
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
  public void ndfaWithConcat() {
    String expression = "ab";
    RegExTree ret = null;
    int expectedFinalState = 3;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transitionTable = ndfa.getTransition();
    int finalState = transitionTable.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();
    int expectedEpsilon[][] = { { -1 }, { 2 }, { -1 }, { -1 } };
    int expectedTransition[] = { 1, -1, 3 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      if (expectedEpsilon[i][0] != -1) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          assertEquals(expectedEpsilon[i][j], (int) epsilonTransitionTable[i].get(j));
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
  public void ndfaWithStar() {
    String expression = "a*b";
    RegExTree ret = null;
    int expectedFinalState = 5;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transitionTable = ndfa.getTransition();
    int finalState = transitionTable.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();
    int expectedEpsilon[][] = { { 1, 3 }, { -1 }, { 3, 1 }, { 4 }, { -1 }, { -1 } };
    int expectedTransition[] = { -1, 2, -1, -1, 5 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      if (expectedEpsilon[i][0] != -1) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          assertEquals(expectedEpsilon[i][j], (int) epsilonTransitionTable[i].get(j));
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
  public void ndfaWithAltern() {
    String expression = "a|b";
    RegExTree ret = null;
    int expectedFinalState = 5;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transitionTable = ndfa.getTransition();
    int finalState = transitionTable.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();
    int expectedEpsilon[][] = { { 1, 3 }, { -1 }, { 5 }, { -1 }, { 5 }, { -1 } };
    int expectedTransition[] = { -1, 2, -1, 4 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      if (expectedEpsilon[i][0] != -1) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          assertEquals(expectedEpsilon[i][j], (int) epsilonTransitionTable[i].get(j));
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
  public void ndfaWithAlternComplex() {
    String expression = "ab|ba";
    RegExTree ret = null;
    int expectedFinalState = 9;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transitionTable = ndfa.getTransition();
    int finalState = transitionTable.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();
    int expectedEpsilon[][] = { { 1, 5 }, { -1 }, { 3 }, { -1 }, { 9 }, { -1 }, { 7 }, { -1 }, { 9 }, { -1 } };
    int expectedTransition[] = { -1, 2, -1, 4, -1, 6, -1, 8 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      if (expectedEpsilon[i][0] != -1) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          assertEquals(expectedEpsilon[i][j], (int) epsilonTransitionTable[i].get(j));
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
  public void ndfaWithAlternAndStar() {
    String expression = "a|bc*";
    RegExTree ret = null;
    int expectedFinalState = 9;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transitionTable = ndfa.getTransition();
    int finalState = transitionTable.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();
    int expectedEpsilon[][] = { { 1, 3 }, { -1 }, { 9 }, { -1 }, { 5 }, { 6, 8 }, { -1 }, { 8, 6 }, { 9 }, { -1 } };
    int expectedTransition[] = { -1, 2, -1, 4, -1, -1, 7 };

    // Test for epsilon transition step
    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      if (expectedEpsilon[i][0] != -1) {
        for (int j = 0; j < expectedEpsilon[i].length; j++) {
          assertEquals(expectedEpsilon[i][j], (int) epsilonTransitionTable[i].get(j));
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
