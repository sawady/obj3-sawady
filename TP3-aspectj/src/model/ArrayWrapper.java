package model;

import java.util.Arrays;

public class ArrayWrapper {
	private Object[] array;

	public ArrayWrapper(Object[] anArray) {
		this.array = anArray;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (!Arrays.equals(array, other.array))
			return false;
		return true;
	}

}
