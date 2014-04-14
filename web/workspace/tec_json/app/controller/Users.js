Ext.define('tec_json.controller.Users', {
	extend : 'Ext.app.Controller',
	stores : [ 'Users' ],
	models : [ 'User' ],
	views : [ 'users.Form' ],
	init : function() {
		this.control({
			'UsersGrid' : {
				selectionchange : this.gridSelectionChange,
				viewready : this.onViewReady
			}
		});
	}
});