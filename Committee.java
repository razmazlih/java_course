public class Committee {
    private String name;
    private Lecturer chairman;
    private Lecturer[] members;
    private int memberCount;

    public Committee(String name, Lecturer chairman) {
        this.name = name;
        this.members = new Lecturer[10];
        this.memberCount = 0;

        if (chairman != null && chairman.isDoctorOrAbove()) {
            this.chairman = chairman;
        } else {
            this.chairman = null;
        }
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
                for (int j = i; j < memberCount -1; j++) {
                    members[j] = members[j + 1];
                }

                members[memberCount - 1] = null;
                return true;
            }
        }

        return false;
    }
}
