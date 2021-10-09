import java.util.ArrayList;

public class BookSearch {

  public static String searchRegexInBook(ArrayList<String> livre, CompleteAutomaton automateDeterminise, String regex) {
    String res = "";
    ArrayList<String> matchLines = new ArrayList<String>();
    int cpt = 0;
    for (int i = 0; i < livre.size(); i++) {
      // Tableau des mots d'une ligne
      String[] word = livre.get(i).split(" ");
      // taille d'une ligne
      for (int j = 0; j < word.length; j++) {
        if (CompleteAutomaton.regexValidator(word[j], automateDeterminise, regex)) {
          matchLines.add(livre.get(i));
          break;
        }
      }
    }
    for (int k = 0; k < matchLines.size(); k++) {
      String[] word = matchLines.get(k).split(" ");
      for (int w = 0; w < word.length; w++) {
        if (CompleteAutomaton.regexValidator(word[w], automateDeterminise, regex)) {
          res += Color.BLUE + word[w] + Color.RESET + " ";
          cpt += 1;
        } else {
          res += word[w] + " ";
        }
        if (w == word.length - 1) {
          res += "\n";
        }
      }
    }
    System.out.println("Match result: " + cpt);
    return res;
  }
}
