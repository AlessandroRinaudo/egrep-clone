import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;

public class DeterminisationTest {

  @BeforeClass
  public static void initAll() {
    System.out.println("Test Determinisation begining");
  }

  @Test
  public void simpleDeterminisation() {
    // Arrange
    String expression = "abc";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    System.out.println("det" + det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 1, 3 };
    String expectedDeterminisationColumn[] = { "a", "b", "c" };
    int expectedDeterminisationValue[] = { 1, 3, 5 };

    // Test for line
    for (int i = 0; i < det.FromNdfaToDfa.size(); i++) {
      System.out.println("det " + det.FromNdfaToDfa.get(i).line.get(0));
      System.out.println("expected " + expectedDeterminisationLine[i]);
      assertEquals(expectedDeterminisationLine[i], (int) det.FromNdfaToDfa.get(i).line.get(0));
    }
  }
}
