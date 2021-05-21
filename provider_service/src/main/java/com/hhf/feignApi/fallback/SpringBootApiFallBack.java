package com.hhf.feignApi.fallback;

import com.hhf.feignApi.SpringBootApi;
import com.hhf.utils.ResultUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringBootApiFallBack implements SpringBootApi {
    @Override
    public Map<String, Object> deleteUserById(Long id) {
        return ResultUtils.getFailResult("降级！");
    }

    @Override
    public Map<String, Object> myIndex() {
        return ResultUtils.getFailResult("降级！");
    }
}
