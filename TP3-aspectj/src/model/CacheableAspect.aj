package model;

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
		if (!isChachedSignature(signature)) {
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

}