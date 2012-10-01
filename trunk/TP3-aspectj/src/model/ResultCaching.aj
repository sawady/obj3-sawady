package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public aspect ResultCaching {

	private Map<String, Map<ArrayWrapper, Integer>> cachedResults = new HashMap<String, Map<ArrayWrapper, Integer>>();

	pointcut isCachable() : call(@CachResults * *(..));

	Integer around() : isCachable() {
		String signature = thisJoinPoint.getSignature().toString();
		ArrayWrapper args = new ArrayWrapper(thisJoinPoint.getArgs());
		if (isCachedArgs(signature, args)) {
			return getCachedResult(signature, args);
		} else {
			Integer result = proceed();
			catchResult(signature, args, result);
			return result;
		}
	}

	private void catchResult(String signature, ArrayWrapper args,	Integer proceed) {
		if (isCachedArgs(signature, args)) {
			
		} else {
			
		}
	}
	
	private boolean isChachedSignature(String signature) {
		return this.cachedResults.containsKey(signature);
	}

	private boolean isCachedArgs(String signature, ArrayWrapper args) {
		return isChachedSignature(signature)
				&& this.cachedResults.get(signature).containsKey(args);
	}

	private Integer getCachedResult(String signature, ArrayWrapper args) {
		return this.cachedResults.get(signature).get(args);
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

		private ResultCaching getOuterType() {
			return ResultCaching.this;
		}

	}

}