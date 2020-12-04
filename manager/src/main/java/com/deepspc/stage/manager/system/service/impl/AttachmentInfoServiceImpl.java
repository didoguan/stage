package com.deepspc.stage.manager.system.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.manager.conf.PropertiesConfig;
import com.deepspc.stage.manager.exception.ManagerExceptionCode;
import com.deepspc.stage.manager.system.entity.AttachmentInfo;
import com.deepspc.stage.manager.system.mapper.AttachmentInfoMapper;
import com.deepspc.stage.manager.system.model.UploadResult;
import com.deepspc.stage.manager.system.service.IAttachmentInfoService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gzw
 * @date 2020/12/4 10:01
 */
@Service
public class AttachmentInfoServiceImpl extends ServiceImpl<AttachmentInfoMapper, AttachmentInfo> implements IAttachmentInfoService {

    private final PropertiesConfig propertiesConfig;

    public AttachmentInfoServiceImpl(PropertiesConfig propertiesConfig) {
        this.propertiesConfig = propertiesConfig;
    }

    @Override
    public UploadResult uploadFile(MultipartFile file, String relateId) {
        return uploadFile(file, null, relateId);
    }

    @Override
    public UploadResult uploadFile(MultipartFile file, String filePath, String relateId) {
        AttachmentInfo attachmentInfo = new AttachmentInfo();
        Long attachmentId = IdWorker.getId();
        attachmentInfo.setAttachmentId(attachmentId);
        String originalFileName = file.getOriginalFilename();
        attachmentInfo.setOriginalFileName(originalFileName);
        //获取文件后缀
        int lastIndexOf = originalFileName.lastIndexOf(".");
        String fileSuffix = originalFileName.substring(lastIndexOf + 1);
        attachmentInfo.setFileType(fileSuffix);
        //文件大小
        int kb = new BigDecimal(file.getSize()).divide(BigDecimal.valueOf(1024))
                                .setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        attachmentInfo.setFileSize(kb);
        attachmentInfo.setFilePath(filePath);
        String newFileName = attachmentId.toString() + "." + fileSuffix;
        attachmentInfo.setNewFileName(newFileName);
        if (null != relateId) {
            attachmentInfo.setRelateId(relateId);
        }
        ShiroUser user = ShiroKit.getShiroUser();
        attachmentInfo.setCreatorId(user.getUserId());
        attachmentInfo.setCreatorName(user.getUserName());
        attachmentInfo.setCreateDate(new Date());

        if (StrUtil.isBlank(filePath)) {
            filePath = propertiesConfig.getAttachmentPath();
        }
        //保存文件到指定目录
        File newFile = new File(filePath + newFileName);
        //创建父目录
        FileUtil.mkParentDirs(newFile);
        try {
            //保存文件
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StageException(ManagerExceptionCode.FILE_UPLOAD_FAILED.getCode(),
                                        ManagerExceptionCode.FILE_UPLOAD_FAILED.getMessage());
        }
        this.save(attachmentInfo);

        UploadResult uploadResult = new UploadResult();
        uploadResult.setFileId(attachmentId);
        uploadResult.setOriginalFileName(originalFileName);
        uploadResult.setNewFileName(newFileName);
        uploadResult.setFileSize(kb);
        uploadResult.setFileType(fileSuffix);
        uploadResult.setFilePath(filePath);
        return uploadResult;
    }
}
