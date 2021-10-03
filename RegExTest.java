import org.junit.Test;

import static org.junit.Assert.*;

public class RegExTest {

  @Test
  public void testExpressionWithAlternAndEtoile() {
    // Arrange
    String expression = "a|bc*";
    RegExTree ret = null;
    String expectedResult = "|(a,.(b,*(c)))";

    // Act
    RegExTree treeResult = RegEx.toRegexTree(ret, expression);

    // Assert statements
    assertEquals(expectedResult, treeResult.toString());
  }

  @Test
  public void testAssert() {

    // Variable declaration
    String string1 = "Junit";
    String string2 = "Junit";
    int[] airethematicArrary1 = { 1, 2, 3 };
    int[] airethematicArrary2 = { 1, 2, 3 };

    // Assert statements
    assertEquals(string1, string2);
    assertArrayEquals(airethematicArrary1, airethematicArrary2);
  }

}
