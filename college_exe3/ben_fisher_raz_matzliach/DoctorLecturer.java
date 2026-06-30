package ben_fisher_raz_matzliach;

public class DoctorLecturer extends Lecturer implements ArticleWriter {
    private Article[] articles;
    private int articleCount;

    public DoctorLecturer(String name, int idNumber, String degreeName, double salary, Article[] articles)
            throws CollegeActionException {
        this(name, idNumber, Degree.DOCTOR, degreeName, salary, articles);
    }

    protected DoctorLecturer(String name, int idNumber, Degree degree, String degreeName, double salary,
            Article[] articles) throws CollegeActionException {
        super(name, idNumber, degree, degreeName, salary);
        this.articles = new Article[2];
        this.articleCount = 0;

        if (articles != null) {
            for (int i = 0; i < articles.length; i++) {
                addArticle(articles[i]);
            }
        }
    }

    private void increaseArticlesArray() {
        Article[] newArticles = new Article[articles.length * 2];

        for (int i = 0; i < articleCount; i++) {
            newArticles[i] = articles[i];
        }

        articles = newArticles;
    }

    public void addArticle(Article article) throws CollegeActionException {
        if (article == null) {
            throw new CollegeActionException("Article does not exist.");
        }

        if (articleCount == articles.length) {
            increaseArticlesArray();
        }

        articles[articleCount] = article;
        articleCount++;
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
