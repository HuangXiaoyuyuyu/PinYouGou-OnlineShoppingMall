package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    TbItemMapper tbItemMapper;

    @Value("${pagedir}")
    private String pagedir;

    @Override
    public boolean genItemHtml(Long goodsId) {
        freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            Template template = configuration.getTemplate("item.ftl");
            //创建数据模型
            Map dataModel = new HashMap<>();

            //1.商品主表数据
            TbGoods goods = tbGoodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods",goods);

            //2.商品扩展表数据
            TbGoodsDesc goodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc",goodsDesc);

            //3.读取商品分类
            String itemCat1 = tbItemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String itemCat2 = tbItemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String itemCat3 = tbItemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
            dataModel.put("itemCat1",itemCat1);
            dataModel.put("itemCat2",itemCat2);
            dataModel.put("itemCat3",itemCat3);

            //4.读取SKU列表数据
            TbItemExample example = new TbItemExample();
            TbItemExample.Criteria criteria = example.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);//SPU ID
            criteria.andStatusEqualTo("1");//状态有效

            example.setOrderByClause("is_default desc");//按是否默认字段降序排序 目的是返回的结果是第一天SKU

            List<TbItem> itemList = tbItemMapper.selectByExample(example);
            dataModel.put("itemList",itemList);

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
