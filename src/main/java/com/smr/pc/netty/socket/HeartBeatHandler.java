package com.smr.pc.netty.socket;

import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * HeartBeat Handler
 *
 * @author QJ
 * @date 2018/12/24
 */
public class HeartBeatHandler extends ChannelHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "read idle";
                    break;
                case WRITER_IDLE:
                    eventType = "write idle";
//                    pong(ctx);
                    break;
                case ALL_IDLE:
                    eventType = "all idle";
//                    close(ctx);
                    break;
                default:
            }

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 发送心跳
     *
     * @param ctx
     */
    private void pong(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new TextWebSocketFrame("pong:" + System.currentTimeMillis()));
    }

    /**
     * 关闭连接
     *
     * @param ctx
     */
    private void close(ChannelHandlerContext ctx) {
        ChannelFuture cf = ctx.writeAndFlush(new TextWebSocketFrame("Timeout! Connection will close"));
      /*  cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                ctx.channel().close();
            }
        });*/

        cf.addListener(ChannelFutureListener.CLOSE);
    }


}
