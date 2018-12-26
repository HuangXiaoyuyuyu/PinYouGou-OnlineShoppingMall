package com.pinyougou.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pay.service.WeixinPayService;
import entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.IdWorker;

import java.util.Map;

/**
 * @Author OliverYu
 * @Date 2018/12/25 14:19
 * @Description TODO
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    @Reference
    private WeixinPayService weixinPayService;

    /**
     * 生成二维码
     * @return
     */
    @RequestMapping("/createNative")
    public Map createNative(){
        IdWorker idworker=new IdWorker();
        return weixinPayService.createNative(idworker.nextId()+"","1");
    }

    /**
     * 查询支付状态
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/queryPayStatus")
    public Result queryPayStatus(String out_trade_no) throws Exception {
        Result result = null;
        int x = 0;
        while (true) {
            Map map = weixinPayService.queryPayStatus(out_trade_no);
            if (map == null) {
                result = new Result(false,"支付异常");
                break;
            }
            System.out.println(map);
            if (map.get("trade_state") != null && map.get("trade_state").equals("SUCCESS")) {
                result = new Result(true,"支付成功");
                break;
            }

            Thread.sleep(3000);

            x++;
            if (x >= 100) {
                result = new Result(false,"二维码超时");
                break;
            }
        }
        return result;
    }
}
