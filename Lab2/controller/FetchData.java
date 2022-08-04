package controller;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.io.IOException;
import model.CurrencyEntity;

public class FetchData {
    private static String LastUpdate = LocalDateTime.now().toString();
    private static ArrayList<String> allShortHand = new ArrayList<>();
    private static ArrayList<String> shortHandList = new ArrayList<>();
    private static HashMap<String, ArrayList<CurrencyEntity>> cache = new HashMap<>();
    private static final String apiKey = "DdQw4w9WgXcQ";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void getAllShorthands() {
        String url_str = String.format("https://free.currconv.com/api/v7/currencies?apiKey=%s", apiKey);
        String retrievedJson = null;
        try {
            retrievedJson = IOUtils.toString(new URL(url_str), Charset.defaultCharset());
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        JSONObject jsonOBJ = new JSONObject(retrievedJson).getJSONObject("results");
        Iterator<String> keysToCopyIterator = jsonOBJ.keys();
        while (keysToCopyIterator.hasNext()) {
            String key = (String) keysToCopyIterator.next();
            shortHandList.add(key);
        }
    }

    public static void refreshData() {
        LastUpdate = LocalDateTime.now().toString();
        allShortHand.forEach((src) -> {
            ArrayList<CurrencyEntity> histList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                String dateEnd = LocalDate.now().minusDays(7 * i).format(formatter);
                String dateStart = LocalDate.now().minusDays(7 + (7 * i)).format(formatter);
                String url_str = String.format(
                        "https://free.currconv.com/api/v7/convert?q=%s_THB&compact=ultra&date=%s&endDate=%s&apiKey=%s",
                        src,
                        dateStart, dateEnd, apiKey);
                String retrievedJson = null;
                try {
                    retrievedJson = IOUtils.toString(new URL(url_str), Charset.defaultCharset());
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                JSONObject jsonOBJ = new JSONObject(retrievedJson).getJSONObject(String.format("%s_THB", src));
                Iterator<String> keysToCopyIterator = jsonOBJ.keys();
                while (keysToCopyIterator.hasNext()) {
                    String key = (String) keysToCopyIterator.next();
                    Double rate = Double.parseDouble(jsonOBJ.get(key).toString());
                    histList.add(new CurrencyEntity(rate, key));
                }
            }
            histList.sort(new Comparator<CurrencyEntity>() {
                @Override
                public int compare(CurrencyEntity o1, CurrencyEntity o2) {
                    return o1.getTimestamp().compareTo(o2.getTimestamp());
                }
            });
            cache.put(src, histList);
        });
    };

    public static void addData(String src) {
        LastUpdate = LocalDateTime.now().toString();
        ArrayList<CurrencyEntity> histList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String dateEnd = LocalDate.now().minusDays(7 * i).format(formatter);
            String dateStart = LocalDate.now().minusDays(7 + (7 * i)).format(formatter);
            String url_str = String.format(
                    "https://free.currconv.com/api/v7/convert?q=%s_THB&compact=ultra&date=%s&endDate=%s&apiKey=%s",
                    src,
                    dateStart, dateEnd, apiKey);
            String retrievedJson = null;
            try {
                retrievedJson = IOUtils.toString(new URL(url_str), Charset.defaultCharset());
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            JSONObject jsonOBJ = new JSONObject(retrievedJson).getJSONObject(String.format("%s_THB", src));
            Iterator<String> keysToCopyIterator = jsonOBJ.keys();
            while (keysToCopyIterator.hasNext()) {
                String key = (String) keysToCopyIterator.next();
                Double rate = Double.parseDouble(jsonOBJ.get(key).toString());
                histList.add(new CurrencyEntity(rate, key));
            }
            histList.sort(new Comparator<CurrencyEntity>() {
                @Override
                public int compare(CurrencyEntity o1, CurrencyEntity o2) {
                    return o1.getTimestamp().compareTo(o2.getTimestamp());
                }
            });
            cache.put(src, histList);
        }
        ;
    };

    public static ArrayList<CurrencyEntity> fetch_range(String src, int N) {
        src = src.toUpperCase();
        return new ArrayList<CurrencyEntity>(
                cache.get(src).subList(cache.get(src).size() - N - 1, cache.get(src).size()));
    }

    public static String getLastUpdate() {
        return LastUpdate;
    }

    public static boolean addToFetch(String str) {
        str = str.toUpperCase();
        if (shortHandList.contains(str) && !allShortHand.contains(str)) {
            allShortHand.add(str);
            addData(str);
            return true;
        }
        return false;
    }

    public static int remove(String str) {
        str = str.toUpperCase();
        int index = -1;
        if (allShortHand.contains(str)) {
            index = allShortHand.indexOf(str);
            allShortHand.remove(str);
            cache.remove(str);
        }
        return index;
    }
}
