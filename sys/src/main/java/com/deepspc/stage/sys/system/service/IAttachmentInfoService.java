package com.deepspc.stage.sys.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepspc.stage.sys.system.entity.AttachmentInfo;
import com.deepspc.stage.sys.system.model.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAttachmentInfoService extends IService<AttachmentInfo> {

    /**
     * 上传文件(默认路径)
     * @param file 要上传的文件
     * @return UploadResult
     */
    UploadResult uploadFile(MultipartFile file, String relateId);

    /**
     * 上传文件(指定路径)
     * @param file 要上传的文件
     * @param filePath 要上传的路径
     * @return UploadResult
     */
    UploadResult uploadFile(MultipartFile file, String filePath, String relateId);

    /**
     * 删除多个附件
     * @param attachmentIds 要删除的附件集
     */
    void deleteBatchAttachment(List<Long> attachmentIds);
}
