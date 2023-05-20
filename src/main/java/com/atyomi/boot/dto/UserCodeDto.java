package com.atyomi.boot.dto;

import com.atyomi.boot.domain.User;
import lombok.Data;

@Data
public class UserCodeDto extends User {
    private Integer code;
}
