var tip = null;

Ext.define('Ext.ux.EMailCheckTrigger', {
    extend: 'Ext.form.field.Trigger',
    alias: 'widget.eMailCheckTrigger',
    initComponent: function () {
        var me = this;
        me.triggerCls = 'x-form-search-trigger';
        me.callParent(arguments);
    },
    // override onTriggerClick
    onTriggerClick: function(e) {
    	UserDwr.isExits({userId : Ext.getCmp('joinForm').getForm().findField('userId').getValue()}, function(result){
    		if(result==1){
//    			Ext.Msg.alert('중복확인', '사용할 수 없습니다.');
    			Ext.getCmp('joinForm').getForm().findField('idCheckField').setValue('<span style="color:red;">이미 존재하는 정보입니다.</span>');
    		}else{
    			Ext.getCmp('joinForm').getForm().findField('isExitsId').setValue(0);
    			Ext.getCmp('joinForm').getForm().findField('idCheckField').setValue('<span style="color:green;">사용 가능합니다.</span>');
    		}
    	});
    }
});

Ext.define('Ext.ux.NickCheckTrigger', {
	extend: 'Ext.form.field.Trigger',
	alias: 'widget.nickCheckTrigger',
	initComponent: function () {
		var me = this;
		me.triggerCls = 'x-form-search-trigger';
		me.callParent(arguments);
	},
	// override onTriggerClick
	onTriggerClick: function() {
		UserDwr.isExits({nickname : Ext.getCmp('joinForm').getForm().findField('nickname').getValue()}, function(result){
			if(result==1){
//    			Ext.Msg.alert('중복확인', '사용할 수 없습니다.');
				Ext.getCmp('joinForm').getForm().findField('nickCheckField').setValue('<span style="color:red;">이미 존재하는 별명입니다.</span>');
			}else{
				Ext.getCmp('joinForm').getForm().findField('isExitsNick').setValue(0);
				Ext.getCmp('joinForm').getForm().findField('nickCheckField').setValue('<span style="color:green;">사용 가능합니다.</span>');
			}
		});
	}
});

var formPanel = Ext.widget('form', {
	id: 'joinForm',
    frame: true,
    width: 350,
    bodyPadding: 10,
    bodyBorder: true,
    title: 'Account Registration',

    defaults: {
        anchor: '100%'
    },
    fieldDefaults: {
        labelAlign: 'left',
        msgTarget: 'none',
        invalidCls: '' //unset the invalidCls so individual fields do not get styled as invalid
    },

    /*
     * Listen for validity change on the entire form and update the combined error icon
     */
    listeners: {
        fieldvaliditychange: function() {
            this.updateErrorState();
        },
        fielderrorchange: function() {
            this.updateErrorState();
        }
    },

    updateErrorState: function() {
        var me = this,
            errorCmp, fields, errors;

        if (me.hasBeenDirty || me.getForm().isDirty()) { //prevents showing global error when form first loads
            errorCmp = me.down('#formErrorState');
            fields = me.getForm().getFields();
            errors = [];
            fields.each(function(field) {
                Ext.Array.forEach(field.getErrors(), function(error) {
                    errors.push({name: field.getFieldLabel(), error: error});
                });
            });
            errorCmp.setErrors(errors);
            me.hasBeenDirty = true;
        }
    },

    items: [
	{
		xtype: 'eMailCheckTrigger',
		name: 'userId',
		fieldLabel: 'e-Mail',
		allowBlank: false,
		minLength: 6,
		maxLength: 50,
		vtype: 'email',
		emptyText: '이메일'
	}
	,{
        xtype: 'displayfield',
        name: 'idCheckField',
        fieldLabel: '',
        value: '<span style="color:red;">이메일 중복 여부를 확인해주세요.</span>'
    }
	,{
        xtype: 'textfield',
        name: 'userName',
        fieldLabel: 'User Name',
        allowBlank: false,
        maxLength: 20
    }, {
    	xtype: 'nickCheckTrigger',
    	name: 'nickname',
    	fieldLabel: 'Nick Name',
    	allowBlank: false,
    	maxLength: 20
    },{
		xtype: 'displayfield',
		name: 'nickCheckField',
		fieldLabel: '',
		value: '<span style="color:red;">별명 중복 여부를 확인해주세요.</span>'
    }, {
        xtype: 'textfield',
        name: 'passwd',
        fieldLabel: 'Password',
        inputType: 'password',
        style: 'margin-top:15px',
        allowBlank: false,
        minLength: 8
    }, {
        xtype: 'textfield',
        name: 'password2',
        fieldLabel: 'Repeat Password',
        inputType: 'password',
        allowBlank: false,
        /**
         * Custom validator implementation - checks that the value matches what was entered into
         * the passwd field.
         */
        validator: function(value) {
            var passwd = this.previousSibling('[name=passwd]');
            return (value === passwd.getValue()) ? true : 'Passwords do not match.';
        }
    }, {
    	xtype : 'hidden',
    	name  : 'isExitsId',
    	value : 1
    }, {
    	xtype : 'hidden',
    	name: 'isExitsNick',
    		value : 1
    },

    /*
     * Terms of Use acceptance checkbox. Two things are special about this:
     * 1) The boxLabel contains a HTML link to the Terms of Use page; a special click listener opens this
     *    page in a modal Ext window for convenient viewing, and the Decline and Accept buttons in the window
     *    update the checkbox's state automatically.
     * 2) This checkbox is required, i.e. the form will not be able to be submitted unless the user has
     *    checked the box. Ext does not have this type of validation built in for checkboxes, so we add a
     *    custom getErrors method implementation.
     */
    {
        xtype: 'checkboxfield',
        name: 'acceptTerms',
        fieldLabel: 'Terms of Use',
        hideLabel: true,
        margin: '15 0 0 0',
        boxLabel: 'I have read and accept the <a href="#" class="terms">Terms of Use</a>.',

        // Listener to open the Terms of Use page link in a modal window
        listeners: {
            click: {
                element: 'boxLabelEl',
                fn: function(e) {
                    var target = e.getTarget('.terms'),
                        win;
                    
                    e.preventDefault();
                    
                    if (target) {
                        win = Ext.getCmp('termsWindow') || Ext.widget('window', {
                        	id: 'termsWindow',
                            closeAction: 'hide',
                            title: 'Terms of Use',
                            modal: true,
                            contentEl: Ext.getDom('legalese'),
                            width: 700,
                            height: 400,
                            bodyPadding: '10 20',
                            autoScroll: true,
                            
                            buttons: [{
                                text: 'Decline',
                                handler: function() {
                                    this.up('window').close();
                                    formPanel.down('[name=acceptTerms]').setValue(false);
                                }
                            }, {
                                text: 'Accept',
                                handler: function() {
                                    this.up('window').close();
                                    formPanel.down('[name=acceptTerms]').setValue(true);
                                }
                            }]
                        });
                        win.show();
                    }
                }
            }
        },

        // Custom validation logic - requires the checkbox to be checked
        getErrors: function() {
            return this.getValue() ? [] : ['You must accept the Terms of Use'];
        }
    }],

    dockedItems: [{
        xtype: 'container',
        dock: 'bottom',
        layout: {
            type: 'hbox',
            align: 'middle'
        },
        padding: '10 10 5',

        items: [{
            xtype: 'component',
            id: 'formErrorState',
            baseCls: 'form-error-state',
            flex: 1,
            validText: 'Form is valid',
            invalidText: 'Form has errors',
            tipTpl: Ext.create('Ext.XTemplate', '<ul><tpl for="."><li><span class="field-name">{name}</span>: <span class="error">{error}</span></li></tpl></ul>'),

            getTip: function() {
				if (!tip) {
					tip = this.tip = Ext.widget('tooltip', {
						target: this.el,
						title: 'Error Details:',
						minWidth: 200,
						autoHide: false,
						anchor: 'top',
						mouseOffset: [-11, -2],
						closable: true,
						constrainPosition: false,
						cls: 'errors-tip'
					});
				}
				tip.show();
                return tip;
            },

            setErrors: function(errors) {
                var me = this,
                    baseCls = me.baseCls,
                    tip = me.getTip();

                errors = Ext.Array.from(errors);

                // Update CSS class and tooltip content
                if (errors.length) {
                    me.addCls(baseCls + '-invalid');
                    me.removeCls(baseCls + '-valid');
                    me.update(me.invalidText);
                    tip.setDisabled(false);
                    tip.update(me.tipTpl.apply(errors));
                } else {
                    me.addCls(baseCls + '-valid');
                    me.removeCls(baseCls + '-invalid');
                    me.update(me.validText);
                    tip.setDisabled(true);
                    tip.hide();
                }
            }
        }, {
            xtype: 'button',
            formBind: true,
            disabled: true,
            text: 'Submit Registration',
            width: 140,
            handler: function() {
            	// ID 중복검사를 하지 않은 것.
            	if(Ext.getCmp('joinForm').getForm().findField('isExitsId').getValue() != "0"){
            		Ext.Msg.alert('알림', 'ID 중복확인을 해주세요.');
            		return;
            	}
            	
            	if(Ext.getCmp('joinForm').getForm().findField('isExitsNick').getValue() != "0"){
            		Ext.Msg.alert('알림', '별명 중복확인을 해주세요.');
            		return;
            	}
            	
                var form = this.up('form').getForm();
                UserDwr.addUser(form.getValues(), function(result){
                	if(result){
                		var msg = (result==1)?'등록이 완료 되었습니다.':'실패';
                		Ext.Msg.alert('알림', msg);

						if(result == 1){
							form.getFields().each(function(f) {
								f.reset();
							});
							Ext.getCmp('joinForm').getForm().findField('idCheckField').setValue('<span style="color:red;">이메일 중복 여부를 확인해주세요.</span>');
							Ext.getCmp('joinForm').getForm().findField('nickCheckField').setValue('<span style="color:red;">별명 중복 여부를 확인해주세요.</span>');
							Ext.getCmp('joinForm').getForm().findField('isExitsId').setValue(1);
							Ext.getCmp('joinForm').getForm().findField('isExitsNick').setValue(1);

							joinWindow.close();
						}
                	}
                });
            }
        }]
    }]
});


Ext.define('pms.widget.joinWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.joinWindow',
	title: 'Register',
	closeAction: 'hide',
	width: 300,
	height: 350,
	layout: 'fit',
	resizable: true,
	modal: true,
	items: formPanel,
	listeners: {
		close : {
			fn : function(panel, opts){
				formPanel.getForm().getFields().each(function(f) {
					f.reset();
				});

				if(tip)
					tip.close();
			}
		}
	}
});
