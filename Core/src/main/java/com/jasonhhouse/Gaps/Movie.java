package com.jasonhhouse.Gaps;/*
 * Copyright 2019 Jason H House
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public final class Movie implements Comparable<Movie> {

    private final String name;

    private final int year;

    private final String collection;

    private final int tvdbId;

    @Nullable
    private String imdbId;

    private Movie(int tvdbId, @Nullable String imdbId, String name, int year, String collection) {
        this.tvdbId = tvdbId;
        this.imdbId = imdbId;
        this.name = name;
        this.year = year;
        this.collection = collection;
    }

    Movie(String imdbId, String name, int year, String collection) {
        this(-1, imdbId, name, year, collection);
    }

    Movie(int tvdbId, String name, int year, String collection) {
        this(-1, null, name, year, collection);
    }

    Movie(String name, int year, String collection) {
        this(-1, name, year, collection);
    }

    public int getTvdbId() {
        return tvdbId;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getCollection() {
        return collection;
    }

    @Nullable
    public String getImdbId() {
        return imdbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year);
    }

    @Override
    public String toString() {
        return name + " (" + year + ")" + (StringUtils.isNotEmpty(collection) ? "  ---  collection='" + collection + '\'' : "");
    }

    public int compareTo(Movie o) {
        return getName().compareTo(o.getName());
    }

}
