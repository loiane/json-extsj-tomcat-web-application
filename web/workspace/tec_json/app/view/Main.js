Ext.define('tec_json.view.Main', {
	extend : 'Ext.container.Container',
	alias : 'widget.tec-main',
	requires : [ 'tec_json.view.TabPanel', 'tec_json.view.LoginForm' ],
	items : [ {
		"xtype" : "tec-tab-panel"
	} ]
});