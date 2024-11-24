package app.handong.feed.constants;

public enum Permission {
    ADMIN_GET_USER("adminGetUser"),
    ADMIN_FIREBASE_FILES("adminFirebaseFiles"),
    ADMIN_DELETE_FEED("adminDeleteFeed");

    private final String value;

    Permission(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
