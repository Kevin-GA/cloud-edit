package com.newtranx.cloud.edit.proxy;

import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.config.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: niujiaxin
 * @Date: 2021-03-01 14:18
 */

@Component
@FeignClient(value = "file-service",
//        url = "http://123.57.180.198",
        configuration = FeignSupportConfig.class)

public interface FeignClientProxy {

    @RequestMapping(value = "/file/upload", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Object fileUpload(@RequestPart(value = "data") MultipartFile data );

//    /**
//     * 容错处理类，当调用失败时返回
//     */
//    @Component
//    class DefaultFallback implements FeignClientProxy {
//        @Override
//        public Result<Object> invoke(MultipartFile data){
//            return Result.getFailureResult(FailureCodeEnum.SEND_SMS_CODE_ERROR.getCode(),"文件上传错误");
//        }
//    }

}
