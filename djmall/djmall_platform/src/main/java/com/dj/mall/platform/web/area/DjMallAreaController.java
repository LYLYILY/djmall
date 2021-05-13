package com.dj.mall.platform.web.area;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.common.base.ResultModel;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.platform.vo.shop.area.DjMallAreaVOResp;
import com.dj.mall.product.api.area.DjMallAreaService;
import com.dj.mall.product.dto.area.DjMallAreaDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address/")
public class DjMallAreaController {

    @Reference
    private DjMallAreaService djMallAreaService;

    /**
     *
     * 展示全部地区
     */
    @PostMapping("show")
    public ResultModel<Object> findDjMallAreaAll() throws Exception {
        List<DjMallAreaDTO> djMallAreaDTO = djMallAreaService.findDjMallAreaAll();
        List<DjMallAreaVOResp> djMallArea = DozerUtil.mapList(djMallAreaDTO, DjMallAreaVOResp.class);
        return new ResultModel<>().success(djMallArea);
    }

    /**
     * 查询根据pid
     */
    @GetMapping("showByPid")
    public ResultModel<Object> findAreaByPid(Integer pid) throws Exception {
        List<DjMallAreaDTO> djMallAreaDTOList = djMallAreaService.findAreaByPid(pid);
        return new ResultModel<>().success(djMallAreaDTOList);
    }





}
