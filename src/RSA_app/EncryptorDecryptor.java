package RSA_app;

import java.math.BigInteger;
import java.util.Map;

/**
 * Handles asymmetric encryption/decryption with RSA-scheme, coverts String of symbols to array of integers (via symmetric encryption/decryption) and other way around,
 * converts arrays of integers to arrays of BigIntegers (and other way around), coverts arrays of BigIntegers to Strings where each element of array ends with # and leaves the value of the element intact,
 * and bundles these methods together in different combinations in four different methods (depending on whether the user wants to perform encryption or decryption with a public or private key pair).
 */
public class EncryptorDecryptor {
    private final Map<Character, Integer> charToIntMap;
    private final Map<Integer, Character> intToCharMap;

    /**
     * Generates two maps, which maps characters and integers to each other.
     */
    public EncryptorDecryptor() {
        MapsCharInt mapsCharInt = new MapsCharInt();
        charToIntMap = mapsCharInt.getCharToIntMap();
        intToCharMap = mapsCharInt.getIntToCharMap();
    }

    /**
     * Converts a String to an array of integers, using map from class MapsCharInt.
     * @param message String to convert.
     * @return Array of integers correlating to symbols making up input-String.
     */
    private int [] stringToInt(String message) {
        int [] messageConverted = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            messageConverted[i] = charToIntMap.get(c);
        }
        return messageConverted;
    }

    /**
     * Converts an array of integers to a String, using map from class MapsCharInt.
     * @param message Array of integers to convert.
     * @return String of symbols correlating to integer elements in input-array.
     */
    private String intToString(int [] message) {
        String messageOut = "";
        for (int i = 0; i < message.length; i++) {
            int j = message[i];
            if ((j - 2) > (intToCharMap.size())) {  // -2 since first key in map is "2". This if-statement and code-block isn't necessary for the code to function, just to avoid output from decryption being null when incorrect ciphertext is entered.
                j = ((j - 2) % intToCharMap.size()) + 2;
            }
            messageOut += intToCharMap.get(j);
        }

        return messageOut;
    }

    /**
     * Encrypts/decrypts a BigInteger.
     * @param message Message/data to encrypt/decrypt.
     * @param exponent Public/private exponent key.
     * @param modulus Modulus key
     * @return The encrypted/decrypted message/data.
     */
    public BigInteger encryptDecrypt(BigInteger message, BigInteger exponent, BigInteger modulus) {
        BigInteger output = message;
        for (BigInteger exp = BigInteger.valueOf(2); exp.compareTo(exponent) <= 0; exp = exp.add(BigInteger.ONE)) {  // BigInteger equivalent of: for (int exp = 2; exp <= exponent; exp++)
            output = (output.multiply(message)).mod(modulus);
        }
        return output;
    }

    /**
     * Encrypts/decrypts a BigInteger.
     * @param message Message/data to encrypt/decrypt.
     * @param exponent Public/private exponent key.
     * @param modulus Modulus key
     * @return The encrypted/decrypted message/data.
     */
    public BigInteger [] encryptDecryptArray(BigInteger [] message, BigInteger exponent, BigInteger modulus) {
        BigInteger [] outputArray = new BigInteger [message.length];
        for (int index = 0; index < message.length; index++){
            BigInteger outputElement = message[index];
            for (BigInteger exp = BigInteger.valueOf(2); exp.compareTo(exponent) <= 0; exp = exp.add(BigInteger.ONE)) {  // BigInteger equivalent of: for (int exp = 2; exp <= exponent; exp++)
                outputElement = (outputElement.multiply(message[index])).mod(modulus);
            }
            outputArray[index] = outputElement;
        }
        return outputArray;
    }

    /**
     * Converts array of integers to array of BigIntegers.
     * @param inputArray Array of integers.
     * @return Array of BigIntegers.
     */
    private BigInteger [] convertIntArrayToBigIntArray(int [] inputArray) {
        BigInteger [] outputArray = new BigInteger[inputArray.length];
        int index = 0;
        for (int i: inputArray){
            outputArray[index] = BigInteger.valueOf(i);
            index++;
        }
        return outputArray;
    }

    /**
     * Converts array of BigIntegers to array of integers.
     * @param inputArray Array of BigIntegers.
     * @return Array of integers.
     */
    private int [] convertBigIntArrayToIntArray(BigInteger [] inputArray) {
        int [] outputArray = new int[inputArray.length];
        int index = 0;
        for (BigInteger i: inputArray){
            outputArray[index] = i.intValue();
            index++;
        }
        return outputArray;
    }

    /**
     * Turns the input-Array into a String where each element ends by an #.
     * @param inputArray An array of BigIntegers.
     * @return A string where each element of inputArray ends with an #.
     */
    private String convertBigIntArrayToString(BigInteger [] inputArray) {
        String output = "";
        for (BigInteger i: inputArray) {
            output += i.toString();
            output += "#";
        }
        return output;
    }

    /**
     * Turns the input String to an array of BigIntegers where each portion of the String ending with # is placed in an array element.
     * @param inputString String comprised of portions of integers ending in #.
     * @return Array of BigIntegers, where each element is equal to the portions in the input-String ending with #.
     */
    private BigInteger [] convertStringToBigIntArray(String inputString) {
        String[] inputStringArray = inputString.split("#");
        int countElements = inputStringArray.length;
        BigInteger[] outputArray = new BigInteger[countElements];
        int index = 0;
        for (String element: inputStringArray) {
            outputArray[index] = BigInteger.valueOf(Integer.parseInt(element));
            index++;
        }
        return outputArray;
    }

    /**
     * Calls several methods in a series in order to encrypt a message using supplied public key pair.
     * @param message Message String to encrypt.
     * @param e Public key exponent.
     * @param n Modulus of public and private key.
     * @return Encrypted message.
     */
    public String encryptMessageWithPublicKeys(String message, BigInteger e, BigInteger n) {
        int [] messageInt = stringToInt(message);
        BigInteger [] messageBigInt = convertIntArrayToBigIntArray(messageInt);
        BigInteger[] messageBigIntEncrypted = encryptDecryptArray(messageBigInt, e, n);
        return convertBigIntArrayToString(messageBigIntEncrypted);
    }

    /**
     * Calls several methods in a series in order to encrypt a message using supplied private key pair.
     * @param message Message String to encrypt.
     * @param d Private key exponent.
     * @param n Modulus of public and private key.
     * @return Encrypted message.
     */
    public String encryptMessageWithPrivateKeys(String message, BigInteger d, BigInteger n) {
        int [] messageInt = stringToInt(message);
        BigInteger [] messageBigInt = convertIntArrayToBigIntArray(messageInt);
        BigInteger[] messageBigIntEncrypted = encryptDecryptArray(messageBigInt, d, n);
        return convertBigIntArrayToString(messageBigIntEncrypted);
    }

    /**
     * Calls several methods in a series in order to encrypt a message using supplied public key pair.
     * @param encryptedMessage Encrypted message String to decrypt.
     * @param e Public key exponent.
     * @param n Modulus of public and private key.
     * @return Decrypted message.
     */
    public String decryptMessageWithPublicKeys(String encryptedMessage, BigInteger e, BigInteger n) {
        BigInteger [] encryptedMessageBigIntArray = convertStringToBigIntArray(encryptedMessage);
        BigInteger [] decryptedMessageBigIntArray = encryptDecryptArray(encryptedMessageBigIntArray, e, n);
        int [] decryptedMessageIntArray = convertBigIntArrayToIntArray(decryptedMessageBigIntArray);
        return intToString(decryptedMessageIntArray);
    }

    /**
     * Calls several methods in a series in order to encrypt a message using supplied public key pair.
     * @param encryptedMessage Encrypted message String to decrypt.
     * @param d Private key exponent.
     * @param n Modulus of public and private key.
     * @return Decrypted message.
     */
    public String decryptMessageWithPrivateKeys(String encryptedMessage, BigInteger d, BigInteger n) {
        BigInteger [] encryptedMessageBigIntArray = convertStringToBigIntArray(encryptedMessage);
        BigInteger [] decryptedMessageBigIntArray = encryptDecryptArray(encryptedMessageBigIntArray, d, n);
        int [] decryptedMessageIntArray = convertBigIntArrayToIntArray(decryptedMessageBigIntArray);
        return intToString(decryptedMessageIntArray);
    }
}
