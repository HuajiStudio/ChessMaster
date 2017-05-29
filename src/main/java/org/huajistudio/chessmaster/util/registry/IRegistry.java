package org.huajistudio.chessmaster.util.registry;

import java.util.Set;

public interface IRegistry<K, V> extends Iterable<V> {
	V get(K name);

	/**
	 * Register an object on this registry.
	 */
	V put(K key, V value);

	/**
	 * Gets all the keys recognized by this registry.
	 */
	Set<K> keySet();
}
