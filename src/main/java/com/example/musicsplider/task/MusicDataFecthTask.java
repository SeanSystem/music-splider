package com.example.musicsplider.task;

import com.example.musicsplider.dao.MusicRepository;
import com.example.musicsplider.entity.MusicData;
import com.example.musicsplider.utils.DataFetchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 定时获取音乐信息
 *
 * @author Sean
 * @date 2019/08/27
 */
@Component
public class MusicDataFecthTask {

    @Autowired
    private MusicRepository musicRepository;

    /**
     * 定时爬取数据到es中
     */
    public void fetchData() {
        List<MusicData> musicData = DataFetchUtils.getMusicData();
        if (null != musicData && musicData.size() > 0) {
            try {
                musicRepository.saveAll(musicData);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        System.out.println("测试定时任务调度");
    }
}
