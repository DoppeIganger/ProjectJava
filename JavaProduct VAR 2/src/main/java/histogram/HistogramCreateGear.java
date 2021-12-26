package histogram;

import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import db.DataBaseGear;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

public class HistogramCreateGear {
    public static void CreateHistogram(String filename, Connection conn, String XAxis, String YAxis, int width, int height) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            addMedianPrice(conn, dataset, "Лампа");
            addMedianPrice(conn, dataset, "Когтеточка-комплекс");
            addMedianPrice(conn, dataset, "Механизм поворота Brinsea ");
            addMedianPrice(conn, dataset, "Наполнитель силикагелевый");
            addMedianPrice(conn, dataset, "Овоскоп Novital ");
            addMedianPrice(conn, dataset, "Кормушка педальная");
            addMedianPrice(conn, dataset, "Лоток для куриных яиц");
            addMedianPrice(conn, dataset, "Матрас для собак");
            addMedianPrice(conn, dataset, "Электронная плата с экраном");
            addMedianPrice(conn, dataset, "Лежак круглый с бортом");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String title = filename.split("\\.")[0];
        JFreeChart chart = ChartFactory.createBarChart(
                title,
                XAxis,
                YAxis,
                dataset,
                PlotOrientation.VERTICAL,
                true,                     // include legend
                false,                     // tooltips
                false                     // URLs
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(212, 212, 212));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint (Color.white);
        SaveHistogram(chart, filename, width, height);
    }

    private static void addMedianPrice(Connection conn, DefaultCategoryDataset dataset, String rowKey) throws SQLException {
        dataset.addValue(DataBaseGear.getMedianPrice(conn, rowKey), rowKey, "");
    }

    private static void SaveHistogram(JFreeChart chart, String name, int width, int height) {
        try {
            ChartUtilities.saveChartAsPNG(new File(name), chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Histogram \"" +name+ "\" saved successfully!");
    }
}