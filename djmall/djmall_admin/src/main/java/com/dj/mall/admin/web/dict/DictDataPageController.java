package com.dj.mall.admin.web.dict;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.DictDataVOResp;
import com.dj.mall.common.constant.DictConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.DictDataService;
import com.dj.mall.dict.dto.DictDataDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dict/")
public class DictDataPageController {

    @Reference
    private DictDataService dictDataService;

    /**
     * 字典展示
     * @param map
     * @return
     * @throws Exception
     */
    @RequiresPermissions(DictConstant.DICT_DATA)
    @GetMapping("toShow")
    public String toShow(ModelMap map) throws Exception {
        List<DictDataDTO> system = dictDataService.findDictNameByParentCode("SYSTEM");
        map.put("system", system);
        return "/dict/show";
    }

    /**
     * 去修改
     * @param code
     * @param map
     * @return
     * @throws Exception
     */
    @RequiresPermissions(DictConstant.DICT_DATA_UPDATE_BTN)
    @GetMapping("toUpd/{code}")
    public String toUpd(@PathVariable String code, ModelMap map) throws Exception {
        DictDataDTO dictDataDTO = dictDataService.findDictNameByCode(code);
        map.put("list", DozerUtil.map(dictDataDTO, DictDataVOResp.class));
        return "/dict/update";
    }
}
