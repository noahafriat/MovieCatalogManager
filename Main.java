// -----------------------------------------------------------------
// Assignment 2
// Written by: Noah Afriat (40276193) and Robert Mounsef (40279248)
// -----------------------------------------------------------------

/**
 * Noah Afriat (40276193) and Robert Mounsef(40279248)
 * COMP249
 * Assignment 2 
 * March 27, 2024
 */

import java.io.*;
import java.util.Scanner;


/**
 * The Main class is the entry point for the movie record system program. 
 * It provides functionality to read movie records from CSV files, validate them,
 * partition them into separate CSV files based on genre, serialize and deserialize them,
 * and provide an interactive navigation interface for exploring movie records.
 */
public class Main {
    private static String filesFolder = "A2_files";
    private static String[] validGenres = {"musical", "comedy", "animation", "adventure", "drama", "crime", "biography", "horror", "action", "documentary", "fantasy", "mystery", "sci-fi", "family", "western", "romance", "thriller"};
    private static String[] validRatings = {"PG", "Unrated", "G", "R", "PG-13", "NC-17"};
    private static Movie[][] allMovies;
    
    /**
     * Default constructor for the Main class.
     */
    public Main() {}
    
    /**
     * The main method is the entry point of the movie record system program.
     * It orchestrates the execution of different parts of the program including
     * partitioning, serialization, deserialization, and interactive navigation.
     *
     * @param args Command-line arguments (not used in this program).
     * @throws IOException If an I/O exception occurs while reading or writing files.
     */
    public static void main(String[] args) throws IOException  {
        
        // create and write to the part1_manifest file
        String part1_manifest = "part1_manifest.txt";
        
        // part 2’s manifest file
        String part2_manifest = do_part1(part1_manifest);   // partition
        
        // part 3’s manifest file
        String part3_manifest = do_part2(part2_manifest);   // serialize
                                do_part3(part3_manifest);   // deserialize
        
        interactivelyNavigateMovies();                       // Navigate
        
        return;
    }
    
    /**
     * Executes part 1 of the program, which involves reading movie records from CSV files,
     * validating them, and partitioning them into separate CSV files based on genre.
     *
     * @param part1_manifest The filename of the manifest file containing references to CSV files.
     * @return The filename of the manifest file generated for part 2.
     */
    public static String do_part1(String part1_manifest) {
        String movieYearFileName;
        int counter = 1;

        initializeFiles();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filesFolder + "//" + part1_manifest));
            while((movieYearFileName = reader.readLine()) != null) {
                try {
                    BufferedReader reader2 = new BufferedReader(new FileReader(filesFolder + "//" + movieYearFileName));
                    String record;
                    while((record = reader2.readLine()) != null) {
                        try {
                            validateRecord(record, movieYearFileName, counter);
                        } catch (MissingFieldsException e) {
                            // System.out.println("Missing fields exception found"); For testing

                        } catch (ExcessFieldsException e) {
                            // System.out.println("Excess fields exception found"); For testing

                        } catch (MissingQuotesException e) {
                            // System.out.println("Missing quotes exception found"); For testing

                        } catch (BadYearException e) {
                            // System.out.println("Bad year exception found"); For testing

                        } catch (BadDurationException e) {
                            // System.out.println("Bad duration exception found"); For testing

                        } catch (BadScoreException e) {
                            // System.out.println("Bad score exception found"); For testing

                        } catch (BadRatingException e) {
                            // System.out.println("Bad rating exception found"); For testing

                        } catch (BadGenreException e) {
                            // System.out.println("Bad genre exception found"); For testing

                        } catch (BadTitleException e) {
                            // System.out.println("Bad title exception found"); For testing

                        } catch (BadNameException e) {
                            // System.out.println("Bad name exception found"); For testing
                        }
                        counter++;  
                    }
                }
                catch (FileNotFoundException e) {
                   // System.out.println(movieYearFileName +  " not found."); For testing
                } catch (IOException e) {
                   // System.out.println("Cannot read from " + movieYearFileName); For testing
                }
                
            }
        } catch (FileNotFoundException e) {
            // System.out.println("Manifest file not found."); For testing
            return "";
        } catch (IOException e) {
            // System.out.println("Cannot read from file."); For testing
        }
        
        String filePath = filesFolder + "//part2_manifest.txt";
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
            for (String genre : validGenres) {
                bufferedWriter.write(genre+".csv\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Could not write to part_2_manifest.txt");
        } 
        return "part2_manifest.txt";
    } 
	
    /**
     * Executes part 2 of the program, which involves reading a manifest file containing references
     * to CSV files partitioned by genre, loading movies from each CSV file, serializing them into
     * binary files, and generating a manifest file for part 3.
     *
     * @param part2_manifest The filename of the manifest file containing references to CSV files.
     * @return The filename of the manifest file generated for part 3.
     * @throws IOException If an I/O exception occurs while reading or writing files.
     */
    public static String do_part2(String part2_manifest) throws IOException {
        try (BufferedReader manifestReader = new BufferedReader(new FileReader(filesFolder + "//" + part2_manifest))) {
            String line;
            while ((line = manifestReader.readLine()) != null) {
                String csvFileName = line.trim();
                // Load movies from each CSV file and serialize them into a binary file
                serializeMoviesForGenre(csvFileName);
            }
        } catch (IOException e) {
            System.err.println("Error reading manifest file: " + e.getMessage());
        }
        
        String filePath = filesFolder + "//part3_manifest.txt";
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
            for (String genre : validGenres) {
                bufferedWriter.write(genre+".ser\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Could not write to part_3_manifest.txt");
        } 
        
        return "part3_manifest.txt"; 
    }

    
    /**
     * Executes part 3 of the program, which involves reading a manifest file containing references
     * to serialized movie files partitioned by genre, deserializing these movies, and displaying them.
     *
     * @param part3_manifest The filename of the manifest file containing references to serialized movie files.
     */
    public static void do_part3(String part3_manifest) {
        try (BufferedReader manifestReader = new BufferedReader(new FileReader(filesFolder + "//" + part3_manifest))) {
            int genreCount = countLines(filesFolder + "//" + part3_manifest);
            allMovies = new Movie[genreCount][];
            String line;
            int genreIndex = 0;
            while ((line = manifestReader.readLine()) != null) {
                String serFileName = line.trim();
                // Deserialize movies for each genre and add them to the allMovies array
                allMovies[genreIndex++] = deserializeMoviesForGenre(serFileName);
            }
            System.out.println("\n\n");
            
            for (int i = 0; i < allMovies.length; i++) {
                // System.out.println("Genre: " + validGenres[i] + "\n"); For testing
                if (allMovies[i] == null || allMovies[i].length == 0) {
                    // System.out.println("There are no " + validGenres[i] + " records."); For testing
                } else {
					// for (Movie movie : allMovies[i])
					// System.out.println(movie);    For testing
				}
                
                // System.out.println("\n\n"); For testing
            }
        } catch (IOException e) {
            System.err.println("Error reading manifest file: " + e.getMessage());
        }
    }

    /**
     * Validates a record from the input file to ensure it meets the syntax and semantic requirements.
     *
     * @param record             		The record to be validated.
     * @param movieYearFileName  		The filename of the movie year file containing the record.
     * @param position           		The position of the record in the file.
     * @throws MissingFieldsException   If any required field is missing in the record.
     * @throws ExcessFieldsException    If there are extra fields in the record.
     * @throws MissingQuotesException   If quotes are missing where necessary in the record.
     * @throws BadYearException         If the year in the record is invalid.
     * @throws BadDurationException     If the duration in the record is invalid.
     * @throws BadScoreException        If the score in the record is invalid.
     * @throws BadRatingException       If the rating in the record is invalid.
     * @throws BadGenreException        If the genre in the record is invalid.
     * @throws BadTitleException        If the title in the record is invalid.
     * @throws BadNameException         If any of the names (director, actors) in the record is invalid.
     */
    public static void validateRecord(String record, String movieYearFileName, int position) throws MissingFieldsException, 
    ExcessFieldsException, MissingQuotesException, BadYearException, BadDurationException, 
    BadScoreException, BadRatingException, BadGenreException, BadTitleException, BadNameException {
        
        // Checking for missing quotes
        if (!isMatchedQuotes(record)) {
            String error = "Missing Quotes";
            String errorType = "Syntax";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            throw new MissingQuotesException();
        }
        
        String[] features = record.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); 
    
        // Checking for missing or excess fields
        if (features.length < 10) {
            String error = "Missing field(s)";
            String errorType = "Syntax";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            throw new MissingFieldsException();
        } else if (features.length > 10) {
            String error = "Excess field(s)";
            String errorType = "Syntax";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            throw new ExcessFieldsException();
        }
        
        // Checking for semantic errors
        
        // Validating year
        int year;
        if (features[0].trim().length() > 0) {
            try {
                year = Integer.parseInt(features[0].trim());
                if (year < 1990 || year > 1999) {
                    String error = "Invalid year";
                    String errorType = "Semantic";
                    writeToBadRecords(error, errorType, record, movieYearFileName, position);
                    return;
                };
            } catch (NumberFormatException e) {
                String error = "Invalid year";
                String errorType = "Semantic";
                writeToBadRecords(error, errorType, record, movieYearFileName, position);
                return;
            }
        } else {
            String error = "Missing year";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;        
        }
        
        // Validating movie title
        String movieTitle;
        if (features[1].trim().length() > 0) {
            movieTitle = features[1].trim();
        } else {
            String error = "Missing title";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;
        }
        
        // Validating duration
        int duration;
        if (features[2].trim().length() > 0) {
                try {
                    duration = Integer.parseInt(features[2].trim());
                    if (duration < 30 || duration > 300) {
                        String error = "Invalid duration";
                        String errorType = "Semantic";
                        writeToBadRecords(error, errorType, record, movieYearFileName, position);
                        return;
                    };
                } catch (NumberFormatException e) {
                    String error = "Invalid duration";
                    String errorType = "Semantic";
                    writeToBadRecords(error, errorType, record, movieYearFileName, position);
                    return;
                }
        } else {    
            String error = "Missing duration";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;
        }
        
        // Validating genre
        String genre = "";
        if (features[3].trim().length() > 0) {
            genre = features[3].trim();
            
            boolean isValidGenre = false;
            for (String validGenre : validGenres) {
                if (genre.equalsIgnoreCase(validGenre)) {
                    isValidGenre = true;
                    break;
                }
            }
            if (!isValidGenre) {
                String error = "Invalid genre";
                String errorType = "Semantic";
                writeToBadRecords(error, errorType, record, movieYearFileName, position);
                return;
            }
        } else {
            String error = "Missing genre";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;
        }       
        
        // Validating rating
        String rating = "";
        if (features[4].trim().length() > 0) {
            rating = features[4].trim();
            
            boolean isValidRating = false;
            for (String validRating : validRatings) {
                if (rating.equalsIgnoreCase(validRating)) {
                    isValidRating = true;
                    break;
                }
            }   
            if (!isValidRating) {
                String error = "Invalid rating";
                String errorType = "Semantic";
                writeToBadRecords(error, errorType, record, movieYearFileName, position);
                return;
            }
        } else {
            String error = "Missing rating";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;
        }       
        
        // Validating score
        double score;
        if (features[5].trim().length() > 0) {
            try {
                score = Double.parseDouble(features[5].trim());
                if (score < 0 || score > 10) {
                    String error = "Invalid score";
                    String errorType = "Semantic";
                    writeToBadRecords(error, errorType, record, movieYearFileName, position);
                    return;
                }
            } catch (NumberFormatException e) {
                String error = "Invalid score";
                String errorType = "Semantic";
                writeToBadRecords(error, errorType, record, movieYearFileName, position);
                return;
            }
        } else {    
            String error = "Missing score";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;
        }       
        
        // Validating director
        String director;
        if (features[6].trim().length() > 0) {
            director = features[6].trim();
        } else {
            String error = "Missing name(s)";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;
        }   
        
        // Validating actors
        String actor1;
        if (features[7].trim().length() > 0) {
            actor1 = features[7].trim();
        } else {
            String error = "Missing name(s)";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;        }       
        
        String actor2;
        if (features[8].trim().length() > 0) {
            actor2 = features[8].trim();
        } else {
            String error = "Missing name(s)";
            String errorType = "Semantic";
            writeToBadRecords(error, errorType, record, movieYearFileName, position);
            return;        }            
        
        String actor3;
        if (features[9].trim().length() > 0) {
            actor3 = features[9].trim();
        } else {
            String error = "Missing name(s)";
            String errorType = "Semantic";
			writeToBadRecords(error, errorType, record, movieYearFileName, position);
			return;		}
		
		
		// When here, it means our record passed all our checks (syntax and semantic)
		writeRecordToRespectiveFile(genre, record);
	}
	
	/**
	 * Checks if the number of quotation marks in the given string is even, indicating that all quotes are matched.
	 *
	 * @param str The string to be checked.
	 * @return True if the number of quotes is even, indicating matched quotes, otherwise false.
	 */
	public static boolean isMatchedQuotes(String str) {
		return (str.length() - str.replace("\"", "").length()) % 2 == 0;
	}
	
	/**
	 * Initializes the necessary files for storing bad movie records and records for each genre.
	 * If the files already exist, they are truncated.
	 */
	public static void initializeFiles() {
		
		// initialize bad movie records file
		String filePath = filesFolder + "//bad_movie_records.txt";
		
		try {
			// initialize the file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
            bufferedWriter.close();
		}
		catch (IOException e) {
            // Handle IO exceptions
			System.out.println("Could not write to bad_movie_records.txt");

        }
		
		// initialize all files for all genres
		for (String genre : validGenres) {
			try {
				filePath = filesFolder + "//" + genre + ".csv";
				
				// initialize the file
	            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, false));
	            bufferedWriter.close();
			}
			catch (IOException e) {
	            // Handle IO exceptions
				System.out.println("Could not write to " + genre + ".csv");
	        }
		}
	}
	
	/**
	 * Writes a movie record to the file corresponding to its genre.
	 *
	 * @param genre  The genre of the movie.
	 * @param record The movie record to be written.
	 */
	public static void writeRecordToRespectiveFile(String genre, String record) {
		String filePath = filesFolder + "//" + genre + ".csv";

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));

            // Write content to the file
            bufferedWriter.write(record + "\n");

            // Close the writer
            bufferedWriter.close();

        } catch (IOException e) {
            // Handle IO exceptions
        	System.out.println("Could not write to " + genre + ".csv");
        }
	}
	
	/**
	 * Writes information about a bad movie record to the file storing bad records.
	 *
	 * @param error             The description of the error encountered.
	 * @param errorType         The type of error encountered.
	 * @param record            The movie record that caused the error.
	 * @param movieYearFileName The name of the input file where the error occurred.
	 * @param counter           The position of the record in the input file.
	 */
	public static void writeToBadRecords(String error, String errorType, String record, String movieYearFileName, int counter) {
		 String filePath = filesFolder + "//bad_movie_records.txt";

	        try {
	            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));

	            // Write content to the file
	            bufferedWriter.write(
	            "Error: " + error + "\n" + 
	    	    "Error Type : " + errorType + "\n" + 
	            "Movie Record :" + record + "\n" +
	    	    "Input File: " + movieYearFileName + "\n" +
	    	    "Record's Position: " + counter +"\n\n");

	            // Close the writer
	            bufferedWriter.close();

	        } catch (IOException e) {
	            // Handle IO exceptions
	        	System.out.println("Could not write to bad_movie_records.txt");
	        }
	}
	
	/**
	 * Serializes an array of Movie objects loaded from a CSV file and writes it to a binary file.
	 *
	 * @param csvFileName The name of the CSV file containing movie data.
	 */
	public static void serializeMoviesForGenre(String csvFileName) {
	    String genre = csvFileName.substring(0, csvFileName.lastIndexOf('.'));
	    Movie[] movieArray = loadMoviesFromCSV(csvFileName);

	    if (movieArray.length == 0) {
	        serializeEmptyArray(csvFileName);
	        return;
	    }

	    String serializedFileName = genre + ".ser";

	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filesFolder + "//" + serializedFileName))) {
	        oos.writeObject(movieArray);
	        // System.out.println("Movies for genre '" + genre + "' serialized and written to file: " + serializedFileName); For testing
	    } catch (IOException e) {
	        // System.err.println("Error while serializing movies for genre '" + genre + "': " + e.getMessage()); For testing
	    }
	}
	
	/**
	 * Loads movie data from a CSV file into an array of Movie objects.
	 *
	 * @param csvFileName The name of the CSV file containing movie data.
	 * @return An array of Movie objects loaded from the CSV file.
	 */
	public static Movie[] loadMoviesFromCSV(String csvFileName) {
	    final int INITIAL_CAPACITY = 100; // Initial capacity of the movie array
	    Movie[] movieArray = new Movie[INITIAL_CAPACITY];
	    int size = 0; // Number of movies loaded from CSV

	    try (BufferedReader csvReader = new BufferedReader(new FileReader(filesFolder + "//" + csvFileName))) {
	        String line;
	        while ((line = csvReader.readLine()) != null) {
	            String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
	            int year = Integer.parseInt(data[0].trim());
	            String title = data[1].trim();
	            int duration = Integer.parseInt(data[2].trim());
	            String genre = data[3].trim();
	            String rating = data[4].trim();
	            double score = Double.parseDouble(data[5].trim());
	            String director = data[6].trim();
	            String actor1 = data[7].trim();
	            String actor2 = data[8].trim();
	            String actor3 = data[9].trim();

	            // Create a new Movie object for each line and add it to the array
	            movieArray[size++] = new Movie(year, title, duration, genre, rating, score, director, actor1, actor2, actor3);

	            if (size >= movieArray.length) {
	                movieArray = resizeArray(movieArray, movieArray.length * 2);
	            }
	        }
	    } catch (IOException | NumberFormatException e) {
	        System.err.println("Error reading CSV file '" + csvFileName + "': " + e.getMessage());
	    }
	    
	    
	    // Trim the array to actual size
	    movieArray = resizeArray(movieArray, size);
	    return movieArray;
	}
	
	/**
	 * Resizes an array of Movie objects to the specified size.
	 *
	 * @param array   The original array to be resized.
	 * @param newSize The new size of the array.
	 * @return The resized array.
	 */
	public static Movie[] resizeArray(Movie[] array, int newSize) {
	    Movie[] newArray = new Movie[newSize];
	    System.arraycopy(array, 0, newArray, 0, Math.min(array.length, newSize));
	    return newArray;
	}
	
	/**
	 * Serializes an empty array of Movie objects and writes it to a binary file.
	 *
	 * @param csvFileName The name of the CSV file indicating the genre.
	 */
	public static void serializeEmptyArray(String csvFileName) {
	    String genre = csvFileName.substring(0, csvFileName.lastIndexOf('.'));
	    String serializedFileName = genre + ".ser";
	    
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filesFolder + "//" + serializedFileName))) {
	        oos.writeObject(new Movie[0]); // Serialize an empty array
	        // System.out.println("Empty movie array serialized and written to file: " + serializedFileName); For testing
	    } catch (IOException e) {
	        // System.err.println("Error while serializing empty movie array for genre '" + genre + "': " + e.getMessage()); For testing
	    }
	}
	
	/**
	 * Deserializes an array of Movie objects from a binary file.
	 *
	 * @param serFileName The name of the serialized file containing movie data.
	 * @return An array of Movie objects deserialized from the file.
	 */
	public static Movie[] deserializeMoviesForGenre(String serFileName) {
        Movie[] genreMovies = new Movie[0];
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filesFolder + "//" + serFileName))) {
            // Read the array of Movie objects from the serialized file
            genreMovies = (Movie[]) ois.readObject();
            // System.out.println("Movies deserialized from file: " + serFileName); For testing
        } catch (IOException | ClassNotFoundException e) {
            // System.err.println("Error while deserializing movies for genre '" + serFileName + "': " + e.getMessage()); For testing
        }
        return genreMovies;
    }
    
	/**
	 * Counts the number of lines in a file.
	 *
	 * @param filename The name of the file to count lines from.
	 * @return The number of lines in the file.
	 * @throws IOException If an I/O error occurs.
	 */
    public static int countLines(String filename) throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.readLine() != null) count++;
        }
        return count;
    }
	
    /**
     * Allows the user to interactively navigate through the movie records system.
     * The method displays a main menu where the user can select options to navigate movie arrays or exit the system.
     * If the user chooses to navigate a movie array, they are presented with a genre submenu where they can select a specific genre to navigate.
     * After selecting a genre, the user can navigate through the movies of that genre, displaying a specified number of movies at a time.
     * Navigation can be done by entering positive or negative integers to move forward or backward through the movie list.
     * The method continuously prompts the user for input until they choose to exit the system.
     */

 // Interactively navigates through movie records based on user input
    public static void interactivelyNavigateMovies() {
        Scanner scanner = new Scanner(System.in); // Scanner for user input

        String menuSelection; // User's main menu selection
        String genreSelection; // User's genre selection
        int movieSelection; // User's movie selection
        int displayNumber = 1; // Currently displayed genre index
        int displayCount; // Number of movies to display
        String s; // User input as string
        int n; // Numeric input
        int index = 0; // Index for navigating movies
        int emptyGenre = 0; // Counter for empty genres

        System.out.println("Welcome to the movie record system:\n");

        // Main loop for interacting with the user
        while (true) {
            System.out.println("-----------------------------");
            System.out.println("      Main Menu             ");
            System.out.println("-----------------------------");
            System.out.println(" s  Select a movie array to navigate");

            // Display genre navigation options
            if (allMovies[displayNumber - 1] == null) {
                System.out.println(" n  Navigate " + validGenres[displayNumber - 1] + " movies (+" + emptyGenre + " records)");
            } else {
                System.out.println(" n  Navigate " + validGenres[displayNumber - 1] + " movies (" + allMovies[displayNumber - 1].length + " records)");
            }

            System.out.println(" x  Exit");
            System.out.println("-----------------------------");
            System.out.println();
            System.out.print("Enter Your Choice: ");

            menuSelection = scanner.next(); // Read user's choice

            if (menuSelection.equals("s")) {
                System.out.println("------------------------------");
                System.out.println("    Genre Sub-Menu           ");
                System.out.println("------------------------------");

                // Display genre options
                for (int i = 0; i < validGenres.length; i++) {
                    int tabs = (validGenres[i] != null && validGenres[i].length() <= 5) ? 3 : 2;
                    String tabString = "\t".repeat(tabs);

                    if (validGenres[i] == null) {
                        System.out.println((i + 1) + " " + validGenres[i] + tabString + "movies (" + emptyGenre + " records)");
                    } else {
                        System.out.println((i + 1) + " " + validGenres[i] + tabString + "(" + allMovies[i].length + " movies)");
                    }
                }

                System.out.println(validGenres.length + 1 + " Exit");
                System.out.println("------------------------------");
                System.out.print("Enter Your Choice: ");
                genreSelection = scanner.next();

                if (isNumeric(genreSelection)) {
                    movieSelection = Integer.parseInt(genreSelection);
                    if (movieSelection <= 17 && movieSelection >= 1) {
                        displayNumber = movieSelection;
                    } else if (movieSelection == 18) ;

                    else {
                        System.out.println("Invalid Option. That wasn't one of the choices");
                    }
                } else {
                    System.out.println("Invalid Option. That wasn't one of the choices");
                }
            } else if (menuSelection.equals("n")) {
                // Navigate through movies
                if (allMovies[displayNumber - 1] == null) {
                    System.out.println("Navigating " + validGenres[displayNumber - 1] + " movies (+" + emptyGenre + " records)");
                } else {
                    System.out.println("Navigating " + validGenres[displayNumber - 1] + " movies (" + allMovies[displayNumber - 1].length + ")");
                }

                while (true) {
                    System.out.print("Enter Your Choice: ");
                    n = 0;
                    s = scanner.next();
                    if (isNumeric(s))
                        n = Integer.parseInt(s);

                    else {
                        System.out.println("Invalid entry\n");
                        continue;
                    }

                    if (allMovies[displayNumber - 1].length == 0) {
                        System.out.println("There are no movies of this genre.");
                        break;
                    }

                    if (n < 0) {
                        // Move backwards through movies
                        displayCount = n * -1;

                        if (index - displayCount < 0) {
                            System.out.println("\nBOF has been reached\n");
                            for (int i = 0; i <= index; i++) {
                                System.out.println(allMovies[displayNumber - 1][i]);
                            }
                            index = 0;
                        } else {
                            for (int i = index - displayCount + 1; i <= index; i++) {
                                System.out.println(allMovies[displayNumber - 1][i]);
                            }
                            index = index - displayCount + 1;
                        }

                    } else if (n > 0) {
                        // Move forwards through movies
                        displayCount = n;

                        if (index + displayCount > allMovies[displayNumber - 1].length - 1) {
                            for (int i = index; i < allMovies[displayNumber - 1].length; i++) {
                                System.out.println(allMovies[displayNumber - 1][i]);
                            }
                            System.out.println("EOF has been reached\n");
                            index = allMovies[displayNumber - 1].length - 1;
                        } else {
                            for (int i = index; i < index + displayCount; i++) {
                                System.out.println(allMovies[displayNumber - 1][i]);
                            }
                            index += displayCount - 1;
                        }
                    } else break;

                }
            } else if (menuSelection.equalsIgnoreCase("x")) {
                // Exit the program
                System.out.println("\nThank you for using the movie record system, goodbye!");
                scanner.close();
                System.exit(0);
            } else {
                System.out.println("Invalid choice, try again.");
            }
            System.out.println();
        }
    }

    /**
     * Checks if a given string represents a numeric value.
     * 
     * @param s the string to be checked
     * @return true if the string represents a numeric value, false otherwise
     */
    public static boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c) && c != '-') {
                return false;
            }
            if (c == '-' && i != 0) {
                return false;
            }
        }
        return true;
    }
}