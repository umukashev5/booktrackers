package kz.ualikhan.booktrackers.model;

public enum BookStatus {
    WANT_TO_READ("Хочу прочитать"),
    READING("Читаю"),
    FINISHED("Прочитал"),
    DROPPED("Брошено");

    private final String displayName;

    BookStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}