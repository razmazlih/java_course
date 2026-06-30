package ben_fisher_raz_matzliach;

import java.io.Serializable;

public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String title;

    public Article(String title) throws CollegeActionException {
        if (title == null || title.trim().isEmpty()) {
            throw new CollegeActionException("Article title cannot be empty.");
        }

        this.title = title.trim();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Article)) {
            return false;
        }

        Article otherArticle = (Article) other;
        return title.equalsIgnoreCase(otherArticle.title);
    }
}
