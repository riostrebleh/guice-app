package br.helbertrios.guice.app.http;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.security.SecureRandom;
import java.util.*;

abstract class MimeTypeUtilsFake {
    /**
     * Comparator used by {@link #sortBySpecificity(List)}.
     */
    public static final Comparator<MimeTypeFake> SPECIFICITY_COMPARATOR = new MimeTypeFake.SpecificityComparator<MimeTypeFake>();
    /**
     * Public constant mime type that includes all media ranges (i.e. "&#42;/&#42;").
     */
    public static final MimeTypeFake ALL;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#ALL}.
     */
    public static final String ALL_VALUE = "*/*";
    /**
     * Public constant mime type for {@code application/atom+xml}.
     *
     * @deprecated as of 4.3.6, in favor of {@code MediaType} constants
     */
    @Deprecated
    public static final MimeTypeFake APPLICATION_ATOM_XML;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#APPLICATION_ATOM_XML}.
     *
     * @deprecated as of 4.3.6, in favor of {@code MediaType} constants
     */
    @Deprecated
    public static final String APPLICATION_ATOM_XML_VALUE = "application/atom+xml";
    /**
     * Public constant mime type for {@code application/x-www-form-urlencoded}.
     *
     * @deprecated as of 4.3.6, in favor of {@code MediaType} constants
     */
    @Deprecated
    public static final MimeTypeFake APPLICATION_FORM_URLENCODED;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#APPLICATION_FORM_URLENCODED}.
     *
     * @deprecated as of 4.3.6, in favor of {@code MediaType} constants
     */
    @Deprecated
    public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";
    /**
     * Public constant mime type for {@code application/json}.
     */
    public static final MimeTypeFake APPLICATION_JSON;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#APPLICATION_JSON}.
     */
    public static final String APPLICATION_JSON_VALUE = "application/json";
    /**
     * Public constant mime type for {@code application/octet-stream}.
     */
    public static final MimeTypeFake APPLICATION_OCTET_STREAM;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#APPLICATION_OCTET_STREAM}.
     */
    public static final String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";
    /**
     * Public constant mime type for {@code application/xhtml+xml}.
     *
     * @deprecated as of 4.3.6, in favor of {@code MediaType} constants
     */
    @Deprecated
    public static final MimeTypeFake APPLICATION_XHTML_XML;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#APPLICATION_XHTML_XML}.
     *
     * @deprecated as of 4.3.6, in favor of {@code MediaType} constants
     */
    @Deprecated
    public static final String APPLICATION_XHTML_XML_VALUE = "application/xhtml+xml";
    /**
     * Public constant mime type for {@code application/xml}.
     */
    public static final MimeTypeFake APPLICATION_XML;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#APPLICATION_XML}.
     */
    public static final String APPLICATION_XML_VALUE = "application/xml";
    /**
     * Public constant mime type for {@code image/gif}.
     */
    public static final MimeTypeFake IMAGE_GIF;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#IMAGE_GIF}.
     */
    public static final String IMAGE_GIF_VALUE = "image/gif";
    /**
     * Public constant mime type for {@code image/jpeg}.
     */
    public static final MimeTypeFake IMAGE_JPEG;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#IMAGE_JPEG}.
     */
    public static final String IMAGE_JPEG_VALUE = "image/jpeg";
    /**
     * Public constant mime type for {@code image/png}.
     */
    public static final MimeTypeFake IMAGE_PNG;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#IMAGE_PNG}.
     */
    public static final String IMAGE_PNG_VALUE = "image/png";
    /**
     * Public constant mime type for {@code multipart/form-data}.
     *
     * @deprecated as of 4.3.6, in favor of {@code MediaType} constants
     */
    @Deprecated
    public static final MimeTypeFake MULTIPART_FORM_DATA;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#MULTIPART_FORM_DATA}.
     *
     * @deprecated as of 4.3.6, in favor of {@code MediaType} constants
     */
    @Deprecated
    public static final String MULTIPART_FORM_DATA_VALUE = "multipart/form-data";
    /**
     * Public constant mime type for {@code text/html}.
     */
    public static final MimeTypeFake TEXT_HTML;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#TEXT_HTML}.
     */
    public static final String TEXT_HTML_VALUE = "text/html";
    /**
     * Public constant mime type for {@code text/plain}.
     */
    public static final MimeTypeFake TEXT_PLAIN;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#TEXT_PLAIN}.
     */
    public static final String TEXT_PLAIN_VALUE = "text/plain";
    /**
     * Public constant mime type for {@code text/xml}.
     */
    public static final MimeTypeFake TEXT_XML;
    /**
     * A String equivalent of {@link org.springframework.util.MimeTypeUtils#TEXT_XML}.
     */
    public static final String TEXT_XML_VALUE = "text/xml";
    private static final byte[] BOUNDARY_CHARS =
            new byte[]{'-', '_', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
                    'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                    'V', 'W', 'X', 'Y', 'Z'};
    private static final Random RND = new SecureRandom();
    private static final Charset US_ASCII = StandardCharsets.US_ASCII;

    static {
        ALL = MimeTypeFake.valueOf(ALL_VALUE);
        APPLICATION_ATOM_XML = MimeTypeFake.valueOf(APPLICATION_ATOM_XML_VALUE);
        APPLICATION_FORM_URLENCODED = MimeTypeFake.valueOf(APPLICATION_FORM_URLENCODED_VALUE);
        APPLICATION_JSON = MimeTypeFake.valueOf(APPLICATION_JSON_VALUE);
        APPLICATION_OCTET_STREAM = MimeTypeFake.valueOf(APPLICATION_OCTET_STREAM_VALUE);
        APPLICATION_XHTML_XML = MimeTypeFake.valueOf(APPLICATION_XHTML_XML_VALUE);
        APPLICATION_XML = MimeTypeFake.valueOf(APPLICATION_XML_VALUE);
        IMAGE_GIF = MimeTypeFake.valueOf(IMAGE_GIF_VALUE);
        IMAGE_JPEG = MimeTypeFake.valueOf(IMAGE_JPEG_VALUE);
        IMAGE_PNG = MimeTypeFake.valueOf(IMAGE_PNG_VALUE);
        MULTIPART_FORM_DATA = MimeTypeFake.valueOf(MULTIPART_FORM_DATA_VALUE);
        TEXT_HTML = MimeTypeFake.valueOf(TEXT_HTML_VALUE);
        TEXT_PLAIN = MimeTypeFake.valueOf(TEXT_PLAIN_VALUE);
        TEXT_XML = MimeTypeFake.valueOf(TEXT_XML_VALUE);
    }


    /**
     * Parse the given String into a single {@code MimeType}.
     *
     * @param mimeType the string to parse
     * @return the mime type
     * @throws InvalidMimeTypeExceptionFake if the string cannot be parsed
     */
    public static MimeTypeFake parseMimeType(String mimeType) {
        if (!StringUtilsFake.hasLength(mimeType)) {
            throw new InvalidMimeTypeExceptionFake(mimeType, "'mimeType' must not be empty");
        }

        int index = mimeType.indexOf(';');
        String fullType = (index >= 0 ? mimeType.substring(0, index) : mimeType).trim();
        if (fullType.isEmpty()) {
            throw new InvalidMimeTypeExceptionFake(mimeType, "'mimeType' must not be empty");
        }

        // java.net.HttpURLConnection returns a *; q=.2 Accept header
        if (MimeTypeFake.WILDCARD_TYPE.equals(fullType)) {
            fullType = "*/*";
        }
        int subIndex = fullType.indexOf('/');
        if (subIndex == -1) {
            throw new InvalidMimeTypeExceptionFake(mimeType, "does not contain '/'");
        }
        if (subIndex == fullType.length() - 1) {
            throw new InvalidMimeTypeExceptionFake(mimeType, "does not contain subtype after '/'");
        }
        String type = fullType.substring(0, subIndex);
        String subtype = fullType.substring(subIndex + 1);
        if (MimeTypeFake.WILDCARD_TYPE.equals(type) && !MimeTypeFake.WILDCARD_TYPE.equals(subtype)) {
            throw new InvalidMimeTypeExceptionFake(mimeType, "wildcard type is legal only in '*/*' (all mime types)");
        }

        Map<String, String> parameters = null;
        do {
            int nextIndex = index + 1;
            boolean quoted = false;
            while (nextIndex < mimeType.length()) {
                char ch = mimeType.charAt(nextIndex);
                if (ch == ';') {
                    if (!quoted) {
                        break;
                    }
                } else if (ch == '"') {
                    quoted = !quoted;
                }
                nextIndex++;
            }
            String parameter = mimeType.substring(index + 1, nextIndex).trim();
            if (parameter.length() > 0) {
                if (parameters == null) {
                    parameters = new LinkedHashMap<String, String>(4);
                }
                int eqIndex = parameter.indexOf('=');
                if (eqIndex >= 0) {
                    String attribute = parameter.substring(0, eqIndex).trim();
                    String value = parameter.substring(eqIndex + 1).trim();
                    parameters.put(attribute, value);
                }
            }
            index = nextIndex;
        }
        while (index < mimeType.length());

        try {
            return new MimeTypeFake(type, subtype, parameters);
        } catch (UnsupportedCharsetException ex) {
            throw new InvalidMimeTypeExceptionFake(mimeType, "unsupported charset '" + ex.getCharsetName() + "'");
        } catch (IllegalArgumentException ex) {
            throw new InvalidMimeTypeExceptionFake(mimeType, ex.getMessage());
        }
    }

    /**
     * Parse the given, comma-separated string into a list of {@code MimeType} objects.
     *
     * @param mimeTypes the string to parse
     * @return the list of mime types
     * @throws IllegalArgumentException if the string cannot be parsed
     */
    public static List<MimeTypeFake> parseMimeTypes(String mimeTypes) {
        if (!StringUtilsFake.hasLength(mimeTypes)) {
            return Collections.emptyList();
        }
        String[] tokens = StringUtilsFake.tokenizeToStringArray(mimeTypes, ",");
        List<MimeTypeFake> result = new ArrayList<MimeTypeFake>(tokens.length);
        for (String token : tokens) {
            result.add(parseMimeType(token));
        }
        return result;
    }

    /**
     * Return a string representation of the given list of {@code MimeType} objects.
     *
     * @param mimeTypes the string to parse
     * @return the list of mime types
     * @throws IllegalArgumentException if the String cannot be parsed
     */
    public static String toString(Collection<? extends MimeTypeFake> mimeTypes) {
        StringBuilder builder = new StringBuilder();
        for (Iterator<? extends MimeTypeFake> iterator = mimeTypes.iterator(); iterator.hasNext(); ) {
            MimeTypeFake mimeType = iterator.next();
            mimeType.appendTo(builder);
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }


    /**
     * Sorts the given list of {@code MimeType} objects by specificity.
     * <p>Given two mime types:
     * <ol>
     * <li>if either mime type has a {@linkplain MimeTypeFake#isWildcardType() wildcard type},
     * then the mime type without the wildcard is ordered before the other.</li>
     * <li>if the two mime types have different {@linkplain MimeTypeFake#getType() types},
     * then they are considered equal and remain their current order.</li>
     * <li>if either mime type has a {@linkplain MimeTypeFake#isWildcardSubtype() wildcard subtype}
     * , then the mime type without the wildcard is sorted before the other.</li>
     * <li>if the two mime types have different {@linkplain MimeTypeFake#getSubtype() subtypes},
     * then they are considered equal and remain their current order.</li>
     * <li>if the two mime types have a different amount of
     * {@linkplain MimeTypeFake#getParameter(String) parameters}, then the mime type with the most
     * parameters is ordered before the other.</li>
     * </ol>
     * <p>For example: <blockquote>audio/basic &lt; audio/* &lt; *&#047;*</blockquote>
     * <blockquote>audio/basic;level=1 &lt; audio/basic</blockquote>
     * <blockquote>audio/basic == text/html</blockquote> <blockquote>audio/basic ==
     * audio/wave</blockquote>
     *
     * @param mimeTypes the list of mime types to be sorted
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.2">HTTP 1.1: Semantics
     * and Content, section 5.3.2</a>
     */
    public static void sortBySpecificity(List<MimeTypeFake> mimeTypes) {
        if (mimeTypes.size() > 1) {
            Collections.sort(mimeTypes, SPECIFICITY_COMPARATOR);
        }
    }

    /**
     * Generate a random MIME boundary as bytes, often used in multipart mime types.
     */
    public static byte[] generateMultipartBoundary() {
        byte[] boundary = new byte[RND.nextInt(11) + 30];
        for (int i = 0; i < boundary.length; i++) {
            boundary[i] = BOUNDARY_CHARS[RND.nextInt(BOUNDARY_CHARS.length)];
        }
        return boundary;
    }

    /**
     * Generate a random MIME boundary as String, often used in multipart mime types.
     */
    public static String generateMultipartBoundaryString() {
        return new String(generateMultipartBoundary(), US_ASCII);
    }
}
