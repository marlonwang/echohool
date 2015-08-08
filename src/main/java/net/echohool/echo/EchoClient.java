package net.echohool.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {
	
	private final String host;
	private final int port;
	
	public EchoClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
	public void start() throws Exception
	{
		// EventLoopGroup 可理解为线程池 用来处理连接、接收/发送数据
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			// bootstrap实例 引导启动客户端
			Bootstrap bs = new Bootstrap();
			
			// InetSocketAddress 指定连接的服务器地址
			InetSocketAddress address = new InetSocketAddress(host,port);
			
			// ChannelHandler 客户端成功连接服务器后被执行
			bs.group(group).channel(NioSocketChannel.class).remoteAddress(address)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch)
					{
						ch.pipeline().addLast(new EchoClientHandler());
					}
				});
			
			// 调用connect连接服务器
			ChannelFuture future = bs.connect().sync();
			future.channel().closeFuture().sync();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			// 关闭EventLoopGroup释放资源
			group.shutdownGracefully().sync();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new EchoClient("localhost",12345).start();
		
	}
}
