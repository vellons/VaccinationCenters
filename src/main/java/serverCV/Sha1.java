package serverCV;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * La classe Sha1 permette di ottenere una firma univoca della stringa passata in ingresso.
 * Ha in input una stringa di lunghezza arbitraria e in output la firma della string di 40 caratteri.
 *
 * @author Alex Vellone
 * @see <a href="http://www.sha1-online.com/sha1-java/">sha1-java</a>
 */
public class Sha1 {

    /**
     * Hashing with SHA1
     *
     * @param input String to hash
     * @return String hashed
     */
    public static String sha1(String input) {
        input = "CV-C1A0-" + input + "-CentroVacinale"; // Avoid rainbow tables attacks
        StringBuilder sha1 = new StringBuilder();
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            for (byte b : result) {
                sha1.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error digest SHA1");
        }
        return sha1.toString();
    }
}
