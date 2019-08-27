package com.example.musicsplider.config;

import com.example.musicsplider.task.MusicDataFecthTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz配置类
 *
 * @author Sean
 * @date 2019/08/27
 */

@Configuration
public class QuartzConfig {

    @Bean
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(MusicDataFecthTask task) {
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setConcurrent(false);
        jobDetailFactoryBean.setName("fetch");
        jobDetailFactoryBean.setGroup("fetch_group");
        jobDetailFactoryBean.setTargetObject(task);
        jobDetailFactoryBean.setTargetMethod("fetchData");
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTrigger(MethodInvokingJobDetailFactoryBean detailFactoryBean) {
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(detailFactoryBean.getObject());
        // 设置表达式,每3秒执行一次
        triggerFactoryBean.setCronExpression("0/5 * * * * ?");
        triggerFactoryBean.setName("fetchMusicData");
        return triggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactory(CronTriggerFactoryBean cronTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 设置是否任意一个已定义的Job会覆盖现在的Job。默认为false，即已定义的Job不会覆盖现有的Job。
        bean.setOverwriteExistingJobs(false);
        // 延时启动，应用启动10秒后，定时器才开始启动
        //bean.setStartupDelay(10);
        // 注册定时触发器
        bean.setTriggers(cronTrigger.getObject());
        return bean;
    }
}
