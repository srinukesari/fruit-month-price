package distributed.systems.fruit_month_price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FruitMonthPriceController {

    @Autowired
    private FruitMonthPriceRepository repository;

    @Autowired
    private Environment environment;
    
    @GetMapping("/fruit-month-price/fruit/{fruit}/month/{month}")
    public FruitMonthPrice retrievePrice(
        @PathVariable String fruit,
        @PathVariable String month
    ){
        FruitMonthPrice fruitMonthPrice = repository.findByFruitAndMonth(fruit, month);

        if(fruitMonthPrice == null){
            throw new RuntimeException("Unable find record for fruit -> " + fruit + 
            " and month -> "+ month);
        }
        String port = environment.getProperty("local.server.port");
        fruitMonthPrice.setEnvironment(port);


        return fruitMonthPrice;
    }

}
