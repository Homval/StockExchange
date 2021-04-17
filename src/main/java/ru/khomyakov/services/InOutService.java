package ru.khomyakov.services;

import ru.khomyakov.App;
import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.exceptions.WrongClientAccountException;

import java.io.*;
import java.util.*;

public class InOutService {
    public static void initClientAccountDataBase(File file) throws IOException {

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines()
                    .forEach(line -> {
                        String[] client = line.split("\t");
//                    check the entered data for ClientAccount
                        try {
                            areParametersCorrect(client);
                        } catch (WrongClientAccountException e) {
                            e.printStackTrace();
                        }

                        App.clients.put(client[0], new ClientAccount(client[0],
                                Integer.parseInt(client[1]),
                                Integer.parseInt(client[2]),
                                Integer.parseInt(client[3]),
                                Integer.parseInt(client[4]),
                                Integer.parseInt(client[5])));
                    });
        }
    }

    public static void saveClientsToFile(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            List<String> accountList = new ArrayList<>(App.clients.keySet());
            accountList.sort(Comparator.naturalOrder());

            for (String client : accountList) {
                writer.write(App.clients.get(client).toString());
                writer.append('\n');
                writer.flush();
            }
        }
    }

    public static void areParametersCorrect(String[] param) throws WrongClientAccountException{
        if (param.length < 6 || param[0] == null || param[0].equals("")) {
            throw new WrongClientAccountException();
        }
    }
}
