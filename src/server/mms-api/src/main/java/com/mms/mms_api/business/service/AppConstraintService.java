package com.mms.mms_api.business.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mms.mms_api.common.AppConstant;

@Service
public class AppConstraintService {

    public Map<String, String> getAllConstraints() {
        Field[] fields = AppConstant.class.getFields();

        Map<String, String> constraints = new HashMap<>();

        Object obj = new Object();

        try {
            for (Field field : fields) {
                constraints.put(field.getName(), field.get(obj).toString());
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return Map.of();
        }

        return constraints;
    }
}
