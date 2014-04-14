Ext.define('tec_json.controller.Main', {
	extend : 'Ext.app.Controller',
	stores : [ 'Status', 'Users' ],

	/**
	 * Configure application event bindings.
	 */
	init : function() {
		var me = this;

		// Component Event Listeners

		me.control({
			'tec-main' : {
				afterrender : me.initMainContainer
			}
		});
	},

	afterLoadStatus : function() {
		var me = this;
		console.log('afterLoadStatus', arguments);
	},

	afterLoadUsers : function() {
		var me = this;
		console.log('afterLoadUsers', arguments);
	},

	initMainContainer : function() {
		var me = this, st = Ext.getStore('Status'), us = Ext.getStore('Users');

		// Ext also set up a getter for you since you enumerated the store in
		// the stores collection above: // get<YourStoreName>Store() -- in this
		// case -- me.getStatusStoreStore() but since you named your store
		// StatusStore the getter looks a little funny -- so I chose an
		// alternate
		// method of fetching the store which reads better in your case. I would
		// have named the store as the plural of whatever specific model it
		// holds
		// e.g. Statuses

		st.on('load', me.afterLoadStatus, me);
		st.load();
		st.on('load', me.afterLoadUsers, me);
		us.load();
	}
});
