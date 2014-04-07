Ext.define('tec_json.view.StatusGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.tec-status-grid',
    store: 'Status',

    title: 'Status Grid',

    columns: [
        {
            text: 'Name',
            dataIndex: 'name',
            width: 150
        },
        {
            text: 'Value',
            dataIndex: 'value',
            flex: 1
        }
    ]
});
