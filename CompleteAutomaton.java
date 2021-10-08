import java.util.ArrayList;

public class CompleteAutomaton {

  protected ArrayList<Automate> regexAutomaton;
  protected ArrayList<Integer> initialLEtters;
  protected ArrayList<Integer> finalLetters;

  public CompleteAutomaton(ArrayList<Automate> regexAutomaton, ArrayList<Integer> initialLEtters,
      ArrayList<Integer> finalLetters) {
    this.regexAutomaton = regexAutomaton;
    this.initialLEtters = initialLEtters;
    this.finalLetters = finalLetters;
  }

  @Override
  public String toString() {
    String res = "AUTOMATE PROPRE :\n";
    for (int i = 0; i < regexAutomaton.size(); i++) {
      res += regexAutomaton.get(i);
    }
    res += "\n initial letters : " + initialLEtters;
    res += "\n final letters : " + finalLetters;
    res += "\n\nEND AUTOMATE PROPRE\n";
    return res;
  }

  // Function to convert String
  // to ArrayList of Characters
  private static ArrayList<Character> convertStringToCharArrayList(String str) {
    ArrayList<Character> chars = new ArrayList<Character>();
    for (char ch : str.toCharArray()) {
      chars.add(ch);
    }
    return chars;
  }

  public static boolean regexValidator(String wordToValidate, CompleteAutomaton automateDeterminise,String regEx) {

    // basic algorihm structure
    ArrayList<Automate> automateRegex = automateDeterminise.regexAutomaton;
    ArrayList<Integer> initialLetters = automateDeterminise.initialLEtters;
    ArrayList<Integer> finalLetters = automateDeterminise.finalLetters;

    // word conversion into char list
    ArrayList<Character> word = convertStringToCharArrayList(wordToValidate);
    boolean isConcatenate = false;
    for (int i = 0; i < word.size(); i++) {
      if (!initialLetters.contains((int) word.get(i)))
        continue;
      if (finalLetters.contains((int) word.get(i)))
        return true;
      // int initialLine = 0;
      // int initialColumn = (int) word.get(i);
      // int j = i + 1;
      // while (j != word.size() && !isConcatenate) {
      //   isConcatenate = concatenation(word.get(j), initialLine, initialColumn, finalLetters, automateRegex);
      //   initialLine += 1; // le numéro de la ligne qui suit
      //   initialColumn = word.get(j);
      //   j++;
      // }
      if (wordToValidate.contains(regEx)) {
        return true;
      }
    }
    return isConcatenate;
  }

  private static boolean concatenation(int character, int initialLine, int initialColumn,
    ArrayList<Integer> finalLetters, ArrayList<Automate> automateRegex) {
    ArrayList<Integer> newInitList = newInitialList(initialLine, initialColumn, automateRegex);
    // System.out.println("initial line : "+initialLine);
    if (!newInitList.contains(character))
      return false;
    if (newInitList.contains(character) && finalLetters.contains(character)) {
      return true;
    }
      
    return false;
  }

  // new initial state list
  private static ArrayList<Integer> newInitialList(int line, int column, ArrayList<Automate> automateRegex) {

    ArrayList<Integer> res = new ArrayList<Integer>();
    int findValue = -1;
    for (int i = 0; i < automateRegex.size(); i++) {
      if (automateRegex.get(i).line == line && automateRegex.get(i).column == column)
        findValue = automateRegex.get(i).valeur;
    }
    for (int i = 0; i < automateRegex.size(); i++) {
      if (automateRegex.get(i).line == findValue)
        res.add(automateRegex.get(i).column);
    }
    return res;
  }

  // private static boolean newConcatenation () {

  // }



}
