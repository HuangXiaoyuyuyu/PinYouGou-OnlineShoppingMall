package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author OliverYu
 * @Date 2018/12/4 17:48
 * @Description TODO
 */
@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    TbGoodsMapper tbGoodsMapper;

    @Autowired
    TbGoodsDescMapper tbGoodsDescMapper;

    @Autowired
    TbItemCatMapper tbItemCatMapper;

    @Value("${pagedir}")
    private String pagedir;

    @Override
    public boolean genItemHtml(Long goodsId) {
        freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            Template template = configuration.getTemplate("item.ftl");
            //创建数据模型
            Map dataModel = new HashMap<>();
            //商品主表数据
            TbGoods goods = tbGoodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods",goods);
            //商品扩展表数据
            TbGoodsDesc goodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc",goodsDesc);
            //读取商品分类
            String itemCat1 = tbItemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String itemCat2 = tbItemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String itemCat3 = tbItemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            dataModel.put("itemCat1",itemCat1);
            dataModel.put("itemCat2",itemCat2);
            dataModel.put("itemCat3",itemCat3);

            Writer out = new FileWriter(pagedir+goodsId+".html");
            template.process(dataModel,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
