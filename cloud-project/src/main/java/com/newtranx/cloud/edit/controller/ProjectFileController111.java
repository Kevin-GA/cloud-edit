//package com.newtranx.cloud.edit.controller;
//
//import com.newtranx.cloud.edit.common.entities.Result;
//import com.newtranx.cloud.edit.common.enums.FailureCodeEnum;
//import com.newtranx.cloud.edit.config.DocApiConfig;
//import com.newtranx.cloud.edit.dto.FileDownParam;
//import com.newtranx.cloud.edit.entities.ProjectFile;
//import com.newtranx.cloud.edit.service.FileFeignService;
//import com.newtranx.cloud.edit.service.ProjectFileService;
//import com.newtranx.cloud.edit.service.TaskService;
//import com.newtranx.cloud.edit.util.ZipUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.*;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipOutputStream;
//
///**
// * @Author: niujiaxin
// * @Date: 2021-03-15 18:09
// */
//@RestController
//@Slf4j
//public class ProjectFileController111 extends BaseController{
//
//    @Autowired
//    private ProjectFileService projectFileService;
//
//    @Autowired
//    private TaskService taskService;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private DocApiConfig docApiConfig;
//
//    @Autowired
//    FileFeignService fileFeignService;
//
//    //文件上传
//    @PostMapping("uploadfile")
//    @Transactional
//    public Result<ProjectFile> uploadfile(@RequestPart("file") MultipartFile file, @RequestParam("projectId") String projectId){
//        Result result = null;
//
//        try {
//            //调用上传文件服务
//            result = fileFeignService.fileUpload(file);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR.getCode(),"文件上传出错失败");
//        }
//
//        Map<String,Object> data = (LinkedHashMap<String,Object>)result.getData();
//        ProjectFile projectFile = new ProjectFile();
//        projectFile.setUserId(getUserId().toString());
//        projectFile.setProjectId(projectId);
//        String[] splitFileName = file.getOriginalFilename().split("\\.");
//        projectFile.setFileName(splitFileName[0]);
//        projectFile.setFilePath(data.get("fullPath").toString());
//        projectFile.setFileExt(splitFileName[1]);
//        projectFile.setFileSize(Integer.parseInt(file.getSize()+""));
//        projectFile.setCreateTime(new Date());
//        projectFile.setUpdateTime(new Date());
//        try {
//            projectFileService.addFile(projectFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR.getCode(),"项目文件信息更新失败");
//        }
//
//        //调用doc api
//        String splitUrl = docApiConfig.getBaseAPIURL()+docApiConfig.getSplit();
//        HttpHeaders headers = new HttpHeaders();
//        //  提交方式表单提交
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        MultiValueMap<String, String> postData= new LinkedMultiValueMap<String, String>();
//        postData.add("pro_id", projectId);
//        postData.add("doc_id", projectFile.getFileId().toString());
//        postData.add("path", projectFile.getFilePath());
//        postData.add("doc_ext", projectFile.getFileExt());
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(postData, headers);
//        //启动一个新的线程
//        new Thread(){
//            public void run(){
//                //  执行HTTP请求
//                ResponseEntity<String> splitResponse = restTemplate.exchange(splitUrl, HttpMethod.POST, requestEntity, String.class);
//                HashMap<String, Object> resMap = new HashMap<>();
//                resMap.put("拆解句段-生成XLIF",splitResponse.getStatusCodeValue());
//            }
//        }.start();
//        return Result.getSuccessResult(projectFile);
//
//    }
//
//    @RequestMapping(value = "downloadZip")
//    public void downloadZip( List<File> files,String zipFilenameA,HttpServletResponse response)throws Exception {
////        List<File> files = new ArrayList<>();
////        List<String> fullpaths1 = fullpaths.getFullpaths();
////        for (String s:fullpaths1) {
////            files.add(new File("http://123.57.180.198:8888/group1/M00/00/02/rB8EW2BLAeuAaWgWAA7H3XSDOfA83.docx"));
////            files.add(new File("http://123.57.180.198:8888/group1/M00/00/04/rB8EW2BPMJeAYa11AAJSbymiBKw215.doc"));
////        }
////        files.add(new File("/Users/guolei/Downloads/CAT开发实现说明.pptx"));
////        files.add(new File("/Users/guolei/Downloads/cloud-project接口文档.docx"));
//
//        //打包文件
//        try {
////            String zipFilenameA = "tempFile.zip" ;
//            File fileA = new File(zipFilenameA);
//            if (!fileA.exists()){
//                fileA.createNewFile();
//            }
//            response.reset();
//            //response.getWriter()
//            //创建文件输出流
//            FileOutputStream fousa = new FileOutputStream(fileA);
//            ZipOutputStream zipOutA = new ZipOutputStream(fousa);
//            ZipUtils.zipFile(files, zipOutA);
//            zipOutA.close();
//            fousa.close();
//            ZipUtils.downloadZip(fileA,response);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 下载网络文件
//    @GetMapping("/downfiles")
//    public void downloadNet(@RequestBody FileDownParam fullpaths, HttpServletResponse response) throws MalformedURLException {
//        List<File> files = new ArrayList<>();
//        String tempZip="tempZip.zip";
//        List<String> fullpaths1 = fullpaths.getFullpaths();
//        //下载文件
//        for (String sf:fullpaths1) {
//            File file = downloadNetFile(sf);
//            if(file !=null)
//                files.add(file);
//        }
//        //打包
////        if(files.size()>0) {
////            try {
////                downloadZip(files,tempZip,response);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
//        //删除下载的文件
////        for (File f:files) {
////            f.delete();
////        }
//
//    }
//
//    public File downloadNetFile(String filePath){
//        int bytesum = 0;
//        int byteread;
//
//        URL url = null;
//        File file = new File(filePath);
//        try {
//            url = new URL(filePath);
//            URLConnection conn = url.openConnection();
//            InputStream inStream = conn.getInputStream();
//            FileOutputStream fs = new FileOutputStream(this.getClass().getResource("/")+file.getName());
//
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((byteread = inStream.read(buffer)) != -1) {
//                bytesum += byteread;
//                System.out.println(bytesum);
//                fs.write(buffer, 0, byteread);
//            }
//            fs.close();
//            return new File(this.getClass().getResource("/")+file.getName());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    //按类型导出文件
//    @PostMapping("exportfile")
//    public Result<Object> exportFile(Integer exportType,String projectId,String fileId){
//        String genengUrl = docApiConfig.getBaseAPIURL()+docApiConfig.getGeneng();
//        String genchiengUrl = docApiConfig.getBaseAPIURL()+docApiConfig.getGenchieng();
//        //  提交方式表单提交
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//        MultiValueMap<String, String> postData= new LinkedMultiValueMap<String, String>();
//        ProjectFile projectFile = projectFileService.getProjectFileByFileId(Long.parseLong(fileId));
//        postData.add("pro_id", projectId);
//        postData.add("doc_id", fileId);
//        postData.add("path", projectFile.getFilePath());
//        postData.add("doc_ext", projectFile.getFileExt());
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(postData, headers);
//
//        ResponseEntity<String> genengResponse = null;
//        ResponseEntity<String> genchiengResponse = null;
//
//        switch (exportType){
//            case 1:
//                genengResponse = restTemplate.exchange(genengUrl, HttpMethod.POST, requestEntity, String.class);
//            case 2:
//                genchiengResponse = restTemplate.exchange(genchiengUrl, HttpMethod.POST, requestEntity, String.class);
//            case 3:
//                genengResponse = restTemplate.exchange(genengUrl, HttpMethod.POST, requestEntity, String.class);
//                genchiengResponse = restTemplate.exchange(genchiengUrl, HttpMethod.POST, requestEntity, String.class);
//
//        }
//        HashMap<String, Object> resMap = new HashMap<>();
//        resMap.put("生成译文",genengResponse.getStatusCodeValue());
//        resMap.put("生成双语",genchiengResponse.getStatusCodeValue());
//
//        return Result.getSuccessResult("200",resMap.toString(),projectFile);
//
//    }
//
//
//    //文件批量上传
//    @PostMapping("batchuploadfile")
//    public Result<List<ProjectFile>> uploadfiles(MultipartFile[] file, Long fileId, String projectId){
//        List<ProjectFile> projectFiles = new ArrayList<>();
//        for (MultipartFile multipartFile : file) {
//            projectFiles.add(uploadfile(multipartFile,projectId).getData());
//        }
//        if(fileId!=null)
//            projectFileService.delFile(fileId);
//        return Result.getSuccessResult(projectFiles);
//
//    }
//
//    //修改文件信息
//    @PutMapping("updateFile")
//    public Result updateFile(ProjectFile projectFile){
//        projectFile.setUpdateTime(new Date());
////        Query query = new Query(Criteria.where("fileId").is(fileId));
////        ProjectTranslateFile mongoProjectTranslateFile = mongoTemplate.findOne(query, ProjectTranslateFile.class);
////        if(mongoProjectTranslateFile==null)
////            return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR.getCode(),"mongodb未查到查分文件结果");
//        try {
//            projectFileService.updateFile(projectFile);
//            return Result.getSuccessResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
//    }
//
//
//
//    //删除文件
//    @GetMapping("delfile")
//    public Result<Object> delfile(Long fileId){
//        try {
//            projectFileService.delFile(fileId);
//            taskService.delTaskByFileId(fileId);
//            return Result.getSuccessResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Result.getFailureResult(FailureCodeEnum.SYSTEM_ERROR);
//    }
//
//    public static void main(String[] args) {
//        File scFileDir = new File("http://123.57.180.198:8888/group1/M00/00/02/rB8EW2BLAeuAaWgWAA7H3XSDOfA83.docx");
//        System.out.println(scFileDir.getName());
//        List<String> files = new ArrayList<>();
////        files.add("http://123.57.180.198:8888/group1/M00/00/02/rB8EW2BLAeuAaWgWAA7H3XSDOfA83.docx");
////        files.add("http://123.57.180.198:8888/group1/M00/00/04/rB8EW2BPMJeAYa11AAJSbymiBKw215.doc");
//        files.add("/Users/guolei/Downloads/CAT开发实现说明.pptx");
//        files.add("/Users/guolei/Downloads/cloud-project接口文档.docx");
//
//        try {
//            compress(files,"/Users/guolei/temp.zip",false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @Title: compress
//     * @Description: TODO
//     * @param filePaths 需要压缩的文件地址列表（绝对路径）
//     * @param zipFilePath 需要压缩到哪个zip文件（无需创建这样一个zip，只需要指定一个全路径）
//     * @param keepDirStructure 压缩后目录是否保持原目录结构
//     * @throws IOException
//     * @return int   压缩成功的文件个数
//     */
//    public static int compress(List<String> filePaths, String zipFilePath,Boolean keepDirStructure) throws IOException{
//        byte[] buf = new byte[1024];
//        File zipFile = new File(zipFilePath);
//        //zip文件不存在，则创建文件，用于压缩
//        if(!zipFile.exists())
//            zipFile.createNewFile();
//        int fileCount = 0;//记录压缩了几个文件？
//        try {
//            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
//            for(int i = 0; i < filePaths.size(); i++){
//                String relativePath = filePaths.get(i);
//                if(StringUtils.isEmpty(relativePath)){
//                    continue;
//                }
//                File sourceFile = new File(relativePath);//绝对路径找到file
//                if(sourceFile == null || !sourceFile.exists()){
//                    continue;
//                }
//
//                FileInputStream fis = new FileInputStream(sourceFile);
//                if(keepDirStructure!=null && keepDirStructure){
//                    //保持目录结构
//                    zos.putNextEntry(new ZipEntry(relativePath));
//                }else{
//                    //直接放到压缩包的根目录
//                    zos.putNextEntry(new ZipEntry(sourceFile.getName()));
//                }
//                //System.out.println("压缩当前文件："+sourceFile.getName());
//                int len;
//                while((len = fis.read(buf)) > 0){
//                    zos.write(buf, 0, len);
//                }
//                zos.closeEntry();
//                fis.close();
//                fileCount++;
//            }
//            zos.close();
//            //System.out.println("压缩完成");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileCount;
//    }
//}
