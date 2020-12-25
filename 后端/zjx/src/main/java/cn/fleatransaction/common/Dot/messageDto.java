package cn.fleatransaction.common.Dot;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class messageDto {
    private String userAvatar;

    private String userName;

    private String message;

    private Timestamp messageTime;

}
