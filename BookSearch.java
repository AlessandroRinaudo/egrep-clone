import java.util.ArrayList;

public class BookSearch {

  public static String searchRegexInBook(ArrayList<String> livre, CompleteAutomaton automateDeterminise) {
    String res = "";
    for (int i = 0; i < livre.size(); i++) {
      String[] word = livre.get(0).split(" ");
      for (int j = 0; j < word.length; j++) {
        if (CompleteAutomaton.regexValidator(word[j], automateDeterminise)) {
          res += livre.get(i) + "\n";
          break;
        }
      }
    }
    return res;
  }

}
