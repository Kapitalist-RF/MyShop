import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static boolean saveBool;
    private static boolean loadBool;
    private static boolean logBool;
    private static Basket basket;
    private static ClientLog clientLog;
    private static File fileLoad;
    private static File fileSave;
    private static File fileCsv;

    public static void main(String[] args) {
        readXml(new File("shop.xml"));
        if (loadBool) {
            if (fileLoad.toString().endsWith(".json")) {
                basket = Basket.loadFromJsonFile(fileLoad);
            } else {
                basket = Basket.loadFromTxtFile(fileLoad);
            }
        }
        if (logBool) {
            clientLog = new ClientLog(fileCsv);
        }
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
                if (myPursh[0].equalsIgnoreCase("end")) {
                    if (logBool) {
                        clientLog.exportAsCSV(new File("log.txt"));
                    }
                    break;
                }
                basket.addToCart(Integer.parseInt(myPursh[0]), Integer.parseInt(myPursh[1]), clientLog, logBool);
                if (saveBool) {
                    if (fileSave.toString().endsWith(".json")) {
                        basket.saveJson(fileSave);
                    } else {
                        basket.saveTxt(fileSave);
                    }

                }
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

    public static void readXml(File xmlFile) {
        String nameFile = null;
        String[] elementsTag = new String[]{"load", "save", "log"};
        String[] tags = new String[]{"enabled", "fileName", "format"};
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(String.valueOf(xmlFile)));
            for (int i = 0; i < elementsTag.length; i++) {
                nameFile = null;
                NodeList nodeList = doc.getElementsByTagName(elementsTag[i]);
                Element element = (Element) nodeList.item(0);
                NodeList list = element.getChildNodes();
                for (int a = 0; a < list.getLength(); a++) {
                    if (list.item(a).getNodeType() == Node.ELEMENT_NODE) {
                        Element elementChild = (Element) list.item(a);
                        if (element.getTagName().equals(elementsTag[0])) {
                            if (elementChild.getTagName().equals(tags[0])) {
                                if (elementChild.getTextContent().equalsIgnoreCase("true")) {
                                    loadBool = true;
                                } else {
                                    break;
                                }
                            } else if (elementChild.getTagName().equals(tags[1])) {
                                nameFile = elementChild.getTextContent().split("\\.")[0];
                            } else if (elementChild.getTagName().equals(tags[2])) {
                                fileLoad = new File(nameFile + "." + elementChild.getTextContent());
                            }
                        } else if (element.getTagName().equals(elementsTag[1])) {
                            if (elementChild.getTagName().equals(tags[0])) {
                                if (elementChild.getTextContent().equalsIgnoreCase("true")) {
                                    saveBool = true;
                                } else {
                                    break;
                                }
                            } else if (elementChild.getTagName().equals(tags[1])) {
                                nameFile = elementChild.getTextContent().split("\\.")[0];
                            } else if (elementChild.getTagName().equals(tags[2])) {
                                fileSave = new File(nameFile + "." + elementChild.getTextContent());
                            }
                        } else if (element.getTagName().equals(elementsTag[2])) {
                            if (elementChild.getTagName().equals(tags[0])) {
                                if (elementChild.getTextContent().equalsIgnoreCase("true")) {
                                    logBool = true;
                                } else {
                                    break;
                                }
                            } else if (elementChild.getTagName().equals(tags[1])) {
                                fileCsv = new File(elementChild.getTextContent());
                            }
                        }
                    }
                }

            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
