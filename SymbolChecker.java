import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class SymbolChecker {

    private String fileName;

    public SymbolChecker(String fileName) {
        this.fileName = fileName;
    }

    public boolean checkSymbols() {
        Stack<Character> stack = new Stack<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                char c = (char) ch;

                if (c == '(' || c == '{' || c == '[') {
                    stack.push(c);
                } else if (c == ')' || c == '}' || c == ']') {
                    if (stack.isEmpty()) {
                        return false; // closing without opening
                    }
                    char open = stack.pop();
                    if (!isMatchingPair(open, c)) {
                        return false; // mismatch
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }

        return stack.isEmpty(); // valid if no unmatched opens remain
    }

    private boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java SymbolChecker <source-file>");
            return;
        }

        SymbolChecker checker = new SymbolChecker(args[0]);
        boolean valid = checker.checkSymbols();

        if (valid) {
            System.out.println("Grouping symbols are correctly matched.");
        } else {
            System.out.println("Grouping symbols are NOT correctly matched.");
        }
    }
}
