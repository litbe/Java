package DesignPattern.Command;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Constructor;

public class Processer {
	public static void main(String[] args) {
		while (true) {
			System.out.println("<request>");

			String request = null;

			try {
				char requestChar[] = new char[128];
				InputStreamReader isr = new InputStreamReader(System.in);
				int length = isr.read(requestChar, 0, 128);
				request = (new String(requestChar, 0, length)).trim();
			} catch (IOException e) {
				System.err.println("Error: " + e);
				break;
			}

			try {
				requestHandler(request, System.out);
			} catch (ExitException e) {
				break;
			} catch (UnknownRequestException e) {
				System.err.println("Unknown request: " + request);
			}

		}
	}

	public static void requestHandler(String request, PrintStream console)
			throws ExitException, UnknownRequestException {

		if (request.equals(""))
			return;

		if (request.startsWith("exit"))
			throw new ExitException();

		else {
			int index = request.indexOf(' ');

			String requestName = request.substring(0, index == -1 ? request.length() : index);

			String commandClassName = "DesignPattern.Command." + request.substring(0, 1).toUpperCase()
					+ requestName.substring(1) + "Command";

			Class c = null;

			try {
				c = Class.forName(commandClassName);
			} catch (ClassNotFoundException e) {
				throw new UnknownRequestException();
			}

			Command com = null;
			try {
				Constructor con = c.getConstructor(new Class[] { String.class, PrintStream.class });
				com = (Command) con.newInstance(new Object[] { request, console });
			} catch (Exception e) {
				throw new UnknownRequestException();
			}

			com.excute();
		}
	}
}

class UnknownRequestException extends Exception {
}

class ExitException extends Exception {
}
