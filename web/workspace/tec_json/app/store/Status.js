Ext.define('tec_json.store.Status', {
    extend: 'Ext.data.Store',
    model: 'tec_json.model.NameValue',

    proxy: {
        type: 'ajax',
        url: 'actions/status',
        reader: {
            type: 'json',
            root: 'status',
            idProperty: 'name'
        }
    }
});