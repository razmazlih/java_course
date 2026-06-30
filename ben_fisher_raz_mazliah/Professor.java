package ben_fisher_raz_matzliach;

import java.io.Serializable;

public class Professor extends Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String professorshipBody;

    public Professor(String name, int idNumber, String degreeName, double salary, Article[] articles,
            String professorshipBody) throws CollegeActionException {
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

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
