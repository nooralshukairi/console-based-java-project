
package consolebasedjavaproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/**
 *
 * @author Noor Al Shukairi
 */
public class ConsoleBasedJavaProject {
    
    public static void main(String[] args) {
        // CSV Reader
        String path = "C:/Users/DeLL/Desktop/Gen Z/Systems/Projects/ConsoleBasedJavaProject/resources/csv-files/Users-csv.csv";
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            br.readLine(); // reads first line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double yearlyIncome = Double.parseDouble(values[8]) * 12;
                String employee = values[0] + " " + values[1] + " is an " + values[5] + " and has " + values[6] +
                        " degree with " + values[7] + " GPA and earning OMR " + yearlyIncome +
                        " yearly. The Arabic name is " + values[2];
                System.out.println(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        // JDBC Connection
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/console_based_java_db", "root", "hogwarts");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from employees");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("firstName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
