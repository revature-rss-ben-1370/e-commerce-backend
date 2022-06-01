package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.HexFormat;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
/**
 * Represents User Entity in SQL database
 * @param salt Randomly generated string to modify password-hash if salt is not previously defined
 **/
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String salt;

    /**
     * if salt is null or empty, a random salt will be generated. Otherwise, return previously defined salt
     *
     * @return string representation of securely randomized byte array
     */
    public String getSalt(){
        if (salt == null || salt.isEmpty()) {

            byte[] randBytes = new byte[16];
            boolean saltValid;
            SecureRandom random = new SecureRandom();
            //salt maker in loop on the off chance it fails the first time
            do {
                saltValid = true;
                random.nextBytes(randBytes);
                salt = HexFormat.of().formatHex(randBytes);
                byte[] unSalt = HexFormat.of().parseHex(salt);
                if (!Arrays.equals(randBytes, unSalt)) {
                    saltValid = false;
                }
            } while (!saltValid);//works first time in tests (tested 500 mil+ times)
        }
        return salt;
    }

    /**
     * returns byte array in hex format as it preserves byte values
     *
     * @return Byte Array of salt
     */
    public byte[] getSaltBytes(){
        return HexFormat.of().parseHex(getSalt());
    }

    /**
     * Save byte array as string while preserving data
     *
     * @param byteArray Should be the byte array generated by SecretKeyFactory after encoding
     */
    public void setPassword(byte[] byteArray){
        this.password = HexFormat.of().formatHex(byteArray);
    }

    /**
     * Should be a Hashed string that was saved with in Hex format
     *
     * @param encryptedPassword Hex encrypted string
     */
    public void setPassword(String encryptedPassword){
        this.password = encryptedPassword;
    }

    /**
     * Encrypt an existing password that should be plain text and replace it with a secure encrypted value
     */
    public void encryptAndSetPassword(String plainTextPassword) throws RuntimeException{
        this.password = encryptPassword(plainTextPassword, this.getSaltBytes());
    }

    /**
     * Statically Available method to encrypt the password without setting it for testing/comparison
     *
     * @param password plaintext password input
     * @param salt     byte array representation of a salt
     * @return Password Derived String to be saved or compared with for validity
     */
    public static String encryptPassword(String password, byte[] salt) throws RuntimeException{
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = f.generateSecret(spec).getEncoded();
            return HexFormat.of().formatHex(hash);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
