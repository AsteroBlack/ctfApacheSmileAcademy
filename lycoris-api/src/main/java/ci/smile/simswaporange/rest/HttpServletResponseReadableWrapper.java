package ci.smile.simswaporange.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class HttpServletResponseReadableWrapper extends HttpServletResponseWrapper {

	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private PrintWriter writer= new PrintWriter(outputStream); ;

	public HttpServletResponseReadableWrapper(HttpServletResponse response) throws IOException {
		super(response);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {
			@Override
			public void write(int b) throws IOException {
				outputStream.write(b);
			}

			@Override
			public void write(byte[] b) throws IOException {
				outputStream.write(b);
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void setWriteListener(WriteListener arg0) {
				// TODO Auto-generated method stub

			}
		};
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (writer != null) {
			writer.flush();
		} else if (outputStream != null) {
			outputStream.flush();
		}
	}

	public String getResponseData() {
		System.out.println("ici" + outputStream.toString());
		return outputStream.toString();
	}

	/*@Override
	public String getHeader(String headerName) {
		System.out.println("response 6");
		System.out.println("headerName : " + headerName);
		String headerValue = super.getHeader(headerName);
		System.out.println("headerValue : " + headerValue);
		if (headerName.equalsIgnoreCase("Accept")) {
			System.out.println("headerName" + "Accept");
			if (headerValue != null) {
				headerValue.replaceAll(MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE);
			} else {
				headerValue = MediaType.TEXT_PLAIN_VALUE;
			}
		} else if (headerName.equalsIgnoreCase("Content-Type")) {
			System.out.println("testtttt 3");
			if (headerValue != null) {
				headerValue.replaceAll(MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE);
			} else {
				headerValue = MediaType.TEXT_PLAIN_VALUE;
			}
		}
		System.out.println("headerValue2 : " + headerValue);
		return headerValue;
	}

	@Override
	public String getContentType() {
		System.out.println("response 7");
		String contentTypeValue = super.getContentType();
		System.out.println("contentTypeValue : " + contentTypeValue);
		if (contentTypeValue != null) {
			if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(contentTypeValue)) {
				contentTypeValue = MediaType.TEXT_PLAIN_VALUE;
				System.out.println("contentTypeValue3 : " + contentTypeValue);
			}
		} else {
			contentTypeValue = MediaType.TEXT_PLAIN_VALUE;
		}
		System.out.println("contentTypeValue2 : " + contentTypeValue);
		return contentTypeValue;
	}*/
}
