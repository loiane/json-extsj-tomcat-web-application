/**
 * setup application
 */
Ext.define('tec_json.Application', {
	name : 'tec_json',
	extend : 'Ext.app.Application',
	controllers : [ 'Main' ]
});

/**
 * handle response errors
 */
Ext.Ajax.on('requestexception', function(con, response, op, e) {
	if (response.status == 401) {
		var win = new Ext.Window({
			id : 'tec-login-window',
			layout : 'fit',
			width : 300,
			height : 150,
			closable : false,
			resizable : false,
			plain : true,
			border : false,
			modal : true,
			defaultFocus : 'username', // set focus on form field...
			items : [ {
				xtype : 'tec-login-form'
			} ]
		});
		win.show();
	} else if (response.status == 403) {
		alert('You do not have the stones to access this content');
	} else if (response.status == 500) {
		alert(response.responseText);
	}
});
