package ben_fisher_raz_matzliach;

import java.util.ArrayList;
import java.io.Serializable;

public class Doctor extends Lecturer implements ArticleWriter, Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Article> articles;

    public Doctor(String name, int idNumber, String degreeName, double salary, ArrayList<Article> articles)
            throws CollegeActionException {
        this(name, idNumber, Degree.DOCTOR, degreeName, salary, articles);
    }

    protected Doctor(String name, int idNumber, Degree degree, String degreeName, double salary,
            ArrayList<Article> articles) throws CollegeActionException {
        super(name, idNumber, degree, degreeName, salary);
        this.articles = new ArrayList<Article>();

        if (articles != null) {
            for (int i = 0; i < articles.size(); i++) {
                addArticle(articles.get(i));
            }
        }
    }

    public void addArticle(Article article) throws CollegeActionException {
        if (article == null) {
            throw new CollegeActionException("Article does not exist.");
        }

        articles.add(article);
    }

    @Override
    public int getArticleCount() {
        return articles.size();
    }

    @Override
    public String getArticlesDetails() {
        if (articles.isEmpty()) {
            return "No articles";
        }

        String result = "";

        for (int i = 0; i < articles.size(); i++) {
            if (i > 0) {
                result += "; ";
            }

            result += (i + 1) + ". " + articles.get(i);
        }

        return result;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Articles count: " + articles.size() +
                ", Articles: " + getArticlesDetails();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }
}
