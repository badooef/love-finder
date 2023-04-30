package com.jialu.lovefinder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jialu.lovefinder.model.entity.Tag;
import com.jialu.lovefinder.service.TagService;
import com.jialu.lovefinder.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author weijialuli
* @description 针对表【tag(标签)】的数据库操作Service实现
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




