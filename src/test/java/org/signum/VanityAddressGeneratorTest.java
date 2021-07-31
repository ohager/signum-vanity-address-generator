package org.signum;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class VanityAddressGeneratorTest {

    @Test
    void getRandomSecret_Words() {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.words = true;

        Random testRandomizer = new Random();
        testRandomizer.setSeed(1);
        String randomSecret = generator.getRandomSecret(testRandomizer);

        assertEquals(randomSecret.split(" ").length, 12);
    }

    @Test
    void getRandomSecret_RandomWords() {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.words = true;
        try {
            Random testRandomizer = SecureRandom.getInstanceStrong();
            HashSet<String> phrases = new HashSet<>();
            for (int i = 0; i < 1000000; ++i) {
                String randomSecret = generator.getRandomSecret(testRandomizer);
                assertFalse(phrases.contains(randomSecret));
                phrases.add(randomSecret);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getRandomSecret_RandomSecret() {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.words = false;
        try {
            Random testRandomizer = SecureRandom.getInstanceStrong();
            HashSet<String> phrases = new HashSet<>();
            for (int i = 0; i < 100000; ++i) {
                String randomSecret = generator.getRandomSecret(testRandomizer);
                assertFalse(phrases.contains(randomSecret));
                phrases.add(randomSecret);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getRandomSecret_NoWords() {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.words = false;

        Random testRandomizer = new Random();
        testRandomizer.setSeed(1);
        String randomSecret = generator.getRandomSecret(testRandomizer);

        assertEquals(randomSecret.split(" ").length, 1);
        assertEquals(randomSecret.length(), 80);
    }

    @Test
    void getAddress() {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        String address = generator.getAddress("secret");
        assertEquals("S-A9WG-79NR-DEG4-AYGJY", address);
        assertNotEquals(generator.getAddress("othersecret"), address);
    }

    @Test
    void getMatchPattern() throws Exception {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.target = "ABCD";

        String[] expectedMatchers = new String[]{
                "S-ABCD-XXXX-XXXX-XXXXX",
                "S-XXXX-ABCD-XXXX-XXXXX",
                "S-XXXX-XXXX-ABCD-XXXXX",
                "S-XXXX-XXXX-XXXX-ABCDE",
        };

        for (int i = 0; i < 4; ++i) {
            generator.position = i + 1;
            Pattern matchPattern = generator.getMatchPattern();
            Matcher matcher = matchPattern.matcher(expectedMatchers[i]);
            Matcher falsyMatcher = matchPattern.matcher(expectedMatchers[(i+1)%4]);
            assertTrue(matcher.find());
            assertFalse(falsyMatcher.find());
        }
    }


    @Test
    void findSecret_Position1() throws Exception {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.target = "AB";
        generator.words = true;
        generator.position = 1;

        String[] secret = generator.findSecret();

        assertEquals(12, secret[0].split(" ").length);
        assertTrue(secret[1].startsWith("S-AB"));
    }


    @Test
    void findSecret_Position2() throws Exception {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.target = "AB";
        generator.words = false;
        generator.position = 2;
        Pattern pattern = Pattern.compile("^S-.{4}-AB");

        String[] secret = generator.findSecret();

        assertEquals(1, secret[0].split(" ").length);
        assertEquals(80, secret[0].length());
        assertTrue(pattern.matcher(secret[1]).find());
    }

    @Test
    void findSecret_Position3() throws Exception {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.target = "AB";
        generator.words = false;
        generator.position = 3;
        Pattern pattern = Pattern.compile("^S-(.{4}-){2}AB");

        String[] secret = generator.findSecret();

        assertEquals(1, secret[0].split(" ").length);
        assertEquals(80, secret[0].length());
        assertTrue(pattern.matcher(secret[1]).find());
    }

    @Test
    void findSecret_Position4() throws Exception {
        VanityAddressGenerator generator = new VanityAddressGenerator();
        generator.target = "AB";
        generator.words = true;
        generator.position = 4;
        Pattern pattern = Pattern.compile("^S-(.{4}-){3}AB");

        String[] secret = generator.findSecret();

        assertEquals(12, secret[0].split(" ").length);
        assertTrue(pattern.matcher(secret[1]).find());
    }

    @Test
    void validate() {
        // TODO
    }
}