package com.pinyougou.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author OliverYu
 * @Date 2019/1/2 13:55
 * @Description TODO
 */
@Component
public class SeckillTask {
    /**
     * 刷新秒杀商品
     */
    @Scheduled(cron = "* * * * * ?")
    public void refreshSeckillGoods() {
        System.out.println("执行了任务调度：" + new Date());
    }

}
