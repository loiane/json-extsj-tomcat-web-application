/**
 * the UI main entry point, ties the tab panel to the view port
 */
Ext.define('tec_json.view.Main', {
	extend : 'Ext.container.Container',
	alias : 'widget.tec-main',
	requires : [ 'tec_json.view.TabPanel', 'tec_json.view.LoginForm' ],
	layout : {
		type : 'fit'
	},
	items : [ {
		"xtype" : "tec-tab-panel"
	} ]
});