package finder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Finder {
    private final String CNP_REGEX =
            "\\b[1-8]" + //gender
                    "\\d{2}" + // year
                    "(02(0[1-9]|[12]\\d)" + // february
                    "|(0[13578]|1[02])(0[1-9]|[12]\\d|3[01])" + //months having 31 days
                    "|(0[469]|11(0[1-9]|[12]\\d|30)))" + //months having 30 days
                    "(0\\d|[1-3]\\d|4[0-6]|5[12])" + //county
                    "\\d{2}[1-9]" + //secvential number
                    "\\d\\b"; //control digit
    private final String CONTROL_CONSTANT = "279146358279";


    private Boolean controlDigitValidation(String cnp) {
        final int C = cnp.charAt(cnp.length() - 1) - '0'; //control digit
        var sum = 0;
        for (var i = 0; i < CONTROL_CONSTANT.length(); i++) {
            sum += (cnp.charAt(i) - '0') * (CONTROL_CONSTANT.charAt(i) - '0');
        }

        int res = sum % 11;
        if (res == 10) res = 1;

        return res == C;
    }


    public List<String> analyseFile(File file) {
        List<String> results=new ArrayList<>();
        if (file != null) {
            try {
                var myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    var pattern = Pattern.compile(CNP_REGEX);
                    var matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        var matched=matcher.group();
                        if(controlDigitValidation(matched)) results.add(matched);
                    }
                }
                myReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
        }
        if(results.isEmpty()) return null;
        return results;
    }
}
