Ext.define('tec_json.view.Main', {
	extend : 'Ext.container.Container',
	alias : 'widget.tec-main',
	requires : [ 'Ext.tab.Panel', 'Ext.layout.container.Border',
			'tec_json.view.StatusGrid', 'tec_json.view.users.Form',
			'tec_json.view.users.Grid' ],
	items : [ {
		xtype : 'tabpanel',
		items : [ {
			id : "tec-status-tab",
			title : "Status",
			tooltip : "System status data",
			xtype : 'tec-status-grid'
		}, {
			id : "tec-users-tab",
			title : "Users",
			tooltip : "user management",
			xtype : 'tec-users-form'
		} ]
	} ]
});