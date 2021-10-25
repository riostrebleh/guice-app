package br.helbertrios.guice.app.http;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class HttpServletRequestFake implements HttpServletRequest {

    /**
     * The default protocol: 'HTTP/1.1'.
     *
     * @since 4.3.7
     */
    public static final String DEFAULT_PROTOCOL = "HTTP/1.1";
    /**
     * The default server address: '127.0.0.1'.
     */
    public static final String DEFAULT_SERVER_ADDR = "127.0.0.1";
    /**
     * The default server name: 'localhost'.
     */
    public static final String DEFAULT_SERVER_NAME = "localhost";
    /**
     * The default server port: '80'.
     */
    public static final int DEFAULT_SERVER_PORT = 80;
    /**
     * The default remote address: '127.0.0.1'.
     */
    public static final String DEFAULT_REMOTE_ADDR = "127.0.0.1";
    /**
     * The default remote host: 'localhost'.
     */
    public static final String DEFAULT_REMOTE_HOST = "localhost";
    private static final String HTTP = "http";
    /**
     * The default scheme: 'http'.
     *
     * @since 4.3.7
     */
    public static final String DEFAULT_SCHEME = HTTP;
    private static final String HTTPS = "https";


    // ---------------------------------------------------------------------
    // Public constants
    // ---------------------------------------------------------------------
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String HOST_HEADER = "Host";
    private static final String CHARSET_PREFIX = "charset=";
    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
    private static final ServletInputStream EMPTY_SERVLET_INPUT_STREAM =
            new DelegatingServletInputStreamFake(StreamUtilsFake.emptyInput());
    private static final BufferedReader EMPTY_BUFFERED_READER =
            new BufferedReader(new StringReader(""));
    /**
     * Date formats as specified in the HTTP RFC.
     *
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section 7.1.1.1 of RFC 7231</a>
     */
    private static final String[] DATE_FORMATS = new String[]{
            "EEE, dd MMM yyyy HH:mm:ss zzz",
            "EEE, dd-MMM-yy HH:mm:ss zzz",
            "EEE MMM dd HH:mm:ss yyyy"
    };


    // ---------------------------------------------------------------------
    // Lifecycle properties
    // ---------------------------------------------------------------------
    private final ServletContext servletContext;
    private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();


    // ---------------------------------------------------------------------
    // ServletRequest properties
    // ---------------------------------------------------------------------
    private final Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();
    /**
     * List of locales in descending order
     */
    private final List<Locale> locales = new LinkedList<Locale>();
    private final Map<String, HeaderValueHolderFake> headers = new LinkedCaseInsensitiveMapFake<HeaderValueHolderFake>();
    private final Set<String> userRoles = new HashSet<String>();
    private final MultiValueMapFake<String, Part> parts = new LinkedMultiValueMapFake<String, Part>();
    private boolean active = true;
    private String characterEncoding;
    private byte[] content;
    private String contentType;
    private String protocol = DEFAULT_PROTOCOL;
    private String scheme = DEFAULT_SCHEME;
    private String serverName = DEFAULT_SERVER_NAME;
    private int serverPort = DEFAULT_SERVER_PORT;
    private String remoteAddr = DEFAULT_REMOTE_ADDR;
    private String remoteHost = DEFAULT_REMOTE_HOST;
    private boolean secure = false;
    private int remotePort = DEFAULT_SERVER_PORT;
    private String localName = DEFAULT_SERVER_NAME;
    private String localAddr = DEFAULT_SERVER_ADDR;
    private int localPort = DEFAULT_SERVER_PORT;
    private boolean asyncStarted = false;


    // ---------------------------------------------------------------------
    // HttpServletRequest properties
    // ---------------------------------------------------------------------
    private boolean asyncSupported = false;
    private AsyncContextFake asyncContext;
    private DispatcherType dispatcherType = DispatcherType.REQUEST;
    private String authType;
    private Cookie[] cookies;
    private String method;
    private String pathInfo;
    private String contextPath = "";
    private String queryString;
    private String remoteUser;
    private Principal userPrincipal;
    private String requestedSessionId;
    private String requestURI;
    private String servletPath = "";
    private HttpSession session;
    private boolean requestedSessionIdValid = true;
    private boolean requestedSessionIdFromCookie = true;
    private boolean requestedSessionIdFromURL = false;


    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------

    /**
     * Create a new {@code MockHttpServletRequest} with a default
     * {@link ServletContextFake}.
     *
     * @see #HttpServletRequestFake(ServletContext, String, String)
     */
    public HttpServletRequestFake() {
        this(null, "", "");
    }

    /**
     * Create a new {@code MockHttpServletRequest} with a default
     * {@link ServletContextFake}.
     *
     * @param method     the request method (may be {@code null})
     * @param requestURI the request URI (may be {@code null})
     * @see #setMethod
     * @see #setRequestURI
     * @see #HttpServletRequestFake(ServletContext, String, String)
     */
    public HttpServletRequestFake(String method, String requestURI) {
        this(null, method, requestURI);
    }

    /**
     * Create a new {@code MockHttpServletRequest} with the supplied {@link ServletContext}.
     *
     * @param servletContext the ServletContext that the request runs in
     *                       (may be {@code null} to use a default {@link ServletContextFake})
     * @see #HttpServletRequestFake(ServletContext, String, String)
     */
    public HttpServletRequestFake(ServletContext servletContext) {
        this(servletContext, "", "");
    }

    /**
     * Create a new {@code MockHttpServletRequest} with the supplied {@link ServletContext},
     * {@code method}, and {@code requestURI}.
     * <p>The preferred locale will be set to {@link Locale#ENGLISH}.
     *
     * @param servletContext the ServletContext that the request runs in (may be
     *                       {@code null} to use a default {@link ServletContextFake})
     * @param method         the request method (may be {@code null})
     * @param requestURI     the request URI (may be {@code null})
     * @see #setMethod
     * @see #setRequestURI
     * @see #setPreferredLocales
     * @see ServletContextFake
     */
    public HttpServletRequestFake(ServletContext servletContext, String method, String requestURI) {
        this.servletContext = (servletContext != null ? servletContext : new ServletContextFake());
        this.method = method;
        this.requestURI = requestURI;
        this.locales.add(Locale.ENGLISH);
    }


    // ---------------------------------------------------------------------
    // Lifecycle methods
    // ---------------------------------------------------------------------

    /**
     * Return the ServletContext that this request is associated with. (Not
     * available in the standard HttpServletRequest interface for some reason.)
     */
    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    /**
     * Return whether this request is still active (that is, not completed yet).
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Mark this request as completed, keeping its state.
     */
    public void close() {
        this.active = false;
    }

    /**
     * Invalidate this request, clearing its state.
     */
    public void invalidate() {
        close();
        clearAttributes();
    }

    /**
     * Check whether this request is still active (that is, not completed yet),
     * throwing an IllegalStateException if not active anymore.
     */
    protected void checkActive() throws IllegalStateException {
        if (!this.active) {
            throw new IllegalStateException("Request is not active anymore");
        }
    }


    // ---------------------------------------------------------------------
    // ServletRequest interface
    // ---------------------------------------------------------------------

    @Override
    public Object getAttribute(String name) {
        checkActive();
        return this.attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        checkActive();
        return Collections.enumeration(new LinkedHashSet<String>(this.attributes.keySet()));
    }

    @Override
    public String getCharacterEncoding() {
        return this.characterEncoding;
    }

    @Override
    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
        updateContentTypeHeader();
    }

    private void updateContentTypeHeader() {
        if (StringUtilsFake.hasLength(this.contentType)) {
            StringBuilder sb = new StringBuilder(this.contentType);
            if (!this.contentType.toLowerCase().contains(CHARSET_PREFIX) &&
                    StringUtilsFake.hasLength(this.characterEncoding)) {
                sb.append(";").append(CHARSET_PREFIX).append(this.characterEncoding);
            }
            doAddHeaderValue(CONTENT_TYPE_HEADER, sb.toString(), true);
        }
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public int getContentLength() {
        return (this.content != null ? this.content.length : -1);
    }

    public long getContentLengthLong() {
        return getContentLength();
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
        if (contentType != null) {
            try {
                MediaTypeFake mediaType = MediaTypeFake.parseMediaType(contentType);
                if (mediaType.getCharset() != null) {
                    this.characterEncoding = mediaType.getCharset().name();
                }
            } catch (Exception ex) {
                // Try to get charset value anyway
                int charsetIndex = contentType.toLowerCase().indexOf(CHARSET_PREFIX);
                if (charsetIndex != -1) {
                    this.characterEncoding = contentType.substring(charsetIndex + CHARSET_PREFIX.length());
                }
            }
            updateContentTypeHeader();
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        if (this.content != null) {
            return new DelegatingServletInputStreamFake(new ByteArrayInputStream(this.content));
        } else {
            return EMPTY_SERVLET_INPUT_STREAM;
        }
    }

    /**
     * Set a single value for the specified HTTP parameter.
     * <p>If there are already one or more values registered for the given
     * parameter name, they will be replaced.
     */
    public void setParameter(String name, String value) {
        setParameter(name, new String[]{value});
    }

    /**
     * Set an array of values for the specified HTTP parameter.
     * <p>If there are already one or more values registered for the given
     * parameter name, they will be replaced.
     */
    public void setParameter(String name, String... values) {
        this.parameters.put(name, values);
    }

    /**
     * Set all provided parameters <strong>replacing</strong> any existing
     * values for the provided parameter names. To add without replacing
     * existing values, use {@link #addParameters(java.util.Map)}.
     */
    public void setParameters(Map<String, ?> params) {
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value instanceof String) {
                setParameter(key, (String) value);
            } else if (value instanceof String[]) {
                setParameter(key, (String[]) value);
            } else {
                throw new IllegalArgumentException(
                        "Parameter map value must be single value " + " or array of type [" + String.class.getName() + "]");
            }
        }
    }

    /**
     * Add a single value for the specified HTTP parameter.
     * <p>If there are already one or more values registered for the given
     * parameter name, the given value will be added to the end of the list.
     */
    public void addParameter(String name, String value) {
        addParameter(name, new String[]{value});
    }

    /**
     * Add an array of values for the specified HTTP parameter.
     * <p>If there are already one or more values registered for the given
     * parameter name, the given values will be added to the end of the list.
     */
    public void addParameter(String name, String... values) {
        String[] oldArr = this.parameters.get(name);
        if (oldArr != null) {
            String[] newArr = new String[oldArr.length + values.length];
            System.arraycopy(oldArr, 0, newArr, 0, oldArr.length);
            System.arraycopy(values, 0, newArr, oldArr.length, values.length);
            this.parameters.put(name, newArr);
        } else {
            this.parameters.put(name, values);
        }
    }

    /**
     * Add all provided parameters <strong>without</strong> replacing any
     * existing values. To replace existing values, use
     * {@link #setParameters(java.util.Map)}.
     */
    public void addParameters(Map<String, ?> params) {
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value instanceof String) {
                addParameter(key, (String) value);
            } else if (value instanceof String[]) {
                addParameter(key, (String[]) value);
            } else {
                throw new IllegalArgumentException("Parameter map value must be single value " +
                        " or array of type [" + String.class.getName() + "]");
            }
        }
    }

    /**
     * Remove already registered values for the specified HTTP parameter, if any.
     */
    public void removeParameter(String name) {
        this.parameters.remove(name);
    }

    /**
     * Remove all existing parameters.
     */
    public void removeAllParameters() {
        this.parameters.clear();
    }

    @Override
    public String getParameter(String name) {
        String[] arr = (name != null ? this.parameters.get(name) : null);
        return (arr != null && arr.length > 0 ? arr[0] : null);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return (name != null ? this.parameters.get(name) : null);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this.parameters);
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public String getServerName() {
        String rawHostHeader = getHeader(HOST_HEADER);
        String host = rawHostHeader;
        if (host != null) {
            host = host.trim();
            if (host.startsWith("[")) {
                int indexOfClosingBracket = host.indexOf(']');
                host = host.substring(0, indexOfClosingBracket + 1);
            } else if (host.contains(":")) {
                host = host.substring(0, host.indexOf(':'));
            }
            return host;
        }

        // else
        return this.serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public int getServerPort() {
        String rawHostHeader = getHeader(HOST_HEADER);
        String host = rawHostHeader;
        if (host != null) {
            host = host.trim();
            int idx;
            if (host.startsWith("[")) {
                int indexOfClosingBracket = host.indexOf(']');
                idx = host.indexOf(':', indexOfClosingBracket);
            } else {
                idx = host.indexOf(':');
            }
            if (idx != -1) {
                return Integer.parseInt(host.substring(idx + 1));
            }
        }

        // else
        return this.serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public BufferedReader getReader() throws UnsupportedEncodingException {
        if (this.content != null) {
            InputStream sourceStream = new ByteArrayInputStream(this.content);
            Reader sourceReader = (this.characterEncoding != null) ?
                    new InputStreamReader(sourceStream, this.characterEncoding) :
                    new InputStreamReader(sourceStream);
            return new BufferedReader(sourceReader);
        } else {
            return EMPTY_BUFFERED_READER;
        }
    }

    @Override
    public String getRemoteAddr() {
        return this.remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    @Override
    public String getRemoteHost() {
        return this.remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    @Override
    public void setAttribute(String name, Object value) {
        checkActive();
        if (value != null) {
            this.attributes.put(name, value);
        } else {
            this.attributes.remove(name);
        }
    }

    @Override
    public void removeAttribute(String name) {
        checkActive();
        this.attributes.remove(name);
    }

    /**
     * Clear all of this request's attributes.
     */
    public void clearAttributes() {
        this.attributes.clear();
    }

    /**
     * Add a new preferred locale, before any existing locales.
     *
     * @see #setPreferredLocales
     */
    public void addPreferredLocale(Locale locale) {
        this.locales.add(0, locale);
    }

    /**
     * Set the list of preferred locales, in descending order, effectively replacing
     * any existing locales.
     *
     * @see #addPreferredLocale
     * @since 3.2
     */
    public void setPreferredLocales(List<Locale> locales) {
        this.locales.clear();
        this.locales.addAll(locales);
    }

    /**
     * Return the first preferred {@linkplain Locale locale} configured
     * in this mock request.
     * <p>If no locales have been explicitly configured, the default,
     * preferred {@link Locale} for the <em>server</em> mocked by this
     * request is {@link Locale#ENGLISH}.
     * <p>In contrast to the Servlet specification, this mock implementation
     * does <strong>not</strong> take into consideration any locales
     * specified via the {@code Accept-Language} header.
     *
     * @see javax.servlet.ServletRequest#getLocale()
     * @see #addPreferredLocale(Locale)
     * @see #setPreferredLocales(List)
     */
    @Override
    public Locale getLocale() {
        return this.locales.get(0);
    }

    /**
     * Return an {@linkplain Enumeration enumeration} of the preferred
     * {@linkplain Locale locales} configured in this mock request.
     * <p>If no locales have been explicitly configured, the default,
     * preferred {@link Locale} for the <em>server</em> mocked by this
     * request is {@link Locale#ENGLISH}.
     * <p>In contrast to the Servlet specification, this mock implementation
     * does <strong>not</strong> take into consideration any locales
     * specified via the {@code Accept-Language} header.
     *
     * @see javax.servlet.ServletRequest#getLocales()
     * @see #addPreferredLocale(Locale)
     * @see #setPreferredLocales(List)
     */
    @Override
    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(this.locales);
    }

    /**
     * Return {@code true} if the {@link #setSecure secure} flag has been set
     * to {@code true} or if the {@link #getScheme scheme} is {@code https}.
     *
     * @see javax.servlet.ServletRequest#isSecure()
     */
    @Override
    public boolean isSecure() {
        return (this.secure || HTTPS.equalsIgnoreCase(this.scheme));
    }

    /**
     * Set the boolean {@code secure} flag indicating whether the mock request
     * was made using a secure channel, such as HTTPS.
     *
     * @see #isSecure()
     * @see #getScheme()
     * @see #setScheme(String)
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return new RequestDispatcherFake(path);
    }

    @Override
    @Deprecated
    public String getRealPath(String path) {
        return this.servletContext.getRealPath(path);
    }

    @Override
    public int getRemotePort() {
        return this.remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    @Override
    public String getLocalName() {
        return this.localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @Override
    public String getLocalAddr() {
        return this.localAddr;
    }

    public void setLocalAddr(String localAddr) {
        this.localAddr = localAddr;
    }

    @Override
    public int getLocalPort() {
        return this.localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    @Override
    public AsyncContext startAsync() {
        return startAsync(this, null);
    }

    @Override
    public AsyncContext startAsync(ServletRequest request, ServletResponse response) {
        if (!this.asyncSupported) {
            throw new IllegalStateException("Async not supported");
        }
        this.asyncStarted = true;
        this.asyncContext = new AsyncContextFake(request, response);
        return this.asyncContext;
    }

    @Override
    public boolean isAsyncStarted() {
        return this.asyncStarted;
    }

    public void setAsyncStarted(boolean asyncStarted) {
        this.asyncStarted = asyncStarted;
    }

    @Override
    public boolean isAsyncSupported() {
        return this.asyncSupported;
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return this.asyncContext;
    }

    public void setAsyncContext(AsyncContextFake asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return this.dispatcherType;
    }

    public void setDispatcherType(DispatcherType dispatcherType) {
        this.dispatcherType = dispatcherType;
    }


    // ---------------------------------------------------------------------
    // HttpServletRequest interface
    // ---------------------------------------------------------------------

    @Override
    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    @Override
    public Cookie[] getCookies() {
        return this.cookies;
    }

    public void setCookies(Cookie... cookies) {
        this.cookies = cookies;
    }

    /**
     * Add an HTTP header entry for the given name.
     * <p>While this method can take any {@code Object} as a parameter,
     * it is recommended to use the following types:
     * <ul>
     * <li>String or any Object to be converted using {@code toString()}; see {@link #getHeader}.</li>
     * <li>String, Number, or Date for date headers; see {@link #getDateHeader}.</li>
     * <li>String or Number for integer headers; see {@link #getIntHeader}.</li>
     * <li>{@code String[]} or {@code Collection<String>} for multiple values; see {@link #getHeaders}.</li>
     * </ul>
     *
     * @see #getHeaderNames
     * @see #getHeaders
     * @see #getHeader
     * @see #getDateHeader
     */
    public void addHeader(String name, Object value) {
        if (CONTENT_TYPE_HEADER.equalsIgnoreCase(name) && !this.headers.containsKey(CONTENT_TYPE_HEADER)) {
            setContentType(value.toString());
        } else {
            doAddHeaderValue(name, value, false);
        }
    }

    private void doAddHeaderValue(String name, Object value, boolean replace) {
        HeaderValueHolderFake header = HeaderValueHolderFake.getByName(this.headers, name);
        if (header == null || replace) {
            header = new HeaderValueHolderFake();
            this.headers.put(name, header);
        }
        if (value instanceof Collection) {
            header.addValues((Collection<?>) value);
        } else if (value.getClass().isArray()) {
            header.addValueArray(value);
        } else {
            header.addValue(value);
        }
    }

    /**
     * Remove already registered entries for the specified HTTP header, if any.
     *
     * @since 4.3.20
     */
    public void removeHeader(String name) {
        this.headers.remove(name);
    }

    /**
     * Return the long timestamp for the date header with the given {@code name}.
     * <p>If the internal value representation is a String, this method will try
     * to parse it as a date using the supported date formats:
     * <ul>
     * <li>"EEE, dd MMM yyyy HH:mm:ss zzz"</li>
     * <li>"EEE, dd-MMM-yy HH:mm:ss zzz"</li>
     * <li>"EEE MMM dd HH:mm:ss yyyy"</li>
     * </ul>
     *
     * @param name the header name
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section 7.1.1.1 of RFC 7231</a>
     */
    @Override
    public long getDateHeader(String name) {
        HeaderValueHolderFake header = HeaderValueHolderFake.getByName(this.headers, name);
        Object value = (header != null ? header.getValue() : null);
        if (value instanceof Date) {
            return ((Date) value).getTime();
        } else if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof String) {
            return parseDateHeader(name, (String) value);
        } else if (value != null) {
            throw new IllegalArgumentException(
                    "Value for header '" + name + "' is not a Date, Number, or String: " + value);
        } else {
            return -1L;
        }
    }

    private long parseDateHeader(String name, String value) {
        for (String dateFormat : DATE_FORMATS) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
            simpleDateFormat.setTimeZone(GMT);
            try {
                return simpleDateFormat.parse(value).getTime();
            } catch (ParseException ex) {
                // ignore
            }
        }
        throw new IllegalArgumentException("Cannot parse date value '" + value + "' for '" + name + "' header");
    }

    @Override
    public String getHeader(String name) {
        HeaderValueHolderFake header = HeaderValueHolderFake.getByName(this.headers, name);
        return (header != null ? header.getStringValue() : null);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        HeaderValueHolderFake header = HeaderValueHolderFake.getByName(this.headers, name);
        return Collections.enumeration(header != null ? header.getStringValues() : new LinkedList<String>());
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(this.headers.keySet());
    }

    @Override
    public int getIntHeader(String name) {
        HeaderValueHolderFake header = HeaderValueHolderFake.getByName(this.headers, name);
        Object value = (header != null ? header.getValue() : null);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else if (value instanceof String) {
            return Integer.parseInt((String) value);
        } else if (value != null) {
            throw new NumberFormatException("Value for header '" + name + "' is not a Number: " + value);
        } else {
            return -1;
        }
    }

    //@Override
    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    //@Override
    public String getPathInfo() {
        return this.pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    //@Override
    public String getPathTranslated() {
        return (this.pathInfo != null ? getRealPath(this.pathInfo) : null);
    }

    //@Override
    public String getContextPath() {
        return this.contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    //@Override
    public String getQueryString() {
        return this.queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    //@Override
    public String getRemoteUser() {
        return this.remoteUser;
    }

    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    public void addUserRole(String role) {
        this.userRoles.add(role);
    }

    //@Override
    public boolean isUserInRole(String role) {
        return (this.userRoles.contains(role) || (this.servletContext instanceof ServletContextFake &&
                ((ServletContextFake) this.servletContext).getDeclaredRoles().contains(role)));
    }

    //@Override
    public Principal getUserPrincipal() {
        return this.userPrincipal;
    }

    public void setUserPrincipal(Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @Override
    public String getRequestedSessionId() {
        return this.requestedSessionId;
    }

    public void setRequestedSessionId(String requestedSessionId) {
        this.requestedSessionId = requestedSessionId;
    }

    @Override
    public String getRequestURI() {
        return this.requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    @Override
    public StringBuffer getRequestURL() {
        String scheme = getScheme();
        String server = getServerName();
        int port = getServerPort();
        String uri = getRequestURI();

        StringBuffer url = new StringBuffer(scheme).append("://").append(server);
        if (port > 0 && ((HTTP.equalsIgnoreCase(scheme) && port != 80) ||
                (HTTPS.equalsIgnoreCase(scheme) && port != 443))) {
            url.append(':').append(port);
        }
        if (StringUtilsFake.hasText(uri)) {
            url.append(uri);
        }
        return url;
    }

    @Override
    public String getServletPath() {
        return this.servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    @Override
    public HttpSession getSession(boolean create) {
        checkActive();
        // Reset session if invalidated.
        if (this.session instanceof HttpSessionFake && ((HttpSessionFake) this.session).isInvalid()) {
            this.session = null;
        }
        // Create new session if necessary.
        if (this.session == null && create) {
            this.session = new HttpSessionFake(this.servletContext);
        }
        return this.session;
    }

    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    public void setSession(HttpSession session) {
        this.session = session;
        if (session instanceof HttpSessionFake) {
            HttpSessionFake mockSession = ((HttpSessionFake) session);
            mockSession.access();
        }
    }

    /**
     * The implementation of this (Servlet 3.1+) method calls
     * {@link HttpSessionFake#changeSessionId()} if the session is a mock session.
     * Otherwise it simply returns the current session id.
     *
     * @since 4.0.3
     */
    public String changeSessionId() {
        if (this.session instanceof HttpSessionFake) {
            return ((HttpSessionFake) this.session).changeSessionId();
        }
        return this.session.getId();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return this.requestedSessionIdValid;
    }

    public void setRequestedSessionIdValid(boolean requestedSessionIdValid) {
        this.requestedSessionIdValid = requestedSessionIdValid;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return this.requestedSessionIdFromCookie;
    }

    public void setRequestedSessionIdFromCookie(boolean requestedSessionIdFromCookie) {
        this.requestedSessionIdFromCookie = requestedSessionIdFromCookie;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return this.requestedSessionIdFromURL;
    }

    public void setRequestedSessionIdFromURL(boolean requestedSessionIdFromURL) {
        this.requestedSessionIdFromURL = requestedSessionIdFromURL;
    }

    @Override
    @Deprecated
    public boolean isRequestedSessionIdFromUrl() {
        return isRequestedSessionIdFromURL();
    }

    @Override
    public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void login(String username, String password) throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void logout() throws ServletException {
        this.userPrincipal = null;
        this.remoteUser = null;
        this.authType = null;
    }

    public void addPart(Part part) {
        this.parts.add(part.getName(), part);
    }

    @Override
    public Part getPart(String name) throws IOException, ServletException {
        return this.parts.getFirst(name);
    }

    @Override
    public Collection<Part> getParts() throws IOException, ServletException {
        List<Part> result = new LinkedList<Part>();
        for (List<Part> list : this.parts.values()) {
            result.addAll(list);
        }
        return result;
    }


}


