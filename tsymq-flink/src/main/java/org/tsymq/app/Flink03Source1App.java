package org.tsymq.app;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

public class Flink03Source1App {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> ds1 = env.fromElements("java,springboot", "java,springcloud");
        ds1.print("ds1:");

        DataStream<String> ds2 = env.fromCollection(Arrays.asList("java,springboot", "java,springcloud"));
        ds2.print("ds2:");

        //设置并行度
        env.setParallelism(1);
        DataStream<Long> ds3 = env.fromSequence(1,10);
        ds3.print("ds3:");

        env.execute("source job");

    }

}
