package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author OliverYu
 * @Date 2018/11/16 10:14
 * @Description TODO
 */
@Data
public class Goods implements Serializable {

    private TbGoods tbGoods;    //商品SPU
    private TbGoodsDesc tbGoodsDesc;    //商品扩展
    private List<TbItem> tbItems;

}
