package Util;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by xiaoyue.wang on 2019/5/7.
 */

public class MapUtil<k,V> implements Map<k, V> {

    public MapUtil() {
    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(k key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(@NonNull Map<? extends k, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @NonNull
    @Override
    public Set<k> keySet() {
        return null;
    }

    @NonNull
    @Override
    public Collection<V> values() {
        return null;
    }

    @NonNull
    @Override
    public Set<Entry<k, V>> entrySet() {
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super k, ? super V> action) {

    }

    @Override
    public void replaceAll(BiFunction<? super k, ? super V, ? extends V> function) {

    }

    @Override
    public V putIfAbsent(k key, V value) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(k key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public V replace(k key, V value) {
        return null;
    }

    @Override
    public V computeIfAbsent(k key, Function<? super k, ? extends V> mappingFunction) {
        return null;
    }

    @Override
    public V computeIfPresent(k key, BiFunction<? super k, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V compute(k key, BiFunction<? super k, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V merge(k key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return null;
    }
}
