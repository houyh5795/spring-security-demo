package cn.houyuehui.mapper;

import cn.houyuehui.dto.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @Author: jsonhou
 * @Date: 2019/10/31 8:12
 */
@Component
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username=#{username}")
    User findByUsername(@Param("username") String username);
}
