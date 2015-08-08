package net.echohool.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty服务器端的启动配置
 * 
 * 启动服务器由serverBootstrap对象管理
 * 此处要配置的选项：
 * 1. 端口
 * 2. 线程模式
 * 3. 时间循环
 * 4. 逻辑处理程序handler
 * 
 * @author Administrator
 *
 */
public class EchoServer {
	
	private final int port;
	
	public EchoServer(int port){
		this.port = port;
	}
	
	public void start() throws InterruptedException{
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			// ServerBootstrap 实例
			ServerBootstrap sb = new ServerBootstrap();
			
			ChannelInitializer<Channel> initializer =new ChannelInitializer<Channel>() {

				// 配置业务处理handler
				@Override
				protected void initChannel(Channel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new EchoServerHandler());
				}
				
			};
			// 非阻塞传输 local socket address
			// 添加 handler 到 channel pipeline
			sb.group(group).channel(NioServerSocketChannel.class).localAddress(port)
				.childHandler(initializer);
			
			// 绑定server， 等待server 关闭并释放资源
			ChannelFuture future = sb.bind().sync();
			System.out.println(EchoServer.class.getName() + "started and listen on "+
					future.channel().localAddress());
			future.channel().closeFuture().sync();
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			group.shutdownGracefully().sync();
		}
	}
	
/*	public static void main(String[] args) throws Exception
	{
		// 指定端口 65535
		new EchoServer(45535).start();
	}*/
}
