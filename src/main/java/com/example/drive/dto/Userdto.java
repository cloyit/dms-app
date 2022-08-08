package com.example.drive.dto;

import com.example.drive.entity.User;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class Userdto extends User implements Serializable {

    @ApiModelProperty("未通过帖子总数")
    private Integer unpassNum;

}
