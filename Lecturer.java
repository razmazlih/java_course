public class Lecturer {
    private String name;
    private int idNumber;
    private Degree degree;
    private String degreeName;
    private double salary;
    private Department department;

    public Lecturer(String name, int idNumber, Degree degree, String degreeName, double salary) {
        this.name = name;
        this.idNumber = idNumber;
        this.degree = degree;
        this.degreeName = degreeName;
        this.salary = salary;
        this.department = null;
    }
    
    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public boolean isDoctorOrAbove() {
        return degree.isDoctorOrAbove();
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", ID: " + idNumber +
                ", Degree: " + degree +
                ", Degree name: " + degreeName +
                ", Salary: " + salary;
    }
}
