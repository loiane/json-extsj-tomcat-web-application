/**
 * the login controller
 */
Ext.define('tec_json.controller.Login', {
	extend : 'Ext.app.Controller',
	views : [ 'LoginForm' ],
	stores : [ 'CurrentUser' ],
	init : function() {
		//this.control();
	}
});