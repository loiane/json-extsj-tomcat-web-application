/**
 * User admin grid
*/

//to enable editing
var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    clicksToEdit: 1
});

Ext.define('tec_json.view.users.Grid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.tec-users-grid',
    store: 'Users',
    plugins: [cellEditing],
    title: 'User Grid',
    columns: [
        {
			text : 'id',
			dataIndex : 'userId',
			hidden : true
        },
        {
        	text: 'Name',
            dataIndex: 'userName',
            flex: 1,
            field: {
                allowBlank: false,
                xtype: 'textfield'
           }
        },
        {
        	text: 'email',
            dataIndex: 'email',
            flex: 1,
            field: {
                allowBlank: false,
                xtype: 'textfield'
           }
        },
        {
        	text: 'enabled',
            dataIndex: 'enabled',
            flex: 1,
            xtype: 'checkcolumn'
        },
        {
            text: 'roles',
            dataIndex: 'userRoles',
            flex: 1,
            field: {
                xtype: 'combobox',
                store: {
                	fields: ['role', 'display'],
                	data: [
                	       {role : 'ROLE_ANONYMOUS', display : 'Guest'},
                	       {role : 'USER_ROLE', display : 'User'},
                	       {role : 'ROLE_ADMIN', display : 'Admin'}
        	       ]
                },
                editable : false,
                multiSelect : true,
                valueField: 'role',
                displayField : 'display'
            }
        }
]});
