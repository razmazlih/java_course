package ben_fisher_raz_matzliach;

public class CollegeActionException extends Exception {
    public CollegeActionException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "CollegeActionException: " + getMessage();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof CollegeActionException)) {
            return false;
        }

        CollegeActionException otherException = (CollegeActionException) other;

        if (getMessage() == null) {
            return otherException.getMessage() == null;
        }

        return getMessage().equals(otherException.getMessage());
    }
}
