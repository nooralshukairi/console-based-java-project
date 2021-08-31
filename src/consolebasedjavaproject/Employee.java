package consolebasedjavaproject;

import java.lang.reflect.Field;
import java.util.Date;

public class Employee {
    private String firstName;
    private String lastName;
    private String arabicName;
    private Date dob;
    private String gender;
    private String nationality;
    private String latestCollegeDegree;
    private Double gpa;
    private Double monthlyIncome;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLatestCollegeDegree() {
        return latestCollegeDegree;
    }

    public void setLatestCollegeDegree(String latestCollegeDegree) {
        this.latestCollegeDegree = latestCollegeDegree;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getName() + "[");
        Field fields[] = this.getClass().getDeclaredFields();
        char c = ' ';

        for (Field field : fields) {
            try {
                sb.append(c).append(field.getName()).append("-").append(field.get(this));
                c = ',';
            } catch (IllegalAccessException e) {
            }
        }
        sb.append("]");
        return sb.toString();

//        return "Employee{" +
//                "firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", arabicName='" + arabicName + '\'' +
//                ", dob=" + dob +
//                ", gender='" + gender + '\'' +
//                ", nationality='" + nationality + '\'' +
//                ", latestCollegeDegree='" + latestCollegeDegree + '\'' +
//                ", gpa=" + gpa +
//                ", monthlyIncome=" + monthlyIncome +
//                '}';
    }
}
