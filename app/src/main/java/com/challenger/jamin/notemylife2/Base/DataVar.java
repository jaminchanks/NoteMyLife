package com.challenger.jamin.notemylife2.Base;

import android.os.Environment;

import java.io.File;

/**
 * Created by jamin on 8/12/15.
 */
public interface DataVar {
    String APP_ROOT_FILE =  Environment.getExternalStorageDirectory().getPath()
            + File.separator + "NoteMyLife";
    String APP_IMG_FILE = APP_ROOT_FILE + File.separator + "img";

    //把当前用户的登陆信息保存到本地时的键
    String SP_FILE = "USER_DATA";
    String SP_USER_ID = "userId";
    String SP_EMAIL = "userAccount";
    String SP_PASSWORD = "userPassword";
    String SP_HEAD = "head";
    String INIT_HEAD_IMAGE_FILE_NAME = "0.png";
}
