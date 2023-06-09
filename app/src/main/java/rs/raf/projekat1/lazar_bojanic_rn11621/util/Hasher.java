package rs.raf.projekat1.lazar_bojanic_rn11621.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Hasher {
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
    public static boolean checkPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
