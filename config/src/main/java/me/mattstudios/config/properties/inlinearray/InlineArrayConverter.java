package me.mattstudios.config.properties.inlinearray;

import me.mattstudios.config.properties.InlineArrayProperty;

/**
 * Converter from a simple String value to an array of multiple values.
 * Used in {@link InlineArrayProperty}.
 *
 * @param <T> the array type
 */
public interface InlineArrayConverter<T> {

    /**
     * Converts from the String to an array of the converter's type.
     *
     * @param in the string to convert from
     * @return array with elements based on the input String, never null
     */
    T[] fromString(String in);

    /**
     * Converts the provided array to its String representation (opposite of {@link #fromString(String)}).
     *
     * @param value the value to convert
     * @return String representation of the array
     */
    String toExportValue(T[] value);

}
