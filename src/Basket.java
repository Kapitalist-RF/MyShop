import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Basket {

    private int[] prices;
    private String[] products;
    private int[] baskets;

    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.baskets = new int[products.length];
    }

    private Basket(int[] prices, String[] products, int[] baskets) {
        this.prices = prices;
        this.products = products;
        this.baskets = baskets;
    }

    public void addToCart(int productNum, int amount) {
        if (productNum <= products.length && productNum > 0) {
            baskets[productNum -1] += amount;
        }
    }

    public void printCart() {
        int sum = 0;
        StringBuilder sb = new StringBuilder();
        System.out.println("My Basket: ");
        if (!Arrays.stream(baskets).allMatch(o1 -> o1 == 0)) {
            for (int i = 0; i < baskets.length; i++) {
                sb.delete(0, sb.length());
                if (baskets[i] != 0) {
                    sb.append(i + 1)
                            .append(". ")
                            .append(products[i])
                            .append(" - ")
                            .append(baskets[i])
                            .append(" шт. ")
                            .append(prices[i])
                            .append(" руб/шт ")
                            .append(baskets[i] * prices[i])
                            .append(" руб в сумме.");
                    System.out.println(sb);
                    sum += baskets[i] * prices[i];
                }
            }
            System.out.println("Итого: " + sum + " руб.");
        }
    }

    public void saveTxt(File textFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFile))) {
            for (String product : products) {
                writer.write(product + " ");
            }
            writer.newLine();
            for (int price : prices) {
                writer.write(price + " ");
            }
            writer.newLine();
            for (int basket : baskets) {
                writer.write(basket + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromTxtFile(File textFile) {
        if (textFile.exists() && textFile.canRead()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
                String str = null;
                List<String> list = new ArrayList<>();
                while ((str = reader.readLine()) != null){
                    list.add(str);
                }
                if(list.size() == 3) {
                    String[] products = list.get(0).split(" ");
                    int[] prices = Arrays.stream(list.get(1).split(" "))
                            .flatMapToInt(o1 -> IntStream.of(Integer.parseInt(o1))).toArray();
                    int[] baskets = Arrays.stream(list.get(2).split(" "))
                            .flatMapToInt(o1 -> IntStream.of(Integer.parseInt(o1))).toArray();
                    return new Basket(prices, products, baskets);
                } else {
                    return null;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int[] getPrices() {
        return prices;
    }

    public String[] getProducts() {
        return products;
    }
}
