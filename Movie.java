// -----------------------------------------------------------------
// Assignment 2
// Written by: Noah Afriat (40276193) and Robert Mounsef (40279248)
// -----------------------------------------------------------------

/** 
 * Noah Afriat (40276193) and Robert Mounsef (40279248)
 * COMP249
 * Assignment 2 
 * March 27, 2024
 */

import java.io.Serializable;

/**
 * The Movie class represents a movie object with various attributes 
 * such as year, title, duration, genre, rating, score, director, and actors.
 * It implements the Serializable interface to support serialization.
 */
public class Movie implements Serializable {
	   
	/**
	 * The year the movie was released.
	 */
	private int year;

	/**
	 * The title of the movie.
	 */
	private String title;

	/**
	 * The duration of the movie in minutes.
	 */
	private int duration;

	/**
	 * The genre of the movie.
	 */
	private String genre;

	/**
	 * The rating of the movie (e.g., PG-13, R).
	 */
	private String rating;

	/**
	 * The score of the movie.
	 */
	private double score;

	/**
	 * The director of the movie.
	 */
	private String director;

	/**
	 * The first actor/actress starring in the movie.
	 */
	private String actor1;

	/**
	 * The second actor/actress starring in the movie.
	 */
	private String actor2;

	/**
	 * The third actor/actress starring in the movie.
	 */
	private String actor3;


    /**
     * Constructs a Movie object with the specified attributes.
     *
     * @param year     The year the movie was released.
     * @param title    The title of the movie.
     * @param duration The duration of the movie in minutes.
     * @param genre    The genre of the movie.
     * @param rating   The rating of the movie.
     * @param score    The score of the movie.
     * @param director The director of the movie.
     * @param actor1   The first actor of the movie.
     * @param actor2   The second actor of the movie.
     * @param actor3   The third actor of the movie.
     */
    public Movie(int year, String title, int duration, String genre, String rating, double score, String director, String actor1, String actor2, String actor3) {
        this.year = year;
        this.title = title;
        this.duration = duration;
        this.genre = genre;
        this.rating = rating;
        this.score = score;
        this.director = director;
        this.actor1 = actor1;
        this.actor2 = actor2;
        this.actor3 = actor3;
    }

    /**
     * Retrieves the year of the movie.
     *
     * @return The year of the movie.
     */
    public int getYear() {
        return year;
    }

    /**
     * Retrieves the title of the movie.
     *
     * @return The title of the movie.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the duration of the movie.
     *
     * @return The duration of the movie in minutes.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Retrieves the genre of the movie.
     *
     * @return The genre of the movie.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Retrieves the rating of the movie.
     *
     * @return The rating of the movie.
     */
    public String getRating() {
        return rating;
    }

    /**
     * Retrieves the score of the movie.
     *
     * @return The score of the movie.
     */
    public double getScore() {
        return score;
    }

    /**
     * Retrieves the director of the movie.
     *
     * @return The director of the movie.
     */
    public String getDirector() {
        return director;
    }

    /**
     * Retrieves the first actor of the movie.
     *
     * @return The first actor of the movie.
     */
    public String getActor1() {
        return actor1;
    }

    /**
     * Retrieves the second actor of the movie.
     *
     * @return The second actor of the movie.
     */
    public String getActor2() {
        return actor2;
    }

    /**
     * Retrieves the third actor of the movie.
     *
     * @return The third actor of the movie.
     */
    public String getActor3() {
        return actor3;
    }

    /**
     * Sets the year of the movie.
     *
     * @param year The year of the movie.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Sets the title of the movie.
     *
     * @param title The title of the movie.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the duration of the movie.
     *
     * @param duration The duration of the movie in minutes.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Sets the genre of the movie.
     *
     * @param genre The genre of the movie.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets the rating of the movie.
     *
     * @param rating The rating of the movie.
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * Sets the score of the movie.
     *
     * @param score The score of the movie.
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Sets the director of the movie.
     *
     * @param director The director of the movie.
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Sets the first actor of the movie.
     *
     * @param actor1 The first actor of the movie.
     */
    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    /**
     * Sets the second actor of the movie.
     *
     * @param actor2 The second actor of the movie.
     */
    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    /**
     * Sets the third actor of the movie.
     *
     * @param actor3 The third actor of the movie.
     */
    public void setActor3(String actor3) {
        this.actor3 = actor3;
    }

    /**
     * Checks if two Movie objects are equal.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Movie movie = (Movie) obj;
        return (year == movie.year &&
                duration == movie.duration &&
                rating.equals(movie.rating) &&
                score == movie.score &&
                title.equals(movie.title) &&
                genre.equals(movie.genre) &&
                director.equals(movie.director) &&
                actor1.equals(movie.actor1) &&
                actor2.equals(movie.actor2) &&
                actor3.equals(movie.actor3));
    }

    /**
     * Returns a string representation of the Movie object.
     *
     * @return A string representation of the Movie object.
     */
    @Override
    public String toString() {
        return "Movie{" +
                "year=" + year +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", genre='" + genre + '\'' +
                ", rating='" + rating + '\'' +
                ", score=" + score +
                ", director='" + director + '\'' +
                ", actor1='" + actor1 + '\'' +
                ", actor2='" + actor2 + '\'' +
                ", actor3='" + actor3 + '\'' +
                '}';
    }
}
