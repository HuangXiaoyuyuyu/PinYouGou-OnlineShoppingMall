package com.pinyougou.pojogroup;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author OliverYu
 * @Date 2018/11/8 17:41
 * @Description 规格组合实体类
 */
@Data
public class Specification implements Serializable {

    private TbSpecification Specification;

    private List<TbSpecificationOption> specificationOptionList;

}
