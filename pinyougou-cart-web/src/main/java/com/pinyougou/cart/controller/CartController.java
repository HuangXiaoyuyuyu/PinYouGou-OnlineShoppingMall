package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.pojogroup.Cart;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.CookieUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author OliverYu
 * @Date 2018/12/17 18:12
 * @Description TODO
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Reference
    private CartService cartService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/findCartList")
    public List<Cart> findCartList() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);

        //从cookie获取购物车
        String cartListString = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
        if (cartListString == null || cartListString.equals("")) {
            cartListString = "[]";
        }
        List<Cart> cartList_cookie = JSON.parseArray(cartListString, Cart.class);

        if (name.equals("anonymousUser")) {
            System.out.println("从cookie获取购物车");
            return cartList_cookie;
        }else {
            //获取redis购物车
            List<Cart> cartList_redis = cartService.findCartListFromRedis(name);
            if (cartList_cookie.size() > 0) {
                //得到合并后的购物车
                List<Cart> cartList = cartService.mergeCartList(cartList_cookie, cartList_redis);
                //将合并后的购物车存入redis
                cartService.saveCartToRedis(name,cartList);
                utils.CookieUtil.deleteCookie(request,response,"cartList");
                System.out.println("合并购物车");
                return  cartList;
            }
            return cartList_redis;
        }
    }

    @RequestMapping("/addGoodsToCartList")
    @CrossOrigin(origins = "http://localhost:9105",allowCredentials = "true")
    public Result addGoodsToCartList(Long itemId,Integer num) {

        //response.setHeader("Access-Control-Allow-Origin", "http://localhost:9105");
        //response.setHeader("Access-Control-Allow-Credentials", "true");

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);

       try{
           //（1）从cookie中取出购物车
           List<Cart> cartList = findCartList();
           //（2）向购物车添加商品
           cartList = cartService.addGoodsToCartlist(cartList,itemId,num);
           if (name.equals("anonymousUser")) {
               //（3）将购物车存入cookie
               utils.CookieUtil.setCookie(request,response,"cartList",JSON.toJSONString(cartList),3600*24,"UTF-8");
               System.out.println("向cookie存取购物车");
           } else {
               cartService.saveCartToRedis(name,cartList);
           }

           return new Result(true,"添加成功");
       }catch (Exception e) {
           e.printStackTrace();
           return new Result(false,"添加失败");
       }
    }
}
