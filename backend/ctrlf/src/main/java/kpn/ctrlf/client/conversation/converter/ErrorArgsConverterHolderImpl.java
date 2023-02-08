package kpn.ctrlf.client.conversation.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public final class ErrorArgsConverterHolderImpl implements ErrorArgsConverterHolder {
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock readLock = lock.readLock();
	private final Lock writeLock = lock.writeLock();

	private final Map<String, Supplier<ErrorArgsConverter>> suppliers = new HashMap<>();

	@Override
	public ErrorArgsConverter take(String code) {
		readLock.lock();
			ErrorArgsConverter result = suppliers.containsKey(code) && suppliers.get(code) != null
				? suppliers.get(code).get()
				: new DefaultErrorArgsConverter();
		readLock.unlock();

		return result;
	}

	@Override
	public ErrorArgsConverterHolder set(String code, Supplier<ErrorArgsConverter> supplier) {
		writeLock.lock();
			suppliers.put(code, supplier);
		writeLock.unlock();

		return this;
	}

	@Override
	public ErrorArgsConverterHolder erase(String code) {
		writeLock.lock();
			suppliers.remove(code);
		writeLock.unlock();

		return this;
	}
}
