package com.hit.dao;

import com.hit.algorithm.IAlgoCache;
import com.hit.model.Article;

public interface IDao {

	void save(IAlgoCache<Article> cache);
	IAlgoCache<Article> load();
}
