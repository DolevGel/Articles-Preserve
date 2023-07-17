package com.hit.service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dao.impl.ArticleDaoImpl;
import com.hit.model.Article;

public class Service {

	private final static long SAVE_DELAY = 60000 * 10;
	private final static long SAVE_SCHEDULE = SAVE_DELAY * 3;
	private IDao dao;
	private IAlgoCache<Article> cache;
	private Timer saveTimer;

	public Service(IAlgoCache<Article> inCache) {
		
		dao = new ArticleDaoImpl("../resources/cache.bin");
		cache  = dao.load();
		if(cache == null) {
			this.cache = inCache;
		}
		this.saveTimer = new Timer();
		saveTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				dao.save(cache);
			}
		}, SAVE_DELAY, SAVE_SCHEDULE);
	}

	public synchronized boolean add(Article b) {
		boolean response = true;
		cache.refer(b);
		return response;
	}

	public synchronized Article get() {
		
		return cache.get();
	}


}
