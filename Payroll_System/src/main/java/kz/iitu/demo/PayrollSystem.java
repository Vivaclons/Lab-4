package kz.iitu.demo;

import kz.iitu.demo.DAO.EmployeeDao;
import kz.iitu.demo.service.SalaryCalculatorService;
import kz.iitu.demo.type.CommissionEmployee;
import kz.iitu.demo.type.HourlyEmployee;
import kz.iitu.demo.type.SalariedCommissionEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class PayrollSystem {
    private Scanner sc;
    private EmployeeDao employeeDao;
    private DBConnection dbConnection;
    private SalaryCalculatorService salaryCalculatorService;


    @Autowired
    public PayrollSystem(EmployeeDao employeeDao, DBConnection dbConnection, SalaryCalculatorService salaryCalculatorService) {
        this.employeeDao = employeeDao;
        this.dbConnection = dbConnection;
        this.salaryCalculatorService = salaryCalculatorService;
        this.sc = new Scanner(System.in);
    }

    public void add10PercentSalaryForSalariedCommission() {
        String sql = "SELECT * FROM salaried_commission_employees";

        ResultSet resultSet = dbConnection.getData(sql);

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double salary = resultSet.getDouble("salary");
                Double percentageSales = resultSet.getDouble("percentage_sales");
                Double amountOfCommission = resultSet.getDouble("amount_of_commission");

                Double newSalary = salary * 1.1;

                employeeDao.updateSalary(
                        new SalariedCommissionEmployee(id, name, newSalary, percentageSales, amountOfCommission),
                        salary
                );
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }
    }

    private void changeSalaryForSalariedEmployee() {
        String sql = "SELECT * FROM salaried_employees";

        ArrayList<Employee> employees = new ArrayList<>();
        ResultSet resultSet = dbConnection.getData(sql);

        try {
            int i = 0;
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double salary = resultSet.getDouble("salary");
                employees.add(new Employee(id, name, salary));
                System.out.println((i++) + ") " + "ID: " + id + " " + " Name: " + name);
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }

        System.out.print("chose employee: ");
        Integer indexEmployee = sc.nextInt();
        Double oldSalary = employees.get(indexEmployee).getSalary();
        System.out.print("input new salary: ");
        employees.get(indexEmployee).setSalary(sc.nextDouble());

        employeeDao.updateSalary(employees.get(indexEmployee), oldSalary);
    }

    private void changeSalaryForHourlyEmployee() {
        String sql = "SELECT * FROM hourly_employees";

        ArrayList<Employee> employees = new ArrayList<>();
        ResultSet resultSet = dbConnection.getData(sql);

        try {
            int i = 0;
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double salary = resultSet.getDouble("salary");
                Integer work_hour = resultSet.getInt("work-hour");
                Double salary_hour = resultSet.getDouble("salary-hour");

                employees.add(new HourlyEmployee(id, name, salary, work_hour, salary_hour));
                System.out.println((i++) + ") " + "ID: " + id + " " + " Name: " + name);
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }

        System.out.print("chose employee: ");
        Integer indexEmployee = sc.nextInt();
        Double oldSalary = employees.get(indexEmployee).getSalary();
        System.out.print("input new salary hour: ");
        ((HourlyEmployee)employees.get(indexEmployee)).setSalaryHour(sc.nextDouble());
        salaryCalculatorService.calculateSalary((HourlyEmployee) employees.get(indexEmployee));
        employeeDao.updateSalary(employees.get(indexEmployee), oldSalary);
    }

    private void changeSalaryForCommissionEmployee() {
        String sql = "SELECT * FROM commission_employees";

        ArrayList<Employee> employees = new ArrayList<>();
        ResultSet resultSet = dbConnection.getData(sql);

        try {
            int i = 0;
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double salary = resultSet.getDouble("salary");
                Double percentageSales = resultSet.getDouble("percentage_sales");

                employees.add(new CommissionEmployee(id, name, salary, percentageSales));
                System.out.println((i++) + ") " + "ID: " + id + " " + " Name: " + name);
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }

        System.out.print("chose employee: ");
        Integer indexEmployee = sc.nextInt();
        Double oldSalary = employees.get(indexEmployee).getSalary();
        System.out.print("input sale amount: ");
        Double saleAmount = sc.nextDouble();
        salaryCalculatorService.calculateSalary((CommissionEmployee) employees.get(indexEmployee), saleAmount);
        employeeDao.updateSalary(employees.get(indexEmployee), oldSalary);
    }

    private void changeSalaryForSalariedCommissionEmployee() {
        String sql = "SELECT * FROM salaried_commission_employees";

        ArrayList<Employee> employees = new ArrayList<>();
        ResultSet resultSet = dbConnection.getData(sql);

        try {
            int i = 0;
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Double salary = resultSet.getDouble("salary");
                Double percentageSales = resultSet.getDouble("percentage_sales");
                Double amountOfCommission = resultSet.getDouble("amount_of_commission");

                employees.add(new SalariedCommissionEmployee(id, name, salary, percentageSales, amountOfCommission));
                System.out.println((i++) + ") " + "ID: " + id + " " + " Name: " + name);
            }
        } catch (SQLException sqlE) {
            System.out.println("ERROR!");
            System.out.println(sqlE);
        }

        System.out.print("chose employee: ");
        Integer indexEmployee = sc.nextInt();
        Double oldSalary = employees.get(indexEmployee).getSalary();
        System.out.print("input sale amount: ");
        Double saleAmount = sc.nextDouble();
        salaryCalculatorService.calculateSalary((SalariedCommissionEmployee) employees.get(indexEmployee), saleAmount);
        employeeDao.updateSalary(employees.get(indexEmployee), oldSalary);
    }

    public void showMenu() {
        boolean isWord = true;
        while(isWord) {
            System.out.println("\nenter 1 - Adding 10% base salaries for all Salaried-Commission employees");
            System.out.println("enter 2 - change salary for Salaried employee");
            System.out.println("enter 3 - change salary for Hourly employee");
            System.out.println("enter 4 - change salary for Commission employee");
            System.out.println("enter 5 - change salary for Salaried-Commission employee");
            System.out.println("enter 0 - Exit Program");
            System.out.print("enter: ");
            String chose = sc.next();

            switch (chose) {
                case "1":
                    add10PercentSalaryForSalariedCommission();
                    break;
                case "2":
                    changeSalaryForSalariedEmployee();
                    break;
                case "3":
                    changeSalaryForHourlyEmployee();
                    break;
                case "4":
                    changeSalaryForCommissionEmployee();
                    break;
                case "5":
                    changeSalaryForSalariedCommissionEmployee();
                    break;
                case "0":
                    isWord = false;
                    break;
                default:
                    System.out.println("Invalid argument!");
            }
        }
    }
}
