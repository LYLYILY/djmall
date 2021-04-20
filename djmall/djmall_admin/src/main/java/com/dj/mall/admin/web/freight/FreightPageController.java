package com.dj.mall.admin.web.freight;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.freight.FreightVOResp;
import com.dj.mall.common.constant.DictConstant;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.DictDataService;
import com.dj.mall.dict.api.FreightService;
import com.dj.mall.dict.dto.DictDataDTO;
import com.dj.mall.dict.dto.FreightDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/freight/")
public class FreightPageController {

    @Reference
    private DictDataService dictDataService;

    @Reference
    private FreightService freightService;

    @GetMapping("toShow")
    @RequiresPermissions(DictConstant.COURIER_FEE)
    public String toShow(ModelMap map) throws Exception {
        List<DictDataDTO> logisticsCompanyList = dictDataService.findDictNameByParentCode(DictConstant.EXPRESS_COMPANY);
        map.put("logisticsCompanyList", logisticsCompanyList);
        return "/dict/freight/show";
    }

    /**
     * 去修改页面
     * @param id
     * @param map
     * @return
     * @throws Exception
     */
    @RequiresPermissions("FREIGHT_UPDATE_BTN")
    @RequestMapping("toUpdate")
    public String toUpdate(Integer id, ModelMap map) throws Exception {
        FreightDTO freightDTO = freightService.findFreightById(id);
        FreightVOResp freightVOResp = DozerUtil.map(freightDTO, FreightVOResp.class);
        map.put("freight", freightVOResp.getFreight());
        map.put("id", id);
        return "/dict/freight/update";
    }
}
