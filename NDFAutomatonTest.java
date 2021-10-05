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

    // Act
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    int[][] transition = ndfa.getTransition();
    int finalState = transition.length - 1;
    ArrayList<Integer>[] epsilonTransitionTable = ndfa.getEpsilonTransition();

    // ArrayList<Integer>[] expectedEpsilonTransitionTable;
    int gauche2[] = { 0, 1, 3 };
    ArrayList<Integer> gauche = new ArrayList<Integer>();
    gauche.add(1);
    gauche.add(3);
    int droite2[] = { 0, 2, 4 };
    // expectedEpsilonTransitionTable[0] = gauche;

    for (int i = 0; i < epsilonTransitionTable.length; i++) {
      for (int state : epsilonTransitionTable[i]) {
        System.out.println("i " + i);
        System.out.println("state " + state);
        // System.out.println("i2[i] " + gauche2[i]);
        // System.out.println("state[2i] " + droite2[i]);
        // result += " " + i + " -- epsilon --> " + state + "\n";
        // assertEquals(gauche2[i], i);
        // assertEquals(droite2[i], state);
      }
    }

    // Assert statements
    assertEquals(expectedFinalState, finalState);
  }

  @Test
  public void ndfaWithDot() {
    String expression = "a.b";
    RegExTree ret = null;
    String expectedResult = ".(a,b)";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    assertEquals(expectedResult, treeResult.toString());
  }

  @Test
  public void ndfaWithDotInterpretation() {
    String expression = "a.b";
    RegExTree ret = null;
    String expectedResult = ".(.(a,.),b)";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    assertNotSame(expectedResult, treeResult.toString());
  }

  @Test
  public void ndfaWithException() throws Exception {
    String expression = "*";
    RegExTree ret = null;
    String expectedResult = null;

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    assertEquals(expectedResult, treeResult);

  }

  @Test
  public void ndfaWithAlternAndEtoile() {
    String expression = "a|bc*";
    RegExTree ret = null;
    String expectedResult = "|(a,.(b,*(c)))";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    assertEquals(expectedResult, treeResult.toString());
  }

  @Test
  public void expressionNDFAutomaton() {
    String expression = "a|bc*";
    RegExTree ret = null;
    String expectedResult = "|(a,.(b,*(c)))";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    assertEquals(expectedResult, treeResult.toString());
  }

}
