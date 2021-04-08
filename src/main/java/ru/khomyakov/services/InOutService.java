package ru.khomyakov.services;

import ru.khomyakov.domain.ClientAccount;

import java.io.*;
import java.util.*;

public class InOutService {

    public static Map<String, ClientAccount> initClientAccountDataBase(File file) throws FileNotFoundException {
        final Map<String, ClientAccount> clientAccountDataBase = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.lines()
                .forEach(line -> {
                    String[] client = line.split("\t");
                    clientAccountDataBase.put(client[0], new ClientAccount(client[0],
                            Integer.parseInt(client[1]),
                            Integer.parseInt(client[2]),
                            Integer.parseInt(client[3]),
                            Integer.parseInt(client[4]),
                            Integer.parseInt(client[5])));
                });
        return clientAccountDataBase;
    }

    public static void saveClientsToFile(Map<String, ClientAccount> clients, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            List<String> accountList = new ArrayList<>(clients.keySet());
            accountList.sort(Comparator.naturalOrder());

            for (String client : accountList) {
                writer.write(clients.get(client).toString());
                writer.append('\n');
                writer.flush();
            }

        }
    }
}
