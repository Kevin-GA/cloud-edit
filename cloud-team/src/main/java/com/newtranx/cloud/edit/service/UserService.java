package com.newtranx.cloud.edit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.newtranx.cloud.edit.entities.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ywj
 * @since 2021-01-26
 */
public interface UserService extends IService<User> {

    User findByUsername(String username);

    User getByRefUserId(String refUserId);

}
