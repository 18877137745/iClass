package com.example.hp.iclass.HttpFunction.Function.Teacher_Function;

import com.example.hp.iclass.HttpFunction.Function.IPCondition;
import com.example.hp.iclass.HttpFunction.Thread.Teacher_Thread.UpdateStartTime;
import com.example.hp.iclass.OBJ.SubjectOBJ;

/**
 * Created by spencercjh on 2017/11/30.
 * iClass
 */

public class Fun_UpdateStartTime {

    public static void http_UpdateStartTime(SubjectOBJ subjectOBJ) throws InterruptedException {
        String ip = IPCondition.server_ip;
        String url = ip + "iClass_Sever/UpdateStartTime";
        UpdateStartTime thread = new UpdateStartTime(url, subjectOBJ.getSubject_id());
        thread.start();
        thread.join();
    }
}
