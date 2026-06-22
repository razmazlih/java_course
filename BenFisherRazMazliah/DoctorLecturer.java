package BenFisherRazMazliah;

public class DoctorLecturer extends Lecturer implements ArticleWriter {
    private final String[] articles;
    private final int articleCount;

    public DoctorLecturer(String name, int idNumber, String degreeName, double salary, String[] articles) {
        this(name, idNumber, Degree.DOCTOR, degreeName, salary, articles);
    }

    protected DoctorLecturer(String name, int idNumber, Degree degree, String degreeName, double salary, String[] articles) {
        super(name, idNumber, degree, degreeName, salary);

        if (articles == null) {
            this.articles = new String[0];
            this.articleCount = 0;
        } else {
            this.articles = new String[articles.length];

            for (int i = 0; i < articles.length; i++) {
                this.articles[i] = articles[i];
            }

            this.articleCount = articles.length;
        }
    }

    @Override
    public int getArticleCount() {
        return articleCount;
    }

    @Override
    public String getArticlesDetails() {
        if (articleCount == 0) {
            return "No articles";
        }

        String result = "";

        for (int i = 0; i < articleCount; i++) {
            if (i > 0) {
                result += "; ";
            }

            result += (i + 1) + ". " + articles[i];
        }

        return result;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Articles count: " + articleCount +
                ", Articles: " + getArticlesDetails();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
