package task.shopify.www.shopifytask.util;

/**
 * A class which contains all the utility methods
 *
 * @author Devesh Shetty
 */
public class Util {

    /**
     *A utility method to get the Emoji
     * @param unicode the unicode of the emoji
     * @return The String representation of the emoji
     */
    public static String getEmojiByUnicode(int unicode){
        char[] ch = Character.toChars(unicode);
        return new String(ch);
    }

}
