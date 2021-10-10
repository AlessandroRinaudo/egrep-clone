import java.util.ArrayList;

public class CompleteAutomaton {

  protected ArrayList<Automate> regexAutomaton;
  protected ArrayList<Integer> initialLEtters;
  protected ArrayList<Integer> finalLetters;

  protected int firstState;
  protected ArrayList<Integer> finalState;

  public CompleteAutomaton(ArrayList<Automate> regexAutomaton, ArrayList<Integer> initialLEtters,
      ArrayList<Integer> finalLetters, ArrayList<Integer> finalState) {
    this.regexAutomaton = regexAutomaton;
    this.initialLEtters = initialLEtters;
    this.finalLetters = finalLetters;
    firstState = 0;
    this.finalState = finalState;
  }

  @Override
  public String toString() {
    String res = "AUTOMATE PROPRE :\n";
    for (int i = 0; i < regexAutomaton.size(); i++) {
      res += regexAutomaton.get(i);
    }
    // res += "\n initial letters : " + initialLEtters;
    // res += "\n final letters : " + finalLetters;
    res += "\n initial states : " + firstState;
    res += "\n final states : " + finalState;
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

  public static boolean regexValidator(String wordToValidate, CompleteAutomaton automateDeterminise, String regEx) {

    ArrayList<Character> word = convertStringToCharArrayList(wordToValidate);
    int initialState = automateDeterminise.firstState;
    boolean found = false;

    // Check if any letter are not present in automate
    for (int i = 0; i < word.size() && !found; i++) {
      if (findLetterInAutomaton((int) word.get(i), automateDeterminise))
        found = true;
    }
    if (!found)
      return false;

    found = false;

    int i = 0;
    while (!found && !word.isEmpty() && i < word.size()) {
      if (!findLetterInAutomaton((int) word.get(i), automateDeterminise)) {
        word.remove(i);
        continue;
      }
      // check if the letter is the fist of occurences
      if (!isAutorizhedLetter((int) word.get(i), initialState, automateDeterminise)) {
        word.remove(i);
        continue;
      }
      // check if the letter is also the final state
      if (isFinalLetter((int) word.get(i), initialState, automateDeterminise)) {
        found = true;
      }

      initialState = indexAutorizhedLetter((int) word.get(i), initialState, automateDeterminise);
      i++;
    }
    return found;
  }

  private static boolean findLetterInAutomaton(int letter, CompleteAutomaton automataton) {
    ArrayList<Automate> automatonList = automataton.regexAutomaton;
    for (Automate automatonOccurence : automatonList) {
      if (automatonOccurence.column == letter)
        return true;
    }
    return false;
  }

  private static boolean isAutorizhedLetter(int letterToFind, int initialState, CompleteAutomaton automataton) {
    ArrayList<Automate> automatonList = automataton.regexAutomaton;
    for (Automate automatonOccurence : automatonList) {
      if (automatonOccurence.column == letterToFind && automatonOccurence.line == initialState)
        return true;
    }
    return false;
  }

  private static int indexAutorizhedLetter(int letterToFind, int initialState, CompleteAutomaton automataton) {
    ArrayList<Automate> automatonList = automataton.regexAutomaton;
    for (int i = 0; i < automatonList.size(); i++) {
      if (automatonList.get(i).column == letterToFind && automatonList.get(i).line == initialState)
        return automatonList.get(i).valeur;
    }
    return -1;
  }

  private static boolean isFinalLetter(int letterToFind, int initialState, CompleteAutomaton automataton) {
    ArrayList<Integer> finalState = automataton.finalState;
    ArrayList<Automate> automatonList = automataton.regexAutomaton;
    for (Automate automatonOccurence : automatonList) {
      if (automatonOccurence.column == letterToFind && automatonOccurence.line == initialState
          && finalState.contains(automatonOccurence.valeur))
        return true;
    }
    return false;
  }

}
