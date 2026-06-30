package ben_fisher_raz_matzliach;

public class Department {
    private final String name;
    private Lecturer[] lecturers;
    private int lecturerCount;
    private final int studentCount;

    public Department(String name, int studentCount) {
        this.name = name;
        this.studentCount = studentCount;
        this.lecturers = new Lecturer[2];
        this.lecturerCount = 0;
    }
    
    public String getName() {
        return name;
    }

    private void increaseLecturersArray() {
        Lecturer[] newLecturers = new Lecturer[lecturers.length * 2];

        for (int i = 0; i < lecturerCount; i++) {
            newLecturers[i] = lecturers[i];
        }

        lecturers = newLecturers;
    }

    public void addLecturer(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] == lecturer) {
                throw new CollegeActionException("Lecturer is already assigned to this department.");
            }
        }

        if (lecturerCount == lecturers.length) {
            increaseLecturersArray();
        }

        lecturers[lecturerCount] = lecturer;
        lecturerCount++;
    }

    public void removeLecturer(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] == lecturer) {
                for (int j = i; j < lecturerCount - 1; j++) {
                    lecturers[j] = lecturers[j + 1];
                }

                lecturers[lecturerCount - 1] = null;
                lecturerCount--;
                return;
            }
        }
        
        throw new CollegeActionException("Lecturer is not assigned to this department.");
    }

    @Override
    public String toString() {
        String result = "Department name: " + name +
                ", Students count: " + studentCount +
                ", Lecturers count: " + lecturerCount +
                "\nLecturers:";

        for (int i = 0; i < lecturerCount; i++) {
            result += "\n" + (i + 1) + ". " + lecturers[i];
        }

        return result;
    }

    public double getAverageSalary() {
        if (lecturerCount == 0) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < lecturerCount; i++) {
            sum += lecturers[i].getSalary();
        }

        return sum / lecturerCount;
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
