package com.jialu.lovefinder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jialu.lovefinder.model.entity.TagSearchHistory;

/**
 * @author weijialuli
 * @description 针对表【tag_search_history(标签筛选记录)】的数据库操作Service
 */
public interface TagSearchHistoryService extends IService<TagSearchHistory> {

    /**
     * 添加标签搜索记录
     *
     * @param tagName
     * @return
     */
    boolean addTagSearchHistory(String tagName);
}
