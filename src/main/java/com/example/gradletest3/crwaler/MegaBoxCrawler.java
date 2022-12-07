package com.example.gradletest3.crwaler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;

public class MegaBoxCrawler implements Crawler {

    @Override
    public void Crawling(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();

        }

        Elements element = doc.select("p.no-result-count");
        System.out.println("=== ==== ==== ===");
        Iterator<Element> ie1 = element.select("strong#totCnt").iterator();
//        Iterator<Element> ie1 = element.iterator();

        while (ie1.hasNext()) {
            System.out.println(ie1.next().text());

        }
        System.out.println("=======================================");

    }
}
