package net.batchik.jd.crawler;

import com.google.common.collect.Sets;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponse;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.DefaultHttpRequestFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class WebCrawler {

    public static final String START_NODE = "https://en.wikipedia.org/wiki/Main_Page";

    public static List<String> getLinks(@Nonnull final String body) {
        return Jsoup.parse(body).body()
                .select("a")
                .stream()
                .map(elem -> elem.absUrl("href"))
                .filter(url -> url.length() > 0)
                .collect(Collectors.toList());
    }

    public static void crawl(final String rootNode) throws Exception {
        final int threadCount = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        final RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                .build();
        final HttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();

        final Queue<String> queue = new ConcurrentLinkedQueue<>();
        final Set<String> seen = Sets.newConcurrentHashSet();
        queue.add(rootNode);
        seen.add(rootNode);

        final CyclicBarrier barrier = new CyclicBarrier(threadCount);
        final CountDownLatch finishLatch = new CountDownLatch(threadCount);

        for (int i = 0 ; i < threadCount ; i++) {
            executorService.submit(() -> {
                try {
                    barrier.await();

                    while (true) {
                        @Nullable final String nextNode = queue.poll();
                        if (nextNode != null) {
                            System.out.printf("queue size: %d, seen: %d\n", queue.size(), seen.size());

                            final HttpUriRequest request = RequestBuilder.get(nextNode).build();
                            final HttpResponse response = client.execute(request);
                            final String output = EntityUtils.toString(response.getEntity());
                            final List<String> links = getLinks(output);

                            for (final String link : links) {
                                if (seen.add(link)) {
                                    queue.offer(link);
                                }
                            }
                        } else {
                            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                        }
                    }
                } catch (final Exception ex) {
                    System.err.println("exception calling links");
                    ex.printStackTrace(System.err);
                } finally {
                    finishLatch.countDown();
                }
            });
        }


        finishLatch.await();
        executorService.shutdownNow();

    }
}
