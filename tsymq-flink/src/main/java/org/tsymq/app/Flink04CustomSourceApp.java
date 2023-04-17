package org.tsymq.app;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.tsymq.model.VideoOrder;
import org.tsymq.source.VideoOrderSource;

/**
 * 自定义source
 */
public class Flink04CustomSourceApp {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<VideoOrder> VideoOrderDs = env.addSource(new VideoOrderSource());
        VideoOrderDs.print();

        env.execute("source job");

    }

}
