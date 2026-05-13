public class Department {
        private String name;
        private Lecturer[] lecturers;
        private int lecturerCount;
        private int studentCount;

    public Department(String name, int studentCount) {
        this.name = name;
        this.studentCount = studentCount;
        this.lecturers = new Lecturer[10];
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

    public boolean addLecturer(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }

        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] == lecturer) {
                return false;
            }
        }

        if (lecturerCount == lecturers.length) {
            increaseLecturersArray();
        }

        lecturers[lecturerCount] = lecturer;
        lecturerCount++;
        return true;
    }

    public boolean removeLecturer(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }

        for (int i = 0; i < lecturerCount; i++) {
            if (lecturers[i] == lecturer) {
                for (int j = i; j < lecturerCount - 1; j++) {
                    lecturers[j] = lecturers[j + 1];
                }

                lecturers[lecturerCount - 1] = null;
                lecturerCount--;
                return true;
            }
        }
        
        return false;
    }

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
}

