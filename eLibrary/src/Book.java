public class Book {

    private String bookName;
    private String authorName;
    private String coverImage;

    public Book(String bookName, String authorName, String coverImage) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.setCoverImage(coverImage);
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
}
