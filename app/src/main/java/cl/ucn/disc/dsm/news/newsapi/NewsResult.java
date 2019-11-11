package cl.ucn.disc.dsm.news.newsapi;

import java.util.ArrayList;
import java.util.List;

public class NewsResult {

    public String status;
    public long totalResults;
    public List<Article> articles = new ArrayList<Article>();

}