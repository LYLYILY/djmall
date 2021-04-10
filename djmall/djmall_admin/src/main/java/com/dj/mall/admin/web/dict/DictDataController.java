package com.dj.mall.admin.web.dict;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.DictDataVOReq;
import com.dj.mall.admin.vo.dict.DictDataVOResp;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.DictDataService;
import com.dj.mall.dict.dto.DictDataDTO;
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

    @PostMapping("show")
    public ResultModel show() throws Exception {
        List<DictDataDTO> list = dictDataService.findDictDataAll();
        return new ResultModel().success(DozerUtil.mapList(list, DictDataVOResp.class));
    }

    @PostMapping("update")
    public ResultModel update(DictDataVOReq dictDataVOReq) throws Exception, BusinessException {
        Assert.hasText(dictDataVOReq.getDictName(), "字典名称不能为空");
        dictDataService.update(DozerUtil.map(dictDataVOReq, DictDataDTO.class));
        return new ResultModel().success();
    }

    @PostMapping("add")
    public ResultModel add(DictDataVOReq dictDataVOReq) throws Exception {
        Assert.hasText(dictDataVOReq.getDictName(), "字典名不能为空");
        Assert.hasText(dictDataVOReq.getCode(), "字典CODE不能为空");
        dictDataService.add(DozerUtil.map(dictDataVOReq, DictDataDTO.class));
        return new ResultModel().success();
    }
}
