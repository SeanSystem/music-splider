package com.example.musicsplider.thread;

import com.example.musicsplider.dao.MusicRepository;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.utils.DataFetchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 多线程爬取音乐数据
 *
 * @author Sean
 * 2019/08/28
 */
@Component
public class MusicFetchThread extends Thread {

    @Autowired
    private MusicRepository musicRepository;
    private static int n = 1;
    @Override
    public void run() {
        while (true){
            List<MusicData> musicData = DataFetchUtils.getMusicData();
            if (null != musicData && musicData.size() > 0) {
                try {
                    musicRepository.saveAll(musicData);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
            System.out.println("爬取数据完成！"+n++);
        }
    }
}
