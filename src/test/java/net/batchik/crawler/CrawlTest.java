package net.batchik.crawler;

import net.batchik.jd.crawler.WebCrawler;
import org.junit.Test;

import java.io.IOException;

public class CrawlTest {

    @Test
    public void mainTest() throws Exception {
        WebCrawler.crawl(WebCrawler.START_NODE);
    }
}
