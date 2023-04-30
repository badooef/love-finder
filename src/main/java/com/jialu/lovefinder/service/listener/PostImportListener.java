package com.jialu.lovefinder.service.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jialu.lovefinder.constant.UserConstant;
import com.jialu.lovefinder.model.dto.PostImportDTO;
import com.jialu.lovefinder.model.entity.Post;
import com.jialu.lovefinder.model.entity.Tag;
import com.jialu.lovefinder.model.enums.PostReviewStatusEnum;
import com.jialu.lovefinder.model.enums.TagCategoryEnum;
import com.jialu.lovefinder.service.PostService;
import com.jialu.lovefinder.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.Map.Entry;

/**
 * 帖子 Excel 导入监听器
 * 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 *
 * @author weijialu
 */
@Slf4j
public class PostImportListener implements ReadListener<PostImportDTO> {

    /**
     * 每隔一定条数批量插入数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 500;
    private List<Post> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    // 记录本次导入数据的标签分组
    private final Map<String, Set<String>> categoryTagNameSetMap = new HashMap<String, Set<String>>() {{
        put(TagCategoryEnum.EDUCATION.getValue(), new HashSet<>());
        put(TagCategoryEnum.JOB.getValue(), new HashSet<>());
        put(TagCategoryEnum.PLACE.getValue(), new HashSet<>());
        put(TagCategoryEnum.LOVE_EXP.getValue(), new HashSet<>());
    }};

    private final PostService postService;

    private final TagService tagService;

    public PostImportListener(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    /**
     * 每一条数据解析都会来调用
     *
     * @param postImportDTO one row value
     * @param context
     */
    @Override
    public void invoke(PostImportDTO postImportDTO, AnalysisContext context) {
        Post post = new Post();
        BeanUtils.copyProperties(postImportDTO, post);
        post.setUserId(UserConstant.SYSTEM_USER_ID);
        post.setReviewStatus(PostReviewStatusEnum.PASS.getValue());
        cachedDataList.add(post);
        // 达到 BATCH_COUNT 了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
        // 记录标签
        categoryTagNameSetMap.get(TagCategoryEnum.EDUCATION.getValue()).add(post.getEducation());
        categoryTagNameSetMap.get(TagCategoryEnum.JOB.getValue()).add(post.getJob());
        categoryTagNameSetMap.get(TagCategoryEnum.PLACE.getValue()).add(post.getPlace());
        categoryTagNameSetMap.get(TagCategoryEnum.LOVE_EXP.getValue()).add(post.getLoveExp());
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        // 保存标签
        for (Entry<String, Set<String>> categoryTagNameSetEntry : categoryTagNameSetMap.entrySet()) {
            Tag tag = new Tag();
            tag.setCategory(categoryTagNameSetEntry.getKey());
            tag.setUserId(UserConstant.SYSTEM_USER_ID);
            for (String tagName : categoryTagNameSetEntry.getValue()) {
                tag.setTagName(tagName);
                QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("category", tag.getCategory());
                queryWrapper.eq("tagName", tag.getTagName());
                // 标签不存在才添加
                long hasCount = tagService.count(queryWrapper);
                if (hasCount < 1) {
                    tagService.save(tag);
                    tag.setId(null);
                }
            }
        }
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        postService.saveBatch(cachedDataList);
        log.info("导入成功条数：" + cachedDataList.size());
    }
}