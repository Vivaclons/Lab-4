package kz.iitu.demo.type;

public class CommissionEmployee extends Employee {
    private Double percentageSales;

    public CommissionEmployee() {}

    public CommissionEmployee(Integer id, String name, Double salary, Double percentageSales) {
        super(id, name, salary);
        this.percentageSales = percentageSales;
    }

    public Double getPercentageSales() {
        return percentageSales;
    }

    @Override
    public String toString() {
        return "CommissionEmployee{" +
                "id=" + super.getId() +
                ", name=" + super.getName() +
                ", salary=" + super.getSalary() +
                ", percentageSales=" + percentageSales +
                '}';
    }
}
