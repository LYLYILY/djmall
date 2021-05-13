package com.dj.mall.dict.api.freight;

import com.dj.mall.common.base.BusinessException;
import com.dj.mall.dict.dto.freight.FreightDTO;

import java.util.List;

public interface FreightService {

    /**
     * 运费展示
     * @return
     * @throws Exception
     */
    List<FreightDTO> freightShow() throws Exception;

    /**
     * 运费新增
     * @param freightDTO
     * @throws Exception
     */
    void add(FreightDTO freightDTO) throws Exception, BusinessException;

    /**
     * 根据id查找运费
     * @param id
     * @return
     * @throws Exception
     */
    FreightDTO findFreightById(Integer id) throws Exception;

    /**
     * 修改运费
     * @param freightDTO
     * @throws Exception
     */
    void updateFreight(FreightDTO freightDTO) throws Exception;

}
