package ru.khomyakov.services;

import ru.khomyakov.domain.StockNames;
import ru.khomyakov.domain.StockRequest;
import ru.khomyakov.exceptions.WrongStockRequestException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class OrderRequestsService {

    public static void executeOrdersRequests(String ordersFilePath) throws IOException {
//        Create lists for buyers and sellers requests
        List<StockRequest> sellersRequests = new LinkedList<>();
        List<StockRequest> buyersRequests = new LinkedList<>();


        try(BufferedReader reader = new BufferedReader(new FileReader(ordersFilePath))) {
            String request;
            while ((request = reader.readLine()) != null) {
                String[] requestParts = request.split("\t");
//            check the entered data for StockRequest
                try {
                    areRequestParametersCorrect(requestParts);
                } catch (WrongStockRequestException e) {
                    e.printStackTrace();
                }
                StockRequest stockRequest = new StockRequest(requestParts[0],
                        requestParts[1],
                        StockNames.valueOf(requestParts[2]),
                        Integer.parseInt(requestParts[3]),
                        Integer.parseInt(requestParts[4]));

                switch (requestParts[1]) {
                    case ("b"):
                        addToListOrExecuteRequest(buyersRequests, sellersRequests, stockRequest);
                        break;
                    case ("s"):
                        addToListOrExecuteRequest(sellersRequests, buyersRequests, stockRequest);
                        break;
                }
            }
        }
    }

//        If request not exist yet add it in appropriate list. In other case make transaction
    public static void addToListOrExecuteRequest(List<StockRequest> sameTypeRequests, List<StockRequest> otherTypeRequests, StockRequest stockRequest) {
        if (isRequestAvailableForExecute(stockRequest, otherTypeRequests)) {
            StockRequest earlierRequest = otherTypeRequests.stream()
                                                .filter(req -> req.equals(stockRequest) && !req.getClientName().equals(stockRequest.getClientName()))
                                                .findFirst().orElseThrow(null);
            if (stockRequest.getStockAction().equals("s")) {
                TransactionService.executeTransaction(stockRequest, earlierRequest);
            } else {
                TransactionService.executeTransaction(earlierRequest, stockRequest);
            }
            remove(otherTypeRequests, earlierRequest);

        } else {
            sameTypeRequests.add(stockRequest);
        }
    }

//         Check request on existing equal purpose.
    public static boolean isRequestAvailableForExecute(StockRequest request, List<StockRequest> requests) {
        for (StockRequest request1 : requests) {
            if (isOppositeRequests(request1, request))
                return true;
        }
        return false;
    }

//    Check the entered parameters of request
    public static void areRequestParametersCorrect(String[] param) throws WrongStockRequestException{
        if (param.length < 5
                || param[0] == null
                || param[0].equals("")
                || (!param[1].equals("s") && !param[1].equals("b"))
                || !List.of(StockNames.values()).contains(StockNames.valueOf(param[2]))) {
            throw new WrongStockRequestException();
        }
    }

    public static void remove(List<StockRequest> requests, StockRequest request) {
        Iterator<StockRequest> iterator = requests.iterator();
        while (iterator.hasNext()) {
            StockRequest request1 = iterator.next();
            if (isSameRequests(request1, request)) {
                iterator.remove();
                return;
            }
        }
    }

    public static boolean isOppositeRequests(StockRequest request1, StockRequest request2) {
        return  (request1.equals(request2) && !request1.getClientName().equals(request2.getClientName()));
    }

    public static boolean isSameRequests(StockRequest request1, StockRequest request2) {
        return request1.equals(request2) && request1.getClientName().equals(request2.getClientName());
    }
}
