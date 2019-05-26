package roboticHand.Tools;

import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptorTest {

    @Test
    public void hashPassword() {
        assertEquals("eb34a3f0a617c91b009720af030f5995", Encryptor.hashPassword(""));
    }
}