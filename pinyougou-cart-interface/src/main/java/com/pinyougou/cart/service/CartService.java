package com.pinyougou.cart.service;

import com.pinyougou.pojogroup.Cart;

import java.util.List;

/**
 * @Author OliverYu
 * @Date 2018/12/17 15:25
 * @Description TODO
 */
public interface CartService {

    /**
     * 添加商品到购物车
     * @param cartList
     * @param itemId
     * @param num
     * @return
     */
    public List<Cart> addGoodsToCartlist(List<Cart> cartList,Long itemId,Integer num);

}
