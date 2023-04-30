package com.jialu.lovefinder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jialu.lovefinder.annotation.AuthCheck;
import com.jialu.lovefinder.common.BaseResponse;
import com.jialu.lovefinder.common.DeleteRequest;
import com.jialu.lovefinder.common.ErrorCode;
import com.jialu.lovefinder.common.ResultUtils;
import com.jialu.lovefinder.exception.BusinessException;
import com.jialu.lovefinder.model.dto.TagSearchHistoryQueryRequest;
import com.jialu.lovefinder.model.entity.TagSearchHistory;
import com.jialu.lovefinder.service.TagSearchHistoryService;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标签筛选记录接口
 *
 * @author weijialu
 */
@RestController
@RequestMapping("/tag_search_history")
public class TagSearchHistoryController {

    @Resource
    private TagSearchHistoryService tagSearchHistoryService;

    /**
     * 创建
     *
     * @param tagSearchHistory
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Boolean> addTagSearchHistory(@RequestBody TagSearchHistory tagSearchHistory) {
        if (tagSearchHistory == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String tagName = tagSearchHistory.getTagName();
        return ResultUtils.success(tagSearchHistoryService.addTagSearchHistory(tagName));
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> deleteTagSearchHistory(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        boolean b = tagSearchHistoryService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 分页获取列表
     *
     * @param tagSearchHistoryQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "admin")
    @GetMapping("/list/page")
    public BaseResponse<Page<TagSearchHistory>> listTagSearchHistoryByPage(
            TagSearchHistoryQueryRequest tagSearchHistoryQueryRequest) {
        long current = 1;
        long size = 10;
        QueryWrapper<TagSearchHistory> queryWrapper = new QueryWrapper<>();
        if (tagSearchHistoryQueryRequest != null) {
            // 根据标签名称模糊查询
            String tagName = tagSearchHistoryQueryRequest.getTagName();
            if (StringUtils.isNotBlank(tagName)) {
                queryWrapper.like("tagName", tagName);
            }
        }
        Page<TagSearchHistory> tagSearchHistoryPage = tagSearchHistoryService.page(new Page<>(current, size),
                queryWrapper);
        return ResultUtils.success(tagSearchHistoryPage);
    }

    // endregion
}
