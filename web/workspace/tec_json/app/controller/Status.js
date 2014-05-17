Ext.define('tec_json.controller.Status', {
	extend : 'Ext.app.Controller',
	stores : [ 'Status' ],
	models : [ 'NameValue' ],
	views : [ 'StatusGrid' ],

	/**
	 * Configure status grid event bindings.
	 */
	init : function() {
		var me = this;

		// Component Event Listeners

		me.control({
			'StatusGrid' : {
				selectionchange : this.gridSelectionChange,
				viewready : this.onViewReady,
				afterrender : me.initStatusContainer
			}
		});
	},

	/**
	 * do container initing stuff
	 */
	initStatusContainer : function() {
		Ext.getStore('Status').load();
	}
});