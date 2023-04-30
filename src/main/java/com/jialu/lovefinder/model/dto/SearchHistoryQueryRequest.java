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
public class SearchHistoryQueryRequest extends PageRequest implements Serializable {

    /**
     * 搜索关键词
     */
    private String word;

    private static final long serialVersionUID = 1L;
}