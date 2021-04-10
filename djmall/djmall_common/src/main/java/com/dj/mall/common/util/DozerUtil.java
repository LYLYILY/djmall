package com.dj.mall.common.util;

import org.dozer.DozerBeanMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DozerUtil {

    private static final DozerBeanMapper dozer = new DozerBeanMapper();
    static {
        dozer.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
    }
    /**
     * 对象映射（源与目标属性不一致的情况，可在目标属性中通过@Mapping 手动映射）
     *
     * @param source 源
     * @param target 目标
     * @param <T>
     * @return
     */
    public static <T> T map(Object source, Class<T> target) {
        if (null == source) {
            return null;
        }
        return dozer.map(source, target);
    }


    /**
     * List映射
     *
     * @param <T>
     * @param sourceList 源List
     * @param target     目标
     * @return
     */
    public static <T> List<T> mapList(List<?> sourceList, Class<T> target) {
        if (sourceList == null) {
            return null;
        }
        return sourceList.stream().map(source -> map(source, target)).collect(Collectors.toList());
    }

}

