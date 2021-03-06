package br.helbertrios.guice.app.http;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

abstract class AbstractFileResolvingResourceFake extends AbstractResourceFake {

    /**
     * This implementation returns a File reference for the underlying class path
     * resource, provided that it refers to a file in the file system.
     *
     * @see org.springframework.util.ResourceUtils#getFile(java.net.URL, String)
     */
    @Override
    public File getFile() throws IOException {
        URL url = getURL();
        if (url.getProtocol().startsWith(ResourceUtilsFake.URL_PROTOCOL_VFS)) {
            return AbstractFileResolvingResourceFake.VfsResourceDelegate.getResource(url).getFile();
        }
        return ResourceUtilsFake.getFile(url, getDescription());
    }

    /**
     * This implementation determines the underlying File
     * (or jar file, in case of a resource in a jar/zip).
     */
    @Override
    protected File getFileForLastModifiedCheck() throws IOException {
        URL url = getURL();
        if (ResourceUtilsFake.isJarURL(url)) {
            URL actualUrl = ResourceUtilsFake.extractArchiveURL(url);
            if (actualUrl.getProtocol().startsWith(ResourceUtilsFake.URL_PROTOCOL_VFS)) {
                return AbstractFileResolvingResourceFake.VfsResourceDelegate.getResource(actualUrl).getFile();
            }
            return ResourceUtilsFake.getFile(actualUrl, "Jar URL");
        } else {
            return getFile();
        }
    }

    /**
     * This implementation returns a File reference for the given URI-identified
     * resource, provided that it refers to a file in the file system.
     *
     * @see org.springframework.util.ResourceUtils#getFile(java.net.URI, String)
     */
    protected File getFile(URI uri) throws IOException {
        if (uri.getScheme().startsWith(ResourceUtilsFake.URL_PROTOCOL_VFS)) {
            return AbstractFileResolvingResourceFake.VfsResourceDelegate.getResource(uri).getFile();
        }
        return ResourceUtilsFake.getFile(uri, getDescription());
    }


    @Override
    public boolean exists() {
        try {
            URL url = getURL();
            if (ResourceUtilsFake.isFileURL(url)) {
                // Proceed with file system resolution
                return getFile().exists();
            } else {
                // Try a URL connection content-length header
                URLConnection con = url.openConnection();
                customizeConnection(con);
                HttpURLConnection httpCon =
                        (con instanceof HttpURLConnection ? (HttpURLConnection) con : null);
                if (httpCon != null) {
                    int code = httpCon.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {
                        return true;
                    } else if (code == HttpURLConnection.HTTP_NOT_FOUND) {
                        return false;
                    }
                }
                if (con.getContentLength() >= 0) {
                    return true;
                }
                if (httpCon != null) {
                    // No HTTP OK status, and no content-length header: give up
                    httpCon.disconnect();
                    return false;
                } else {
                    // Fall back to stream existence: can we open the stream?
                    getInputStream().close();
                    return true;
                }
            }
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public boolean isReadable() {
        try {
            URL url = getURL();
            if (ResourceUtilsFake.isFileURL(url)) {
                // Proceed with file system resolution
                File file = getFile();
                return (file.canRead() && !file.isDirectory());
            } else {
                return true;
            }
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public long contentLength() throws IOException {
        URL url = getURL();
        if (ResourceUtilsFake.isFileURL(url)) {
            // Proceed with file system resolution
            return getFile().length();
        } else {
            // Try a URL connection content-length header
            URLConnection con = url.openConnection();
            customizeConnection(con);
            return con.getContentLength();
        }
    }

    @Override
    public long lastModified() throws IOException {
        URL url = getURL();
        if (ResourceUtilsFake.isFileURL(url) || ResourceUtilsFake.isJarURL(url)) {
            // Proceed with file system resolution
            try {
                return super.lastModified();
            } catch (FileNotFoundException ex) {
                // Defensively fall back to URL connection check instead
            }
        }
        // Try a URL connection last-modified header
        URLConnection con = url.openConnection();
        customizeConnection(con);
        return con.getLastModified();
    }

    /**
     * Customize the given {@link URLConnection}, obtained in the course of an
     * {@link #exists()}, {@link #contentLength()} or {@link #lastModified()} call.
     * <p>Calls {@link ResourceUtilsFake#useCachesIfNecessary(URLConnection)} and
     * delegates to {@link #customizeConnection(HttpURLConnection)} if possible.
     * Can be overridden in subclasses.
     *
     * @param con the URLConnection to customize
     * @throws IOException if thrown from URLConnection methods
     */
    protected void customizeConnection(URLConnection con) throws IOException {
        ResourceUtilsFake.useCachesIfNecessary(con);
        if (con instanceof HttpURLConnection) {
            customizeConnection((HttpURLConnection) con);
        }
    }

    /**
     * Customize the given {@link HttpURLConnection}, obtained in the course of an
     * {@link #exists()}, {@link #contentLength()} or {@link #lastModified()} call.
     * <p>Sets request method "HEAD" by default. Can be overridden in subclasses.
     *
     * @param con the HttpURLConnection to customize
     * @throws IOException if thrown from HttpURLConnection methods
     */
    protected void customizeConnection(HttpURLConnection con) throws IOException {
        con.setRequestMethod("HEAD");
    }


    /**
     * Inner delegate class, avoiding a hard JBoss VFS API dependency at runtime.
     */
    private static class VfsResourceDelegate {

        public static ResourceFake getResource(URL url) throws IOException {
            return new VfsResourceFake(VfsUtilsFake.getRoot(url));
        }

        public static ResourceFake getResource(URI uri) throws IOException {
            return new VfsResourceFake(VfsUtilsFake.getRoot(uri));
        }
    }
}
