package com.crane.sharding.test;

/**
 * 类的生命周期是：加载->验证->准备->解析->初始化->使用->卸载，只有在准备阶段和初始化阶段才会涉及类变量的初始化和赋值，因此只针对这两个阶段进行分析；
 *
 * 类的准备阶段需要做是为类变量分配内存并设置默认值，因此类变量st为null、b为0；
 * （需要注意的是如果类变量是final，编译时javac将会为value生成ConstantValue属性，在准备阶段虚拟机就会根据ConstantValue的设置将变量设置为指定的值，
 * 如果这里这么定义：static final int b=112,那么在准备阶段b的值就是112，而不再是0了。）
 * @author syhe
 * @Title: StaticTest
 * @ProjectName sharding-demo
 * @Description: TODO
 * @date 2019-12-0416:58
 */
public class StaticTest {
    public static void main(String[] args) {
        staticFunction();
    }

    static  StaticTest staticTest = new StaticTest();
    static {
        System.out.println("1");
    }

    {
        System.out.println("2");
    }
    public static void staticFunction(){
        System.out.println("4");
    }

    StaticTest(){
        System.out.println("3");
        System.out.println("a="+a+",b="+b+",c="+c);
    }
    int a=110;
    static int b=112;
    static final int c=112;
}


//运行结果
 /*       2
        3
        a=110,b=0,c=112
        1
        4
  */

