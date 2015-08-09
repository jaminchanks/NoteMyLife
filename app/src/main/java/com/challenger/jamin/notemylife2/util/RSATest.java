package com.challenger.jamin.notemylife2.ui.util; /**
 * Created by jamin on 4/27/15.
 *
 * 密钥p,q长度为2048位
 * 暂不支持中文加密
 * 理论上主持很长很长的字符串和纯数字加密,太长的请分组加密
 */


import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSATest {
    private BigInteger p;
    private BigInteger q;
    private BigInteger Yn;
    private BigInteger d;
    public BigInteger e = new BigInteger("65537");
    public BigInteger n;
    private final int BLOCK = 32;

    private static BigInteger x;    //x, y用于扩展欧几里得算法
    private static BigInteger y;

    public RSATest(){
        generateKey();
    }

    /********************打印公钥和密钥信息**************************/
    private void printKeys() {
        System.out.println("/****************密钥信息**********************/");
        System.out.println("公钥:");
        System.out.println("  e:  " + e.toString());
        System.out.println("  n:  " + n.toString());
        System.out.println("密钥:");
        System.out.println("  p:  " + p.toString());
        System.out.println("  q:  " + q.toString());
        System.out.println("  d:  " + d.toString());
        System.out.println("测试: ed % Yn = " + e.multiply(d).mod(Yn));
        System.out.println("/*********************************************/");
    }

    /*******************加密文字*********************/
    public String encryptString(String str) {
        if (str.equals(""))
            str = " ";
        //若字符串不为空,此处理论上应有字符串的切片分组, 以保证可以加密任意长度的字符串...
        // 但String字符串的长度本身就是有限的(str.length() 返回int型数组),所以就不写了
        byte[] bytes = str.getBytes();
        BigInteger strToBigInt = new BigInteger(bytes);
        BigInteger c = strToBigInt.modPow(e, n);  //C = M^e % n
        return c.toString();
    }

    /*******************解密文字***********************/
    public String decryptString(String str) {
        if (str.equals(""))
            str = " ";
        BigInteger bigInt = new BigInteger(str);
        BigInteger m = bigInt.modPow(d, n); // M = C^d % n
        byte[] bytes = m.toByteArray();
        return new String(bytes);
    }

    /*******************加密纯数字**************************/
    public String encrypt(String str) {
        BigInteger bigInt = new BigInteger(str);
        BigInteger c = bigInt.modPow(e, n); //C = M^e % n
        return c.toString();
    }
    /*******************解密纯数字*************************/
    public String decrypt(String str) {
        BigInteger bigInt = new BigInteger(str);
        BigInteger m = bigInt.modPow(d, n); // M = C^d % n
        return m.toString();
    }

    /*****************生成密钥*************************/
    public void generateKey() {
        p = getPrime();  //生成p
        q = getPrime();  //生成q

        n = p.multiply(q);   //计算n的值
        Yn = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));   //计算Yn的值
        gcdEx(e, Yn);
        //利用欧几里得算法求d
        d = x;
    }

    /*****************生成一个指定位数的素数*************************/
    public static BigInteger getPrime() {
        return BigInteger.probablePrime(2048, new Random());
    }

    /*****************扩展欧几里得算法*************************/
    public static BigInteger gcdEx(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            x = BigInteger.ONE;
            y = BigInteger.ZERO;
            return a;
        } else {
            BigInteger r = gcdEx(b, a.mod(b));
            BigInteger t = y;
            y = x.subtract(a.divide(b).multiply(y));
            x = t;
            return r;
        }
    }


    public static void main(String[] args) {
        RSATest rsa = new RSATest();
        rsa.printKeys();
        while (true){
            System.out.print("请输入明文:");
            Scanner scanner = new Scanner(System.in);
            String text = scanner.nextLine();

            String cipherText = rsa.encryptString(text);
            System.out.println("加密后:\n" + cipherText);
            System.out.println("解密后:\n" + rsa.decryptString(cipherText) + "\n");

            /*
            String cipherText = rsa.Encrypt(text);
            System.out.println("加密后:    " + cipherText);
            System.out.println("解密后:    " + rsa.Decrypt(cipherText));
            */
        }
    }
}
