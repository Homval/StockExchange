package ru.khomyakov.services;

import ru.khomyakov.domain.ClientAccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class InitService {

    public static Map<String, ClientAccount> initClientAccountDataBase(File file) throws FileNotFoundException {
        final Map<String, ClientAccount> clientAccountDataBase = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.lines()
                .forEach(line -> {
                    String[] client = line.split("\t");
                    clientAccountDataBase.put(client[0], new ClientAccount(client[0],
                            Integer.valueOf(client[1]),
                            Integer.valueOf(client[2]),
                            Integer.valueOf(client[3]),
                            Integer.valueOf(client[4]),
                            Integer.valueOf(client[5])));
                });
        return clientAccountDataBase;
    }

}
