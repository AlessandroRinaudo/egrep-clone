import java.util.ArrayList;

public class RegexTesting {
  
  public static boolean singleChar (String s,ArrayList<Automate> automateDeterminise) {

      for(int i = 0; i<automateDeterminise.size(); i++) {
        if(s.indexOf(automateDeterminise.get(i).column)!=-1) {
          return true;
        }
        return false;
      }
     


    return false;

  }

  
  


}
