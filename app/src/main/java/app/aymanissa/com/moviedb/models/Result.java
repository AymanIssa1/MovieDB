package app.aymanissa.com.moviedb.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman on 3/2/2018.
 */

public class Result {

    public Integer page;
    public Integer total_results;
    public Integer total_pages;
    public ArrayList<Movie> results = new ArrayList<>();

}
