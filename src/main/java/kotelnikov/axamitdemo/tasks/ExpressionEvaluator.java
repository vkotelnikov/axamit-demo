package kotelnikov.axamitdemo.tasks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEvaluator {

    private static final String PATTERN = "^(\\d+)(?:\\s*)([+\\-*/]+)(?:\\s*)(\\w+)$";

    public static void main(String[] args) {

        evaluateFromFile("expressions.txt");

    }

    /**
     *
     * @param filename name of expressions file
     * @return Path to results file
     */
    public static String evaluateFromFile(String filename) {
        String inputFile = getFileFromResources( filename );
        String resultFile = "src/main/resources/results.txt";
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             Scanner scanner = new Scanner(inputStream);
             FileOutputStream outputStream = new FileOutputStream(resultFile);
        ) {

            while (scanner.hasNextLine()) {
                outputStream.write(evaluate(scanner.nextLine()).getBytes());
                outputStream.write("\n".getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultFile;
    }

    public static String evaluate(String expression) {
        Pattern regex = Pattern.compile(PATTERN);
        Matcher matcher = regex.matcher(expression);

        if (!matcher.find()) {
            return "No expressions found";
        }

        try {
            String operator = matcher.group(2);
            boolean operatorIsValid = checkForValidOperator(operator);
            if (operatorIsValid) {
                long firstNumber = Long.parseLong(matcher.group(1));
                long secondNumber = Long.parseLong(matcher.group(3));
                return compute(operator, firstNumber, secondNumber);
            } else {
                return "Operator \"" + operator + "\" is invalid";
            }
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    private static boolean checkForValidOperator(String operator) {
        if (operator.length() > 1) {
            return false;
        }
        return true;
    }

    private static String compute(String operator, long num1, long num2) {
        long result;
        if ("+".equals(operator)) {
            result = num1 + num2;
        } else if ("-".equals(operator)) {
            result = num1 - num2;
        } else if ("*".equals(operator)) {
            result = num1 * num2;
        } else if ("/".equals(operator)) {
            result = num1 / num2;
        } else {
            return "Unknown operator";
        }

        return result + "";
    }

    private static String getFileFromResources(String name) {
        return ExpressionEvaluator.class.getClassLoader().getResource(name).getPath();
    }
}
