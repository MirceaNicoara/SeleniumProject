package org.example;

import java.util.Random;

public class RandomStringGenerator {

    public String getRandomString() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int stringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

}
