package com.example.musicsplider.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.util.Properties;

/**
 * 获取配置文件中变量的工具类
 *
 * @author Sean
 * 2019/09/03
 */
public final class PropertiesUtils {

    private PropertiesUtils() {
    }

    /**
     * 获取yml配置文件中的变量值
     *
     * @param fieldName 变量名
     * @param fileName  yml配置文件名
     * @return 变量值
     */
    public static Object getCommonYml(String fieldName, String fileName) {
        Resource resource = new ClassPathResource(fileName);
        Properties properties = null;
        try {
            YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
            yamlPropertiesFactoryBean.setResources(resource);
            properties = yamlPropertiesFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties.get(fieldName);
    }

    public static void main(String[] args) {
        Object commonYml = getCommonYml("music.fetch.url", "application.yml");
        System.out.println(commonYml);
    }
}
