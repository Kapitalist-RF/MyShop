import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasketTest {

    private int[] baskets;
    private Basket basket;
    private String[] products = {"Хлеб", "Яблоки", "Молоко"};
    private int[] prices = {100, 200, 300};

    @BeforeEach
    void before(){
        baskets = new int[]{2, 3, 5};
        basket = new Basket(prices, products);
    }

    @Test
    void addToCart() {
        int productLength = 3;
        for(int productNum = 1; productNum <= 3; productNum++){
            boolean conditional = productNum <= productLength && productNum > 0;
            Assert.assertTrue(conditional);
        }
        basket.addToCart(1, 1);
        basket.addToCart(1, 1);
        basket.addToCart(2, 1);
        basket.addToCart(2, 1);
        basket.addToCart(2, 1);
        basket.addToCart(3, 2);
        basket.addToCart(3, 3);
        Assert.assertArrayEquals(baskets, basket.getBaskets());

    }

    @Test
    void printCart() {
        String testString = "My Basket: \n" +
                "1. Хлеб - 2 шт. 100 руб/шт 200 руб в сумме.\n" +
                "2. Яблоки - 3 шт. 200 руб/шт 600 руб в сумме.\n" +
                "3. Молоко - 5 шт. 300 руб/шт 1500 руб в сумме.\n" +
                "Итого: 2300 руб.";
        basket.addToCart(1, 1);
        basket.addToCart(1, 1);
        basket.addToCart(2, 1);
        basket.addToCart(2, 1);
        basket.addToCart(2, 1);
        basket.addToCart(3, 2);
        basket.addToCart(3, 3);

        Assert.assertEquals(testString, basket.printCartString());


    }
}