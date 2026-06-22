package BenFisherRazMazliah;

public class Committee {
    private final String name;
    private Lecturer chairman;
    private Lecturer[] members;
    private int memberCount;

    public Committee(String name, Lecturer chairman) throws CollegeActionException {
        this.name = name;
        this.members = new Lecturer[2];
        this.memberCount = 0;
        setChairman(chairman);
    }

    public String getName() {
        return name;
    }

    public Lecturer getChairman() {
        return chairman;
    }


    private void increaseMembersArray() {
        Lecturer[] newMembers = new Lecturer[members.length * 2];

        for (int i = 0; i < memberCount; i++) { 
            newMembers[i] = members[i];
        }

        members = newMembers;
    }

    public void addMember(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        if (lecturer == chairman) {
            throw new CollegeActionException("The chairman is already part of the committee.");
        }

        for (int i = 0; i < memberCount; i++) {
            if (members[i] == lecturer) {
                throw new CollegeActionException("Lecturer is already a committee member.");
            }
        }

        if (members.length == memberCount) {
            increaseMembersArray();
        }

        members[memberCount] = lecturer;
        memberCount++;
    }

    public void removeMember(Lecturer lecturer) throws CollegeActionException {
        if (lecturer == null) {
            throw new CollegeActionException("Lecturer does not exist.");
        }

        for (int i = 0; i < memberCount; i++) {
            if (members[i] == lecturer) {
                for (int j = i; j < memberCount - 1; j++) {
                    members[j] = members[j + 1];
                }

                members[memberCount - 1] = null;
                memberCount--;
                return;
            }
        }

        throw new CollegeActionException("Lecturer is not a committee member.");
    }

    public boolean hasMember(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }

        for (int i = 0; i < memberCount; i++) {
            if (members[i] == lecturer) {
                return true;
            }
        }

        return false;
    }

    public int getStaffCount() {
        int count = memberCount;

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

        for (int i = 0; i < memberCount; i++) {
            if (members[i] != chairman) {
                total += getArticleCount(members[i]);
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

    public void setChairman(Lecturer chairman) throws CollegeActionException {
        if (chairman == null) {
            throw new CollegeActionException("Chairman lecturer does not exist.");
        }

        if (!chairman.isDoctorOrAbove()) {
            throw new CollegeActionException("Committee chairman must be a doctor or professor.");
        }

        if (hasMember(chairman)) {
            removeMember(chairman);
        }

        this.chairman = chairman;
    }

    @Override
    public String toString() {
        String result = "Committee name: " + name +
                ", Chairman: " + chairman +
                ", Members count: " + memberCount +
                "\nMembers:";
        
        for (int i = 0; i < memberCount; i++) {
            result += "\n" + (i + 1) + ". " + members[i];
        }

        return result;
    }
}
