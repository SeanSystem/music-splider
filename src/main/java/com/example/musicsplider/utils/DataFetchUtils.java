package com.example.musicsplider.utils;

import com.example.musicsplider.entity.MusicData;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 爬取音乐数据的工具类
 *
 * @author Sean
 * 2019/08/26
 */
public class DataFetchUtils {

    private static final String MUSIC_URL = "http://www.ytmp3.cn/shou/yt.php";

    private DataFetchUtils() {
    }

    /**
     * 获取音乐数据
     *
     * @return
     */
    public static List<MusicData> getMusicData() {
        String dataFromUrl = getDataFromUrl(MUSIC_URL);
        return formatData(dataFromUrl);
    }

    /**
     * 获取音乐接口原始数据
     *
     * @param url
     * @return
     */
    private static String getDataFromUrl(String url) {
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            URL rurl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) rurl.openConnection();
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                is = urlConnection.getInputStream();
                baos = new ByteArrayOutputStream();
                //10MB的缓存
                byte[] buffer = new byte[10485760];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                return baos.toString().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 格式化原始数据
     *
     * @param data
     * @return
     */
    private static List<MusicData> formatData(String data) {
        String[] split = data.replace("DATA(", "").replace(")", "").replace("\n", "")
                .replace("\"", "").split(";");
        ArrayList<MusicData> list = new ArrayList();
        for (String str : split) {
            String[] split1 = str.split(",");
            if (split1.length != 3) {
                continue;
            }
            MusicData musicData = new MusicData();
            musicData.setTitle(split1[0]);
            musicData.setUrl(split1[1]);
            musicData.setId(split1[2]);
            list.add(musicData);
        }
        return list;
    }

    public static void main(String[] args) {
        String dataFromUrl = getDataFromUrl(MUSIC_URL);
        List<MusicData> musicData = formatData(dataFromUrl);
        System.out.println("");
    }
}
