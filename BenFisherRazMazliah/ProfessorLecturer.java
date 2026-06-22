package BenFisherRazMazliah;

public class ProfessorLecturer extends DoctorLecturer {
    private final String professorshipBody;

    public ProfessorLecturer(String name, int idNumber, String degreeName, double salary, String[] articles,
            String professorshipBody) {
        super(name, idNumber, Degree.PROFESSOR, degreeName, salary, articles);
        this.professorshipBody = professorshipBody;
    }

    public String getProfessorshipBody() {
        return professorshipBody;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Professorship granted by: " + professorshipBody;
    }
}
