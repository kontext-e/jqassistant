package com.buschmais.jqassistant.core.scanner.api;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import com.buschmais.jqassistant.core.store.api.model.Descriptor;

/**
 * Defines the interface for a scanner plugin.
 * 
 * @param <I>
 *            The item type accepted by the plugin.
 */
public interface ScannerPlugin<I, D extends Descriptor> {

    /**
     * Defines the annotation for specifying a dependency to another plugin to
     * provide an instance of the given descriptor value.
     * 
     * <pre>
     * &#064;Requires(XmlDescriptor.class)
     * public class MyPlugin implements ScannerPlugin&lt;FileResource, MyDescriptor&gt; {
     * 
     *     public MyDescriptor scan(FileResource item, String path, Scope scope) {
     * 
     *     }
     * }
     * </pre>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Requires {

        /**
         * @return The scanner plugins which must be executed first.
         */
        Class<? extends Descriptor>[] value();
    }

    /**
     * Initialize the plugin.
     * 
     * Life cycle callback for a plugin to do static initialization. Will be
     * exactly once after the plugin has been instantiated.
     */
    void initialize();

    /**
     * Configure the plugin.
     *
     * This method is always called at least once after {@link #initialize()}
     * and allows re-configuring a plugin instance at runtime (e.g. in a Maven
     * multi-module build process).
     * 
     * @param properties
     *            The plugin properties.
     */
    void configure(Map<String, Object> properties);

    /**
     * Return the item type accepted by the plugin.
     * 
     * @return The item type.
     */
    Class<? extends I> getType();

    /**
     * Return the descriptor type produced by the plugin.
     *
     * @return The descriptor type.
     */
    Class<? extends D> getDescriptorType();

    /**
     * Determine if the item is accepted by the plugin.
     * 
     * @param item
     *            The item.
     * @param path
     *            The path where the item is located.
     * @param scope
     *            The scope.
     * @return <code>true</code> if the plugin accepts the item.
     * @throws IOException
     *             If a problem occurs.
     */
    boolean accepts(I item, String path, Scope scope) throws IOException;

    /**
     * Scan the item.
     * 
     * @param item
     *            The item.
     * @param path
     *            The path where the item is located.
     * @param scope
     *            The scope.
     * @param scanner
     *            The scanner instance to delegate items this plugin resolves
     *            from the given item.
     * @return The {@link Descriptor} instance representing the scanned item.
     * @throws IOException
     *             If a problem occurs.
     */
    D scan(I item, String path, Scope scope, Scanner scanner) throws IOException;

}
