package org.misis.tp.ttf.lab3;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class LoggerTest {
    @Test
    public void getInstanceTest() {
        Logger firstInstance = Logger.getInstance();
        Logger secondInstance = Logger.getInstance();

        int firstInstanceIdent = System.identityHashCode(firstInstance);
        int secondInstanceIdent = System.identityHashCode(secondInstance);

        Assertions.assertEquals(firstInstanceIdent, secondInstanceIdent);
    }
}
