package com.dj.mall.dict.impl.data;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.common.base.BusinessException;
import com.dj.mall.common.util.DozerUtil;
import com.dj.mall.dict.api.data.DictDataService;
import com.dj.mall.dict.dto.data.DictDataDTO;
import com.dj.mall.dict.entity.data.DictDataEntity;
import com.dj.mall.dict.mapper.data.DictDataMapper;

import java.util.List;

@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictDataEntity> implements DictDataService {

    /**
     * 根据parentcode查找字典名
     * @param system
     * @return
     * @throws Exception
     */
    @Override
    public List<DictDataDTO> findDictNameByParentCode(String system) throws Exception {
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_code", system);
        List<DictDataEntity> list = super.list(queryWrapper);
        return DozerUtil.mapList(list, DictDataDTO.class);
    }

    /**
     * 字典查询
     * @return
     * @throws Exception
     */
    @Override
    public List<DictDataDTO> findDictDataAll() throws Exception {
        List<DictDataEntity> list = super.list();
        return DozerUtil.mapList(list, DictDataDTO.class);
    }

    /**
     * 根据code查找字典名
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public DictDataDTO findDictNameByCode(String code) throws Exception {
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        DictDataEntity dictDataEntity = super.getOne(queryWrapper);
        return DozerUtil.map(dictDataEntity, DictDataDTO.class);
    }

    /**
     * 字典修改
     * @param dictDataDTO
     * @throws Exception
     * @throws BusinessException
     */
    @Override
    public void update(DictDataDTO dictDataDTO) throws Exception,BusinessException {
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_name", dictDataDTO.getDictName());
        DictDataEntity dictDataEntity = super.getOne(queryWrapper);
        if (dictDataEntity != null && !dictDataEntity.getCode().equals(dictDataDTO.getCode())){
            throw new BusinessException("字典名不能重复！！！");
        }
        UpdateWrapper<DictDataEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("code", dictDataDTO.getCode());
        updateWrapper.set("dict_name", dictDataDTO.getDictName());
        super.update(updateWrapper);
    }

    /**
     * 字典新增
     * @param dictDataDTO
     * @throws Exception
     */
    @Override
    public void add(DictDataDTO dictDataDTO) throws Exception {
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_name", dictDataDTO.getDictName());
        DictDataEntity dictDataEntity = super.getOne(queryWrapper);
        if (dictDataEntity != null){
            throw new BusinessException("此名称已存在");
        }
        QueryWrapper<DictDataEntity> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("code", dictDataDTO.getCode());
        DictDataEntity dictDataEntity1 = super.getOne(queryWrapper1);
        if (dictDataEntity1 != null){
            throw new BusinessException("此CODE已存在");
        }
        dictDataDTO.setCode(dictDataDTO.getCode().toUpperCase());
        dictDataDTO.setParentCode(dictDataDTO.getParentCode().toUpperCase());
        super.save(DozerUtil.map(dictDataDTO, DictDataEntity.class));
    }

    /**
     * 查询字典数据Sex
     * @param sex
     * @return
     * @throws Exception
     */
    @Override
    public List<DictDataDTO> findDictDataSex(String sex) throws Exception {
        QueryWrapper<DictDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_code", sex);
        List<DictDataEntity> list = super.list(queryWrapper);
        return DozerUtil.mapList(list, DictDataDTO.class);
    }
}
