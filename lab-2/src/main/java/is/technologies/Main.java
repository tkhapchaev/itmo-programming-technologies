package is.technologies;

import is.technologies.comparator.Comparator;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Comparator comparator = new Comparator("jdbc:postgresql://localhost:5432/lab?currentSchema=cats",
                "postgres",
                "Timur1883");

        List<String> comparisonResults = comparator.Compare();
        System.out.println("\nComparison results:");

        for (String comparisonResult : comparisonResults) {
            System.out.println(comparisonResult);
        }
    }
}