package com.heller.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

public class DigestUtilsMd5Test {

    /**
     * 空白字符串是能计算 md5 的，而且长度还是正常 md5 的长度
     */
    @Test
    void testBlankStringMd5() {
        String md5Hex = DigestUtils.md5Hex("");
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", md5Hex);
    }

    /**
     * null 计算 md5 不行，直接 NullPointerException
     * 而且还要加上强制类型转化 （String） ，否则报错，因为Java编译静态分析无法定位到合适的调用方法
     */
    @Test
    void testNullStringMd5() {
        assertThrows(NullPointerException.class, () -> DigestUtils.md5Hex((String) null));
    }

}
