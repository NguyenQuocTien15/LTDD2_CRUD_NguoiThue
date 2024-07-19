package com.example.ltdd2_crud_nguoithue.Models;

import java.io.Serializable;

public class San implements Serializable {
    String idSan, tenSan;

    public San() {
    }

    public San(String idSan, String tenSan) {
        this.idSan = idSan;
        this.tenSan = tenSan;
    }

    public String getIdSan() {
        return idSan;
    }

    public void setIdSan(String idSan) {
        this.idSan = idSan;
    }

    public String getTenSan() {
        return tenSan;
    }

    public void setTenSan(String tenSan) {
        this.tenSan = tenSan;
    }



    @Override
    public String toString() {
        return "San{" +
                "idSan='" + idSan + '\'' +
                ", tenSan='" + tenSan + '\'' +
                '}';
    }
}
