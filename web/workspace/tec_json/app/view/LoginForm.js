Ext.define('tec_json.view.LoginForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.tec-login-form',
	id : 'tec-login-form',
	labelWidth : 80,
	url : 'j_spring_security_check',
	frame : true,
	title : 'Please Login',
	defaultType : 'textfield',
	monitorValid : true,
	focusOnToFront : false,
	items : [ {
		fieldLabel : 'Username',
		name : 'j_username',
		itemId : 'username',
		allowBlank : false
	}, {
		fieldLabel : 'Password',
		name : 'j_password',
		itemId : 'password',
		inputType : 'password',
		allowBlank : false
	} ],
	buttons : [ {
		text : 'Login',
		formBind : true,
		handler : function(button, e) {
			var form = button.up('tec-login-form').getForm();
			form.submit({
				method : 'POST',
				waitTitle : 'Connecting',
				waitMsg : 'Sending data...',
				success : function(form, action) {
					var data = Ext.JSON.decode(action.response.responseText);
					if (data.success) {
						var wdw = form.owner.up('window');
						wdw.close();
						location.reload();
					} else {
						alert("your a big fat phoney!!!");
					}
				}
			});
		}
	} ]
});
