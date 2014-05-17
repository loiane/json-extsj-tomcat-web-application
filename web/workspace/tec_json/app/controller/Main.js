Ext.define('tec_json.controller.Main', {
	extend : 'Ext.app.Controller',
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

	initMainContainer : function() {
		Ext.getStore('CurrentUser').load();
	}
});
