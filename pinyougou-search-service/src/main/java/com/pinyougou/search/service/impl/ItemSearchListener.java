package com.pinyougou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * @Author OliverYu
 * @Date 2018/12/6 18:20
 * @Description TODO
 */
@Component
public class ItemSearchListener implements MessageListener {

    @Autowired
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(Message message) {
        System.out.println("监听到消息...");

        TextMessage textMessage = (TextMessage) message;
        try {
            String text = textMessage.getText();//json字符串
            List<TbItem> list = JSON.parseArray(text, TbItem.class);
            itemSearchService.importList(list);
            System.out.println("导入到solr索引库");
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
