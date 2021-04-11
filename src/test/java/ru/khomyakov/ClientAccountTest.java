package ru.khomyakov;

import org.junit.Test;
import ru.khomyakov.domain.ClientAccount;
import static org.junit.Assert.*;

public class ClientAccountTest {

    @Test
    public void isTheSameClientsEqual(){
        ClientAccount account1 = new ClientAccount("A", 10, 10, 10, 10, 10);
        ClientAccount account2 = new ClientAccount("A", 100, 100, 100, 100, 100);

        assertEquals(account1, account2);
    }

    @Test
    public void isClientsNotEqual(){
        ClientAccount account1 = new ClientAccount("A", 10, 10, 10, 10, 10);
        ClientAccount account2 = new ClientAccount("B", 10, 10, 10, 10, 10);

        assertNotEquals(account1, account2);
    }
}
