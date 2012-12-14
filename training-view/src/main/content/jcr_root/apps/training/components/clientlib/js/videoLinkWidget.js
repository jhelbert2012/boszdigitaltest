

Ejst.videoLinkWidget = CQ.Ext.extend(CQ.form.CompositeField, {

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    hiddenField: null,

    /**
     * @private
     * @type CQ.Ext.form.ComboBox
     */
    videoTypeField: null,
    
    /**
     * @private
     * @type CQ.Ext.form.Label
     */    
    mediumLabelField: null, 
     
    /**
     * @private
     * @type CQ.form.PathField
     */
    mediumLinkField: null,
    
    /**
     * @private
     * @type CQ.Ext.form.Label
     */    
    largeLabelField: null, 

    /**
     * @private
     * @type CQ.form.PathField
     */
    largeLinkField: null,
    
    constructor: function(config) {
        config = config || { };
        var defaults = {
            "border": false,
            "layout": "table",
            "columns": 6
        };
        config = CQ.Util.applyDefaults(config, defaults);
        Ejst.videoLinkWidget.superclass.constructor.call(this, config);
    },

    // overriding CQ.Ext.Component#initComponent
    initComponent: function() {
        Ejst.videoLinkWidget.superclass.initComponent.call(this);

        this.hiddenField = new CQ.Ext.form.Hidden({
            name: this.name
        });
        this.add(this.hiddenField);
        
        this.videoTypeField = new CQ.form.Selection({
            type:"select",
            listeners: {
                selectionchanged: {
                    scope:this,
                    fn: this.updateHidden
                }
            },
            optionsProvider: this.optionsProvider,
        });
        this.add(this.videoTypeField);
        
        this.mediumLabelField = new CQ.Ext.form.Label({
            text:" Medium: "
        });
        this.add(this.mediumLabelField);

        this.mediumLinkField = new CQ.form.PathField({
            rootPath: "/",
            showTitlesInTree: false,
            allowBlank: false,
            listeners: {
                dialogselect: {
                    scope:this,
                    fn:this.updateHidden
                },
                search: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(this.mediumLinkField);
        
        this.largeLabelField = new CQ.Ext.form.Label({
            text:" Large: "
        });
        this.add(this.largeLabelField);

        this.largeLinkField = new CQ.form.PathField({
            rootPath: "/",
            showTitlesInTree: false,
            allowBlank: false,
            listeners: {
                dialogselect: {
                    scope:this,
                    fn:this.updateHidden
                },
                search: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(this.largeLinkField);

    },

    // overriding CQ.form.CompositeField#processPath
    processPath: function(path) {
        //console.log("videoLinkWidget#processPath", path);
        this.videoTypeField.processPath(path);
    },

    // overriding CQ.form.CompositeField#processRecord
    processRecord: function(record, path) {
        //console.log("videoLinkWidget#processRecord", path, record);
        this.videoTypeField.processRecord(record, path);
    },

    // overriding CQ.form.CompositeField#setValue
    setValue: function(value) {
        var parts = value.split("\t");
        this.videoTypeField.setValue(parts[0]);
        this.mediumLinkField.setValue(parts[1]);
        this.largeLinkField.setValue(parts[2]);
        this.hiddenField.setValue(value);
    },

    // overriding CQ.form.CompositeField#getValue
    getValue: function() {
        return this.getRawValue();
    },

    // overriding CQ.form.CompositeField#getRawValue
    getRawValue: function() {
        if (!this.videoTypeField) {
            return null;
        }
        var videoType = this.videoTypeField.getValue();
        var medium = this.mediumLinkField.getValue();
        var large = this.largeLinkField.getValue();
        return videoType + "\t" + medium + "\t" + large;
    },

    // private
    updateHidden: function() {
        this.hiddenField.setValue(this.getValue());
    }

});

// register xtype
CQ.Ext.reg('videoLinkWidget', Ejst.videoLinkWidget);

