package com.pinyougou.solrutil;

import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author OliverYu
 * @Date 2018/11/27 15:53
 * @Description TODO
 */
@Component
public class SolrUtil {

    @Autowired
    private TbItemMapper tbItemMapper;

    public void importItemData() {
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);

        System.out.println("---商品列表---");
        for (TbItem tbItem : tbItems) {
            System.out.println(tbItem.getId()+" "+tbItem.getTitle()+" "+tbItem.getPrice());
        }
        System.out.println("结束");
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
        SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
        solrUtil.importItemData();
    }

}
