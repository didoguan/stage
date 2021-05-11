package com.deepspc.stage.sys.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepspc.stage.sys.system.entity.AttachmentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttachmentInfoMapper extends BaseMapper<AttachmentInfo> {

    void deleteBatchAttachment(@Param("attachmentIds") List<Long> attachmentIds);
}
