package ben_fisher_raz_matzliach;

import java.util.ArrayList;
import java.io.Serializable;

public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private ArrayList<Lecturer> lecturers;
    private final int studentCount;

    public Department(String name, int studentCount) {
        this.name = name;
        this.studentCount = studentCount;
        this.lecturers = new ArrayList<Lecturer>();
    }

    public String getName() {
        return name;
    }

    public void addLecturer(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        if (lecturers.contains(lecturer)) {
            throw new CollegeActionException("Lecturer is already assigned to this department.");
        }

        lecturers.add(lecturer);
    }

    public void removeLecturer(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        if (!lecturers.remove(lecturer)) {
            throw new CollegeActionException("Lecturer is not assigned to this department.");
        }
    }

    @Override
    public String toString() {
        String result = "Department name: " + name +
                ", Students count: " + studentCount +
                ", Lecturers count: " + lecturers.size() +
                "\nLecturers:";

        for (int i = 0; i < lecturers.size(); i++) {
            result += "\n" + (i + 1) + ". " + lecturers.get(i);
        }

        return result;
    }

    public double getAverageSalary() {
        if (lecturers.isEmpty()) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < lecturers.size(); i++) {
            sum += lecturers.get(i).getSalary();
        }

        return sum / lecturers.size();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Department)) {
            return false;
        }

        Department otherDepartment = (Department) other;

        if (name == null) {
            return otherDepartment.name == null;
        }

        return name.equalsIgnoreCase(otherDepartment.name);
    }
}
