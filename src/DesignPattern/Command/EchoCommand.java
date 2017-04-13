package DesignPattern.Command;

import java.io.PrintStream;

public class EchoCommand implements Command{
	String request;
	PrintStream console;
	
	public EchoCommand(String request, PrintStream console) {
		this.request = request;
		this.console = console;
	}
	
	public void excute() {
		String echoStr = request.substring("bigecho".length()).trim().toUpperCase();
		console.println(echoStr);
	}
	
}
