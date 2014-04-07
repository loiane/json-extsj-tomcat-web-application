Ext.define('tec_json.view.Main', {
    extend: 'Ext.container.Container',
    alias: 'widget.tec-main',
    requires:[
        'Ext.tab.Panel',
        'Ext.layout.container.Border',
        'tec_json.view.StatusGrid'
    ],


    layout: {
        type: 'border'
    },

    items: [{
        region: 'west',
        xtype: 'panel',
        title: 'navigation',
        width: 150
    },{
        region: 'center',
        xtype: 'tabpanel',
        items: { xtype: 'tec-status-grid' }
    }]
});