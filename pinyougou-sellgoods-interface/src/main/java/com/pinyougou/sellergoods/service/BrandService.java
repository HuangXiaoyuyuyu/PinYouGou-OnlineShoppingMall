package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import entity.PageResult;

import java.util.List;

public interface BrandService {

    public List<TbBrand> findAll();

    public PageResult findpage(int pageNum,int pageSize);

    public void add(TbBrand tbBrand);

    public TbBrand findOne(Long id);

    public void update(TbBrand tbBrand);

    public void delete(Long[] ids);

    public PageResult search(TbBrand tbBrand,int pageNum,int pageSize);

}
