package net.echohool.echo;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty使用futures和回调的概念
 * 服务器端逻辑处理，由handler实现，不同的时间类型由不同的handler处理
 * 接收数据的channel handler必须继承ChannelInboundHandlerAdapter 
 * 并必须重写channelRead方法
 * 
 * 服务端要返回相同的消息给客户端,在服务器执行完成写操作前不能释放调用读取到的消息，因为写操作
 * 是异步的，只有写操作完成后 Netty才会自动释放消息
 * 
 * 此处的handler将客户端发送给服务器的数据返回给客户端
 * @author Administrator
 *
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	
	// 接收数据
	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg)
	{
		System.out.println("Server received: "+ msg);
		ctx.write(msg);
	}
	
	// 接收完成后的操作
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
	{
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	
	// 异常处理
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		cause.printStackTrace();
		ctx.close();
	}

}
