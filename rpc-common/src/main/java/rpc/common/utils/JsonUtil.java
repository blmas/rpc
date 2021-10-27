package rpc.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * FileName: JsonUtil
 *
 * @author: zhanyigeng
 * Date:     2021/10/15
 * Description:
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        // 对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 统一日期格式
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
        // 忽略在json字符串中存在, 但在java对象中不存在对应属性的情况, 防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String objToStr(T obj) {
        if (null == obj) {
            return null;
        }

        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("objToStr error: ", e);
            return null;
        }
    }

    public static <T> T strToObj(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str) || null == clazz) {
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("strToObj error: ", e);
            return null;
        }
    }

    public static <T> List<T> strToList(String str, Class<T> clazz) {
        if (StringUtils.isBlank(str) || null == clazz) {
            return null;
        }
        return JSONArray.parseArray(str, clazz);
    }

    public static <K, V, T> Map<K, V> objToMap(T obj) {
        if (obj == null) {
            return null;
        }

        try {
            return (objectMapper.readValue(objectMapper.writeValueAsString(obj), Map.class));
        } catch (Exception e) {
            log.warn("objToMap error: ", e);
            return null;
        }
    }

    public static <K, V, T> T mapToObject(Map<K, V> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }

        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(map), clazz);
        } catch (Exception e) {
            log.error("mapToObject error", e);
            return null;
        }
    }
}

