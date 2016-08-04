package kz.soz.tatoeba;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * Created by Abylay.Sabirgaliyev
 * Created: 15.06.2016 16:07
 * Copyright � LLP JazzSoft
 */
public class Test {

    public static void main(String[] args) throws Exception {
        fixSSL();
        Test test = new Test();
        final DataAdapter dataAdapter = new DataAdapter();
        final BlockingQueue<SentenceTranslation> sentenceTranslationQueue = new LinkedBlockingQueue<>(30);

        String from = "eng";
        String to = "tur";

        new Thread(() -> {
            while (true) {
                List<SentenceTranslation> translations = dataAdapter.fetchRandomTen(from, to);
                translations.forEach(e -> {
                    try {
                        sentenceTranslationQueue.put(e);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                });
            }
        }).start();

        new Thread(() -> {
            while (true) {
                List<SentenceTranslation> translations = new ArrayList<SentenceTranslation>();
                while (translations.size() < 10) {
                    try {
                        translations.add(sentenceTranslationQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int variantsCount = 4;
                translations.forEach(translation -> {
                    System.out.println(translation.getSentence(from));
                    List<SentenceTranslation> variants = sentenceTranslationQueue.stream().filter(s -> s != translation).collect(Collectors.toList());
                    Collections.shuffle(variants);
                    variants = variants.subList(0, variants.size() >= variantsCount - 1 ? variantsCount - 1 : variants.size());
                    variants.add(translation);
                    Collections.shuffle(variants);
                    for (int i = 0; i < variants.size(); i++) {
                        System.out.println((i + 1) + ". " + variants.get(i).getSentence(to));
                    }
                    Scanner s = new Scanner(System.in);
                    String str = s.nextLine();
                    if (variants.get(Integer.valueOf(str) - 1) == translation) {
                        System.out.println("Right");
                    } else {
                        System.out.println("Wrong. Right answer is: " + translation.getSentence(to));
                    }
                    System.out.println();
                });
            }
        }).start();
    }

    private static void fixSSL() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    /*
     * end of the fix
     */
    }
}
