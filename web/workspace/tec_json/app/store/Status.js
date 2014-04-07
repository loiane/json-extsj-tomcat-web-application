Ext.define('tec_json.store.Status', {
    extend: 'Ext.data.Store',
    model: 'tec_json.model.NameValue',

    proxy: {
        type: 'ajax',
        url: 'actions/status',
//        url: '/app/store/status.json', // for local testing
        reader: {
            type: 'json',
            root: 'status',
            idProperty: 'name'
        }
    }
});