package com.newtranx.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newtranx.task.commons.exceptions.BusinessException;
import com.newtranx.task.commons.response.ResponseCode;
import com.newtranx.task.commons.utils.PageData;
import com.newtranx.task.domain.Task;
import com.newtranx.task.dto.TaskR;
import com.newtranx.task.dto.TeamUser;
import com.newtranx.task.dto.TeamUserR;
import com.newtranx.task.mapper.TaskMapper;
import com.newtranx.task.mapper.TeamUserMapper;
import com.newtranx.task.service.ITaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.micrometer.core.instrument.binder.BaseUnits;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author AHui
 * @since 2021-01-29
 */
@Service
@Transactional
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {
    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TeamUserMapper teamUserMapper;
    @Override
    public boolean create(Task task) {

        boolean save = false;
        Long handledCount = task.getHandledCount();
        Long tatalCount = task.getTatalCount();
        if(handledCount>0){
            task.setStatus(1);
        }else if(handledCount == tatalCount){
            task.setStatus(2);
        }else{
            task.setStatus(0);
        }
        BigDecimal progress = BigDecimal.valueOf(0.00);
        if(tatalCount==0){
            throw new BusinessException(ResponseCode.WORDS_IS_EMPTY);
        }else{
            progress = new BigDecimal((handledCount*1.0/tatalCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        String fileIds = task.getFileIds();
        String types = task.getTypes();
        String[] typeslList = types.split(";");
        String[] fileIdlList = fileIds.split(";");
        if(fileIdlList.length>0){
            for(String filedId:fileIdlList){
                QueryWrapper<Task> wrapper = new QueryWrapper<>();
                //构造查询条，构建查询条件
                if (filedId !=null ) {
                    wrapper.eq("file_id",filedId);
                }
                if (task.getProjectId() !=null ) {
                    wrapper.eq("project_id",task.getProjectId());
                }
                List<Task> taskList = super.list(wrapper);
                if(taskList.size()>=3){
                    throw new BusinessException(ResponseCode.REPEATED_IS_PROCESS);
                }
               List<String> typestrings = new ArrayList<>();
                for(Task task12 :taskList){
                    String type = task12.getType();
                    typestrings.add(type);
                }
                List tasks = new ArrayList();
                if(typeslList.length>0){
                    for(String type:typeslList){
                        if(typestrings.contains(type)){
                            throw new BusinessException(ResponseCode.REPEATED_IS_COMPLETION);
                        }
                        Task task1 = new Task();
                        BeanUtils.copyProperties(task,task1);
                        task1.setFileId(Long.valueOf(filedId));
                        task1.setType(type);
                        task1.setProgress(progress);
                        tasks.add(task1);
                    }
                }
                save = super.saveBatch(tasks);
            }
        }

        return save;
    }

    @Override
    public boolean remove(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean update(Task task) {
        BigDecimal progress = BigDecimal.valueOf(0.00);
        Long handledCount=0L;
        if(task.getHandledCount()!=null) {
            handledCount = task.getHandledCount();
        }
        Long tatalCount = 0L;
        if(task.getTatalCount()!=null){
            tatalCount = task.getTatalCount();
            if(tatalCount==0){
                throw new BusinessException(ResponseCode.WORDS_IS_EMPTY);
            }else{
                progress = new BigDecimal((handledCount*1.0/tatalCount)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }
            if(handledCount>0){
                task.setStatus(1);
            }else if(handledCount == tatalCount){
                task.setStatus(2);
            }else{
                task.setStatus(0);
            }
            task.setProgress(progress);
        }




        return super.updateById(task);
    }

    @Override
    public Task get(Long id) {
        return super.getById(id);
    }

    @Override
    public TaskR getAllInfo(Long id) {
        if (null != id) {
            QueryWrapper<Task> wrapper = new QueryWrapper<>();
            wrapper.eq("t.task_id",id);
            wrapper.eq("t.is_del",0);
            wrapper.groupBy("t.task_id");
            return taskMapper.getAllInfo(wrapper);
        }
        return null;
    }

    @Override
    public IPage<Task> page(int current, int size, Task task) {
        Page<Task> page = new Page<>(current, size);
        LambdaQueryWrapper<Task> wrapper = new LambdaQueryWrapper<>();

        // TODO 查询
        // TODO 排序

        return super.page(page, wrapper);
    }
    @Override
    public IPage<TaskR> listByQuery(int current, int size, Task task) {
        Page<Task> page = new Page<>(current, size);
        // 查询条件
        if (null != task) {
            QueryWrapper<Task> wrapper = new QueryWrapper<>();
            //构造查询条，构建查询条件
            if (task.getUserIds() !=null ) {
                String[] split = task.getUserIds().split(",");
                List<String> resultList = new ArrayList<>(split.length);
                Collections.addAll(resultList,split);
                //wrapper.in("t.user_id",task.getUserIds());
                wrapper.in("t.user_id",resultList);
            }
            if (task.getUserId() !=null ) {
                wrapper.eq("t.user_id",task.getUserId());
            }
            if (task.getStatus() !=null ) {
                wrapper.eq("t.status",task.getStatus());
            }
            if (task.getType()!=null) {
                wrapper.eq("t.type",task.getType());
            }
            wrapper.eq("t.is_del",0);
            wrapper.groupBy("t.task_id");
            /**排序：sortColumns值不传就按三个字段倒序排序
             * sortColumns=1：按公司名称排序；sortColumns=2：按任务排序；sortColumns=3：按交付时间排序；sortColumns=4：按进度排序
             * sortType，排序方式：0顺序，1倒序
             */
            if(task.getSortColumns()!=null&&StringUtils.isNotEmpty(task.getSortColumns())){
                if(task.getSortType()!=null&&StringUtils.isNotEmpty(task.getSortType())){
                    if(task.getSortType().equals("1")){
                        if(task.getSortColumns().equals("1")){
                            wrapper.orderByDesc("t.create_time DESC,tm.team_name");
                        }else if(task.getSortColumns().equals("2")){
                            wrapper.orderByDesc("t.create_time DESC,t.type");
                        }else if(task.getSortColumns().equals("3")){
                            wrapper.orderByDesc("t.create_time DESC,t.due_time");
                        }else if(task.getSortColumns().equals("4")){
                            wrapper.orderByDesc("t.create_time DESC,t.progress");
                        }

                    }else{
                        if(task.getSortColumns().equals("1")){
                            wrapper.orderByAsc("t.create_time ASC,tm.team_name");
                        }else if(task.getSortColumns().equals("2")){
                            wrapper.orderByAsc("t.create_time ASC,t.type");
                        }else if(task.getSortColumns().equals("3")){
                            wrapper.orderByAsc("t.create_time ASC,t.due_time");
                        }else if(task.getSortColumns().equals("4")){
                            wrapper.orderByDesc("t.create_time ASC,t.progress");
                        }
                    }

                }

            }else{
                wrapper.orderByDesc("t.create_time DESC,t.due_time DESC,tm.team_name DESC,t.type DESC,t.progress");
            }
            return taskMapper.listByQuery(page, wrapper);
        }
        return taskMapper.listByQuery(page, null);
    }
    /**
     * 查询当前任务所有参与人员
     * @param teamId
     * @return
     */
    @Override
    public List<TeamUserR> selectTeamUser(Long teamId){
        return teamUserMapper.selectTeamUser(teamId);
    }

    @Override
    public List<TeamUser> selectUserIdsByUserId(Long userId) {
        return teamUserMapper.getTeamUserInfo(userId);
    }
}
