//package com.newtranx.cloud.edit.controller;
//
//import com.newtranx.cloud.edit.common.entities.Result;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @Author: niujiaxin
// * @Date: 2021-03-10 13:01
// */
//@RestController
//@Slf4j
//public class TestController {
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @ApiOperation(value = "addtest")
//    @PostMapping(value = "/addtest")
//    public Result addtest() {
//        for(int i = 0; i< 2; i++){
//            ProjectTranslateFile a = new ProjectTranslateFile();
//            a.setFileId("9999");
//            a.setProjectId("1");
//            a.setCurrentProgress("0.00");
//            a.setOriginalText("原文"+i + "abscdawdawdwdwd  dawd dawd adwa ");
//            a.setTranslateText("译文"+i+"译文测试");
//            a.setTranslateType(1);
//            a.setCurrentState(1);
//            a.setSequenceId(i+"");
//            mongoTemplate.save(a);
//        }
//        return Result.getSuccessResult();
//    }
//
//    @ApiOperation(value = "addtest")
//    @GetMapping(value = "/find")
//    public Result addtest(String id) {
//        Criteria fileId = Criteria.where("fileId").is(id);
////        Query query = new Query(Criteria.where("_id").is(id));
//        Query query = new Query(fileId);
//        ProjectTranslateFile one = mongoTemplate.findOne(query, ProjectTranslateFile.class);
//        ProjectTranslateFile projectTranslateFile = mongoTemplate.findById(id, ProjectTranslateFile.class);
//        return Result.getSuccessResult(projectTranslateFile);
//    }
//}
