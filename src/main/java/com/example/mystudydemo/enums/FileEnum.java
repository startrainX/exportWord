package com.example.mystudydemo.enums;

import lombok.Getter;

/**
 * @Description:文件下载枚举类
 * @Param:
 * @Return:
 * @Author: CCA
 * @Date: 2021/7/15 14:46
 */
public enum FileEnum {

    AR(1,"AR.rar"),
    CHROME32(2,"chrome_89-32.exe"),
    CHROME64(3,"chrome_89-64.exe"),
    DSS(4,"DSS.exe"),
    CZSC(5,"czsc.rar"),
    RYMB(6,"警员导入模板.xlsx");

    @Getter
    private Integer type;

    @Getter
    private String fileName;

    FileEnum(Integer type, String fileName) {
        this.type = type;
        this.fileName = fileName;
    }

    public static FileEnum forEach_FileEnum(int index) {
        FileEnum[] myArray = FileEnum.values();
        for (FileEnum element : myArray) {
            if (index == element.getType()) {
                return element;
            }
        }
        return null;
    }

}
