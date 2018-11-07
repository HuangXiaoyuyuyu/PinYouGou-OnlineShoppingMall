package entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */

@Data
@AllArgsConstructor
public class PageResult implements Serializable {

    private Long total;
    private List rows;



}
