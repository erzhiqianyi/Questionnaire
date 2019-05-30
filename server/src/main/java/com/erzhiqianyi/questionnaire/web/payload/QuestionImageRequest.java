package com.erzhiqianyi.questionnaire.web.payload;

import com.erzhiqianyi.questionnaire.util.JsonUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;

@Data
@Log4j2
public class QuestionImageRequest {
    private String name;
    private String url;

    public QuestionImageRequest(String csv) {
        if (StringUtils.isEmpty(csv)) {
            throw new IllegalArgumentException("数据不能为空");
        }
        String[] arr = csv.split("/");
        log.info(JsonUtil.toJson(arr));
        if (arr.length != 2){
            throw new IllegalArgumentException("数据格式不正确");
        }
        this.name = arr[0];
        this.url = arr[1];
    }
}
