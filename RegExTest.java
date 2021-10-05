import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.BeforeClass;

public class RegExTest {

  @BeforeClass
  public static void initAll() {
    System.out.println("Test Regex tree begining");
  }

  @Test
  public void regexTreeWithNothing() {
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
  public void regexTreeWithDot() {
    String expression = "a.b";
    RegExTree ret = null;
    String expectedResult = ".(a,b)";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    assertEquals(expectedResult, treeResult.toString());
  }

  @Test
  public void regexTreeWithDotInterpretation() {
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
  public void regexTreeWithAlternAndEtoile() {
    String expression = "a|bc*";
    RegExTree ret = null;
    String expectedResult = "|(a,.(b,*(c)))";

    RegExTree treeResult = RegEx.toRegexTree(ret, expression);
    assertEquals(expectedResult, treeResult.toString());
  }
}
