/**
 * The current User store
 */
Ext.define('tec_json.store.CurrentUser', {
	extend : 'Ext.data.Store',
	model : 'tec_json.model.User',
	proxy : {
		type : 'ajax',
		url : 'actions/user_current',
		reader : {
			type : 'json',
			root : 'user',
			idProperty : 'userId'
		}
	}
});