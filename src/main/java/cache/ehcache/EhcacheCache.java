package cache.ehcache;
import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;

public class EhcacheCache {

	public CacheManager manager = CacheManager.create(getClass().getClassLoader().getResource("ehcache.xml"));
	public Cache cache = null;
	public Cache tradeCache = null;
	private String cacheName;
	private boolean local = false;

	public EhcacheCache() {

	}

	public void init() {
		if (cache == null) {
//			if (local) {
//				cache = new Cache(cacheName, 100, false, true, 100, 100);
//			} else {
//				cache = new Cache(cacheName, 100,
//						MemoryStoreEvictionPolicy.LFU, false, "", true, 100,
//						100, false, 100, null);
//				new RegisteredEventListeners(cache)
//						.registerListener(new RMICacheReplicatorFactory()
//								.createCacheEventListener(new Properties()));
//				cache
//						.setBootstrapCacheLoader(new RMIBootstrapCacheLoaderFactory()
//								.createBootstrapCacheLoader(new Properties()));
//			}
//			manager.addCache(cache);
			cache = manager.getCache("finCache");
		}
		if(tradeCache == null){
			tradeCache = manager.getCache("tradeCache");
		}
	}

	public void put(String key, Serializable obj) {
		init();
		cache.put(new Element(key, obj));
	}

	public void put(String key, Serializable obj, int liveTime) {
		init();
		cache.put(new Element(key, obj, false, liveTime, liveTime));
	}

	public Object get(String key) {
		init();
		Element ele = cache.get(key);
		return ele == null ? null : ele.getObjectValue();
	}

	public boolean remove(String key) {
		init();
		return cache.remove(key);
	}
	
	public void putTrade(String key, Serializable obj) {
		init();
		tradeCache.put(new Element(key, obj));
	}

	public void putTrade(String key, Serializable obj, int liveTime) {
		init();
		tradeCache.put(new Element(key, obj, false, liveTime, liveTime));
	}

	public Object getTrade(String key) {
		init();
		Element ele = tradeCache.get(key);
		return ele == null ? null : ele.getObjectValue();
	}

	public boolean removeTrade(String key) {
		init();
		return tradeCache.remove(key);
	}

	public void shutdown() {
		CacheManagerPeerProvider jgrpMana = (CacheManagerPeerProvider)manager.getCacheManagerPeerProvider("JGroups");
		if(jgrpMana != null)
			jgrpMana.dispose();
		manager.shutdown();
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}
}
