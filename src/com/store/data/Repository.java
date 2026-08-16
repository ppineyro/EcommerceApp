package com.store.data;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Repository<T> {
	private final List<T> items;
	
	public Repository() {
		this.items = new ArrayList<>();
	}
	
	public void add(T item) {
		if (item != null) {
			items.add(item);
		}
	}
	
	public boolean remove(T item) {
		return items.remove(item);
	}
	
	public List<T> getAll(){
		return new ArrayList<>(items);
	}
	
	public List<T> find(Predicate<T> predicate) {
		List<T> result = new ArrayList<>();
		for (T item : items) {
			if (predicate.test(item)) {
				result.add(item);
			}
		}
		return result;
	}
	
	public T findFirst(Predicate<T> predicate) {
		for (T item : items) {
			if (predicate.test(item)) {
				return item;
			}
		}
		return null;
	}
	
	public int count() {
		return items.size();
	}
}
