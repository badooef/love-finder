package com.jialu.lovefinder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jialu.lovefinder.common.ErrorCode;
import com.jialu.lovefinder.exception.BusinessException;
import com.jialu.lovefinder.model.enums.ReportStatusEnum;
import com.jialu.lovefinder.service.ReportService;
import com.jialu.lovefinder.model.entity.Report;
import com.jialu.lovefinder.mapper.ReportMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author weijialuli
 * @description 针对表【report(举报)】的数据库操作Service实现
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    @Override
    public void validReport(Report report, boolean add) {
        if (report == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String content = report.getContent();
        Long reportedId = report.getReportedId();
        Integer status = report.getStatus();
        if (StringUtils.isNotBlank(content) && content.length() > 1024) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "内容过长");
        }
        // 创建时必须指定
        if (add) {
            if (reportedId == null || reportedId <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        } else {
            if (status != null && !ReportStatusEnum.getValues().contains(status)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }
}




