Ext.define('tec_json.view.Viewport', {
	extend : 'Ext.container.Viewport',
	requires : [ 'Ext.layout.container.Fit', 'tec_json.view.Main' ],
	layout : {
		type : 'fit'
	},
	items : [ {
		xtype : 'tec-main'
	} ]
});
