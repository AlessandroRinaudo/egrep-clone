import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;

public class AutomateTest {

  @BeforeClass
  public static void initAll() {
    System.out.println("Test Automate begining");
  }

  @Test
  public void simpleAutomate() {
    // Arrange
    String expression = "abc";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);
    System.out.println(automateDeterminise.get(0).line);

    int expectedDeterminisationLine[] = { 0, 1, 3 };
    int expectedDeterminisationColumn[] = { 97, 98, 99 };
    int expectedDeterminisationValue[] = { 1, 3, 5 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      // Test for line
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      // Test for column
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      // Test for value
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithConcat() {
    String expression = "a.b";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);
    System.out.println(automateDeterminise.get(0).line);

    int expectedDeterminisationLine[] = { 0, 1 };
    int expectedDeterminisationColumn[] = { 97, 98 };
    int expectedDeterminisationValue[] = { 1, 3 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithAltern() {
    String expression = "ab|ba";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);
    System.out.println(automateDeterminise.get(0).line);

    int expectedDeterminisationLine[] = { 0, 0, 2, 6 };
    int expectedDeterminisationColumn[] = { 97, 98, 98, 97 };
    int expectedDeterminisationValue[] = { 2, 6, 4, 8 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithAlternAndEtoile() {
    String expression = "a|bc*";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);
    System.out.println(automateDeterminise.get(0).line);

    int expectedDeterminisationLine[] = { 0, 0, 4 };
    int expectedDeterminisationColumn[] = { 97, 98, 99 };
    int expectedDeterminisationValue[] = { 2, 4, 7 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }
}
