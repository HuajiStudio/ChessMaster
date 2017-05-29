package org.huajistudio.chessmaster.util.registry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class SimpleRegistry<K, V> extends HashMap<K, V> implements IRegistry<K, V> {
	@Override
	public Iterator<V> iterator() {
		return values().iterator();
	}

	@SuppressWarnings("unchecked")
	public V getRandomObject(Random random) {
		return (V) values().toArray()[random.nextInt(values().size())];
	}
}
