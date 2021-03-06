package me.mattstudios.config.properties;

import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.properties.types.PropertyType;
import me.mattstudios.config.resource.PropertyReader;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Set property of configurable type. The sets are immutable and preserve the order.
 *
 * @param <T> the set type
 */
public class SetProperty<T> extends BaseProperty<Set<T>> {

    private final PropertyType<T> type;

    /**
     * Constructor.
     *
     * @param type the property type
     * @param defaultValue the default value of the property
     */
    public SetProperty(PropertyType<T> type, Set<T> defaultValue) {
        super(Collections.unmodifiableSet(defaultValue));
        Objects.requireNonNull(type, "type");
        this.type = type;
    }

    @Override
    protected Set<T> getFromReader(PropertyReader reader, ConvertErrorRecorder errorRecorder) {
        List<?> list = reader.getList(getPath());

        if (list != null) {
            return list.stream()
                .map(elem -> type.convert(elem, errorRecorder, this))
                .filter(Objects::nonNull)
                .collect(setCollector());
        }
        return null;
    }

    @Override
    public Object toExportValue(Set<T> value) {
        return value.stream()
            .map(t -> type.toExportValue(t, this))
            .collect(Collectors.toList());
    }

    protected Collector<T, ?, Set<T>> setCollector() {
        return Collectors.collectingAndThen(Collectors.toCollection(LinkedHashSet::new), Collections::unmodifiableSet);
    }

    private static <E> Set<E> newSet(E[] array) {
        return Arrays.stream(array).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
