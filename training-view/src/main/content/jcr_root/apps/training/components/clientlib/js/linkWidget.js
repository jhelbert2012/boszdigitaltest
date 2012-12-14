

Ejst.CustomWidget = CQ.Ext.extend(CQ.form.CompositeField, {

    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    hiddenField: null,

    /**
     * @private
     * @type CQ.Ext.form.Label
     */    
    linkLabel: null, 
     
    /**
     * @private
     * @type CQ.Ext.form.TextField
     */
    labelField: null,
    
    /**
     * @private
     * @type CQ.Ext.form.Label
     */    
    pathLabel: null, 

    /**
     * @private
     * @type CQ.form.PathField
     */
    pathField: null,
    
    constructor: function(config) {
        config = config || { };
        var defaults = {
            "border": false,
            "layout": "table",
            "columns":4
        };
        config = CQ.Util.applyDefaults(config, defaults);
        Ejst.CustomWidget.superclass.constructor.call(this, config);
    },

    // overriding CQ.Ext.Component#initComponent
    initComponent: function() {
        Ejst.CustomWidget.superclass.initComponent.call(this);

        this.hiddenField = new CQ.Ext.form.Hidden({
            name: this.name
        });
        this.add(this.hiddenField);
        
        this.linkLabel = new CQ.Ext.form.Label({
            text:"Label: "
        });
        this.add(this.linkLabel);

        this.labelField = new CQ.Ext.form.TextField({
            allowBlank: false,
            listeners: {
                change: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(this.labelField);
        
        this.pathLabel = new CQ.Ext.form.Label({
            text:"Path: "
        });
        this.add(this.pathLabel);

        this.pathField = new CQ.form.PathField({
            rootPath: "/",
            showTitlesInTree: false,
            allowBlank: false,
            listeners: {
                dialogselect: {
                    scope:this,
                    fn:this.updateHidden
                }
            }
        });
        this.add(this.pathField);

    },

    // overriding CQ.form.CompositeField#processPath
    processPath: function(path) {
        console.log("CustomWidget#processPath", path);
        this.labelField.processPath(path);
    },

    // overriding CQ.form.CompositeField#processRecord
    processRecord: function(record, path) {
        console.log("CustomWidget#processRecord", path, record);
        this.labelField.processRecord(record, path);
    },

    // overriding CQ.form.CompositeField#setValue
    setValue: function(value) {
        var parts = value.split("\t");
        this.labelField.setValue(parts[0]);
        this.pathField.setValue(parts[1]);
        this.hiddenField.setValue(value);
    },

    // overriding CQ.form.CompositeField#getValue
    getValue: function() {
        return this.getRawValue();
    },

    // overriding CQ.form.CompositeField#getRawValue
    getRawValue: function() {
        if (!this.labelField) {
            return null;
        }
        var label = this.labelField.getValue();
        var path = this.pathField.getValue();
        return label + "\t" + path;
    },

    // private
    updateHidden: function() {
        this.hiddenField.setValue(this.getValue());
    }

});

// register xtype
CQ.Ext.reg('linkWidget', Ejst.CustomWidget);
