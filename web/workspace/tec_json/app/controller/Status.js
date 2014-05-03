Ext.define('tec_json.controller.Status', {
	extend : 'Ext.app.Controller',
	stores : [ 'Status' ],
	models : [ 'NameValue' ],
	views : [ 'StatusGrid' ],
	init : function() {

		this.control({
			'UsersGrid' : {
				selectionchange : this.gridSelectionChange,
				viewready : this.onViewReady
			}
		});
	}
});