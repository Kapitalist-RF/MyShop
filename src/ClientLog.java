import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ClientLog {
    private String[] products = new String[2];
    private File csvFile = new File("log.csv");
    private File txtFile = new File("log.txt");
    private boolean nameBool = false;
    private StringBuilder sb = new StringBuilder();

    public void log(int productNum, int amount) {
        if (!nameBool) {
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(String.valueOf(txtFile)), StandardOpenOption.TRUNCATE_EXISTING)) {

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile))) {
                writer.write("productNum,amount");
                writer.newLine();
                nameBool = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(txtFile, true))) {
            sb.append(productNum).append(",").append(amount);
            writer.write(sb.toString());
            writer.newLine();
            sb.delete(0, sb.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exportAsCSV(File txtFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile));
             CSVWriter writer = new CSVWriter(new FileWriter(csvFile))){
            String str = null;
            while ((str = reader.readLine()) != null){
                writer.writeNext(str.split(","));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
