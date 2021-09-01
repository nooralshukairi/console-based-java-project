
package consolebasedjavaproject;

import java.io.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Noor Al Shukairi
 */
public class ConsoleBasedJavaProject {

    private static final Logger LOG = Logger.getLogger(ConsoleBasedJavaProject.class.getName());
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) throws SQLException {
        String[] csvFiles = {"Employees-csv.csv", "Employees-pipe.csv", "Employees-semicolon.csv", "Employees-tab.txt"};
        String[] delimiters = {",", "|", ";", "\t"};
        String rootLocation = "C:\\Users\\DeLL\\Desktop\\Gen Z\\Systems\\Projects\\ConsoleBasedJavaProject\\resources\\csv-files\\";

        Scanner sc = new Scanner(System.in);

        System.out.println("*************** CSV Reader ***************");
        System.out.println("Choose a file number to read: ");

        for (int i = 0; i < csvFiles.length; i++) {
            System.out.println(i+1 + ". " + csvFiles[i]);
        }

        System.out.println("File number: ");
        int fileToRead = sc.nextInt();

        String csvFile = "";
        String delimiter = "";

        switch (fileToRead) {
            case 1:
                csvFile = csvFiles[0];
                delimiter = delimiters[0];
                break;
            case 2:
                csvFile = csvFiles[1];
                delimiter = delimiters[1];
                break;
            case 3:
                csvFile = csvFiles[2];
                delimiter = delimiters[2];
                break;
            case 4:
                csvFile = csvFiles[3];
                delimiter = delimiters[3];
                break;
        }

        File csvFilePath = new File(rootLocation, csvFile);
        List<Employee> employees = readEmployees(csvFilePath, delimiter);
        List<Employee> employeesDB = storeEmployees();
    }

    // CSV Reader
    public static List<Employee> readEmployees(File csvFilePath, String delimiter) {
        List<Employee> employees = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            String line = "";
            int linesRead = 0;
            br.readLine(); // reads first line

            while ((line = br.readLine()) != null) {
                linesRead++;
                String[] tokens = line.split(delimiter);

                // There are 9 tokens in each line
                // 1. First Name
                // 2. Last Name
                // 3. Arabic Name
                // 4. Date of Birth
                // 5. Gender
                // 6. Nationality
                // 7. Latest College Degree
                // 8. GPA
                // 9. Monthly Income

                if (tokens.length < 9) {
                    LOG.warning("Line " + linesRead + " is ignored because it doesn't contain 9 tokens!");
                    continue;
                }

                // create employee object
                Employee employee = new Employee();
                employee.setFirstName(tokens[0]);
                employee.setLastName(tokens[1]);
                employee.setArabicName(tokens[2]);
                try {
                    employee.setDob(DATE_FORMAT.parse(tokens[3]));
                } catch (ParseException e) {
                    LOG.warning("Invalid Date format in line " + linesRead + ": " + tokens[3]);
                }
                employee.setGender(tokens[4]);
                employee.setNationality(tokens[5]);
                employee.setLatestCollegeDegree(tokens[6]);
                try {
                    employee.setGpa(Double.parseDouble(tokens[7]));
                } catch (NumberFormatException e) {
                    LOG.warning("Invalid GPA format in line " + linesRead + ": " + tokens[7]);
                }
                try {
                    employee.setMonthlyIncome(Double.parseDouble(tokens[8]));
                } catch (NumberFormatException e) {
                    LOG.warning("Invalid Monthly Income format in line " + linesRead + ": " + tokens[8]);
                }

                double yearlyIncome = employee.getMonthlyIncome() * 12;
                System.out.println(tokens[0] + " " + tokens[1] + " is an " + tokens[5] + " and has " +
                        tokens[6] + " degree with " + tokens[7] + " GPA and earning OMR " + yearlyIncome +
                        ". The Arabic name is " + tokens[2]);
            }
            LOG.info("Reading file complete. Total number of lines: " + linesRead);
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE,"File not found");
        } catch (IOException e) {
            LOG.log(Level.SEVERE,"Error in reading file");
        }
        return employees;
    }

    private static List<Employee> storeEmployees() throws SQLException {
        // create employee object
        Employee employee = new Employee();

        // JDBC Connection
        String jdbcUrl = "";
        String username = "";
        String password = "";

        int batchSize = 20;

        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
        connection.setAutoCommit(false);

        // Employee table columns
        // 1. id (auto increment)
        // 2. firstName
        // 3. lastName
        // 4. arabicName
        // 5. dob
        // 6. gender
        // 7. nationality
        // 8. latestCollegeDegree
        // 9. monthlyIncome

        String sqlQuery = "insert into employees(firstName, lastName, arabicName, dob, gender, nationality, latestCollegeDegree, gpa, monthlyIncome) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sqlQuery);

        // retrieve employee data from object
        statement.setString(1, employee.getFirstName());
        statement.setString(2, employee.getLastName());
        statement.setString(3, employee.getArabicName());
        statement.setDate(4, (Date) employee.getDob());
        statement.setString(5, employee.getGender());
        statement.setString(6, employee.getNationality());
        statement.setString(7, employee.getLatestCollegeDegree());
        statement.setDouble(8, employee.getGpa());
        statement.setDouble(9, employee.getMonthlyIncome());

        statement.addBatch();

        int count = 0;

        if (count % batchSize == 0) {
            statement.executeBatch();
        }

        statement.executeBatch();
        connection.commit();
        connection.close();

        System.out.println("Employee data inserted successfully");

        return null;
    }
}
