public class Committee {
    private String name;
    private Lecturer chairman;
    private Lecturer[] members;
    private int memberCount;

    public Committee(String name, Lecturer chairman) {
        this.name = name;
        this.members = new Lecturer[10];
        this.memberCount = 0;
        setChairman(chairman);
    }

    public String getName() {
        return name;
    }


    private void increaseMembersArray() {
        Lecturer[] newMembers = new Lecturer[members.length * 2];

        for (int i = 0; i < memberCount; i++) { 
            newMembers[i] = members[i];
        }

        members = newMembers;
    }

    public boolean addMember(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }

        if (lecturer == chairman) {
            return false;
        }

        for (int i = 0; i < memberCount; i++) {
            if (members[i] == lecturer) {
                return false;
            }
        }

        if (members.length == memberCount) {
            increaseMembersArray();
        }

        members[memberCount] = lecturer;
        memberCount++;
        return true;
    }

    public boolean removeMember(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }

        for (int i = 0; i < memberCount; i++) {
            if (members[i] == lecturer) {
                for (int j = i; j < memberCount - 1; j++) {
                    members[j] = members[j + 1];
                }

                members[memberCount - 1] = null;
                memberCount--;
                return true;
            }
        }

        return false;
    }

    public boolean setChairman(Lecturer chairman) {
        if (chairman == null) {
            return false;
        }

        if (!chairman.isDoctorOrAbove()) {
            return false;
        }

        removeMember(chairman);
        this.chairman = chairman;
        return true;
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
