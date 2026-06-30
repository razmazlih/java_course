package ben_fisher_raz_matzliach;

import java.util.ArrayList;
import java.io.Serializable;

public class Committee implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private Lecturer chairman;
    private ArrayList<Lecturer> members;
    private CommitteeMemberType allowedMemberType;

    public Committee(String name, Lecturer chairman, CommitteeMemberType allowedMemberType)
            throws CollegeActionException {
        this.name = name;
        this.members = new ArrayList<Lecturer>();
        this.allowedMemberType = allowedMemberType;
        setChairman(chairman);
    }

    public String getName() {
        return name;
    }

    public Lecturer getChairman() {
        return chairman;
    }

    public CommitteeMemberType getAllowedMemberType() {
        return allowedMemberType;
    }

    public int getMemberCount() {
        return members.size();
    }

    public Lecturer getMember(int index) {
        if (index < 0 || index >= members.size()) {
            return null;
        }

        return members.get(index);
    }

    public void addMember(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        if (lecturer == chairman) {
            throw new AlreadyCommitteeMemberException("The chairman cannot also be a regular committee member.");
        }

        if (members.contains(lecturer)) {
            throw new AlreadyCommitteeMemberException("Lecturer is already a committee member.");
        }

        checkMemberType(lecturer);

        members.add(lecturer);
    }

    private void checkMemberType(Lecturer lecturer) throws InvalidCommitteeMemberTypeException {
        if (allowedMemberType == CommitteeMemberType.REGULAR) {
            if (lecturer instanceof Doctor) {
                throw new InvalidCommitteeMemberTypeException(
                        "This committee only allows regular lecturers (not doctors or professors).");
            }
        } else if (allowedMemberType == CommitteeMemberType.DOCTOR) {
            if (lecturer instanceof Professor) {
                throw new InvalidCommitteeMemberTypeException(
                        "This committee only allows doctors (not professors).");
            }
            if (!(lecturer instanceof Doctor)) {
                throw new InvalidCommitteeMemberTypeException(
                        "This committee only allows doctors.");
            }
        } else if (allowedMemberType == CommitteeMemberType.PROFESSOR) {
            if (!(lecturer instanceof Professor)) {
                throw new InvalidCommitteeMemberTypeException(
                        "This committee only allows professors.");
            }
        }
    }

    public void removeMember(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        if (!members.remove(lecturer)) {
            throw new CollegeActionException("Lecturer is not a committee member.");
        }
    }

    public boolean hasMember(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }

        return members.contains(lecturer);
    }

    public int getStaffCount() {
        int count = members.size();

        if (chairman != null && !hasMember(chairman)) {
            count++;
        }

        return count;
    }

    public int getTotalArticleCount() {
        int total = 0;

        if (chairman != null) {
            total += getArticleCount(chairman);
        }

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i) != chairman) {
                total += getArticleCount(members.get(i));
            }
        }

        return total;
    }

    private int getArticleCount(Lecturer lecturer) {
        if (lecturer instanceof ArticleWriter) {
            ArticleWriter writer = (ArticleWriter) lecturer;
            return writer.getArticleCount();
        }

        return 0;
    }

    public void setChairman(Lecturer chairman) throws InvalidChairmanException {
        if (chairman == null) {
            throw new InvalidChairmanException("Chairman lecturer does not exist.");
        }

        if (!chairman.isDoctorOrAbove()) {
            throw new InvalidChairmanException("Committee chairman must be a doctor or professor.");
        }

        this.chairman = chairman;
    }

    @Override
    public String toString() {
        String result = "Committee name: " + name +
                ", Chairman: " + chairman +
                ", Member type: " + allowedMemberType +
                ", Members count: " + members.size() +
                "\nMembers:";

        for (int i = 0; i < members.size(); i++) {
            result += "\n" + (i + 1) + ". " + members.get(i);
        }

        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Committee)) {
            return false;
        }

        Committee otherCommittee = (Committee) other;

        if (name == null) {
            return otherCommittee.name == null;
        }

        return name.equalsIgnoreCase(otherCommittee.name);
    }
}
