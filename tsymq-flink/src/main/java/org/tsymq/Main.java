package org.tsymq;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class Main {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> stringDataStream = env.fromElements("java,springboot", "java,springcloud");
        DataStream<String> map = stringDataStream.flatMap((new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] splits = s.split(",");
                for (String split : splits) {
                    collector.collect(split);
                }
            }
        }));
        map.print("结果");
        env.execute("first test job");
    }
}