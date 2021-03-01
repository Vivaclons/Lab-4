package kz.iitu.demo.DAO;
import kz.iitu.demo.DBConnection;
import kz.iitu.demo.Employee;
import kz.iitu.demo.salaryChange.SalaryChangeEvent;
import kz.iitu.demo.type.CommissionEmployee;
import kz.iitu.demo.type.HourlyEmployee;
import kz.iitu.demo.type.SalariedCommissionEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDao implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;
    private DBConnection dbConnection;

    @Autowired
    public EmployeeDao(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void updateSalary(Employee employee, Double oldSalary) {
        String tableName;
        String sqlBonus = "";

        if (employee instanceof SalariedCommissionEmployee) {
            tableName = "salaried_commission_employees";
        } else if (employee instanceof HourlyEmployee) {
            tableName = "hourly_employees";
            sqlBonus = ", `salary-hour` = " + ((HourlyEmployee) employee).getSalaryHour();
        } else if (employee instanceof CommissionEmployee) {
            tableName = "commission_employees";
        } else {
            tableName = "salaried_employees";
        }

        String sql = "UPDATE " + tableName + " SET salary = " + employee.getSalary() + sqlBonus + " WHERE id = " + employee.getId();
        dbConnection.updateData(sql);
        System.out.println(sql);
        this.eventPublisher.publishEvent(new SalaryChangeEvent(this, employee, oldSalary));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }
}
