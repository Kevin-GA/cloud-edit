package com.newtranx.cloud.edit.controller;

import com.newtranx.cloud.edit.base.BaseController;
import com.newtranx.cloud.edit.common.entities.Result;
import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
import com.newtranx.cloud.edit.config.DocApiConfig;
import com.newtranx.cloud.edit.config.FileSavePathConfig;
import com.newtranx.cloud.edit.dto.FileDownParam;
import com.newtranx.cloud.edit.dto.FileDownParams;
import com.newtranx.cloud.edit.entities.ProjectFile;
import com.newtranx.cloud.edit.service.*;
import com.newtranx.cloud.edit.util.FileUtil;
import com.newtranx.cloud.edit.util.RandomGenUtil;
import com.newtranx.cloud.edit.util.ZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * @Author: niujiaxin
 * @Date: 2021-03-15 18:09
 */
@RestController
@Slf4j
public class ProjectFileController extends BaseController {

    @Autowired
    private ProjectFileService projectFileService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DocApiConfig docApiConfig;

    @Autowired
    FileFeignService fileFeignService;

    @Autowired
    private FileSavePathConfig fileSavePathConfig;

    //文件上传
    @PostMapping("uploadfile")
    @Transactional
    public Result<ProjectFile> uploadfile(@RequestPart("file") MultipartFile file, @RequestParam("projectId") String projectId){
        Result result = null;

        try {
            //调用上传文件服务
            result = fileFeignService.fileUpload(file);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR.getCode(),"文件上传出错失败");
        }

        Map<String,Object> data = (LinkedHashMap<String,Object>)result.getData();
        ProjectFile projectFile = new ProjectFile();
        projectFile.setUserId(getUserId().toString());
        projectFile.setProjectId(projectId);
        projectFile.setFileName(FilenameUtils.getBaseName(file.getOriginalFilename()));
        projectFile.setFilePath(data.get("fullPath").toString());
        projectFile.setFileExt(FilenameUtils.getExtension(file.getOriginalFilename()));
        projectFile.setFileSize(Integer.parseInt(file.getSize()+""));
        projectFile.setCreateTime(new Date());
        projectFile.setUpdateTime(new Date());
        projectFile.setStatus(-1);
        try {
            projectFileService.addFile(projectFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR.getCode(),"项目文件信息更新失败");
        }

        //调用doc api
        String splitUrl = docApiConfig.getBaseAPIURL()+docApiConfig.getSplit();
        HttpHeaders headers = new HttpHeaders();
        //  提交方式表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> postData= new LinkedMultiValueMap<String, String>();
        postData.add("pro_id", projectId);
        postData.add("doc_id", projectFile.getFileId().toString());
        postData.add("path", projectFile.getFilePath());
        postData.add("doc_ext", projectFile.getFileExt());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(postData, headers);
        //启动一个新的线程
        new Thread(){
            public void run(){
                //  执行HTTP请求
                ResponseEntity<String> splitResponse = restTemplate.exchange(splitUrl, HttpMethod.POST, requestEntity, String.class);
                HashMap<String, Object> resMap = new HashMap<>();
                resMap.put("拆解句段-生成XLIF",splitResponse.getStatusCodeValue());
            }
        }.start();
        return Result.getSuccessResult(projectFile);

    }

    @RequestMapping(value = "downloadZip")
    public void downloadZip( List<File> files,String zipFilenameA,HttpServletResponse response)throws Exception {
//        List<File> files = new ArrayList<>();
//        List<String> fullpaths1 = fullpaths.getFullpaths();
//        for (String s:fullpaths1) {
//            files.add(new File("http://123.57.180.198:8888/group1/M00/00/02/rB8EW2BLAeuAaWgWAA7H3XSDOfA83.docx"));
//            files.add(new File("http://123.57.180.198:8888/group1/M00/00/04/rB8EW2BPMJeAYa11AAJSbymiBKw215.doc"));
//        }
//        files.add(new File("/Users/guolei/Downloads/CAT开发实现说明.pptx"));
//        files.add(new File("/Users/guolei/Downloads/cloud-project接口文档.docx"));

        //打包文件
        try {
//            String zipFilenameA = "tempFile.zip" ;
            File fileA = new File(zipFilenameA);
            if (!fileA.exists()){
                fileA.createNewFile();
            }
            response.reset();
            //response.getWriter()
            //创建文件输出流
            FileOutputStream fousa = new FileOutputStream(fileA);
            ZipOutputStream zipOutA = new ZipOutputStream(fousa);
            ZipUtils.zipFile(files, zipOutA);
            zipOutA.close();
            fousa.close();
            ZipUtils.downloadZip(fileA,response);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("askStatus")
    public Result<Object> askStatus(@RequestBody FileDownParams fullpaths){
        List<ProjectFile> projectFiles = new ArrayList<>();
        Map<String, Object> res = new HashMap<>();
        //查询判断，封装要下载的路径
        for (FileDownParam fp:fullpaths.getFullpaths()) {
            ProjectFile projectFile = projectFileService.getProjectFileByFileId(fp.getFileId());
            projectFiles.add(projectFile);

        }
        int successNum = 0;
        int falseNum = 0;
        boolean isGen = false;
        for (ProjectFile projectFile:projectFiles) {
            if(projectFile.getStatus()==100){
                isGen = true;
                break;
            }
            if(projectFile.getStatus()==10 || projectFile.getStatus()==20){
                successNum ++;
            }
            if(projectFile.getStatus()==11 || projectFile.getStatus()==21){
                falseNum ++;
            }

        }
        if(isGen){
            // 0合成中 1成功 2失败
            res.put("genFiles","0");
        }else {
            if(projectFiles!=null && successNum <= projectFiles.size()) {
                res.put("genFiles", "1");
            }
            if(projectFiles!=null && falseNum == projectFiles.size()){
                res.put("genFiles","2");
            }
        }
        return Result.getSuccessResult(res);
    }

    // 下载网络文件
    @PostMapping("/downfiles")
    public void downloadNet(@RequestBody FileDownParams fullpaths, HttpServletResponse response) throws Exception {
        response.setHeader("name", "malihua");
        List<File> files = new ArrayList<>();
        String tempZip="tempZip"+RandomGenUtil.getRandom()+".zip";
//        List<ProjectFile> projectFiles = new ArrayList<>();

        //查询判断，封装要下载的路径
        for (FileDownParam fp:fullpaths.getFullpaths()) {
            ProjectFile projectFile = projectFileService.getProjectFileByFileId(fp.getFileId());
//            projectFiles.add(projectFile);
            if (fp.getType()==1 && projectFile.getStatus()==10 && projectFile.getFtPath() !=null && projectFile.getFtPath().length()>0){
                fp.setFullpath(projectFile.getFtPath());
            }else if(fp.getType()==2 && projectFile.getStatus()==20 && projectFile.getBilingualPath() !=null && projectFile.getBilingualPath().length()>0){
                fp.setFullpath(projectFile.getBilingualPath());
            }else{
                fp.setFullpath(null);
            }
        }


        //下载文件到服务器
        for (FileDownParam sf:fullpaths.getFullpaths()) {
            if(sf.getFullpath()!=null){
                ProjectFile projectFile = projectFileService.getProjectFileByFileId(sf.getFileId());
                File file = downloadNetFile(sf.getFullpath(),projectFile,sf.getType());
                if(file !=null)
                    files.add(file);
            }

        }

        if(files.size()==0){
//            0合成中 1成功 2失败
            response.setHeader("Expires","2");
//            response.setHeader("nofile","1");
        }
        //打包
        if(files.size()>0) {
            try {
                downloadZip(files,tempZip,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //下载单文件
//        if(files.size()==1) {
//            downOneFile(response,fileSavePathConfig.getSavePath()+files.get(0).getName(),files.get(0).getName());
//        }

        //删除下载到本地的文件
        for (File f:files) {
            f.delete();
        }

    }

    public File downloadNetFile(String filePath, ProjectFile projectFile, Integer downType){
        int bytesum = 0;
        int byteread;
//        String classPath = this.getClass().getResource("/").toString();
//        if(classPath.contains("file:")) {
//            classPath = StringUtils.remove(classPath, "file:");
//        }
        String classPath = fileSavePathConfig.getSavePath();
        String fileName = projectFile.getFileName();
        switch (downType){
            //译文
            case 1:
                fileName = fileName+"-译文" + RandomGenUtil.getRandom() + "." + projectFile.getFileExt();
                break;
            case 2:
                fileName = fileName+"-双语" + RandomGenUtil.getRandom() + "." + projectFile.getFileExt();
                break;
            default:
                 fileName = fileName+"" + RandomGenUtil.getRandom() + "." + projectFile.getFileExt();
        }
        URL url = null;
        File file = new File(filePath);
        try {
            url = new URL(filePath);
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
//            FileOutputStream fs = new FileOutputStream(+file.getName());
            FileOutputStream fs = new FileOutputStream(classPath+fileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
            fs.close();
            return new File(classPath+fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //按类型导出文件
    @PostMapping("exportfile")
    public Result<Object> exportFile(Integer exportType,String projectId,String fileId){
        String genengUrl = docApiConfig.getBaseAPIURL()+docApiConfig.getGeneng();
        String genchiengUrl = docApiConfig.getBaseAPIURL()+docApiConfig.getGenchieng();
        //  提交方式表单提交
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> postData= new LinkedMultiValueMap<String, String>();
        ProjectFile projectFile = projectFileService.getProjectFileByFileId(Long.parseLong(fileId));
        postData.add("pro_id", projectId);
        postData.add("doc_id", fileId);
        postData.add("path", projectFile.getFilePath());
        postData.add("doc_ext", projectFile.getFileExt());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(postData, headers);

        ResponseEntity<String> genengResponse = null;
        ResponseEntity<String> genchiengResponse = null;

        switch (exportType){
            case 1:
                genengResponse = restTemplate.exchange(genengUrl, HttpMethod.POST, requestEntity, String.class);
            case 2:
                genchiengResponse = restTemplate.exchange(genchiengUrl, HttpMethod.POST, requestEntity, String.class);
            case 3:
                genengResponse = restTemplate.exchange(genengUrl, HttpMethod.POST, requestEntity, String.class);
                genchiengResponse = restTemplate.exchange(genchiengUrl, HttpMethod.POST, requestEntity, String.class);

        }
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("生成译文",genengResponse.getStatusCodeValue());
        resMap.put("生成双语",genchiengResponse.getStatusCodeValue());

        return Result.getSuccessResult("200",resMap.toString(),projectFile);

    }


    //文件批量上传
    @PostMapping("batchuploadfile")
    public Result<List<ProjectFile>> uploadfiles(MultipartFile[] file, Long fileId, String projectId){
        List<ProjectFile> projectFiles = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            projectFiles.add(uploadfile(multipartFile,projectId).getData());
        }
        if(fileId!=null)
            projectFileService.delFile(fileId);
        return Result.getSuccessResult(projectFiles);

    }

    //修改文件信息
    @PutMapping("updateFile")
    public Result updateFile(ProjectFile projectFile){
        projectFile.setUpdateTime(new Date());
//        Query query = new Query(Criteria.where("fileId").is(fileId));
//        ProjectTranslateFile mongoProjectTranslateFile = mongoTemplate.findOne(query, ProjectTranslateFile.class);
//        if(mongoProjectTranslateFile==null)
//            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR.getCode(),"mongodb未查到查分文件结果");
        try {
            return Result.getSuccessResult(projectFileService.updateFile(projectFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
    }



    //删除文件
    @GetMapping("delfile")
    public Result<Object> delfile(Long fileId){
        try {
            projectFileService.delFile(fileId);
            taskService.delTaskByFileId(fileId);
            return Result.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
    }

    //批量删除文件
    @GetMapping("delfiles")
    public Result<Object> delfiles(String fileIds){
        try {
            String[] fileIdList = fileIds.split(",");
            for (String f:fileIdList) {
                delfile(Long.parseLong(f));
            }
            return Result.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
    }

    //异步请求小外接口
    @PostMapping("gendoc")
    public Result<Object> gendoc(@RequestBody FileDownParams fullpaths){
        //doc api
        for (FileDownParam fp:fullpaths.getFullpaths()) {
            ProjectFile projectFile = projectFileService.getProjectFileByFileId(fp.getFileId());
            String url = docApiConfig.getBaseAPIURL();
            if (fp.getType()==1 ){
                url += docApiConfig.getGeneng();
            }else if(fp.getType()==2){
                url +=docApiConfig.getGenchieng();
            }
            docApi(projectFile,url);
        }
        return Result.getSuccessResult();
    }

    public void docApi(ProjectFile projectFile,String url){
        projectFile.setStatus(100);
        projectFileService.updateFile(projectFile);
        //调用doc api
        HttpHeaders headers = new HttpHeaders();
        //  提交方式表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> postData= new LinkedMultiValueMap<String, String>();
        postData.add("pro_id", projectFile.getProjectId());
        postData.add("doc_id", projectFile.getFileId().toString());
        postData.add("path", projectFile.getFilePath());
        postData.add("doc_ext", projectFile.getFileExt());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(postData, headers);
        System.out.println(postData.toString());
        //启动一个新的线程
        new Thread(){
            public void run(){
                //  执行HTTP请求
                ResponseEntity<String> splitResponse = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
                System.out.println("doc api=========:"+url);
//                HashMap<String, Object> resMap = new HashMap<>();
//                resMap.put("拆解句段-生成XLIF",splitResponse.getStatusCodeValue());
            }
        }.start();
    }

    //下载单文件
    public void downOneFile(HttpServletResponse response, String filePath, String fileName) throws Exception {
        byte[] data = FileUtil.toByteArray2(filePath);
        fileName = URLEncoder.encode(fileName, "UTF-8");
//        String fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");//为了解决中文名称乱码问题
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//        response.setHeader("Content-Disposition", "attachment;filename="
//                + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
        response.flushBuffer();
    }

}
