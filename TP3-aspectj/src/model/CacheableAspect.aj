package model;

import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public aspect CacheableAspect {

	private static Map<String, Map<ArrayWrapper, Object>> cachedResults = new HashMap<String, Map<ArrayWrapper, Object>>();
	
	declare parents : @Cacheable * implements CacheableQueries;
	
	pointcut cacheableObject() : call(@CacheableFunction * @Cacheable *.*(..));
	
	Object around() : cacheableObject() {
		String signature = thisJoinPoint.getSignature().toString();
		ArrayWrapper args = new ArrayWrapper(thisJoinPoint.getArgs());
		if (isCachedArgs(signature, args)) {
			System.out.println("cached");
			return getCachedResult(signature, args);
		} else {
			Object result = proceed();
			catchResult(signature, args, result);
			return result;
		}
	}

	private void catchResult(String signature, ArrayWrapper args, Object proceed) {
		if(!isChachedSignature(signature)) {
			cachedResults.put(signature, new HashMap<ArrayWrapper, Object>());
		}
		
		cachedResults.get(signature).put(args, proceed);
	}
	
	private boolean isChachedSignature(String signature) {
		return cachedResults.containsKey(signature);
	}

	private boolean isCachedArgs(String signature, ArrayWrapper args) {
		return isChachedSignature(signature)
				&& cachedResults.get(signature).containsKey(args);
	}

	private Object getCachedResult(String signature, ArrayWrapper args) {
		return cachedResults.get(signature).get(args);
	}
	
	public void CacheableQueries.getCache(String signature) {
		cachedResults.get(signature);
	}
	
	public void CacheableQueries.cleanCache(String signature) {
		cachedResults.remove(signature);
	}

	private class ArrayWrapper {
		private Object[] array;

		public ArrayWrapper(Object[] anArray) {
			this.array = anArray;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Arrays.hashCode(array);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ArrayWrapper other = (ArrayWrapper) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (!Arrays.equals(array, other.array))
				return false;
			return true;
		}

		private CacheableAspect getOuterType() {
			return CacheableAspect.this;
		}

	}
	
}