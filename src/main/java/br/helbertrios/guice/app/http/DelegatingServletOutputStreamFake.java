package br.helbertrios.guice.app.http;


import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class DelegatingServletOutputStreamFake extends ServletOutputStream {

    private final OutputStream targetStream;


    /**
     * Create a DelegatingServletOutputStream for the given target stream.
     *
     * @param targetStream the target stream (never {@code null})
     */
    public DelegatingServletOutputStreamFake(OutputStream targetStream) {
        this.targetStream = targetStream;
    }

    /**
     * Return the underlying target stream (never {@code null}).
     */
    public final OutputStream getTargetStream() {
        return this.targetStream;
    }


    @Override
    public void write(int b) throws IOException {
        this.targetStream.write(b);
    }

    @Override
    public void flush() throws IOException {
        super.flush();
        this.targetStream.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.targetStream.close();
    }
}
