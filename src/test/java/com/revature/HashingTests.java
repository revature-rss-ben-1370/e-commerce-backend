package com.revature;

import com.revature.controllers.AuthController;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

@SpringBootTest
public class HashingTests{

    @Autowired
    AuthController authController;

    /**
     * Salt Maker is implemented in models.User.getSalt() method
     * takes roughly 6.7 seconds for Jalil's computer for 1 million tests
     */
    @Test
    public void HashTest(){
        for (int j = 0; j < 1000; j++) {
            byte[] salt = SaltMaker();
            String parsed = new String(salt, StandardCharsets.ISO_8859_1);
            byte[] unSalt = parsed.getBytes(StandardCharsets.ISO_8859_1);
            Assertions.assertTrue(Arrays.equals(salt, unSalt));
        }
    }

    // takes 1 minute 20 secs on Jalil's computer for 1k tests
    @Test
    public void UserHashTest(){
        User testUser = new User(1, "testuser@gmail.com", "password", "test", "user", "NotSoRandomSalt?");
        for (int j = 0; j < 50; j++) {
            testUser.setPassword("password");
            testUser.setSalt(null);
            testUser.encryptAndSetPassword();
            Assertions.assertNotEquals("password", testUser.getPassword());
        }
    }


    @Test
    void TestPassword(){
        User testUser = new User(1, "testuser@gmail.com", "password", "test", "user", "NotSoRandomSalt?");
        testUser.encryptAndSetPassword();
        System.out.println(testUser.getPassword());
    }

    @Test
    public void RegisterTest(){
        RegisterRequest request = new RegisterRequest();
        request.setEmail("newuser@gmail.com");
        request.setPassword("password");
        ResponseEntity<User> test = authController.register(request);
        Assertions.assertTrue(test.getStatusCodeValue() >= 200 && test.getStatusCodeValue() < 300);
    }

    @Test
    public void LoginTest(){
        LoginRequest request = new LoginRequest();
        request.setEmail("testuser@gmail.com");
        request.setPassword("password");
        ResponseEntity<User> test = authController.login(request, new MockHttpServletResponse());
        //Assertions.assertTrue(test.getStatusCodeValue() >= 200 && test.getStatusCodeValue() < 300);
    }

    @Test
    public void failedLoginTest(){
        LoginRequest request = new LoginRequest();
        request.setEmail("testuser@gmail.com");
        request.setPassword("badPassword");
        ResponseEntity<User> test = authController.login(request, new MockHttpServletResponse());
        Assertions.assertFalse(test.getStatusCodeValue() >= 200 && test.getStatusCodeValue() < 300);
    }

    //Salt Maker is implemented in models.User.getSalt() method
    private byte[] SaltMaker(){
        byte[] randBytes = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(randBytes);
        return randBytes;
    }

    //use this to replace "REPLACETHIS" in sql file for salts
    //@Test
    public void replaceSQLFile(){
        String testPass = "password";
        byte[] salt = "NotSoRandomSalt?".getBytes(StandardCharsets.ISO_8859_1);
        KeySpec spec = new PBEKeySpec(testPass.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            replaceSQLPassword(new String(hash, StandardCharsets.ISO_8859_1));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //Mostly copied off internet
    public static void replaceSQLPassword(String replaceWith){
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader("src/main/resources/data.sql"));
            StringBuilder inputBuffer = new StringBuilder();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();

            System.out.println(inputStr); // display the original file for debugging

            // logic to replace lines in the string (could use regex here to be generic)
            inputStr = inputStr.replace("REPLACETHIS", replaceWith);

            // display the new file for debugging
            System.out.println("----------------------------------\n" + inputStr);

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/data.sql");
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }
}
