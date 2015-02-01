Ext.define('tec_json.view.TabPanel', {
	extend : 'Ext.tab.Panel',
	alias : 'widget.tec-tab-panel',
	requires : [ 'tec_json.view.StatusGrid', 'tec_json.view.users.Form' ],
	layout : "border",
	items : [ {
		id : "tec-status-tab",
		title : "Status",
		tooltip : "System status data",
		xtype : 'tec-status-grid',
		region : 'north'
	}, {
		id : "tec-users-tab",
		title : "Users",
		tooltip : "user management",
		xtype : 'tec-users-form',
		region : 'north'
	} ],
	buttonAlign : "center",
	buttons : [ {
		text : 'log out',
		handler : function() {
			Ext.Ajax.request({
				url : 'j_spring_security_logout',
				success : function(response) {
					location.reload();
				}
			});
		}
	} ],
	listeners : {
		tabchange : function(tabPanel, tab) {
			if (tab.id === "tec-status-tab") {
				Ext.getStore('Status').reload();
			} else if (tab.id === "tec-users-tab") {
				Ext.getStore('Users').reload();
			}
			tab.doLayout();
		}
	}
});
