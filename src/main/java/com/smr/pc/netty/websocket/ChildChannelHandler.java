package com.smr.pc.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 自定义管道Handler类
 *
 * @author QJ
 * @date 2018/12/21
 *
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel e) throws Exception {

        //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        e.pipeline().addLast("http-codec",new HttpServerCodec());

        //netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
        //默认http请求是分段传输的  httpheaders + httpcontent
        e.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));

        //以块的方式来写的处理器
        e.pipeline().addLast("http-chunked",new ChunkedWriteHandler());

        //ws://server:port/context_path
        //ws://localhost:9999/ws
        //参数指的是contex_path
        // 不加这个handle 默认http请求握手
//        e.pipeline().addLast(new WebSocketServerProtocolHandler("/spotws"));

        //websocket定义了传递数据的6中frame类型
//        e.pipeline().addLast(new TextWebSocketFrameHa());

        // 1.
        // 2.
        // 3.
        // 4.
        // 5.
        // 6.



        // 空闲检测  没改好
//        e.pipeline().addLast("heart-beat",new IdleStateHandler(5,7,10,TimeUnit.SECONDS));
//        e.pipeline().addLast("heart-beat-handler",new MyHeartBeatHandler());






        // 自定义的handler
        e.pipeline().addLast("handler",new MyWebSocketServerHandler());
    }

}
