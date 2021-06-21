package RSA_app;

import java.lang.Integer;
import java.lang.Character;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates and stores two maps: one which maps characters to integers, and one which maps integers to characters.
 */
public class MapsCharInt {
    private final Map<Character, Integer> charToIntMap;
    private final Map<Integer, Character> intToCharMap;

    /**
     * Generates two maps.
     */
    public MapsCharInt() {
        charToIntMap = generateCharToIntMap();
        intToCharMap = generateIntToCharMap();
    }

    /**
     * Generates a map that maps characters to integers.
     * @return A map that maps characters to integers.
     */
    private Map<Character, Integer> generateCharToIntMap() {
        Map<Character, Integer> charToIntMap = new HashMap<>();
        String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789 .,_-!?'<>:;@$&%#|()[]{}=+-*/\t\n";
        int value = 2; // Don't convert character to 0 or 1.
        for (int i = 0; i < symbols.length(); i++) {
            char c = symbols.charAt(i); // Access each character of String "symbols"
            charToIntMap.put(c, value);
            value++;
        }
        return charToIntMap;
    }

    /**
     * Generates a map that maps integers to characters.
     * @return A map that maps integers to characters.
     */
    private Map<Integer, Character> generateIntToCharMap() {
        Map<Integer, Character> intToCharMap = new HashMap<>();
        String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789 .,_-!?'<>:;@$&%#|()[]{}=+-*/\t\n";
        int value = 2; // Don't convert character to 0 or 1.
        for (int i = 0; i < symbols.length(); i++) {
            char c = symbols.charAt(i);  // Access each character of String "symbols"
            intToCharMap.put(value, c);
            value++;
        }
        return intToCharMap;
    }

    public Map<Integer, Character> getIntToCharMap() {
        return this.intToCharMap;
    }

    public Map<Character, Integer> getCharToIntMap() {
        return this.charToIntMap;
    }
}
