package com.bindada.other;

public enum StatusSources {

    CHECK("待审核"),
    CHECKED("审核通过"),
    UNCHECKED("审核未过");

    StatusSources(String status){
        this.value = status;
    }

    /**
     *  资源审核状态
     */
    public String value;
}
