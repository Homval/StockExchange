package ru.khomyakov;


import org.junit.Before;
import org.junit.Test;
import ru.khomyakov.exceptions.WrongClientAccountException;
import ru.khomyakov.services.InOutService;


public class InOutServiceTest {
    private String[] param;

    @Before
    public void init() {
        param = new String[] {"", "10", "10", "10", "10", "10"};
    }

    @Test(expected = WrongClientAccountException.class)
    public void check_incorrect_parameters_with_empty_string() throws WrongClientAccountException {
       InOutService.areParametersCorrect(param);
    }

    @Test(expected = WrongClientAccountException.class)
    public void check_incorrect_parameters_with_null_string() throws WrongClientAccountException {
        param[0] = null;
        InOutService.areParametersCorrect(param);
    }

    @Test(expected = WrongClientAccountException.class)
    public void check_incorrect_array_length() throws WrongClientAccountException {
        param = new String[] {"A", "10", "10", "10", "10"};
        InOutService.areParametersCorrect(param);
    }

    @Test()
    public void check_correct_parameters() throws WrongClientAccountException {
        param[0] = "A";
        InOutService.areParametersCorrect(param);
    }

}
