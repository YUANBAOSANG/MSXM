package com.yuan.z.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 YDL
 * @创建时间 2020/6/9 16:24
 * @描述
 */
public class ValidationResult {
    //校验结果是否有错
    private boolean hasErrors =false;

    private Map<String,String> errorMsgMap = new HashMap<>();

    public boolean isHasErrors(){
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public String getErrMsg(){
      return  StringUtils.join(errorMsgMap.values().toArray(),",");
    }
}
