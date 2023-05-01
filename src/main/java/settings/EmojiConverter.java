package settings;

public class EmojiConverter {
    private EmojiConverter() {
    }

    public static String convertToRegionalIndicatorEmojis(String input) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                char lowerCaseChar = Character.toLowerCase(c);
                output.append(":regional_indicator_").append(lowerCaseChar).append(": ");
            } else {
                output.append(":red_square: ");
            }
        }
        return output.toString();
    }
}
