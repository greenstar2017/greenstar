define([ "jquery"], function(jQuery) {
	var storage = undefined;
	if (window.localStorage) {
		storage = window.localStorage;
	}
	LocalStorage = {
			
		KEY_CURR_STORE_ID: "KEY_CURR_STORE_ID",//店铺ID本地存储key
		saveLocal : function(key, msg) {
			if (localStorageWork()) {
				storage.setItem(key, msg);
			}
		},
		readLocal : function(key) {
			var localMsg = storage.getItem(key)||"";
			return localMsg;
		},
		removeLocal : function(key) {
			storage.removeItem(key);
		},
		localStorageWork : function() {
			return localStorageWork();
		}
	};

	/**
	 * 浏览器是否支持HTML5本地存储
	 */
	function localStorageWork() {
		if (undefined != storage) {
			return true;
		} else {
			return false;
		}
	}

});