public class Committee {
    private String name;
    private Lecturer chairman;
    private Lecturer[] members;
    private int memberCount;

    public Committee(String name, Lecturer chairman) {
        this.name = name;
        this.chairman = chairman;
        this.members = new Lecturer[10];
        this.memberCount = 0;

        if (chairman != null && chairman.isDoctorOrAbove()) {
            this.chairman = chairman;
        } else {
            this.chairman = null;
        }
    }
}
