/**
 * The User object
 */
Ext.define('tec_json.model.User', {
    extend: 'Ext.data.Model',

    fields: [
        { name: 'userId', type: 'number' },
        { name: 'userName', type: 'string' },
        { name: 'email', type: 'string' },
        { name: 'enabled', type: 'boolean' }
// TODO add roles to model
    ]
});