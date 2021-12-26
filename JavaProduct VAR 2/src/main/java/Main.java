import csv.CsvTools;
import db.*;
import histogram.HistogramCreateGear;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        var connection = DataBaseConnection.getConnection("jdbc:sqlite:./catalog.sqlite");
        try {
            var data = CsvTools.ParseProductCsv("./catalog.csv");
            DataBaseGear.updateProducts(connection, data);

            HistogramCreateGear.CreateHistogram("Task_1.png",
                    DataBaseConnection.getConnection("jdbc:sqlite:./catalog.sqlite"),
                    "Products",
                    "Average price, Ruble",
                    1920,
                    1080);
            if (connection != null) {
                connection.close();
            }
        } catch (IOException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
