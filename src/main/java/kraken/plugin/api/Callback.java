package kraken.plugin.api;

/**
 * A callback, returns no value.
 */
public interface Callback<T> {

    void call(T t);

}
