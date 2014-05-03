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

	initMainContainer : function() {
		Ext.getStore('Status').load();
	}
});
