package br.helbertrios.guice.app.http;


import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;

class MediaTypeFake extends MimeTypeFake implements Serializable {
    /**
     * Public constant media type that includes all media ranges (i.e. "&#42;/&#42;").
     */
    public static final MediaTypeFake ALL;
    /**
     * A String equivalent of {@link MediaTypeFake#ALL}.
     */
    public static final String ALL_VALUE = "*/*";
    /**
     * Public constant media type for {@code application/atom+xml}.
     */
    public final static MediaTypeFake APPLICATION_ATOM_XML;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_ATOM_XML}.
     */
    public final static String APPLICATION_ATOM_XML_VALUE = "application/atom+xml";
    /**
     * Public constant media type for {@code application/x-www-form-urlencoded}.
     */
    public final static MediaTypeFake APPLICATION_FORM_URLENCODED;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_FORM_URLENCODED}.
     */
    public final static String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";
    /**
     * Public constant media type for {@code application/json}.
     */
    public final static MediaTypeFake APPLICATION_JSON;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_JSON}.
     *
     * @see #APPLICATION_JSON_UTF8_VALUE
     */
    public final static String APPLICATION_JSON_VALUE = "application/json";
    /**
     * Public constant media type for {@code application/json;charset=UTF-8}.
     */
    public final static MediaTypeFake APPLICATION_JSON_UTF8;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_JSON_UTF8}.
     */
    public final static String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
    /**
     * Public constant media type for {@code application/octet-stream}.
     */
    public final static MediaTypeFake APPLICATION_OCTET_STREAM;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_OCTET_STREAM}.
     */
    public final static String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";
    /**
     * Public constant media type for {@code application/pdf}.
     *
     * @since 4.3
     */
    public final static MediaTypeFake APPLICATION_PDF;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_PDF}.
     *
     * @since 4.3
     */
    public final static String APPLICATION_PDF_VALUE = "application/pdf";
    /**
     * Public constant media type for {@code application/rss+xml}.
     *
     * @since 4.3.6
     */
    public final static MediaTypeFake APPLICATION_RSS_XML;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_RSS_XML}.
     *
     * @since 4.3.6
     */
    public final static String APPLICATION_RSS_XML_VALUE = "application/rss+xml";
    /**
     * Public constant media type for {@code application/xhtml+xml}.
     */
    public final static MediaTypeFake APPLICATION_XHTML_XML;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_XHTML_XML}.
     */
    public final static String APPLICATION_XHTML_XML_VALUE = "application/xhtml+xml";
    /**
     * Public constant media type for {@code application/xml}.
     */
    public final static MediaTypeFake APPLICATION_XML;
    /**
     * A String equivalent of {@link MediaTypeFake#APPLICATION_XML}.
     */
    public final static String APPLICATION_XML_VALUE = "application/xml";
    /**
     * Public constant media type for {@code image/gif}.
     */
    public final static MediaTypeFake IMAGE_GIF;
    /**
     * A String equivalent of {@link MediaTypeFake#IMAGE_GIF}.
     */
    public final static String IMAGE_GIF_VALUE = "image/gif";
    /**
     * Public constant media type for {@code image/jpeg}.
     */
    public final static MediaTypeFake IMAGE_JPEG;
    /**
     * A String equivalent of {@link MediaTypeFake#IMAGE_JPEG}.
     */
    public final static String IMAGE_JPEG_VALUE = "image/jpeg";
    /**
     * Public constant media type for {@code image/png}.
     */
    public final static MediaTypeFake IMAGE_PNG;
    /**
     * A String equivalent of {@link MediaTypeFake#IMAGE_PNG}.
     */
    public final static String IMAGE_PNG_VALUE = "image/png";
    /**
     * Public constant media type for {@code multipart/form-data}.
     */
    public final static MediaTypeFake MULTIPART_FORM_DATA;
    /**
     * A String equivalent of {@link MediaTypeFake#MULTIPART_FORM_DATA}.
     */
    public final static String MULTIPART_FORM_DATA_VALUE = "multipart/form-data";
    /**
     * Public constant media type for {@code text/event-stream}.
     *
     * @see <a href="https://www.w3.org/TR/eventsource/">Server-Sent Events W3C recommendation</a>
     * @since 4.3.6
     */
    public final static MediaTypeFake TEXT_EVENT_STREAM;
    /**
     * A String equivalent of {@link MediaTypeFake#TEXT_EVENT_STREAM}.
     *
     * @since 4.3.6
     */
    public final static String TEXT_EVENT_STREAM_VALUE = "text/event-stream";
    /**
     * Public constant media type for {@code text/html}.
     */
    public final static MediaTypeFake TEXT_HTML;
    /**
     * A String equivalent of {@link MediaTypeFake#TEXT_HTML}.
     */
    public final static String TEXT_HTML_VALUE = "text/html";
    /**
     * Public constant media type for {@code text/markdown}.
     *
     * @since 4.3
     */
    public final static MediaTypeFake TEXT_MARKDOWN;
    /**
     * A String equivalent of {@link MediaTypeFake#TEXT_MARKDOWN}.
     *
     * @since 4.3
     */
    public final static String TEXT_MARKDOWN_VALUE = "text/markdown";
    /**
     * Public constant media type for {@code text/plain}.
     */
    public final static MediaTypeFake TEXT_PLAIN;
    /**
     * A String equivalent of {@link MediaTypeFake#TEXT_PLAIN}.
     */
    public final static String TEXT_PLAIN_VALUE = "text/plain";
    /**
     * Public constant media type for {@code text/xml}.
     */
    public final static MediaTypeFake TEXT_XML;
    /**
     * A String equivalent of {@link MediaTypeFake#TEXT_XML}.
     */
    public final static String TEXT_XML_VALUE = "text/xml";
    private static final long serialVersionUID = 2069937152339670231L;
    private static final String PARAM_QUALITY_FACTOR = "q";
    /**
     * Comparator used by {@link #sortByQualityValue(List)}.
     */
    public static final Comparator<MediaTypeFake> QUALITY_VALUE_COMPARATOR = new Comparator<MediaTypeFake>() {

        @Override
        public int compare(MediaTypeFake MediaTypeFake1, MediaTypeFake MediaTypeFake2) {
            double quality1 = MediaTypeFake1.getQualityValue();
            double quality2 = MediaTypeFake2.getQualityValue();
            int qualityComparison = Double.compare(quality2, quality1);
            if (qualityComparison != 0) {
                return qualityComparison;  // audio/*;q=0.7 < audio/*;q=0.3
            } else if (MediaTypeFake1.isWildcardType() && !MediaTypeFake2.isWildcardType()) { // */* < audio/*
                return 1;
            } else if (MediaTypeFake2.isWildcardType() && !MediaTypeFake1.isWildcardType()) { // audio/* > */*
                return -1;
            } else if (!MediaTypeFake1.getType().equals(MediaTypeFake2.getType())) { // audio/basic == text/html
                return 0;
            } else { // MediaTypeFake1.getType().equals(MediaTypeFake2.getType())
                if (MediaTypeFake1.isWildcardSubtype() && !MediaTypeFake2.isWildcardSubtype()) { // audio/* < audio/basic
                    return 1;
                } else if (MediaTypeFake2.isWildcardSubtype() && !MediaTypeFake1.isWildcardSubtype()) { // audio/basic > audio/*
                    return -1;
                } else if (!MediaTypeFake1.getSubtype().equals(MediaTypeFake2.getSubtype())) { // audio/basic == audio/wave
                    return 0;
                } else {
                    int paramsSize1 = MediaTypeFake1.getParameters().size();
                    int paramsSize2 = MediaTypeFake2.getParameters().size();
                    // audio/basic;level=1 < audio/basic
                    return (paramsSize2 < paramsSize1 ? -1 : (paramsSize2 == paramsSize1 ? 0 : 1));
                }
            }
        }
    };
    /**
     * /**
     * Comparator used by {@link #sortBySpecificity(List)}.
     */
    public static final Comparator<MediaTypeFake> SPECIFICITY_COMPARATOR = new MimeTypeFake.SpecificityComparator<MediaTypeFake>() {

        @Override
        protected int compareParameters(MediaTypeFake mediaType1, MediaTypeFake mediaType2) {
            double quality1 = mediaType1.getQualityValue();
            double quality2 = mediaType2.getQualityValue();
            int qualityComparison = Double.compare(quality2, quality1);
            if (qualityComparison != 0) {
                return qualityComparison;  // audio/*;q=0.7 < audio/*;q=0.3
            }
            return super.compareParameters(mediaType1, mediaType2);
        }
    };

    static {
        ALL = valueOf(ALL_VALUE);
        APPLICATION_ATOM_XML = valueOf(APPLICATION_ATOM_XML_VALUE);
        APPLICATION_FORM_URLENCODED = valueOf(APPLICATION_FORM_URLENCODED_VALUE);
        APPLICATION_JSON = valueOf(APPLICATION_JSON_VALUE);
        APPLICATION_JSON_UTF8 = valueOf(APPLICATION_JSON_UTF8_VALUE);
        APPLICATION_OCTET_STREAM = valueOf(APPLICATION_OCTET_STREAM_VALUE);
        APPLICATION_PDF = valueOf(APPLICATION_PDF_VALUE);
        APPLICATION_RSS_XML = valueOf(APPLICATION_RSS_XML_VALUE);
        APPLICATION_XHTML_XML = valueOf(APPLICATION_XHTML_XML_VALUE);
        APPLICATION_XML = valueOf(APPLICATION_XML_VALUE);
        IMAGE_GIF = valueOf(IMAGE_GIF_VALUE);
        IMAGE_JPEG = valueOf(IMAGE_JPEG_VALUE);
        IMAGE_PNG = valueOf(IMAGE_PNG_VALUE);
        MULTIPART_FORM_DATA = valueOf(MULTIPART_FORM_DATA_VALUE);
        TEXT_EVENT_STREAM = valueOf(TEXT_EVENT_STREAM_VALUE);
        TEXT_HTML = valueOf(TEXT_HTML_VALUE);
        TEXT_MARKDOWN = valueOf(TEXT_MARKDOWN_VALUE);
        TEXT_PLAIN = valueOf(TEXT_PLAIN_VALUE);
        TEXT_XML = valueOf(TEXT_XML_VALUE);
    }

    /**
     * Create a new {@code MediaTypeFake} for the given primary type.
     * <p>The {@linkplain #getSubtype() subtype} is set to "&#42;", parameters empty.
     *
     * @param type the primary type
     * @throws IllegalArgumentException if any of the parameters contain illegal characters
     */
    public MediaTypeFake(String type) {
        super(type);
    }

    /**
     * Create a new {@code MediaTypeFake} for the given primary type and subtype.
     * <p>The parameters are empty.
     *
     * @param type    the primary type
     * @param subtype the subtype
     * @throws IllegalArgumentException if any of the parameters contain illegal characters
     */
    public MediaTypeFake(String type, String subtype) {
        super(type, subtype, Collections.emptyMap());
    }

    /**
     * Create a new {@code MediaTypeFake} for the given type, subtype, and character set.
     *
     * @param type    the primary type
     * @param subtype the subtype
     * @param charset the character set
     * @throws IllegalArgumentException if any of the parameters contain illegal characters
     */
    public MediaTypeFake(String type, String subtype, Charset charset) {
        super(type, subtype, charset);
    }

    /**
     * Create a new {@code MediaTypeFake} for the given type, subtype, and quality value.
     *
     * @param type         the primary type
     * @param subtype      the subtype
     * @param qualityValue the quality value
     * @throws IllegalArgumentException if any of the parameters contain illegal characters
     */
    public MediaTypeFake(String type, String subtype, double qualityValue) {
        this(type, subtype, Collections.singletonMap(PARAM_QUALITY_FACTOR, Double.toString(qualityValue)));
    }

    /**
     * Copy-constructor that copies the type, subtype and parameters of the given
     * {@code MediaTypeFake}, and allows to set the specified character set.
     *
     * @param other   the other media type
     * @param charset the character set
     * @throws IllegalArgumentException if any of the parameters contain illegal characters
     * @since 4.3
     */
    public MediaTypeFake(MediaTypeFake other, Charset charset) {
        super(other, charset);
    }


    /**
     * Copy-constructor that copies the type and subtype of the given {@code MediaTypeFake},
     * and allows for different parameters.
     *
     * @param other      the other media type
     * @param parameters the parameters, may be {@code null}
     * @throws IllegalArgumentException if any of the parameters contain illegal characters
     */
    public MediaTypeFake(MediaTypeFake other, Map<String, String> parameters) {
        super(other.getType(), other.getSubtype(), parameters);
    }

    /**
     * Create a new {@code MediaTypeFake} for the given type, subtype, and parameters.
     *
     * @param type       the primary type
     * @param subtype    the subtype
     * @param parameters the parameters, may be {@code null}
     * @throws IllegalArgumentException if any of the parameters contain illegal characters
     */
    public MediaTypeFake(String type, String subtype, Map<String, String> parameters) {
        super(type, subtype, parameters);
    }

    /**
     * Parse the given String value into a {@code MediaTypeFake} object,
     * with this method name following the 'valueOf' naming convention
     * (as supported by {@link org.springframework.core.convert.ConversionService}.
     *
     * @param value the string to parse
     * @throws InvalidMediaTypeFakeExceptionFake if the media type value cannot be parsed
     * @see #parseMediaType(String)
     */
    public static MediaTypeFake valueOf(String value) {
        return parseMediaType(value);
    }

    /**
     * Parse the given String into a single {@code MediaTypeFake}.
     *
     * @param MediaTypeFake the string to parse
     * @return the media type
     * @throws InvalidMediaTypeFakeExceptionFake if the media type value cannot be parsed
     */
    public static MediaTypeFake parseMediaType(String MediaTypeFake) {
        MimeTypeFake type;
        try {
            type = MimeTypeUtilsFake.parseMimeType(MediaTypeFake);
        } catch (InvalidMimeTypeExceptionFake ex) {
            throw new InvalidMediaTypeFakeExceptionFake(ex);
        }
        try {
            return new MediaTypeFake(type.getType(), type.getSubtype(), type.getParameters());
        } catch (IllegalArgumentException ex) {
            throw new InvalidMediaTypeFakeExceptionFake(MediaTypeFake, ex.getMessage());
        }
    }

    /**
     * Parse the comma-separated string into a list of {@code MediaTypeFake} objects.
     * <p>This method can be used to parse an Accept or Content-Type header.
     *
     * @param MediaTypeFakes the string to parse
     * @return the list of media types
     * @throws InvalidMediaTypeFakeExceptionFake if the media type value cannot be parsed
     */
    public static List<MediaTypeFake> parseMediaTypes(String MediaTypeFakes) {
        if (!StringUtilsFake.hasLength(MediaTypeFakes)) {
            return Collections.emptyList();
        }
        String[] tokens = StringUtilsFake.tokenizeToStringArray(MediaTypeFakes, ",");
        List<MediaTypeFake> result = new ArrayList<MediaTypeFake>(tokens.length);
        for (String token : tokens) {
            result.add(parseMediaType(token));
        }
        return result;
    }

    /**
     * Parse the given list of (potentially) comma-separated strings into a
     * list of {@code MediaTypeFake} objects.
     * <p>This method can be used to parse an Accept or Content-Type header.
     *
     * @param MediaTypeFakes the string to parse
     * @return the list of media types
     * @throws InvalidMediaTypeFakeExceptionFake if the media type value cannot be parsed
     * @since 4.3.2
     */
    public static List<MediaTypeFake> parseMediaTypeFakes(List<String> MediaTypeFakes) {
        if (CollectionUtilsFake.isEmpty(MediaTypeFakes)) {
            return Collections.emptyList();
        } else if (MediaTypeFakes.size() == 1) {
            return parseMediaTypes(MediaTypeFakes.get(0));
        } else {
            List<MediaTypeFake> result = new ArrayList<MediaTypeFake>(8);
            for (String MediaTypeFake : MediaTypeFakes) {
                result.addAll(parseMediaTypes(MediaTypeFake));
            }
            return result;
        }
    }

    /**
     * Return a string representation of the given list of {@code MediaTypeFake} objects.
     * <p>This method can be used to for an {@code Accept} or {@code Content-Type} header.
     *
     * @param MediaTypeFakes the media types to create a string representation for
     * @return the string representation
     */
    public static String toString(Collection<MediaTypeFake> MediaTypeFakes) {
        return MimeTypeUtilsFake.toString(MediaTypeFakes);
    }

    /**
     * Sorts the given list of {@code MediaTypeFake} objects by specificity.
     * <p>Given two media types:
     * <ol>
     * <li>if either media type has a {@linkplain #isWildcardType() wildcard type}, then the media type without the
     * wildcard is ordered before the other.</li>
     * <li>if the two media types have different {@linkplain #getType() types}, then they are considered equal and
     * remain their current order.</li>
     * <li>if either media type has a {@linkplain #isWildcardSubtype() wildcard subtype}, then the media type without
     * the wildcard is sorted before the other.</li>
     * <li>if the two media types have different {@linkplain #getSubtype() subtypes}, then they are considered equal
     * and remain their current order.</li>
     * <li>if the two media types have different {@linkplain #getQualityValue() quality value}, then the media type
     * with the highest quality value is ordered before the other.</li>
     * <li>if the two media types have a different amount of {@linkplain #getParameter(String) parameters}, then the
     * media type with the most parameters is ordered before the other.</li>
     * </ol>
     * <p>For example:
     * <blockquote>audio/basic &lt; audio/* &lt; *&#047;*</blockquote>
     * <blockquote>audio/* &lt; audio/*;q=0.7; audio/*;q=0.3</blockquote>
     * <blockquote>audio/basic;level=1 &lt; audio/basic</blockquote>
     * <blockquote>audio/basic == text/html</blockquote>
     * <blockquote>audio/basic == audio/wave</blockquote>
     *
     * @param MediaTypeFakes the list of media types to be sorted
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-5.3.2">HTTP 1.1: Semantics
     * and Content, section 5.3.2</a>
     */
    public static void sortBySpecificity(List<MediaTypeFake> MediaTypeFakes) {
        if (MediaTypeFakes.size() > 1) {
            Collections.sort(MediaTypeFakes, SPECIFICITY_COMPARATOR);
        }
    }

    /**
     * Sorts the given list of {@code MediaTypeFake} objects by quality value.
     * <p>Given two media types:
     * <ol>
     * <li>if the two media types have different {@linkplain #getQualityValue() quality value}, then the media type
     * with the highest quality value is ordered before the other.</li>
     * <li>if either media type has a {@linkplain #isWildcardType() wildcard type}, then the media type without the
     * wildcard is ordered before the other.</li>
     * <li>if the two media types have different {@linkplain #getType() types}, then they are considered equal and
     * remain their current order.</li>
     * <li>if either media type has a {@linkplain #isWildcardSubtype() wildcard subtype}, then the media type without
     * the wildcard is sorted before the other.</li>
     * <li>if the two media types have different {@linkplain #getSubtype() subtypes}, then they are considered equal
     * and remain their current order.</li>
     * <li>if the two media types have a different amount of {@linkplain #getParameter(String) parameters}, then the
     * media type with the most parameters is ordered before the other.</li>
     * </ol>
     *
     * @param MediaTypeFakes the list of media types to be sorted
     * @see #getQualityValue()
     */
    public static void sortByQualityValue(List<MediaTypeFake> MediaTypeFakes) {
        if (MediaTypeFakes.size() > 1) {
            Collections.sort(MediaTypeFakes, QUALITY_VALUE_COMPARATOR);
        }
    }

    /**
     * Sorts the given list of {@code MediaTypeFake} objects by specificity as the
     * primary criteria and quality value the secondary.
     *
     * @see MediaTypeFake#sortBySpecificity(List)
     * @see MediaTypeFake#sortByQualityValue(List)
     */
    public static void sortBySpecificityAndQuality(List<MediaTypeFake> MediaTypeFakes) {
        if (MediaTypeFakes.size() > 1) {
            Collections.sort(MediaTypeFakes, new CompoundComparatorFake<MediaTypeFake>(
                    MediaTypeFake.SPECIFICITY_COMPARATOR, MediaTypeFake.QUALITY_VALUE_COMPARATOR));
        }
    }

    @Override
    protected void checkParameters(String attribute, String value) {
        super.checkParameters(attribute, value);
        if (PARAM_QUALITY_FACTOR.equals(attribute)) {
            value = unquote(value);
            double d = Double.parseDouble(value);
        }
    }

    /**
     * Return the quality factor, as indicated by a {@code q} parameter, if any.
     * Defaults to {@code 1.0}.
     *
     * @return the quality factor as double value
     */
    public double getQualityValue() {
        String qualityFactor = getParameter(PARAM_QUALITY_FACTOR);
        return (qualityFactor != null ? Double.parseDouble(unquote(qualityFactor)) : 1D);
    }

    /**
     * Indicate whether this {@code MediaTypeFake} includes the given media type.
     * <p>For instance, {@code text/*} includes {@code text/plain} and {@code text/html},
     * and {@code application/*+xml} includes {@code application/soap+xml}, etc.
     * This method is <b>not</b> symmetric.
     * <p>Simply calls {@link MimeTypeFake#includes(MimeTypeFake)} but declared with a
     * {@code MediaTypeFake} parameter for binary backwards compatibility.
     *
     * @param other the reference media type with which to compare
     * @return {@code true} if this media type includes the given media type;
     * {@code false} otherwise
     */
    public boolean includes(MediaTypeFake other) {
        return super.includes(other);
    }

    /**
     * Indicate whether this {@code MediaTypeFake} is compatible with the given media type.
     * <p>For instance, {@code text/*} is compatible with {@code text/plain},
     * {@code text/html}, and vice versa. In effect, this method is similar to
     * {@link #includes}, except that it <b>is</b> symmetric.
     * <p>Simply calls {@link MimeTypeFake#isCompatibleWith(MimeTypeFake)} but declared with a
     * {@code MediaTypeFake} parameter for binary backwards compatibility.
     *
     * @param other the reference media type with which to compare
     * @return {@code true} if this media type is compatible with the given media type;
     * {@code false} otherwise
     */
    public boolean isCompatibleWith(MediaTypeFake other) {
        return super.isCompatibleWith(other);
    }

    /**
     * Return a replica of this instance with the quality value of the given {@code MediaTypeFake}.
     *
     * @return the same instance if the given MediaTypeFake doesn't have a quality value,
     * or a new one otherwise
     */
    public MediaTypeFake copyQualityValue(MediaTypeFake MediaTypeFake) {
        if (!MediaTypeFake.getParameters().containsKey(PARAM_QUALITY_FACTOR)) {
            return this;
        }
        Map<String, String> params = new LinkedHashMap<String, String>(getParameters());
        params.put(PARAM_QUALITY_FACTOR, MediaTypeFake.getParameters().get(PARAM_QUALITY_FACTOR));
        return new MediaTypeFake(this, params);
    }

    /**
     * Return a replica of this instance with its quality value removed.
     *
     * @return the same instance if the media type doesn't contain a quality value,
     * or a new one otherwise
     */
    public MediaTypeFake removeQualityValue() {
        if (!getParameters().containsKey(PARAM_QUALITY_FACTOR)) {
            return this;
        }
        Map<String, String> params = new LinkedHashMap<String, String>(getParameters());
        params.remove(PARAM_QUALITY_FACTOR);
        return new MediaTypeFake(this, params);
    }
}
