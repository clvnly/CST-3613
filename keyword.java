import java.util.*;
import java.util.regex.MatchResult;
import java.io.*;

public class keywordtest {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a Java source file: ");
        String filename = input.nextLine();

        File file = new File(filename);
        if (file.exists()) {
            countKeywords(file);
        }
        else {
            System.out.println("File " + filename + " does not exist");
        }
    }

    public static void countKeywords(File file) throws Exception {
        // Array of all Java keywords + true, false and null
        String[] keywordString = {"abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null"};

        ArrayList<String> keywordArrayList = new ArrayList<>();
        for(int i = 0; i < keywordString.length; i++)
            keywordArrayList.add(keywordString[i]);

        Set<String> keywordSet = new HashSet<String>(Arrays.asList(keywordString));
        int count = 0;

        Scanner input = new Scanner(file);
        Map<String, Integer> map = new TreeMap<String, Integer>();
        while (input.hasNext())
        {
            String key = input.next();
            if (keywordArrayList.contains(key))
            {
                if (map.get(key) == null)
                    map.put(key, 1);
                else
                    map.put(key, map.get(key)+1);
            }
        }
        for(Map.Entry<String,Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            System.out.println(key + " => " + value);
        }
    }
}