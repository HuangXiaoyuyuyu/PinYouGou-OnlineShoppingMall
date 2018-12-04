package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

/**
 * @Author OliverYu
 * @Date 2018/11/28 10:20
 * @Description TODO
 */
public interface ItemSearchService {
    /**
     * 搜索方法
     * @param searchMap
     * @return
     */
    public Map search(Map searchMap);

    //导入列表
    public void importList(List list);

}
