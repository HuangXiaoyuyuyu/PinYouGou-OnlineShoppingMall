package com.pinyougou.page.service;

/**
 * @Author OliverYu
 * @Date 2018/12/4 17:20
 * @Description TODO
 */
public interface ItemPageService {

    /**
     * 生成商品详细页
     * @param goodsId
     * @return
     */
    public boolean genItemHtml(Long goodsId);

}
