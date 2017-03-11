package net.tobebetter.coding;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * Created by 0513 on 2017/3/11.
 * ASCII： 美国标准信息交换码。用一个字节的7位可以表示
 * ISO8859-1： 拉丁码表（Latin-1）。欧洲码表用一个字节的8位表示
 * GB2312： 中国的中文编码表
 * GBK： 中国的中文编码表升级，融合了更多的中文文字符号
 * Unicode： 国际标准码，融合了多种文字。所有文字都用两个字节来表示,Java语言就是unicode
 * UTF-8： 最多用三个字节来表示一个字符
 */
public class StringTest {
    @Test
    public void sameCode() {
        String original = "编码ABC";
        byte[] utf8 = original.getBytes(Charset.forName("utf-8"));
        Assert.assertEquals(original, new String(utf8, Charset.forName("utf-8")));
        //字符集不包含的情况
        byte[] ASCII = original.getBytes(Charset.forName("ASCII"));
        Assert.assertNotEquals(original, new String(ASCII, Charset.forName("ASCII")));
        //默认编码
        String defaultCharsetName = Charset.defaultCharset().displayName(); //这里由系统所决定
        //System.out.println(defaultCharsetName);
        byte[] defaultCharset = original.getBytes(Charset.forName(defaultCharsetName));
        Assert.assertEquals(original, new String(defaultCharset));
    }
    @Test
    public void diffCode(){
        String original = "编码ABC";
        byte[] GB2312 = original.getBytes(Charset.forName("GB2312"));
        Assert.assertEquals(original, new String(GB2312, Charset.forName("GBK"))); //GB2312为GBK子集，编码相同
        Assert.assertNotEquals(original, new String(GB2312, Charset.forName("utf-8"))); //编码不同
    }

    @Test
    public void utf8Length(){
        String zh = "中";
        byte[] utf8 = zh.getBytes(Charset.forName("utf-8"));
        Assert.assertEquals(3, utf8.length);
        String en = "A";
        byte[] utf8_en = en.getBytes(Charset.forName("utf-8"));
        Assert.assertEquals(1, utf8_en.length);
    }

    @Test
    public void gbkLength(){
        String zh = "中";
        byte[] GBK = zh.getBytes(Charset.forName("GBK"));
        Assert.assertEquals(2, GBK.length);
        String en = "A";
        byte[] GBK_en = en.getBytes(Charset.forName("GBK"));
        Assert.assertEquals(1, GBK_en.length);
    }

    @Test
    public void unicode(){
        byte[] s = "中".getBytes(Charset.forName("Unicode"));
        printBytes(s);
        System.out.println(new String(s, Charset.forName("Unicode")));
        System.out.println(new String(s, Charset.forName("utf-8")));
    }


    /**
     * 打印byte数组
     * @param data
     */
    private static void printBytes( byte[] data) {
        for (byte b : data) {
            System.out.print("0x" + toHexString(b) + " ");
        }
        System.out.println();
    }

    private static String toHexString(byte value) {
        String tmp = Integer.toHexString(value & 0xFF);
        if (tmp.length() == 1) {
            tmp = "0" + tmp;
        }

        return tmp.toUpperCase();
    }
}
