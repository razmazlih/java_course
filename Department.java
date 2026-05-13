public class Department {
        private String name;
        private Lecturer[] lecturers;
        private int lecturerCount;

    public Department(String name) {
        this.name = name;
        this.lecturers = new Lecturer[10];
        this.lecturerCount = 0;
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
                ", Lecturers count: " + lecturerCount +
                "\nLecturers:";

        for (int i = 0; i < lecturerCount; i++) {
            result += "\n" + (i + 1) + ". " + lecturers[i];
        }

        return result;
    }
}

