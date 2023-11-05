package es.ulpgc.Cleaner;

public class Book {
    private String title;
    private String year;
    private String author;
    private String language;

    public Book(String title, String year, String author, String language) {
        this.title = title;
        this.year = year;
        this.author = author;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", author='" + author + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}

