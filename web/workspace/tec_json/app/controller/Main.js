Ext.define('tec_json.controller.Main', {
	extend : 'Ext.app.Controller',
	//not sure why i have to have all stores here
	stores : [ 'Status', 'Users', 'CurrentUser' ],

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

	/**
	 * do container initing stuff
	 */
	initMainContainer : function() {
		Ext.getStore('CurrentUser').load();
	}
});
