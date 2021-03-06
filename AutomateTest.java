import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;

public class AutomateTest {

  @BeforeClass
  public static void initAll() {
    System.out.println("\nTest Automate begining");
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
    String expression = "ab";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

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
  public void automateWithStar() {
    String expression = "a*b";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 0, 2, 2 };
    int expectedDeterminisationColumn[] = { 97, 98, 97, 98 };
    int expectedDeterminisationValue[] = { 2, 5, 2, 5 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithAltern() {
    String expression = "a|b";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 0 };
    int expectedDeterminisationColumn[] = { 97, 98 };
    int expectedDeterminisationValue[] = { 2, 4 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithConcatAndAltern() {
    String expression = "ab*c";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 1, 1, 4, 4 };
    int expectedDeterminisationColumn[] = { 97, 98, 99, 98, 99 };
    int expectedDeterminisationValue[] = { 1, 4, 7, 4, 7 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithAlternComplex() {
    String expression = "ab|ba";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

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
  public void automateWithAlternAndStar() {
    String expression = "a|bc*";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 0, 4, 7 };
    int expectedDeterminisationColumn[] = { 97, 98, 99, 99 };
    int expectedDeterminisationValue[] = { 2, 4, 7, 7 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithConcatAndStar() {
    String expression = "(ab)*";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 2, 4 };
    int expectedDeterminisationColumn[] = { 97, 98, 97 };
    int expectedDeterminisationValue[] = { 2, 4, 2 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithParenthesisAlternAndStar() {
    String expression = "a(b|c)*";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 1, 1, 5, 5, 7, 7 };
    int expectedDeterminisationColumn[] = { 97, 98, 99, 98, 99, 98, 99 };
    int expectedDeterminisationValue[] = { 1, 5, 7, 5, 7, 5, 7 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithAlternConcatAndStar() {
    String expression = "a|bc*d";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 0, 4, 4, 7, 7 };
    int expectedDeterminisationColumn[] = { 97, 98, 99, 100, 99, 100 };
    int expectedDeterminisationValue[] = { 2, 4, 7, 10, 7, 10 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }

  @Test
  public void automateWithLongStar() {
    String expression = "adf*cs";
    RegExTree ret = null;
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    NDFAutomaton ndfa = NDFAutomaton.step2_AhoUllman(treeResult);
    ArrayList<DFA> determinationList = Determinisation.DeterminisationFinalisation(0, ndfa);
    Determinisation det = new Determinisation(determinationList, Determinisation.setLast(determinationList, ndfa));
    ArrayList<Automate> automateDeterminise = Automate.minimiseAutomaton(det.FromNdfaToDfa);

    int expectedDeterminisationLine[] = { 0, 1, 3, 3, 6, 6, 9 };
    int expectedDeterminisationColumn[] = { 97, 100, 102, 99, 102, 99, 115 };
    int expectedDeterminisationValue[] = { 1, 3, 6, 9, 6, 9, 11 };

    for (int i = 0; i < automateDeterminise.size(); i++) {
      assertEquals(expectedDeterminisationLine[i], (int) automateDeterminise.get(i).line);
      assertEquals((char) expectedDeterminisationColumn[i], (char) automateDeterminise.get(i).column);
      assertEquals(expectedDeterminisationValue[i], (int) automateDeterminise.get(i).valeur);
    }
  }
}
