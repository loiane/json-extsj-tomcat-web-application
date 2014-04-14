/**
 * The Users store
 */
Ext.define('tec_json.store.Users', {
    extend: 'Ext.data.Store',
    model: 'tec_json.model.User',

    proxy: {
        type: 'ajax',
        url: 'actions/user_admin',
        reader: {
            type: 'json',
            root: 'users',
            idProperty: 'userId'
        }
    }
});