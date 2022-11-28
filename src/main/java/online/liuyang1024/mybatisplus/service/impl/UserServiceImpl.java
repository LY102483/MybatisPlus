package online.liuyang1024.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.liuyang1024.mybatisplus.POJO.User;
import online.liuyang1024.mybatisplus.mapper.UserMapper;
import online.liuyang1024.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by LiuYang on 2022/10/25 16:30
 * Email:1024839103ly@gmail.com
 */
@Service
//倡导自己创建service，这样既可以使用通用的service方法，还能自己定义service方法
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
