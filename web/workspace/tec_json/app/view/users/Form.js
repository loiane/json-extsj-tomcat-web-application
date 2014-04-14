Ext.define('tec_json.view.users.Form', {
	extend : 'Ext.form.Panel',
	alias : 'widget.tec-users-form',
	frame : true,
	title : 'Users Form',

	fieldDefaults : {
		labelAlign : 'left',
		msgTarget : 'side'
	},

	items : [ {
		xtype : 'tec-users-grid'
	} ],
	buttonAlign : "center",
	buttons : [ {
		text : 'save',
		handler : function() {
		}
	}, {
		text : 'cancel',
		handler : function() {
		}
	} ]
});