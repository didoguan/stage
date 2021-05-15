package com.deepspc.stage.esmanager.cost.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deepspc.stage.core.exception.StageException;
import com.deepspc.stage.esmanager.cost.entity.CostCenter;
import com.deepspc.stage.esmanager.cost.mapper.CostCenterMapper;
import com.deepspc.stage.esmanager.cost.service.ICostCenterService;
import com.deepspc.stage.shiro.common.ShiroKit;
import com.deepspc.stage.shiro.model.ShiroUser;
import com.deepspc.stage.sys.common.BaseOrmService;
import com.deepspc.stage.sys.common.SysPropertiesConfig;
import com.deepspc.stage.sys.constant.Const;
import com.deepspc.stage.sys.system.entity.AttachmentInfo;
import com.deepspc.stage.sys.system.service.IAttachmentInfoService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author gzw
 * @date 2021/5/10 14:41
 */
@Service
public class CostCenterServiceImpl extends BaseOrmService<CostCenterMapper, CostCenter> implements ICostCenterService {

    private final SysPropertiesConfig sysPropertiesConfig;

    private final IAttachmentInfoService attachmentInfoService;

    public CostCenterServiceImpl(SysPropertiesConfig sysPropertiesConfig, IAttachmentInfoService attachmentInfoService) {
        this.sysPropertiesConfig = sysPropertiesConfig;
        this.attachmentInfoService = attachmentInfoService;
    }

    @Override
    public Page<CostCenter> loadCostCenterDatas(String costType, String costStartDate, String costEndDate) {
        Page page = defaultPage();
        if (StrUtil.isNotBlank(costStartDate)) {
            costStartDate = costStartDate.trim() + " 00:00:00";
        }
        if (StrUtil.isNotBlank(costEndDate)) {
            costEndDate = costEndDate.trim() + " 23:59:59";
        }
        ShiroUser user = ShiroKit.getShiroUser();
        boolean checkAll = checkAllPermission(user, "/cost/costCenter");
        return this.baseMapper.loadCostCenterDatas(page, checkAll, user.getUserId(), costType, costStartDate, costEndDate);
    }

    @Override
    public void deleteCostCenterDatas(List<Long> ids) {
        if (null != ids && !ids.isEmpty()) {
            this.baseMapper.deleteCostCenter(ids);
        }
    }

    @Override
    public void uploadCostPics(MultipartFile files, Long costCenterId) {
        String originalName = files.getOriginalFilename();
        String fileSuffix = originalName.substring(originalName.indexOf("."), originalName.length());
        AttachmentInfo attachmentInfo = new AttachmentInfo();
        Long picId = IdWorker.getId();
        String picPathName = "/cost/" + picId + fileSuffix;
        String picFilePath = sysPropertiesConfig.getAttachmentPath() + picPathName;
        File file = new File(sysPropertiesConfig.getAttachmentPath() + "/cost/");
        if (!file.exists()) {
            //先创建目录
            file.mkdirs();
        }
        File pic = new File(picFilePath);
        try {
            files.transferTo(pic);
        } catch (IOException e) {
            e.printStackTrace();
            throw new StageException("保存文件失败");
        }
        attachmentInfo.setAttachmentId(picId);
        attachmentInfo.setRelateId(costCenterId + "");
        attachmentInfo.setOriginalFileName(originalName);
        attachmentInfo.setNewFileName(picId + fileSuffix);
        attachmentInfo.setFileType(fileSuffix);
        attachmentInfo.setFileSize(files.getSize());
        attachmentInfo.setFilePath(Const.attachmentUri + picPathName);
        attachmentInfoService.save(attachmentInfo);
    }

    @Override
    public CostCenter getCostCenterDetail(Long costCenterId) {
        return this.baseMapper.getCostCenterDetail(costCenterId);
    }
}
