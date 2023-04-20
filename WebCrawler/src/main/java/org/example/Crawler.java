package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class Crawler {

    HashSet<String> urlset;
    int maxDepth = 2;
    Crawler(){
        urlset = new HashSet<>();
    }

    public void getPageTextAndLinks(String url, int depth){
        if(urlset.contains(url)){
            return;
        }

        if(depth> maxDepth){
            return;
        }
        if(urlset.add(url)){
            System.out.println(url);
        }

        depth++;
        try {

            Document document = Jsoup.connect(url).timeout(5000).get();
//        Indexer work Start from here

            Indexer indexer = new Indexer(document, url);
            System.out.println(document.title());

            Elements availableLinksOnpages = document.select("a[href]");

            for (Element currentLink : availableLinksOnpages) {
                getPageTextAndLinks(currentLink.attr("abs:href"), depth);
            }
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }


    }
    public static void main(String[] args) {
       Crawler crawler = new Crawler();
       crawler.getPageTextAndLinks("https://www.javatpoint.com", 1);
    }
}