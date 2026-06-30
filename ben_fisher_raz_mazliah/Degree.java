package ben_fisher_raz_matzliach;

public enum Degree {
    FIRST(1),
    SECOND(2),
    DOCTOR(3),
    PROFESSOR(4);

    private final int rank;

    Degree(int rank) {
        this.rank = rank;
    }

    public boolean isDoctorOrAbove() {
        return rank >= 3;
    }
}
