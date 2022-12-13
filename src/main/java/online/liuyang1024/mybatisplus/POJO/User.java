package online.liuyang1024.mybatisplus.POJO;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import online.liuyang1024.mybatisplus.enums.SexEnum;

/**
 * Created by LiuYang on 2022/09/17 15:59
 * Email:1024839103ly@gmail.com
 */

@Data
// @TableName("")  //MybatisPlus默认是实体类名对应表名，但是当实体类名和表名不同时，可以通过这个注解设置实体类所对应的表名
public class User {



    // @TableId注解的value属性用于指定主键的字段，type属性设置主键的生成策略
    //在前面的测试中我们发现，MybatisPlus会默认把数据库中id列作为主键，其值由雪花算法进行生成，但是假如我们将该列命名为uid,
    // 那么MybatisPlus就会找不到主键，我们便需要通过@TableId这个注解来告诉MybatisPlus这个属性为主键
    // @TableId   //将这个属性所对应的字段作为主键
    // private Long uid;

    //假设数据库主键名为uid,但是实体类中主键命名为id，那么需要注解：
    // @TableId(value = "uid")

    //假设不使用mybatisplus提供的雪花算法自动生成id，使用数据库的自增策略
    // 首先需要将数据库中ID设置为自动递增，然后使用注解
    // @TableId(type = IdType.AUTO)

    //一些注意事项，例如在创建对象的时候就指定了ID，那么就不会使用mybatis的任何ID策略(雪花算法，主键自增)
    private Long id;


    //制定属性所对应的字段名
    //假设数据库中某列命名为user_name，但是实体类中命名为name，此时需要使用注解
    // @TableField("user_name")
    private String name;

    private Integer age;

    private String email;

    @TableLogic       //该注解表示此属性为逻辑删除，0表示未删除，1表示删除，进行逻辑删除后，查询也会失效。
    // SELECT id,name,age,email,is_deleted FROM user WHERE id IN ( ? , ? , ? ) AND is_deleted=0
    private Integer isDeleted;

    private SexEnum sex;
}
