package com.hit.dao.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.model.Article;

public class ArticleDaoImpl implements IDao{

	private String dataFile;
	
	public ArticleDaoImpl(String dataFile) {
		this.dataFile =dataFile;
	}
	@Override
	public void save(IAlgoCache<Article> cache) {
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.dataFile))){
			out.writeObject(cache);
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public IAlgoCache<Article> load() {
		IAlgoCache<Article> cache = null;
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.dataFile))){
			cache = (IAlgoCache<Article>)in.readObject();
		}catch(IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return cache;
	}

}
