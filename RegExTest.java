import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.BeforeClass;

public class RegExTest {

  @BeforeClass
  public static void initAll() {
    System.out.println("\nTest Regex tree begining");
  }

  @Test
  public void simpleRegexTree() {
    // Arrange
    String expression = "abc";
    RegExTree ret = null;
    String expectedResult = ".(.(a,b),c)";

    // Act
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    // Assert statements
    assertEquals(expectedResult, treeResult.toString());
  }

  @Test
  public void regexTreeWithConcat() {
    String expression = "a.b";
    RegExTree ret = null;
    String expectedResult = ".(a,b)";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    assertEquals(expectedResult, treeResult.toString());
  }

  @Test
  public void regexTreeWithConcatInterpretation() {
    String expression = "a.b";
    RegExTree ret = null;
    String expectedResult = ".(.(a,.),b)";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    assertNotSame(expectedResult, treeResult.toString());
  }

  @Test
  public void regexTreeWithException() throws Exception {
    String expression = "*";
    RegExTree ret = null;
    String expectedResult = null;

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    assertEquals(expectedResult, treeResult);

  }

  @Test
  public void regexTreeWithAltern() {
    String expression = "ab|ba";
    RegExTree ret = null;
    String expectedResult = "|(.(a,b),.(b,a))";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    assertEquals(expectedResult, treeResult.toString());
  }

  @Test
  public void regexTreeWithAlternAndEtoile() {
    String expression = "a|bc*";
    RegExTree ret = null;
    String expectedResult = "|(a,.(b,*(c)))";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    assertEquals(expectedResult, treeResult.toString());
  }
}
