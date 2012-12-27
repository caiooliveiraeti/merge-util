package br.com.javacia.merge.util;

import java.util.List;


public interface DifferenceInspector {

	public abstract List<Difference> findDifferences();
}