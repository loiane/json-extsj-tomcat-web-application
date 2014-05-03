Ext.define('tec_json.view.Main', {
	extend : 'Ext.container.Container',
	alias : 'widget.tec-main',
	requires : [ 'Ext.tab.Panel', 'Ext.layout.container.Border',
			'tec_json.view.TabPanel', 'tec_json.view.StatusGrid',
			'tec_json.view.users.Form', 'tec_json.view.LoginForm',
			'tec_json.view.users.Grid' ],
	items : [ {
		"xtype" : "tec-tab-panel"
	} ]
});