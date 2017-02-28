package net.tobebetter.lambda;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static javafx.scene.input.KeyCode.T;

/**
 * 函数接口
 * 对于只一个方法的接口，匿名实时时，lambda表达式可根据参数类型推断出具体接口及使用的方法
 *
 * 目前对集合类用到的3个方法：
 * filter:筛选
 * map:修改数值
 * reduce:累加
 */
public class LambdaUtil {

    public static void sortListDesc(List<Integer> numbers){
        Collections.sort(numbers, (x, y) -> y - x);
    }

    public static List<Integer> filterList(List<Integer> numbers){
        /**
         * 原代码
         */
        Predicate<Integer> matched = new Predicate<Integer>() {
            public boolean test(Integer s) {
                return s > 2;
            }
        };
        numbers.stream().filter(matched);
        /**
         * lambda
         */
        return numbers.stream().filter(s -> s > 2).collect(Collectors.toList());
    }


    public static List<Integer> mapList(List<Integer> numbers){
        /**
         * 原代码
         */
        Function<Integer, Integer> function = new Function() {
            @Override
            public Object apply(Object o) {
                return (Integer)o + 2;
            }
        };

        List<Integer> list = numbers.stream().map(function).collect(Collectors.toList());
        list.forEach(System.out::print);
        /**
         * lambda
         */
        return numbers.stream().map(s -> s + 2).collect(Collectors.toList());
    }

    public static  long sum(List<Integer> numbers){
        return numbers.stream().reduce((result, opertion) -> result += opertion).get();
    }


    /**
     * 从lambda 表达式引用的本地变量必须是最终变量或实际上的最终变量
     */
    public static void getTotal(List<Integer> numbers){
//        long total = 0l;
//        numbers.forEach((number)->total+=number);
    }
}