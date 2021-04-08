package ru.khomyakov;

import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.services.InitService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    private static final String CLIENTS_FILE_PATH = "";

    public static void main( String[] args ) {
        App app = new App();
        Map<String, ClientAccount> clients;
        try {
            clients = InitService.initClientAccountDataBase(new File(CLIENTS_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


}
