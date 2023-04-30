package com.jialu.lovefinder.model.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * 点赞 / 取消点赞请求
 *
 * @author weijialu
 */
@Data
public class PostDoThumbRequest implements Serializable {

    /**
     * 帖子 id
     */
    private long postId;

    private static final long serialVersionUID = 1L;
}