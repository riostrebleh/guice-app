package br.helbertrios.guice.app.http;

import java.io.IOException;
import java.io.InputStream;

interface InputStreamSourceFake {

    InputStream getInputStream() throws IOException;

}
