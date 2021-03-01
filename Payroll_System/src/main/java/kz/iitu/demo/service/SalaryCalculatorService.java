package kz.iitu.demo.service;

import kz.iitu.demo.Employee;
import kz.iitu.demo.type.CommissionEmployee;
import kz.iitu.demo.type.HourlyEmployee;
import kz.iitu.demo.type.SalariedCommissionEmployee;
import org.springframework.stereotype.Component;

@Component
public class SalaryCalculatorService {
    public Employee calculateSalary(HourlyEmployee employee) {
        Double newSalary;

        if (employee.getWorkHour() > 40) {
            newSalary = 40.0 * employee.getSalaryHour() + (employee.getWorkHour() - 40) * 1.5 * employee.getSalaryHour();
        } else {
            newSalary = employee.getWorkHour() * employee.getSalaryHour();
        }
        employee.setSalary(newSalary);

        return employee;
    }

    public Employee calculateSalary(CommissionEmployee employee, Double amount) {
        Double bonus = amount * employee.getPercentageSales() / 100;
        employee.setSalary(employee.getSalary() + bonus);

        return employee;
    }

    public Employee calculateSalary(SalariedCommissionEmployee employee, Double amount) {
        Double bonus = amount * employee.getPercentageSales() / 100;
        employee.setSalary(employee.getSalary() + bonus);

        return employee;
    }
}
