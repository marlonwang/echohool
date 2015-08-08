package net.echohool.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/*
 * 客户端业务逻辑的实现 交由Handler处理
 * 定义一个继承SimpleChannelInboundHandler 
 * 重写父类的channelActive(), channelRead0(), exceptionCaught()
 * 处理 连接服务器后、接收数据、异常时
 * @author Administrator
 */

/**
 * 客户端使用SimpleChannelInboundHandler 而不是ChannelInboundHandlerAdapter
 * 原因：
 * ChannelInboundAdapter在处理完消息后需要释放资源，这里调用ByteBuf.release()释放资源
 * SimpleChannelInboundHandler在完成channelRead0()后释放消息
 * 这是通过Netty处理所有消息的ChannelHandler实现了RefrenceCounted接口达到的
 * 
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx)
	{
		ctx.write(Unpooled.copiedBuffer("Netty rocks!",CharsetUtil.UTF_8));
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx,ByteBuf msg)
	{
		System.out.println("Client received: "+ ByteBufUtil.hexDump(
				msg.readBytes(msg.readableBytes())));
		for (; ; ) 
		{
			ctx.channel().write("sending msg to server at "+System.currentTimeMillis());
			try 
			{
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		cause.printStackTrace();
		ctx.close();
	}
}
