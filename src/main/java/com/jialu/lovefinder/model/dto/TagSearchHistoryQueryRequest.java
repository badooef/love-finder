package com.jialu.lovefinder.model.dto;

import com.jialu.lovefinder.common.PageRequest;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询请求
 *
 * @author weijialu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TagSearchHistoryQueryRequest extends PageRequest implements Serializable {

    /**
     * 筛选标签名称
     */
    private String tagName;

    private static final long serialVersionUID = 1L;
}