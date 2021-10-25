package br.helbertrios.guice.app.http;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class VfsResourceFake extends AbstractResourceFake {
    private final Object resource;


    /**
     * Create a new {@code VfsResource} wrapping the given resource handle.
     *
     * @param resource a {@code org.jboss.vfs.VirtualFile} instance
     *                 (untyped in order to avoid a static dependency on the VFS API)
     */
    public VfsResourceFake(Object resource) {
        this.resource = resource;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        return VfsUtilsFake.getInputStream(this.resource);
    }

    @Override
    public boolean exists() {
        return VfsUtilsFake.exists(this.resource);
    }

    @Override
    public boolean isReadable() {
        return VfsUtilsFake.isReadable(this.resource);
    }

    @Override
    public URL getURL() throws IOException {
        try {
            return VfsUtilsFake.getURL(this.resource);
        } catch (Exception ex) {
            throw new NestedIOExceptionFake("Failed to obtain URL for file " + this.resource, ex);
        }
    }

    @Override
    public URI getURI() throws IOException {
        try {
            return VfsUtilsFake.getURI(this.resource);
        } catch (Exception ex) {
            throw new NestedIOExceptionFake("Failed to obtain URI for " + this.resource, ex);
        }
    }

    @Override
    public File getFile() throws IOException {
        return VfsUtilsFake.getFile(this.resource);
    }

    @Override
    public long contentLength() throws IOException {
        return VfsUtilsFake.getSize(this.resource);
    }

    @Override
    public long lastModified() throws IOException {
        return VfsUtilsFake.getLastModified(this.resource);
    }

    @Override
    public ResourceFake createRelative(String relativePath) throws IOException {
        if (!relativePath.startsWith(".") && relativePath.contains("/")) {
            try {
                return new VfsResourceFake(VfsUtilsFake.getChild(this.resource, relativePath));
            } catch (IOException ex) {
                // fall back to getRelative
            }
        }

        return new VfsResourceFake(VfsUtilsFake.getRelative(new URL(getURL(), relativePath)));
    }

    @Override
    public String getFilename() {
        return VfsUtilsFake.getName(this.resource);
    }

    @Override
    public String getDescription() {
        return "VFS resource [" + this.resource + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == this || (obj instanceof VfsResourceFake && this.resource.equals(((VfsResourceFake) obj).resource)));
    }

    @Override
    public int hashCode() {
        return this.resource.hashCode();
    }
}
