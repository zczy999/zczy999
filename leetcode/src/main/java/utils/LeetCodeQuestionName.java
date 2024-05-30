package utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LeetCodeQuestionName {

    public static void main(String[] args) {
        String input = "2290. Minimum Obstacle Removal to Reach Corner";
        String formatted = formatString(input);
        System.out.println(formatted); // Outputs: Most_Stones_Removed
    }

    public static String formatString(String input) {
        // Initialize variables to store the numeric prefix and the rest of the text
        String number = "";
        String text = input;

        // Check if the input starts with a numeric sequence followed by a period
        int index = input.indexOf(".");
        if (index != -1 && input.length() > index + 1 && Character.isDigit(input.charAt(0))) {
            // Attempt to parse the numeric prefix
            try {
                number = input.substring(0, index).trim();
                Integer.parseInt(number);  // Validate it's a number
                text = input.substring(index + 1).trim(); // Take the rest after the period
            } catch (NumberFormatException e) {
                // If parsing fails, assume no valid number was found
                number = "";
                text = input;
            }
        }

        // Replace all hyphens with spaces to handle both delimiters uniformly
        text = text.replace('-', ' ');

        String formattedText = Arrays.stream(text.split("\\s+")) // Split the text on whitespace
//                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining("_")); // Join words with an underscore

        // Append the captured number at the end if it exists
        if (!number.isEmpty()) {
            formattedText += "_" + number;
        }

        return formattedText;
    }




}
