Ext.define('tec_json.controller.Users', {
	extend : 'Ext.app.Controller',
	stores : [ 'Users' ],
	models : [ 'User' ],
	views : [ 'users.Form' ],

	/**
	 * Configure users grid event bindings.
	 */
	init : function() {
		var me = this;

		// Component Event Listeners

		me.control({
			'UsersGrid' : {
				selectionchange : this.gridSelectionChange,
				viewready : this.onViewReady,
				afterrender : me.initUsersContainer
			}
		});
	},

	/**
	 * do container initing stuff
	 */
	initUsersContainer : function() {
		Ext.getStore('Users').load();
	}
});