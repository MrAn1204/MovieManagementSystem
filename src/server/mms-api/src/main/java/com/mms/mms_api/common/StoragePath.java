package com.mms.mms_api.common;

public enum StoragePath {
    MOVIE_THUMBNAIL("movies/");

    private final String path;

    StoragePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
