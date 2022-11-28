package online.liuyang1024.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.liuyang1024.mybatisplus.POJO.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * Created by LiuYang on 2022/09/17 16:12
 * Email:1024839103ly@gmail.com
 */

@Mapper
public interface UserMapper extends BaseMapper<User>  {

    /**
     * 根据用户ID查询用户信息为map集合
     * @param id
     * @return
     */
    Map<String,Object> selectMapById(Long id);

}
