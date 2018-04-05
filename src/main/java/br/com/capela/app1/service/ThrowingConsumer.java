package br.com.capela.app1.service;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
	void accept(T t) throws E;
}