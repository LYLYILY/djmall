package com.dj.mall.admin.web.dict;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.DictDataVOReq;
import com.dj.mall.admin.vo.dict.DictDataVOResp;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.constant.DictConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.data.DictDataService;
import com.dj.mall.dict.dto.data.DictDataDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict/")
public class DictDataController {

    @Reference
    private DictDataService dictDataService;

    /**
     * 字典展示
     * @return
     * @throws Exception
     */
    @RequiresPermissions(DictConstant.DICT_DATA)
    @PostMapping("show")
    public ResultModel show() throws Exception {
        List<DictDataDTO> list = dictDataService.findDictDataAll();
        return new ResultModel().success(DozerUtil.mapList(list, DictDataVOResp.class));
    }

    /**
     * 字典修改
     * @param dictDataVOReq
     * @return
     * @throws Exception
     * @throws BusinessException
     */
    @RequiresPermissions(DictConstant.DICT_DATA_UPDATE_BTN)
    @PostMapping("update")
    public ResultModel update(DictDataVOReq dictDataVOReq) throws Exception, BusinessException {
        Assert.hasText(dictDataVOReq.getDictName(), "字典名称不能为空");
        dictDataService.update(DozerUtil.map(dictDataVOReq, DictDataDTO.class));
        return new ResultModel().success();
    }

    /**
     * 字典新增
     * @param dictDataVOReq
     * @return
     * @throws Exception
     */
    @RequiresPermissions(DictConstant.DICT_DATA_ADD_BTN)
    @PostMapping("add")
    public ResultModel add(DictDataVOReq dictDataVOReq) throws Exception {
        Assert.hasText(dictDataVOReq.getDictName(), "字典名不能为空");
        Assert.hasText(dictDataVOReq.getCode(), "字典CODE不能为空");
        dictDataService.add(DozerUtil.map(dictDataVOReq, DictDataDTO.class));
        return new ResultModel().success();
    }
}
