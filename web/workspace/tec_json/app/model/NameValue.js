/**
 * A generic model object for simple entities that only require a value and
 * an associated label.
 */
Ext.define('tec_json.model.NameValue', {
    extend: 'Ext.data.Model',

    fields: [
        { name: 'name', type: 'string' },
        { name: 'value', type: 'string' }
    ]
});