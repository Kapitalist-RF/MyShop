import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        File file = new File("basket.txt");
        Basket basket = Basket.loadFromBinFile(file);
        if (basket == null) {
            String[] products = {"Хлеб", "Яблоки", "Молоко"};
            int[] prices = {100, 200, 300};
            basket = new Basket(prices, products);
        } else {
            basket.printCart();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                shoppingList(basket.getProducts(), basket.getPrices());
                String[] myPursh = reader.readLine().split(" ");
                if(myPursh[0].equalsIgnoreCase("end")){
                    break;
                }
                basket.addToCart(Integer.parseInt(myPursh[0]), Integer.parseInt(myPursh[1]));
                basket.saveBin(file);
                basket.printCart();
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shoppingList(String[] products, int[] prices) {
        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < products.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i + 1)
                    .append(". ")
                    .append(products[i])
                    .append(" ")
                    .append(prices[i])
                    .append(" руб/шт");
            System.out.println(sb.toString());
        }
        System.out.println("Выберите товар и количество или введите `end`");
    }

}
