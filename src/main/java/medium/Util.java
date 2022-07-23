package medium;

public class Util {
    /**
     * Checking incoming string
     * @param str string for checking
     * @return str if the string that was passed correct, return this string
     * @throws IllegalArgumentException if the string is incorrect
     */
    public static String checkString(String str) throws IllegalArgumentException {
        if (str.isEmpty() || str.isBlank()) {
            throw new IllegalArgumentException("You entered incorrect data");
        }
        return str;
    }

    /**
     * Checking incoming number
     * @param number number for checking
     * @return number if the number that was passed correct, return this number
     * @throws IllegalArgumentException if the number is incorrect
     */
    public static double checkNumber(double number) throws IllegalArgumentException {
        if (number < 0) {
            throw new IllegalArgumentException("You entered incorrect data");
        }
        return number;
    }
}
