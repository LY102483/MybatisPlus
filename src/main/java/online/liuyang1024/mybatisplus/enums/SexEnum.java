package online.liuyang1024.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum SexEnum {
    MALE(1,"男"),
    FEMALE(2,"女");

    @EnumValue//标记数据库存的值是sex
    private Integer sex;

    private String sexName;

    SexEnum(Integer sex,String sexName){
        this.sex=sex;
        this.sexName=sexName;
    }

}
